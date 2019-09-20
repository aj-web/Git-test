package org.lanqiao.service.impl;

import java.util.List;

import org.lanqiao.entity.Admin;
import org.lanqiao.entity.Priv;
import org.lanqiao.entity.Role;
import org.lanqiao.mapper.AdminMapper;
import org.lanqiao.mapper.PrivMapper;
import org.lanqiao.mapper.RoleMapper;
import org.lanqiao.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private PrivMapper privMapper;
	@Autowired
	private RoleMapper roleMapper;

	public Admin checkUsernameAndPwd(Admin admin) {
		Admin ad=adminMapper.selectAdminByAcnameAndApwd(admin);
		if(ad!=null) {
			List<Priv> lp=privMapper.getPrivByAid(ad.getId());
			List<Role> lr=roleMapper.searchAdminByAid(ad.getId());
			ad.setLp(lp);
			ad.setLr(lr);
		}
		return ad;
	}

	public boolean updatePwdByacname(Admin admin) {
		int i=adminMapper.updatePwdByAcname(admin);
		return (i>0)?true:false;
	}

	public boolean updateAdminInfo111(Admin admin) {
		System.out.println(admin.getId());
		int i=adminMapper.updateAdminInfo(admin);
		return (i>0)?true:false;
	}

	public List<Priv> showAdminBaseInfo(int rid) {
		// TODO Auto-generated method stub
		return null;
	}

	public Admin showAdminPrivs(int rid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Admin> getAllRoles() {
		/**
		 * 查询出一个admin的集合，admin集合里面没有权利，通过获取admin的id参数查出角色的集合，加到admin里面
		 */
		List<Admin> lp=adminMapper.selectAllAdminInfo();
		for(Admin a:lp) {
			List<Role> pp=adminMapper.selectAllAdminRole(a.getId());
			a.setLr(pp);
		}
		return lp;
	}

	public List<Admin> getAllPrivs() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteAdmin(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Priv> selectAllAdminPrivs() {
		List<Priv> lp=adminMapper.selectAllAdminPrivs();
		return lp;
	}

	public List<Role> selectAllAdminRoles(int rid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Role> selectAllAdminRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	public Admin showAdInfo(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean UpdateAdmininfo(Admin admin) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addRole(Admin admin) {
		// TODO Auto-generated method stub
		return false;
	}

	public int setDfPwd(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Admin> findAdmin(int pid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
