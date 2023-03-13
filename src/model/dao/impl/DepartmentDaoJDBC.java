package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department department) {
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement(
					"INSERT INTO department "
					+ "(Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, department.getName());
			int rowsAffected = preparedStatement.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				if(resultSet.next()) {
					int id = resultSet.getInt(1);
					department.setId(id);
				}
				DB.closeResultSet(resultSet);
			}else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
		} catch (SQLException sqle) {
			throw new DbException(sqle.getMessage());
		} finally {
			DB.closeStatement(preparedStatement);
		}
	}

	@Override
	public void update(Department department) {
	PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement(
					"UPDATE department "
					+ "SET Name = ? "
					+ "WHERE Id = ?");

			preparedStatement.setString(1, department.getName());
			preparedStatement.setInt(2, department.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			throw new DbException(sqle.getMessage());
		} finally {
			DB.closeStatement(preparedStatement);
		}
	}

	@Override
	public void deleteById(Integer id) {
PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = conn.prepareStatement("DELETE FROM department " + "WHERE Id = ? ");

			preparedStatement.setInt(1, id);

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			throw new DbException(sqle.getMessage());
		} finally {
			DB.closeStatement(preparedStatement);
		}

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = conn.prepareStatement(
					"SELECT * FROM department "
					+ "WHERE Id = ?");

			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Department department = new Department();
				department.setId(resultSet.getInt("Id"));
				department.setName(resultSet.getString("Name"));
				return department;

			}
			return null;

		} catch (SQLException sqle) {
			throw new DbException(sqle.getMessage());
		} finally {
			DB.closeResultSet(resultSet);
			DB.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = conn.prepareStatement(
					"SELECT * FROM department ");

			resultSet = preparedStatement.executeQuery();
			List<Department> list = new ArrayList<>();
			while (resultSet.next()) {
				Department department = new Department();
				department.setId(resultSet.getInt("Id"));
				department.setName(resultSet.getString("Name"));
				list.add(department);
			}
			return list;

		} catch (SQLException sqle) {
			throw new DbException(sqle.getMessage());
		} finally {
			DB.closeResultSet(resultSet);
			DB.closeStatement(preparedStatement);
		}
	}

}
