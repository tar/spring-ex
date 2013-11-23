package com.mycelium.exsp.model.dao;

import java.util.List;

import com.mycelium.exsp.model.entities.Entity;

public interface CrudDao<T extends Entity> {
	/**
	 * Retrieves an entity by its id.
	 * 
	 * @param id
	 *            key
	 * @return the entity with the given id or {@literal null} if none found
	 */
	T findOne(long id);

	/**
	 * Persists a given entity assuming it's new and sets the primary key (id) to the new value. Note that the passed
	 * entity id will be ignored. It will not be used as a primary key to insert and will be overwritten.
	 * 
	 * @param t
	 *            an entity to insert
	 * @return generated id of inserted entity
	 */
	long insert(T t);

	/**
	 * Persists entity assuming it already exists in the database.
	 * 
	 * @param t
	 *            entity with id &gt; 0
	 */
	void update(T t);

	/**
	 * Insert or update entity depending on the id. Insert if id&lt;=0, update otherwise.
	 * 
	 * @param t
	 *            entity
	 * @return t
	 */
	T save(T t);

	/**
	 * Deletes the entity with the given {@code id}. No cascading. No error if nothing is deleted.
	 * 
	 * @param id
	 *            primary key
	 */
	void delete(long id);

	/**
	 * Returns all instances of the type.
	 * 
	 * @return all entities
	 */
	List<T> findAll();

	/**
	 * Verifies that the given entity exists in the database. Implementers should take care of entity comparison.
	 * 
	 * @param t
	 *            entity to look for
	 * @return true if an entity was found in the database by id and it is equal to provided one
	 */
	boolean exists(T t);
}
