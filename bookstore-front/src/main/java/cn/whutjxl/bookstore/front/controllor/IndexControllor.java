package cn.whutjxl.bookstore.front.controllor;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.jfinal.core.Controller;
import cn.whutjxl.bookstore.book.service.BookService;
import cn.whutjxl.bookstore.order.model.Order;
import cn.whutjxl.bookstore.order.service.OrderService;
import cn.whutjxl.bookstore.orderitem.model.OrderItem;
import cn.whutjxl.bookstore.user.model.User;
import cn.whutjxl.bookstore.user.service.UserService;

public class IndexControllor extends Controller {

	public void index(){
		Map<String, String> map=new HashMap<String, String>();
		map.put("condition", getPara("condition", ""));
		map.put("category", getPara("category", ""));
		setAttr("bookPage", BookService.getAllBooks(getParaToInt("page", 1), 25, map)); 
		setAttr("condition",  getPara("condition", ""));
		setAttr("category",  getPara("category", ""));
		render("index.html");
	}
	
	public void toRegist(){
		render("regist.html");
	}
	
	public void regist(){
		User user=getModel(User.class);
		String password1=getPara("password1");
		String password2=getPara("password2");
		if(user.getStr("username")==null || "".equals(user.getStr("username"))){
			setAttr("user", user);
			setAttr("password1", password1);
			setAttr("password2",password2 );
			setAttr("msg", "用户名不能为空！");
			render("regist.html");
		}else if(UserService.findByUserName(user.getStr("username"))!=null){
			setAttr("user", user);
			setAttr("password1", password1);
			setAttr("password2",password2 );
			setAttr("msg", "用户名已经被注册！");
			render("regist.html");
		}else if(password1==null || "".equals(password1) || password2==null || "".equals(password2)){
			setAttr("user", user);
			setAttr("password1", password1);
			setAttr("password2",password2 );
			setAttr("msg", "密码不能为空！");
			render("regist.html");
		}else if(!password1.equals(password2)){
			setAttr("user", user);
			setAttr("password1", password1);
			setAttr("password2",password2 );
			setAttr("msg", "两次输入密码不一样！");
			render("regist.html");
		}else if(user.getStr("tel")==null || "".equals(user.getStr("tel"))){
			setAttr("user", user);
			setAttr("password1", password1);
			setAttr("password2",password2 );
			setAttr("msg", "手机号不能为空！");
			render("regist.html");
		}else if(user.getStr("email")==null || "".equals(user.getStr("email"))){
			setAttr("user", user);
			setAttr("password1", password1);
			setAttr("password2",password2 );
			setAttr("msg", "邮箱不能为空！");
			render("regist.html");
		}else if(user.getStr("addr")==null || "".equals(user.getStr("addr"))){
			setAttr("user", user);
			setAttr("password1", password1);
			setAttr("password2",password2 );
			setAttr("msg", "地址不能为空！");
			render("regist.html");
		}else{
			user.set("password", password1);
			user.set("level", 0);
			user.set("state", 1);
			user.set("createTime", new Timestamp(System.currentTimeMillis()));
			user.set("updateTime", new Timestamp(System.currentTimeMillis()));
			UserService.saveUser(user);
			setSessionAttr("user", user);
			redirect("/");
		}
	}
	
	public void logout(){
		removeSessionAttr("user");
		redirect("/");
	}
	
	public void toLogin(){
		render("login.html");
	}
	
	public void login(){
		String  username=getPara("username");
		String password=getPara("password");
		if(username==null || "".equals(username)){
			setAttr("username", username);
			setAttr("password", password);
			setAttr("msg", "用户名不能为空！");
			render("login.html");
		}else if(password==null || "".equals(password)){
			setAttr("username", username);
			setAttr("password", password);
			setAttr("msg", "密码不能为空！");
			render("login.html");
		}else if(UserService.checkLogin(username, password)==null){
			setAttr("username", username);
			setAttr("password", password);
			setAttr("msg", "用户名或者密码错误！");
			render("login.html");
		}else if(UserService.checkLogin(username, password).getInt("state")==0){
			setAttr("username", username);
			setAttr("password", password);
			setAttr("msg", "账号已被冻结，请联系管理员解决！");
			render("login.html");
		}else{
			User user=UserService.checkLogin(username, password);
			setSessionAttr("user", user);
			redirect("/");
		}
		
	}
	
	public void showDetail(){
		Integer bid=getParaToInt("bid");
		setAttr("book", BookService.getByBid(bid));
		render("detail.html");
	}
	
	public void addBooks(){
		Integer bid=getParaToInt("bid");
		Integer count=getParaToInt("count");
		User user=getSessionAttr("user");
		if(user==null){
			setAttr("msg", "请先登陆！");
			render("login.html");
		}else{
			List<Order> list=OrderService.getOrderByStateUid(user.getInt("uid"), 0);
			Order order=null;
			if(list==null||list.size()==0){
				order=new Order();
				order.set("oid", String.valueOf(System.currentTimeMillis())+new Random().nextInt(1000));
				order.set("state", 0);
				order.set("uid", user.getInt("uid"));
				order.save();
			}else{
				order=list.get(0);
			}
			String oid=order.getStr("oid");
			OrderItem orderItem=new OrderItem();
			orderItem.set("bid", bid);
			orderItem.set("oid", oid);
			orderItem.set("count", count);
			orderItem.set("subTotal", BookService.getByBid(bid).getDouble("price")*count);
			orderItem.set("createTime", new Timestamp(System.currentTimeMillis()));
			orderItem.save();
			redirect("/orderitem");
		}
	}
}
