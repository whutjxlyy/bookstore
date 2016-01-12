package cn.whutjxl.bookstore.book.model;

import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class Book extends Model<Book> {
	public static final Book me=new Book();
	
	public Page<Book> getPages(int pageNumber, int pageSize,Map<String, String> map) {
		String condition=map.get("condition");
		String category=map.get("category");
		String sql=" from books";
		
		int flag=0;
		if(condition!=null&&!"".equals(condition)){
			if(flag==0){
				sql+=" where bname like \'%"+condition+"%\'";
				flag=1;
			}else{
				sql+=" and bname like \'%"+condition+"%\'";
			}
		}
		
		if(category!=null&&!"".equals(category)){
			if(flag==0){
				sql+=" where category like \'%"+category+"%\'";
				flag=1;
			}else{
				sql+=" and category like \'%"+category+"%\'";
			}
		}
		
		sql+=" order by updateTime desc";
		return paginate(pageNumber, pageSize, "select *", sql);
	}
}
