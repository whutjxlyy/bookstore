package cn.whutjxl.bookstore.admin.controllor;

import java.sql.Timestamp;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;

import cn.whutjxl.bookstore.book.model.Book;
import cn.whutjxl.bookstore.book.service.BookService;
import cn.whutjxl.bookstore.user.model.User;
import cn.whutjxl.bookstore.utils.FileUtils;

public class BookController extends Controller {

	public void index() {
		User user = getSessionAttr("user");
		if (user == null) {
			setAttr("msg", "请先登录！");
			render("/WEB-INF/pages/index/login.html");
		} else {
			setAttr("bookPage", BookService.getAllBooks(getParaToInt("page", 1), 25, getPara("condition", "")));
			setAttr("condition", getPara("condition", ""));
			render("list.html");
		}
	}

	public void showDetail() {
		Integer bid = getParaToInt("bid");
		if (bid != null) {
			setAttr("book", BookService.getByBid(bid));
		}
		render("edit.html");
	}

	public void saveBook(){
		String realPath=PathKit.getWebRootPath();
		String filePath=realPath+"\\images\\books\\";
		UploadFile upfile=getFile("book.pic", filePath);
		Book book=getModel(Book.class);
		Integer bid=book.getInt("bid");
		String bname=book.getStr("bname");
		Double price=book.getDouble("price");
		String author=book.getStr("author");
		String content=book.getStr("content");
		Long stock=book.getLong("stock");
		if(bname==null||"".equals(bname)){
			setAttr("msg", "图书名不能为空！");
			render("edit.html");
		}else if(price==null){
			setAttr("msg", "价格不能为空！");
			render("edit.html");
		}else if(author==null||"".equals(author)){
			setAttr("msg", "作者不能为空！");
			render("edit.html");
		}else if(content==null||"".equals(content)){
			setAttr("msg", "简介不能为空！");
			render("edit.html");
		}else if(stock==null){
			setAttr("msg", "库存不能为空！");
			render("edit.html");
		}else{
			if(bid==null){
				Book newBook=new Book();
				newBook.set("bname", bname);
				newBook.set("price", price);
				newBook.set("author", author);
				newBook.set("content", content);
				newBook.set("stock", stock);
				newBook.set("createTime", new Timestamp(System.currentTimeMillis()));
				newBook.set("updateTime", new Timestamp(System.currentTimeMillis()));
				if(upfile!=null){
					newBook.set("pic", upfile.getFileName());
				}
				BookService.saveBook(newBook);
				setAttr("msg", "添加成功");
			}else{
				Book oldBook=BookService.getByBid(bid);
				oldBook.set("bname", bname);
				oldBook.set("price", price);
				oldBook.set("author", author);
				oldBook.set("content", content);
				oldBook.set("stock", stock);
				oldBook.set("updateTime", new Timestamp(System.currentTimeMillis()));
				if(upfile==null){
					oldBook.set("pic", null);
				}else{
					oldBook.set("pic", upfile.getFileName());
				}
				BookService.updateBook(oldBook);
				setAttr("msg", "修改成功！");
			}
			String dPath="E:\\JavaApplication\\bookstore\\bookstore-front\\src\\main\\webapp\\images\\books\\";
			FileUtils.copyfile(filePath, dPath, upfile.getFileName());
			render("edit.html");
		}
	}
}
