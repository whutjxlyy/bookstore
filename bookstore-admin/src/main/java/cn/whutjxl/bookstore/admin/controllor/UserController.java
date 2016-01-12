package cn.whutjxl.bookstore.admin.controllor;

import java.sql.Timestamp;

import com.jfinal.core.Controller;

import cn.whutjxl.bookstore.user.model.User;
import cn.whutjxl.bookstore.user.service.UserService;

public class UserController extends Controller {

	public void index(){
		User user=getSessionAttr("user");
		if(user==null){
			setAttr("msg", "请先登录!");
			render("/WEB-INF/pages/index/login.html");
		}else{
			setAttr("userList0", UserService.getAllUserByState(1));   //userList0代表正常用户
			setAttr("userList1", UserService.getAllUserByState(0));   //userList1代表锁定用户
			render("userinfo.html");
		}
	}
	
	public void changeState(){
		Integer uid=getParaToInt("uid");
		User user=UserService.getUserByUid(uid);
		Integer state=user.getInt("state");
		if(state==1){
			user.set("state", 0);
		}else{
			user.set("state", 1);
		}
		user.set("updateTime", new Timestamp(System.currentTimeMillis()));
		UserService.updateUser(user);
		redirect("/user");
	}
}
