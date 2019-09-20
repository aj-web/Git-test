package org.lanqiao.service.impl;

import java.util.List;

import org.lanqiao.entity.Priv;
import org.lanqiao.entity.Role;
import org.lanqiao.mapper.AdminMapper;
import org.lanqiao.mapper.PrivMapper;
import org.lanqiao.mapper.RoleMapper;
import org.lanqiao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	PrivMapper privMapper;
	@Autowired
	AdminMapper adminMapper;
	@Autowired
	RoleMapper roleMapper;
	
	
	public List<Role> getAllRoles() {
		List<Role> lr=roleMapper.searchAdminPower();//只有角色的ID和rname
		for(Role r:lr) {
			List<Priv> lp=privMapper.getPrivByRid(r.getRid());//遍历获取所有权限，方法很经典
			r.setLp(lp);
		}
		return lr;
	}
	public List<Priv> getAllPrivs() {
		List<Priv> lr=privMapper.getAllPriv();
		return lr;
	}
	public boolean addRole(Role role) {
		int id=roleMapper.insertRole(role);
		if(id!=-1) {
			role.setRid(id);			
			roleMapper.insertRolePrivs(role);
		}
		
		return id!=-1?true:false;
	}
	public boolean deleteRole(int rid) {
		int i=roleMapper.deleteRole(rid);
		int j=roleMapper.deleteRolePrivs(rid);
		return (i+j)>=2?true:false;
	}
	public boolean UpdateRoleAndPrivs(Role role) {
		int i=roleMapper.updateRolePrivs(role);  //更新角色的名字
		int j=roleMapper.deleteRolePrivs(role.getRid());  //删除角色的权利
		int k=roleMapper.insertRolePrivs(role);	//为角色赋予权利
		return i+j+k>3?true:false;
	}
	public Role getRoleById(int rid) {
		Role role=roleMapper.findRoleByRid(rid);
		List<Priv> lp=privMapper.getPrivByRid(rid);
		role.setLp(lp);
		return role;	
	}
	public Role getPrivByRole(Role role) {
		List<Priv> lp=privMapper.findPrivByRole(role);
		Role r=new Role();
		r.setLp(lp);
		return r;
	}

}
