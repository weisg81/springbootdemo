package pers.weisg.spring.boot;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pers.weisg.spring.boot.common.util.GenerateUtil;
import pers.weisg.spring.boot.dao.DeptDAO;
import pers.weisg.spring.boot.entity.Dept;

/** 
 * @Description: TODO(用一句话描述该文件做什么)
 * @author WEISANGNG   
 * @date 2018年5月12日   
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class DaoTest {
	
	@Resource
	private DeptDAO deptDAO;
	
	@Test
	public void testDeptDAO() {
		Dept dept = new Dept();
		dept.setIdDept(GenerateUtil.generateId());
		dept.setDeptName("测试部");
		dept.setDeptDesc("软件测试部");
		deptDAO.insertDept(dept);
	}
	
	@Test
	public void testDeptDAO2() {
		Dept dept = new Dept();
		
		dept = deptDAO.queryDeptById("6919B3DE9B8A419FB09FA9573D3DCAB3");
		System.out.println(dept.getDeptName());
	}
}
