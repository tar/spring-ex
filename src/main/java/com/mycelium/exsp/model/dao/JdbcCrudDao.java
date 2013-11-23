package com.mycelium.exsp.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.mycelium.exsp.model.entities.Entity;
import com.mycelium.exsp.utils.EntityUtils;

/**
 * This implementation assumes that the table has the column named id that will implicitly be mapped to the entity id
 * field. It also assumes that the value for the id column is generated on insert.
 * 
 * @param <T>
 *            entity class
 */
public class JdbcCrudDao<T extends Entity> extends NamedParameterJdbcDaoSupport implements CrudDao<T> {
	protected String _findOneSql;
	protected String _findAllSql;
	protected String _insertSql;
	protected String _updateSql;
	protected String _deleteSql;
	protected RowMapper<T> _rowMapper;

	protected String _findSql;
	protected String _tableName;
	protected Map<String, String> _fieldMapping;
	protected String _alias;
	protected SqlBuilder _sqlBuilder;

	protected RowMapper<T> getRowMapper() {
		return _rowMapper;
	}

	public void setBeanClass(Class<T> beanClass) {
		setRowMapper(new BeanPropertyRowMapper<T>(beanClass));
	}

	public void setRowMapper(RowMapper<T> rowMapper) {
		_rowMapper = rowMapper;
	}

	public void setFindOneSql(String findOneSql) {
		_findOneSql = findOneSql;
	}

	public void setFindAllSql(String findAllSql) {
		_findAllSql = findAllSql;
	}

	public void setInsertSql(String insertSql) {
		_insertSql = insertSql;
	}

	public void setUpdateSql(String updateSql) {
		_updateSql = updateSql;
	}

	public void setDeleteSql(String deleteSql) {
		_deleteSql = deleteSql;
	}

	public void setTableName(String tableName) {
		_tableName = tableName;
	}

	/**
	 * @param alias
	 *            table alias for sql select query generation. If not specified it will be the first letter of the table
	 *            name
	 */
	public void setAlias(String alias) {
		_alias = alias;
	}

	public String getAlias() {
		return _alias;
	}

	/**
	 * Define mapping between sql name and java name for all fields except id
	 * 
	 * @param fieldMapping
	 *            map: table column name -> class field name
	 */
	public void setFieldMapping(Map<String, String> fieldMapping) {
		_fieldMapping = fieldMapping;
	}

	/**
	 * Sets the query to retrieve entities <b>without</b> the WHERE, ORDER BY or other clauses that come after SELECT or
	 * JOIN Used to construct more specialised queries, such as findAll, findOne etc.
	 * 
	 * @param findSql
	 *            a SELECT query including only FROM and JOIN clauses
	 */
	public void setFindSql(String findSql) {
		_findSql = findSql;
	}

	protected SqlParameterSource makeSqlParameterSource(T t) {
		return new BeanPropertySqlParameterSource(t);
	}

	protected class SqlBuilder {
		public String makeUpdateSql() {
			StringBuilder update = new StringBuilder("update ").append(_tableName).append(" set ");
			boolean first = true;
			for (Map.Entry<String, String> pair : _fieldMapping.entrySet()) {
				if (!first) {
					update.append(", ");
				}
				update.append(pair.getKey()).append(" = :").append(pair.getValue());
				first = false;
			}
			return update.append(" where id = :id").toString();
		}

		public String makeDeleteSql() {
			return "delete from " + _tableName + " where id=?";
		}

		public String makeFindSql() {
			return makeFindSql(_fieldMapping);
		}

		public String makeFindSql(Map<String, String> mapping) {
			String aliasDot = _alias.isEmpty() ? "" : _alias + ".";
			StringBuilder find = new StringBuilder("select ").append(aliasDot).append("id");

			for (Map.Entry<String, String> pair : mapping.entrySet()) {
				find.append(", ");
				String columnName = pair.getKey();
				String columnAlias = pair.getValue();
				find.append(aliasDot).append(columnName).append(" as ").append(columnAlias);
			}
			return find.append(" from ").append(_tableName).append(" ").append(_alias).toString();
		}

