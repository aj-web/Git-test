package org.lanqiao.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.lanqiao.entity.Admin;
import org.lanqiao.entity.Role;
import org.lanqiao.mapper.PrivMapper;
import org.lanqiao.mapper.RoleMapper;
import org.lanqiao.service.AdminService;
import org.lanqiao.service.PrivService;
import org.lanqiao.service.RoleService;
import org.lanqiao.service.impl.AdminServiceImpl;
import org.lanqiao.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

@Controller("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@Resource
	private RoleService roleService;
	
	@Autowired 
	private RoleMapper roleMapper;
	 

	/*
	 * String path=request.getServletContext().getContextPath();
	 */
	/*
	 * @RequestMapping("login") public String login() { return "role/login.jsp";
	 * //当login页面放在WEB-INF下面时 }
	 */
	/**
	 * 访问主页页面
	 * 
	 * @return
	 */
	@RequestMapping("/index.html")
	public String login1() {
		return "index"; // 当跳转的页面页面放在WEB-INF下面时
	}
	
	/**
	 * 访问角色管理
	 * 
	 * @return
	 */
	@RequestMapping("/role/role_list.html")
	public String login2() {
		return "/role/role_list"; // 当跳转的页面页面放在WEB-INF下面时
	}
	
	/**
	 * 访问管理员页面
	 * 
	 * @return
	 */
	@RequestMapping("/admin/admin_list.html")
	public String login3() {
		return "admin/admin_list"; // 当跳转的页面页面放在WEB-INF下面时
	}

	
	/**
	 * 访问资费管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/fee/fee_list.html")
	public String login4() {
		return "/fee/fee_list"; // 当跳转的页面页面放在WEB-INF下面时
	}

	
	/**
	 * 测试json
	 * @return
	 */
	@RequestMapping("/testjson")
	public String test() {
		return "role/json";
	}
	/**
	 * 访问账务账号页面
	 * 
	 * @return
	 */
	@RequestMapping("/account/account_list.html")
	public String login5() {
		return "admin/admin_list"; // 当跳转的页面页面放在WEB-INF下面时
	}

	/**
	 * 访问业务管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/service/service_list.html")
	public String login6() {
		return "service/service_list"; // 当跳转的页面页面放在WEB-INF下面时
	}

	/**
	 * 访问账单管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/bill/bill_list.html")
	public String login7() {
		return "bill/bill_list"; // 当跳转的页面页面放在WEB-INF下面时
	}

	/**
	 * 访问报表页面
	 * 
	 * @return
	 */
	@RequestMapping("/report/report_list.html")
	public String login8() {
		return "report/report_list"; // 当跳转的页面页面放在WEB-INF下面时
	}

	/**
	 * 访问个人信息页面
	 * 
	 * @return
	 */
	@RequestMapping("/user/user_info.html")
	public String login9() {
		return "user/user_info"; // 当跳转的页面页面放在WEB-INF下面时
	}

	/**
	 * 访问修改密码页面
	 * 
	 * @return
	 */
	@RequestMapping("/user/user_modi_pwd.html")
	public String login10() {
		return "user/user_modi_pwd"; // 当跳转的页面页面放在WEB-INF下面时
	}

	// 登录
	@RequestMapping("/login")
	public String login(Admin admin, String valicode, HttpServletRequest request, HttpSession session) {

		Logger logger = Logger.getLogger("log4j.properties");
		String path = request.getServletContext().getContextPath();
		Admin admin1 = adminService.checkUsernameAndPwd(
				new Admin(admin.getAcname(), Hashing.md5().hashString(admin.getApwd(), Charsets.UTF_8).toString()));
		if (admin1 == null) {
			logger.info("登录失败");
			request.getSession().setAttribute("erroMsg", "用户名或密码错误");
			return "redirect:login.jsp";
		} else {
			logger.info("登录成功");
			session.setAttribute("Ad", admin1);
//				request.getSession().setAttribute("Ad",admin1);
			return "index";
		}
	}

	@RequestMapping("exitAction.do")
	public String exit(HttpSession session, HttpServletRequest request) {
		request.getSession().invalidate();
		String path = request.getServletContext().getContextPath();
		return "redirect:login.jsp";
	}

	// 改密码
	@RequestMapping("updateAction.do")
	public String UpdateUserInfo(String newpwd, String oldpwd, String renewpwd, HttpServletRequest request,HttpServletResponse response, HttpSession session) throws Exception {
		Admin admin = (Admin) session.getAttribute("Ad");
		String oldpwd1 = Hashing.md5().hashString(oldpwd, Charsets.UTF_8).toString();
		String newpwd1 = Hashing.md5().hashString(newpwd, Charsets.UTF_8).toString();
		if (oldpwd1.equals(admin.getApwd())) {
			admin.setApwd(newpwd1);
			boolean b = adminService.updatePwdByacname(admin);
			if (b = true) {
				return "redirect:login.jsp";
			} else {
				request.setAttribute("errorPwMsg", "对不起修改失败");
				return "user/user_modi_pwd";
			}
		} else {
			request.setAttribute("errorPwMsg", "旧密码错误，请重新输入");
			return "user/user_modi_pwd";
//			request.getRequestDispatcher("/view/user/user_modi_pwd.jsp").forward(request, response);	
		}
	}

	//管理员增加管理员
	
	  public void addAdmin(HttpServletRequest request, HttpServletResponse response,String aname,String acname,String apwd,String reapwd,String aemail,String atel) throws ServletException, IOException { 
		  //头像 userimg	  
	  String [] roles=request.getParameterValues("role"); 
	  List<Role> lr=new ArrayList<Role>(); 
	  for(String priv : roles) { 
		  Role r = new Role();
	  r.setRid(Integer.parseInt(priv)); 
	  lr.add(r); 
	  } 
	  Admin admin=new Admin();
	  admin.setAcname(acname); 
	  admin.setAname(aname); 
	  admin.setAtel(atel);
	  admin.setAemail(aemail); 
	  admin.setApwd(apwd); 
	  admin.setLr(lr); 
	  boolean b=adminService.addRole(admin); 
	  if(b) { 
		  request.setAttribute("msg","添加成功"); 
		  }else{ 
			  request.setAttribute("msg", "添加失败"); 
			  }
	  request.getRequestDispatcher("/admin/modiShowAction.do").forward(request,response); 
	  }	 
}

