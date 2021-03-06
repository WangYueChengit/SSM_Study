package easymall.controller;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.sun.org.apache.bcel.internal.generic.NEW;

import easymall.po.Address;
import easymall.po.OrderItem;
import easymall.po.Orders;
import easymall.po.Products;
import easymall.po.User;
import easymall.pojo.MyCart;
import easymall.pojo.OrderInfo;
import easymall.service.AddressService;
import easymall.service.CartService;
import easymall.service.OrderService;
import easymall.service.ProductsService;
@Controller("orderController")
@RequestMapping("/order")
public class OrderController extends BaseController{
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private AddressService addressService;
	@RequestMapping("/order_add")
	public String order_add(String cartIds,Model model,HttpSession session) {
		//1.将购物车中所有选中商品的cartID组合起来的字符串拆分为数组
		String[] arrCartIds=cartIds.split(",");
		
		List<MyCart> carts=new ArrayList<MyCart>();
		//2.遍历数组，根据cartID编号查找购物车，添加到carts中
		for(String cid:arrCartIds) {
			Integer cartID=Integer.parseInt(cid);
			MyCart cart=cartService.findByCartID(cartID);
			carts.add(cart);
		}
		User user=(User)session.getAttribute("user");
		Integer uidInteger=user.getId();
		Address address=addressService.findAddress(uidInteger);
		String add=address.getAddress();
		model.addAttribute("carts",carts);
		model.addAttribute("cartIds",cartIds);
		model.addAttribute("add", add);
		return("order_add");
	}
	@RequestMapping("/addOrder")
	public String addOrder(String receiverinfo,String cartIds,
			HttpSession session) {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=df.format(new Date());
		Timestamp timeStamp=Timestamp.valueOf(time);
		User user=(User)session.getAttribute("user");
		String orderId=UUID.randomUUID().toString();
		Orders myOrder=new Orders(orderId,null,receiverinfo,0,timeStamp,user.getId());//paystate默认是0，表示未支付
		orderService.addOrder(cartIds, myOrder);
		Address address=new Address(user.getId(),receiverinfo);
		addressService.updateAddress(address);
		return "forward:/order/showorder";
	}
	@RequestMapping("/showorder")
	public String showorder(HttpSession session,Model model) {
		//1.获取当前登录用户
		User user=(User)session.getAttribute("user");
		//2.根据用户id查询该用户的所有订单信息，查询orders表
		List<OrderInfo> orderInfoList=findOrderInfoByUserId(user.getId());
		//3.将该用户的所有订单信息的list集合存入request狱中，转发order_list.jsp中显示
		model.addAttribute("orderInfos", orderInfoList);
		return "order_list";
	}
	
	public List<OrderInfo> findOrderInfoByUserId(Integer userId){
		List<OrderInfo> orderInfoList=new ArrayList<OrderInfo>();
		//1.根据用户id查询该用户的所有订单信息，查询orders表
		List<Orders> orderList=orderService.findOrderByUserId(userId);
		//2.遍历每一个订单，通过订单id查询当前订单中包含的所有订单信息
		for(Orders order:orderList) {
			//根据用户order_id查询该订单中包含的所有订单单项信息
			List<OrderItem> orderitems=orderService.orderitem(order.getId());
			//3.便利每一个订单项，通过订单项获取商品信息级商品的购买数量
			Map<Products, Integer> map=new HashMap<Products, Integer>();
			for(OrderItem orderItem:orderitems) {
				//3.1获取商品id,通过商品id查询商品信息，返回Product对象
				Products product=productsService.oneProduct(orderItem.getProduct_id());
				//3.2将商品信息和购买数量存入map中
				map.put(product, orderItem.getBuynum());
			}
			//4.将订单信息和所有的订单项信息存入OrderInfo中
			OrderInfo orderInfo=new OrderInfo();
			orderInfo.setOrder(order);
			orderInfo.setMap(map);
			//5.将一个完整的订单信息存入List集合中
			orderInfoList.add(orderInfo);
		}
		return orderInfoList;
	}
	@RequestMapping("/delorder")
	public String delorder(String id,Model model) {
		orderService.delorder(id);
		return "redirect:/order/showorder";
	}
	@RequestMapping("/payorder")
	public String payorder(String id,Model model) {
		orderService.payorder(id);
		return "redirect:/order/showorder";
	}
}
