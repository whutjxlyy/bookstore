package cn.whutjxl.bookstore.order.service;

import java.util.List;

import cn.whutjxl.bookstore.order.model.Order;

public class OrderService {

	public static List<Order> getOrderByStateUid(Integer uid,Integer state){
		return Order.me.find("select * from orders where uid=? and state=?",uid,state);
	}
	
	public static void deleteOrderByOid(String oid){
		Order.me.deleteById(oid);
	}
	
	public static void updateOrder(Order order){
		order.update();
	}
	
	public static Order getByOid(String oid){
		return Order.me.findById(oid);
	}
	
	public static List<Order> getOrderByState(Integer state){
		return Order.me.find("select * from orders where state=?",state);
	}
}
