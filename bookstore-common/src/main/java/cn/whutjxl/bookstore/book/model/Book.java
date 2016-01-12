package cn.whutjxl.bookstore.book.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class Book extends Model<Book> {
	public static final Book me=new Book();
	
	public Page<Book> paginate(int pageNumber, int pageSize, String bname) {
		String sql="";
		if(bname==null || "".equals(bname)){
			sql="from books order by updateTime desc";
		}else{
			sql="from books where bname like \'%"+bname+"%\' order by updateTime desc";
		}
		return paginate(pageNumber, pageSize, "select *", sql);
	}
}
