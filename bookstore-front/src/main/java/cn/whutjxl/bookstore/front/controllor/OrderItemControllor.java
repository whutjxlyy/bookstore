package cn.whutjxl.bookstore.front.controllor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;

import cn.whutjxl.bookstore.book.model.Book;
import cn.whutjxl.bookstore.book.service.BookService;
import cn.whutjxl.bookstore.order.model.Order;
import cn.whutjxl.bookstore.order.service.OrderService;
import cn.whutjxl.bookstore.orderitem.model.OrderItem;
import cn.whutjxl.bookstore.orderitem.service.OrderItemService;
import cn.whutjxl.bookstore.user.model.User;

public class OrderItemControllor extends Controller {
	
	public void index(){
		User user=getSessionAttr("user");
		if(user==null){
			setAttr("msg", "请先登录！");
			render("/WEB-INF/pages/index/login.html");
		}else{
			List<OrderItem> orderItemList=null;
			List<Order> orderList=OrderService.getOrderByStateUid(user.getInt("uid"), 0);
			if(orderList==null||orderList.size()==0){
				setAttr("orderItemList", orderItemList);
				render("orderitems.html");
			}else{
				String oid=orderList.get(0).getStr("oid");
				orderItemList=OrderItemService.getOrderItemsByOid(oid);
				setAttr("orderItemList", orderItemList);
				render("orderitems.html");
			}
		}
	}
	
	public void deleteOrderItem(){
		Integer id=getParaToInt("id");
		OrderItem orderItem=OrderItemService.getOrderItemById(id);
		String oid=orderItem.getStr("oid");
		OrderItemService.deleteOrderItem(orderItem);
		List<OrderItem> list=OrderItemService.getOrderItemsByOid(oid);
		if(list==null||list.size()==0){
			OrderService.deleteOrderByOid(oid);
		}
		redirect("/orderitem");
	}
	
	public void submitOrder(){
		User user=getSessionAttr("user");
		if(user==null){
			setAttr("msg", "请先登录！");
			render("/WEB-INF/pages/index/login.html");
		}else{
			List<Order> orderList=OrderService.getOrderByStateUid(user.getInt("uid"), 0);
			if(orderList==null||orderList.size()==0){
				redirect("/orderitem");
			}else{
				String oid=orderList.get(0).getStr("oid");
				List<OrderItem> list=OrderItemService.getOrderItemsByOid(oid);
				List<String> msgList=new ArrayList<String>();
				for (OrderItem oi : list) {
					Integer count=oi.getInt("count");
					Long stock=BookService.getByBid(oi.getInt("bid")).getLong("stock");
					if(count>stock){
						msgList.add(BookService.getByBid(oi.getInt("bid")).getStr("bname"));
					}
				}
				if(msgList.size()>0){
					setSessionAttr("msg", msgList.toString()+" 库存不足，请重新选定库存！");
					redirect("/orderitem");
				}else{
					render("submit.html");
				}
			}
		}
	}
	
	public void toOrder(){
		String uname=getPara("uname");
		String tel=getPara("tel");
		String addr=getPara("addr");
		
		if(uname==null||"".equals(uname)){
			setAttr("uname", uname);
			setAttr("tel", tel);
			setAttr("addr", addr);
			setAttr("msg", "收货人不能为空！");
			render("submit.html");
		}else if(tel==null||"".equals(tel)){
			setAttr("uname", uname);
			setAttr("tel", tel);
			setAttr("addr", addr);
			setAttr("msg", "联系电话不能为空！");
			render("submit.html");
		}else if(addr==null||"".equals(addr)){
			setAttr("uname", uname);
			setAttr("tel", tel);
			setAttr("addr", addr);
			setAttr("msg", "收货地址不能为空！");
			render("submit.html");
		}else{
			User user=getSessionAttr("user");
			if(user==null){
				setAttr("msg", "请先登录！");
				render("/WEB-INF/pages/index/login.html");
			}else{
				List<Order> orderList=OrderService.getOrderByStateUid(user.getInt("uid"), 0);
				if(orderList==null||orderList.size()==0){
					redirect("/orderitem");
				}else{
					Order order=orderList.get(0);
					List<OrderItem> list=OrderItemService.getOrderItemsByOid(order.getStr("oid"));
					double total=0.0;
					for (OrderItem oi : list) {
						Book book=BookService.getByBid(oi.getInt("bid"));
						Long stock=book.getLong("stock");
						book.set("stock", stock-oi.getInt("count"));
						BookService.updateBook(book);
						total=total+oi.getDouble("subTotal");
					}
					order.set("total", total);
					order.set("state", 1);
					order.set("uname", uname);
					order.set("addr", addr);
					order.set("tel", tel);
					order.set("createTime", new Timestamp(System.currentTimeMillis()));
					OrderService.updateOrder(order);
					redirect("/order");
				}
			}
		}
	}

}
