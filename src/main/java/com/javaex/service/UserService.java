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
	
	public boolean emailcheck(String email) {
		boolean result;
		if(userdao.getUser(email) == null) { //주소값이 있냐 없냐 즉 new 를 했냐 안했냐를 묻는다. 해당 데이터가 없으면 new를 안해서 주소값이 없음
			result = true;
		}else {
			result = false;
		}
			
		return result;
	}
	
	public void modify(UserVo uservo) {
		
		userdao.updateUser(uservo);
	}
	
}
