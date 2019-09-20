package org.lanqiao.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lanqiao.entity.Admin;
import org.lanqiao.entity.Role;
import org.lanqiao.mapper.RoleMapper;
import org.lanqiao.service.AdminService;
import org.lanqiao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/role")
public class RoleController {
	@Autowired
	private AdminService adminService;

	@Resource
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
		protected String roleListAction(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			List<Role> lr = roleService.getAllRoles();
			request.setAttribute("roleList", lr);
//		  request.getRequestDispatcher("/view/role/role_list.jsp").forward(request,response); 
			return "role/role_list";
		}

		// 删除管理员页面的信息
		@RequestMapping("/role/deleteRoleAction.do")
		protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
			String rid = request.getParameter("rid");
			boolean b = roleService.deleteRole(Integer.parseInt(rid));
			if (b) {
				request.setAttribute("msg", "删除成功");		
			} else {
				request.setAttribute("msg", "删除失败");
			}

				request.getRequestDispatcher("/role/roleListAction.do").forward(request, response);

		}
}
