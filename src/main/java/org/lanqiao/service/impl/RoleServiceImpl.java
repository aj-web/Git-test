package org.lanqiao.service.impl;

import java.sql.Date;
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
		System.out.println("开始插入角色名称");
		java.sql.Date date=new Date(new java.util.Date().getTime());
		String rname=role.getRname();
		System.out.println(rname);
		int id=roleMapper.insertRole(rname,date);
		System.out.println(id);
		System.out.println("插入角色名称完成");
		if(id!=-1) {
			role.setRid(id);			
			List<Priv> lp=role.getLp();	//插入pid和时间
			for(Priv priv:lp) {
				int pid=priv.getPid();
				int rid=role.getRid();
				int k=roleMapper.insertRolePrivs(rid,pid,date);		//为角色赋予权利插入rid		 
			}
		}
		
		return id!=-1?true:false;
	}
	public boolean deleteRole(int rid) {
		int i=roleMapper.deleteRole(rid);
		int j=roleMapper.deleteRolePrivs(rid);
		return (i+j)>=2?true:false;
	}
	public boolean UpdateRoleAndPrivs(Role role) {
		int k=0;
		int i=roleMapper.updateRoleName(role);  //更新角色的名字
		int j=roleMapper.deleteRolePrivs(role.getRid());  //删除角色的权利
		List<Priv> lp=role.getLp();	//插入pid和时间
		for(Priv priv:lp) {
			int pid=priv.getPid();
			int rid=role.getRid();	
			java.sql.Date date=new Date(new java.util.Date().getTime());
			k=roleMapper.insertRolePrivs(rid,pid,date);		//为角色赋予权利插入rid		 
		}
		
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
