package cn.whutjxl.bookstore.order.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

import cn.whutjxl.bookstore.orderitem.model.OrderItem;
import cn.whutjxl.bookstore.orderitem.service.OrderItemService;

public class Order extends Model<Order> {

	public static final Order me=new Order();
	
	public List<OrderItem> getAllItems(){
		return OrderItemService.getOrderItemsByOid(getStr("oid"));
	}
}
