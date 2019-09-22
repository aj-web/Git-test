package org.lanqiao.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lanqiao.entity.Admin;
import org.lanqiao.entity.Priv;
import org.lanqiao.entity.Role;
import org.lanqiao.entity.Student;
import org.lanqiao.mapper.RoleMapper;
import org.lanqiao.service.AdminService;
import org.lanqiao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("/role")
public class RoleController {
	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleMapper roleMapper;

	// 显示个人信息并修改
	@RequestMapping("/user/updateAdminInfo.do")
	public void updateListUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		System.out.println(id);
		String aname = request.getParameter("aname");
		System.out.println(aname);
		String atel = request.getParameter("atel");
		System.out.println(atel);
		String aemail = request.getParameter("aemail");
		boolean b = adminService.updateAdminInfo111(new Admin(Integer.parseInt(id), aname, atel, aemail));
		// 修改Session
		Admin ad = (Admin) request.getSession().getAttribute("Ad");
		ad.setAname(aname);
		ad.setAtel(atel);
		ad.setAemail(aemail);
		if (b) {
			response.getWriter().print("ok");
		} else {
			response.getWriter().print("false");
		}
	}

	// 显示角色管理页面的信息

	@RequestMapping("/role/roleListAction.do")
//		@ResponseBody
	protected String roleListAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Role> lr = roleService.getAllRoles();
		request.setAttribute("roleList", lr);
//			Map<String, Object> m=new HashMap<String, Object>();
//			m.put("total", 8);
//			m.put("rows", lr);
//		  request.getRequestDispatcher("/view/role/role_list.jsp").forward(request,response); 
//			return m;
		return "role/role_list";

	}

	// 删除管理员页面的信息
	@RequestMapping("/role/deleteRoleAction.do")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String rid = request.getParameter("rid");
		boolean b = roleService.deleteRole(Integer.parseInt(rid));
		if (b) {
			request.setAttribute("msg", "删除成功");
		} else {
			request.setAttribute("msg", "删除失败");
		}

		request.getRequestDispatcher("/role/roleListAction.do").forward(request, response);
	}

	@RequestMapping("/role/TestJSON.do")
	@ResponseBody
	protected Map showJson(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Student> ls = new ArrayList<Student>();
		ls.add(new Student("jack", "jack1", "135544785", "测试"));
		ls.add(new Student("jack", "jack2", "135544785", "测试"));
		ls.add(new Student("jack", "jack13", "135544785", "测试"));
		ls.add(new Student("jack", "jack14", "135544785", "测试"));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("total", "4");
		m.put("rows", ls);
		return m;
	}

	// 修改管理员页面信息

	@RequestMapping("/role/updateRoleAction.do")
	public String showUpdateAdmin(HttpServletRequest request, HttpServletResponse response, String rid)
			throws ServletException, IOException {
		System.out.println(rid);
		Role role = roleService.getRoleById(Integer.parseInt(rid));
		String rname = role.getRname();
		List<Priv> lp = roleService.getAllPrivs();
		Role role1 = roleService.getPrivByRole(role);
		role1.setRname(rname);
		request.setAttribute("role", role1); // 得到该角色的权限
		request.setAttribute("privslist", lp); // 得到所有权限
		return "/role/role_modi";
	}

	// 提交修改的信息

	@RequestMapping("/role/reallyUpdateAction.do") 
	  public void reallyUpdateAdmin(HttpServletRequest request, HttpServletResponse response,String rname,String rid) throws ServletException, IOException {
	  //把前端用户选择的所有权限的ID数组封装在一个权利的list集合中，该集合只存了pid的值 
	  String [] privs=request.getParameterValues("priv"); 
	  List<Priv> lp=new ArrayList<Priv>(); 
	  for(String priv : privs) { 
		  Priv p = new Priv();
		  p.setPid(Integer.parseInt(priv)); 
		  lp.add(p); 
	  } 
	  Role role=new Role();
	  //得到一个对象，对象中有角色的名字和权利 role.setRname(rname);
	  role.setRid(Integer.parseInt(rid)); 
	  role.setLp(lp); boolean
	  b=roleService.UpdateRoleAndPrivs(role); //先更新角色名，再删除所有权利，再插入权利 
	  if(b) {
	  request.setAttribute("msg", "修改成功"); 
	  }else{
		request.setAttribute("msg", "修改失败");
	  }
	  request.getRequestDispatcher("/role/privListAction.do").forward(request,response);
}

// 角色管理的增加功能
	@RequestMapping("") 
  	protected void addUser (HttpServletRequest request,HttpServletResponse response,String rname) throws ServletException, IOException {
	  request.setCharacterEncoding("utf-8"); 
	  String [] privs=request.getParameterValues("priv");
	  //把前端用户选择的所有权限的ID数组封装在一个权利的list集合中，该集合只存了pid的值
	  List<Priv> lp=new ArrayList<Priv>(); 
	  for(String p:privs) { 
		  Priv pr=new Priv();
		  pr.setPid(Integer.parseInt(p)); 
		  lp.add(pr); 
	  } 
	  Role role=new Role();
	  role.setRname(rname); role.setLp(lp); boolean b=roleService.addRole(role);
	  if(b) { 
		  request.setAttribute("msg", "添加成功"); 
	  }else{
		  request.setAttribute("msg", "添加失败"); }
	  	  request.getRequestDispatcher("/role/privListAction.do").forward(request,response);
	  }
}
