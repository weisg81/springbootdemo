package pers.weisg.spring.boot.entity;

import pers.weisg.spring.boot.common.entity.BaseEntity;

/** 
 * @Description: TODO(用一句话描述该文件做什么)
 * @author WEISANGNG   
 * @date 2018年5月12日   
 */
public class Dept extends BaseEntity{

	private static final long serialVersionUID = 4841328708717016509L;
	
	private String idDept;
	private String deptName;
	private String deptDesc;
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptDesc() {
		return deptDesc;
	}
	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}
	public String getIdDept() {
		return idDept;
	}
	public void setIdDept(String idDept) {
		this.idDept = idDept;
	}
}
