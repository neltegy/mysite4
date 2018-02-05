package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userdao;
	
	public UserVo login(String email,String password) {
		
		return userdao.getUser(email, password);
	}
	
	public void join(UserVo uservo) {
		
		userdao.insert(uservo);
	}
	
	public UserVo bringinfo(int no) {
		
		return userdao.getUserByNo(no);
	}
	
	public void modify(UserVo uservo) {
		
		userdao.updateUser(uservo);
	}
	
}
