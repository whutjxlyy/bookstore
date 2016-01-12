package cn.whutjxl.bookstore.book.service;

import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import cn.whutjxl.bookstore.book.model.Book;

public class BookService {
	
	public static Page<Book> getAllBooks(int pageNumber, int pageSize, Map<String, String> map){
		return Book.me.getPages(pageNumber, pageSize, map);
	}

	public static Book getByBid(Integer bid){
		return Book.me.findById(bid);
	}
	
	public static void updateBook(Book book){
		book.update();
	}
	
	public static void saveBook(Book book){
		book.save();
	}
}
