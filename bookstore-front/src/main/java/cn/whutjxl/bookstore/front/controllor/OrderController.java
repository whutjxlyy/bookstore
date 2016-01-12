package cn.whutjxl.bookstore.front.controllor;

import com.jfinal.core.Controller;

import cn.whutjxl.bookstore.order.model.Order;
import cn.whutjxl.bookstore.order.service.OrderService;
import cn.whutjxl.bookstore.user.model.User;

public class OrderController extends Controller {

	public void index(){
		User user=getSessionAttr("user");
		if(user==null){
			setAttr("msg", "请先登录！");
			render("/WEB-INF/pages/index/login.html");
		}else{
			Integer uid=user.getInt("uid");
			setAttr("orderList1", OrderService.getOrderByStateUid(uid, 1));
			setAttr("orderList2", OrderService.getOrderByStateUid(uid, 2));
			setAttr("orderList3", OrderService.getOrderByStateUid(uid, 3));
			render("orders.html");
		}
	}
	
	public void confirm(){
		String oid=getPara("oid");
		Order order=OrderService.getByOid(oid);
		order.set("state", 3);
		OrderService.updateOrder(order);
		redirect("/order");
	}
}
