package cn.whutjxl.bookstore.user.service;

import java.util.List;

import cn.whutjxl.bookstore.user.model.User;

public class UserService {

	public static User findByUserName(String username){
		return User.me.findFirst("select * from users where username=?",username);
	}
	
	public static void saveUser(User user){
		user.save();
	}
	
	public static User checkLogin(String username,String password){
		return User.me.findFirst("select * from users where username=? and password=?",username,password);
	}
	
	public static List<User> getAllUserByState(Integer state){
		return User.me.find("select * from users where level=? and state=?",0,state);
	}
	
	public static User getUserByUid(Integer uid){
		return User.me.findById(uid);
	}
	
	public static void updateUser(User user){
		user.update();
	}
}
