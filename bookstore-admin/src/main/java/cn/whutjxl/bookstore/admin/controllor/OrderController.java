package cn.whutjxl.bookstore.admin.controllor;

import com.jfinal.core.Controller;

import cn.whutjxl.bookstore.order.model.Order;
import cn.whutjxl.bookstore.order.service.OrderService;
import cn.whutjxl.bookstore.user.model.User;

public class OrderController extends Controller {

	public void index(){
		User user=getSessionAttr("user");
		if(user==null){
			setAttr("msg", "请先登录!");
			render("/WEB-INF/pages/index/login.html");
		}else{
			Integer uid=user.getInt("uid");
			setAttr("orderList1", OrderService.getOrderByState(1));
			setAttr("orderList2", OrderService.getOrderByState(2));
			setAttr("orderList3", OrderService.getOrderByState(3));
			render("orders.html");
		}
	}
	
	public void sendProduct(){
		String oid=getPara("oid");
		Order order=OrderService.getByOid(oid);
		order.set("state", 2);
		OrderService.updateOrder(order);
		redirect("/order");
	}
}
