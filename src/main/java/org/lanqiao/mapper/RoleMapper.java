package org.lanqiao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.lanqiao.entity.Priv;
import org.lanqiao.entity.Role;
import org.springframework.stereotype.Repository;

public interface RoleMapper {
	/**
	 * 查询管理员的角色,获取ID和名字
	 */
	List<Role> searchAdminByAid(int aid);
	
	/**
	 * 无脑查询管理员的所有权限,不需要参数
	 */
	List<Role> searchAdminPower();
	/**
	 * 角色管理中添加角色
	 * @param role
	 * @returnint 返回值,表示获取添加新记录的生成的id值
	 */
	int insertRole(@Param("rname")String rname,java.sql.Date date);
	/**
	 * 角色管理中的添加角色赋予权限
	 * @return 
	 */
	int insertRolePrivs(int rid,int pid,java.sql.Date date);
	/**
	 * 删除角色
	 * @param role
	 * @return
	 */
	int deleteRole(int rid);
	/**
	 * 删除角色的权利
	 * @param role
	 * @return
	 */
	int deleteRolePrivs(int rid);
	/**
	 * 更新角色的姓名
	 * @param rid
	 * @return
	 */
	int updateRoleName(Role role);
	/**
	 * 角色管理修改功能，用rid获取当前选中用户
	 * @param rid
	 * @return
	 */
	Role findRoleByRid(int rid);
}
