package com.javaex.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlsession;
	
	public UserVo getUser(String email,String password) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("password", password);
		
		return sqlsession.selectOne("user.selectUserByEmailPW", map);
	}
	
	public void insert(UserVo uservo) {
		
		sqlsession.insert("user.insertUser", uservo);
	}
	
	public UserVo getUserByNo(int no) {
		
		return sqlsession.selectOne("user.selectUserByNo", no);
	}
	
	public void updateUser(UserVo uservo) {
		
		sqlsession.insert("user.insertUserByNamePasswordGenderNo", uservo);
	}
	
}
