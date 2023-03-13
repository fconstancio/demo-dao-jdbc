package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {
	
	public static void main(String[] args) {
		
		
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("=== Test 1: department findById ===");
		Department department = departmentDao.findById(1);
		System.out.println(department);
		
		System.out.println("\n=== Test 2: department findAll ===");
		List<Department> list = departmentDao.findAll();
		list.forEach(System.out::println);
		
		System.out.println("\n=== Test 3: department insert ===");
		Department newDepartment = new Department(null, "Food");
		departmentDao.insert(newDepartment);
		System.out.println();
		System.out.println("Inserted! New id = " + newDepartment.getId());
		
		System.out.println("\n=== Test 4: department update ===");
		department = departmentDao.findById(9);
		department.setName("RH");
		departmentDao.update(department);
		System.out.println("Update completed!");
		
		System.out.println("\n=== Test 5: department delete ===");
		departmentDao.deleteById(10);
		System.out.println("Deleted completed!");
		
	}

}
