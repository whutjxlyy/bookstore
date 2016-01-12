package cn.whutjxl.bookstore.orderitem.service;

import java.util.List;

import cn.whutjxl.bookstore.orderitem.model.OrderItem;

public class OrderItemService {

	public static List<OrderItem> getOrderItemsByOid(String oid){
		return OrderItem.me.find("select * from orderitems where oid=?",oid);
	}
	
	public static OrderItem getOrderItemById(Integer id){
		return OrderItem.me.findById(id);
	}
	
	public static void deleteOrderItem(OrderItem orderItem){
		orderItem.delete();
	}
}
