package pers.weisg.spring.boot.dao;

import pers.weisg.spring.boot.entity.Dept;

/** 
 * @Description: 部门DAO
 * @author WEISANGNG   
 * @date 2018年5月12日   
 */
public interface DeptDAO {
	
	public Dept queryDeptById(String idDept);
	
	public void insertDept(Dept dept);
}
