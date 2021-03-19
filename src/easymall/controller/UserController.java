package easymall.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import easymall.po.Address;
import easymall.po.User;
import easymall.service.AddressService;
import easymall.service.UserService;

@Controller("userController")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private AddressService addressService;
	@RequestMapping("/user/login")
	public String Login(User user,HttpSession session,Model model) {
		User muser=userService.login(user);
		if(muser!=null) {
			session.setAttribute("user",muser);
			
			return "redirect:/index.jsp";
		}else {
			model.addAttribute("messageError", "用户名或密码错误！");
			return "login";
		}
	}
	@RequestMapping(value="/user/checkUser",method=RequestMethod.POST)
	public void check(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException{
		String username=request.getParameter("username");
		if(userService.checkUsername(username)) {
			response.getWriter().print("用户名"+username+"已被注册！");
		}else {
			response.getWriter().print("恭喜您,"+username+"可以使用！");
		}
	}
	@RequestMapping("/user/regist")
	public String regist(User user,String checknum,String valistr,HttpSession session,Model model) {
		
		
		if(user.getUsername()==null||user.getUsername()=="") {
			model.addAttribute("msg", "用户名不能为空！");
			return "regist";
		}
		if(user.getPassword()==null||user.getPassword()=="") {
			model.addAttribute("msg", "密码不能为空！");
			return "regist";
		}
		if(check(checknum, model, session)==false) {
			model.addAttribute("msg", "验证未通过！");
			return "regist";
		}
		if(userService.regist(user)>0) {
			
			model.addAttribute("msg", "注册成功");
			Address address=new Address(user.getId(),"");
			addressService.addAddress(address);
			return "regist";
		}else {
			model.addAttribute("msg", "注册失败");
			return "regist";
		}
	}
	@RequestMapping("/user/email")
	public void email(String emailstr,Model model,HttpSession session) {
		
		try {
			   HtmlEmail email = new HtmlEmail();
			   email.setHostName("smtp.qq.com");
			   email.setCharset("utf-8");
			   email.addTo(emailstr);
			   email.setFrom("846761569@qq.com","Easymall官方");
			   email.setAuthentication("846761569@qq.com", "biwbwmqautvgbeed");
			   email.setSubject("恭喜您注册EasyMall成功！");
			   Random radomRandom=new Random();
			   String resuString="";
			   for(int i=0;i<6;i++) {
				   resuString+=radomRandom.nextInt(10);
			   }
			   System.out.println(resuString);
			   session.setAttribute("check", resuString);
			   email.setMsg("亲爱的用户：\n您的验证码为："+resuString+"\n如果您收到了这封邮件，代表您已经成功注册了EasyMall的账号!"
			     + "现在就返回EasyMall进行验证吧！");
			   email.send();
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
	}
	
	public boolean check(String checktext,Model model,HttpSession session) {
		
		String ch=session.getAttribute("check").toString();
		if(checktext.equals(ch)) {
			model.addAttribute("msg","验证码正确");
			
			
			return true;
		}else {
			
			model.addAttribute("checkmsg", "验证码错误！");
			return false;
		}
		
	}
}
