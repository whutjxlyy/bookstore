package cn.whutjxl.bookstore.front.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

import cn.whutjxl.bookstore.book.model.Book;
import cn.whutjxl.bookstore.front.controllor.IndexControllor;
import cn.whutjxl.bookstore.front.controllor.OrderController;
import cn.whutjxl.bookstore.front.controllor.OrderItemControllor;
import cn.whutjxl.bookstore.order.model.Order;
import cn.whutjxl.bookstore.orderitem.model.OrderItem;
import cn.whutjxl.bookstore.user.model.User;

public class MyConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		PropKit.use("c3p0.properties");
		me.setDevMode(PropKit.getBoolean("devMode",false));
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/", IndexControllor.class, "/WEB-INF/pages/index/");
		me.add("/orderitem", OrderItemControllor.class,"/WEB-INF/pages/orderitems/");
		me.add("/order", OrderController.class, "/WEB-INF/pages/orders/");
	}

	@Override
	public void configPlugin(Plugins me) {
		C3p0Plugin c3p0Plugin =new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"));
		me.add(c3p0Plugin);
		
		ActiveRecordPlugin arp=new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		arp.addMapping("books", "bid", Book.class);
		arp.addMapping("orderitems", "id", OrderItem.class);
		arp.addMapping("orders", "oid", Order.class);
		arp.addMapping("users", "uid", User.class);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new SessionInViewInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
	}

}
