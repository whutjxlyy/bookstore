package cn.whutjxl.bookstore.admin.controllor;

import com.jfinal.core.Controller;

import cn.whutjxl.bookstore.user.model.User;
import cn.whutjxl.bookstore.user.service.UserService;

public class IndexControllor extends Controller {

	public void index(){
		render("login.html");
	}
	
	public void login(){
		String username=getPara("username");
		String password=getPara("password");
		if(username==null||"".equals(username)){
			setAttr("username", username);
			setAttr("password", password);
			setAttr("msg", "用户名不能为空！");
			render("login.html");
		}else if(password==null||"".equals(password)){
			setAttr("username", username);
			setAttr("password", password);
			setAttr("msg", "密码不能为空！");
			render("login.html");
		}else if(UserService.checkLogin(username, password)==null){
			setAttr("username", username);
			setAttr("password", password);
			setAttr("msg", "用户名或密码错误！");
			render("login.html");
		}else{
			User user=UserService.checkLogin(username, password);
			if(user.getInt("level")==0){
				setAttr("username", username);
				setAttr("password", password);
				setAttr("msg", "您不是管理员，不能登录管理后台！");
				render("login.html");
			}else{
				setSessionAttr("user", user);
				render("index.html");
			}
		}
	}
	
	public void logout(){
		removeSessionAttr("user");
		redirect("/");
	}
}