		public String makeInsertSql() {
			StringBuilder insert = new StringBuilder("insert into ").append(_tableName).append(" (");
			StringBuilder values = new StringBuilder(") values (");
			boolean first = true;
			for (Map.Entry<String, String> pair : _fieldMapping.entrySet()) {
				if (!first) {
					insert.append(", ");
					values.append(", ");
				}
				insert.append(pair.getKey());
				values.append(':').append(pair.getValue());
				first = false;
			}
			values.append(")");
			return insert.append(values).toString();
		}
	}

	@Override
	protected void initDao() throws Exception {
		super.initDao();
		String className = getClass().getSimpleName();
		if (_tableName == null && getClass() != JdbcCrudDao.class) {
			int index = className.lastIndexOf("Dao");
			if (index > 0)
				_tableName = className.substring(0, index).toLowerCase();
		}
		if (_alias == null) {
			if (_tableName != null)
				_alias = _tableName.substring(0, 1);
			else
				_alias = "";
		}
		if (_fieldMapping != null && _tableName != null) {
			_sqlBuilder = new SqlBuilder();
			if (_insertSql == null)
				setInsertSql(_sqlBuilder.makeInsertSql());
			if (_deleteSql == null)
				setDeleteSql(_sqlBuilder.makeDeleteSql());
			if (_findSql == null)
				setFindSql(_sqlBuilder.makeFindSql());
			if (_updateSql == null)
				setUpdateSql(_sqlBuilder.makeUpdateSql());
		}
		if (_findSql != null) {
			if (_findAllSql == null)
				setFindAllSql(_findSql);
			if (_findOneSql == null)
				setFindOneSql(_findSql + " where id=?");
		}
	}

	/**
	 * 
	 * @param whereSql
	 *            part of sql query starting with the 'join' or 'where' clause
	 * @param args
	 *            query arguments
	 * @return list of entities matching the where clause
	 */
	protected List<T> find(String whereSql, Object... args) {
		return getJdbcTemplate().query(_findSql + " " + whereSql, getRowMapper(), args);
	}

	protected T findOne(String whereSql, Object... args) {
		return queryForObjectOrNull(_findSql + " " + whereSql, getRowMapper(), args);
	}

	protected <O> O queryForObjectOrNull(String sql, RowMapper<O> rowMapper, Object... args) throws DataAccessException {
		List<O> results = getJdbcTemplate().query(sql, args, new RowMapperResultSetExtractor<O>(rowMapper, 2));
		return DataAccessUtils.singleResult(results);
	}

	@Override
	public T findOne(long id) {
		return queryForObjectOrNull(_findOneSql, getRowMapper(), id);
	}

	@Override
	public List<T> findAll() {
		return getJdbcTemplate().query(_findAllSql, getRowMapper());
	}

	public Map<Long, T> findAllAsMap() {
		return EntityUtils.asMap(findAll());
	}

	@Override
	public long insert(T t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(_insertSql, makeSqlParameterSource(t), keyHolder, new String[] { "id" });
		long id = keyHolder.getKey().longValue();
		t.setId(id);
		return id;
	}

	@Override
	public void update(T t) {
		getNamedParameterJdbcTemplate().update(_updateSql, makeSqlParameterSource(t));
	}

	@Override
	public void delete(long id) {
		getJdbcTemplate().update(_deleteSql, id);
	}

	@Override
	public boolean exists(T t) {
		T existing = findOne(t.getId());
		return existing != null && checkEquals(t, existing);
	}

	@Override
	public T save(T t) {
		if (t.getId() == 0) {
			insert(t);
		} else {
			update(t);
		}
		return t;
	}

	protected boolean checkEquals(T first, T second) {
		return first.getId() == second.getId();
	}
}
