package easymall.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("indexController")
@RequestMapping("/index")
public class IndexController {
	@RequestMapping("/login")
	public String login() {
		return "login";//跳转到"/WEB-INF/jsp/login.jsp"
	}
	@RequestMapping("/regist")
	public String register() {
		return "regist";
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/index.jsp";//跳转到主页
	}
	@RequestMapping("/ditu")
	public String ditu() {
		return "ditu";
	}
}
