package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	Connection conn;
	private List<TodoItem> list;

	public TodoList() {
		this.conn = DbConnect.getConnection();
		this.list = new ArrayList<TodoItem>();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date, importance)"
				+ " values (?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getImportance());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(String index) {
		String sql = "delete from list where id in (" + index + ");";
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?, importance=?"
				+ " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getImportance());
			pstmt.setInt(7, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int completeItem(String index) {
		String sql = "update list set is_completed = 1 where id in (" + index + ");";
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int notCompleteItem(String index) {
		String sql = "update list set is_completed = 0 where id in (" + index + ");";
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteComplete() {
		String sql = "delete from list where is_completed = 1;";
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int changeAchievement(int index, int percent) {
		String sql = "update list set percent = ? where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, percent);
			pstmt.setInt(2, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int changeImportance(int index, int importance) {
		String sql = "update list set importance = ? where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, importance);
			pstmt.setInt(2, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				int percent = rs.getInt("percent");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, importance, percent);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getWeekList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				int percent = rs.getInt("percent");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, importance, percent);
				t.setId(id);
				t.setCurrent_date(current_date);
				SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
				try {
					Date c_date = f.parse(f.format(new Date()));
					Date d_date = f.parse(due_date);
					long subDate = d_date.getTime() - c_date.getTime();
					long calDate = subDate / (24 * 60 * 60 * 1000);
					if (calDate <= 7 && calDate > -1)
						list.add(t);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getDayList(int day) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				int percent = rs.getInt("percent");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, importance, percent);
				t.setId(id);
				t.setCurrent_date(current_date);
				SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
				try {
					Date c_date = f.parse(f.format(new Date()));
					Date d_date = f.parse(due_date);
					long subDate = d_date.getTime() - c_date.getTime();
					long calDate = subDate / (24 * 60 * 60 * 1000);
					if (calDate <= day && calDate > -1)
						list.add(t);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				int percent = rs.getInt("percent");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, importance, percent);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getList(int comp) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE is_completed = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comp);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				int percent = rs.getInt("percent");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, importance, percent);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getListCategory(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				int percent = rs.getInt("percent");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, importance, percent);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if (ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int importance = rs.getInt("importance");
				int percent = rs.getInt("percent");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, importance, percent);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public boolean isDuplicate(String title) {
		PreparedStatement pstmt;
		int count = 0;
		try {
			String sql = "SELECT count(*) FROM list WHERE title = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			count = rs.getInt("count(*)");
			pstmt.close();
			if (count == 0)
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public TodoItem getItem(int index) {
		return list.get(index);
	}
}