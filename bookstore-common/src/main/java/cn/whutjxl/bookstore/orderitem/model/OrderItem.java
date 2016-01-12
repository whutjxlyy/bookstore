package cn.whutjxl.bookstore.orderitem.model;

import com.jfinal.plugin.activerecord.Model;

import cn.whutjxl.bookstore.book.model.Book;
import cn.whutjxl.bookstore.book.service.BookService;

public class OrderItem extends Model<OrderItem> {

	public static final OrderItem me=new OrderItem();
	
	public Book getBook(){
		return BookService.getByBid(getInt("bid"));
	}
}
