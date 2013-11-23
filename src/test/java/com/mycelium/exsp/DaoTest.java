package com.mycelium.exsp;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mycelium.exsp.model.dao.JdbcCrudDao;
import com.mycelium.exsp.model.entities.UserEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-config.xml" })
@Transactional
public class DaoTest {

	@Resource(name = "userDao")
	private JdbcCrudDao<UserEntity> _userDao;

	@Test
	public void testUserDao() {
		System.out.println("Select all users");
		List<UserEntity> allArticles = _userDao.findAll();
		System.out.println(allArticles);
	}

}
