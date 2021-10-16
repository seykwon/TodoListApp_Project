package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {

	public static void createItem(TodoList l) {

		String title, category, desc, due_date;
		int importance;
		Scanner sc = new Scanner(System.in);

		System.out.print("[�׸� �߰�]\n" + "ī�װ� > ");
		category = sc.next();

		sc.nextLine();
		System.out.print("���� > ");
		title = sc.nextLine().trim();

		if (l.isDuplicate(title)) {
			System.out.println("������ �ߺ��˴ϴ�!");
			return;
		}

		System.out.print("���� > ");
		desc = sc.nextLine().trim();

		System.out.print("��������(yyyy/mm/dd) > ");
		due_date = sc.nextLine().trim();
		
		System.out.print("�߿䵵 (1~5) > ");
		importance = sc.nextInt();

		TodoItem t = new TodoItem(category, title, desc, due_date, importance);

		if (l.addItem(t) > 0)
			System.out.println("�߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {

		Scanner sc = new Scanner(System.in);

		System.out.print("[�׸� ����]\n" + "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		String i = sc.nextLine().trim();

		if(l.deleteItem(i) > 0)
			System.out.println("�����Ǿ����ϴ�.");
		else
			System.out.println("�ùٸ� ��ȣ�� �Է����ּ���.");
	}

	public static void updateItem(TodoList l) {

		String new_category, new_title, new_description, new_due_date;
		int new_importance;
		Scanner sc = new Scanner(System.in);

		System.out.print("[�׸� ����]\n" + "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int i = sc.nextInt();

		System.out.print("�� ī�װ� > ");
		new_category = sc.next();

		sc.nextLine();
		System.out.print("�� ���� > ");
		new_title = sc.nextLine().trim();

		System.out.print("�� ���� > ");
		new_description = sc.nextLine().trim();

		System.out.print("�� ��������(yyyy/mm/dd) > ");
		new_due_date = sc.nextLine().trim();
		
		System.out.print("�� �߿䵵 (1~5) > ");
		new_importance = sc.nextInt();

		TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date, new_importance);
		t.setId(i);
		if (l.updateItem(t) > 0)
			System.out.println("�����Ǿ����ϴ�.");
	}

	public static void completeItem(TodoList l, String index) {
		if(l.completeItem(index) > 0)
			System.out.println("�Ϸ� üũ�Ͽ����ϴ�.");
		else
			System.out.println("�ùٸ� ��ȣ�� �Է����ּ���.");
	}
	
	public static void notCompleteItem(TodoList l, String index) {
		if(l.notCompleteItem(index) > 0)
			System.out.println("�Ϸ� üũ ��ҵǾ����ϴ�.");
	}
	
	public static void deleteComplete(TodoList l) {
		if(l.deleteComplete() > 0)
			System.out.println("�Ϸ�� �׸��� ��� �����Ǿ����ϴ�.");
	}
	
	public static void changeAchievement(TodoList l, int index) {
		Scanner sc = new Scanner(System.in);

		System.out.print("�� �ۼ�Ʈ�� �����Ͻðڽ��ϱ�? > ");
		int percent = sc.nextInt();
		if(l.changeAchievement(index, percent) > 0)
			System.out.println("�޼����� ����Ǿ����ϴ�.");
	}
	
	public static void changeImportance(TodoList l, int index) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("�߿䵵�� ������ �����Ͻðڽ��ϱ�? (1~5) > ");
		int importance = sc.nextInt();
		if(l.changeImportance(index, importance) > 0)
			System.out.println("�߿䵵�� ����Ǿ����ϴ�.");
	}
	
	public static void listAll(TodoList l) {
		System.out.printf("[��ü ���, �� %d��]\n", l.getCount());

		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listWeekList(TodoList l) {
		System.out.println("---------------Week List---------------");
		int count = 0;
		for (TodoItem item : l.getWeekList()) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n�� %d���� �׸��� �Ϸ��ؾ� �մϴ�.\n", count);
	}
	
	public static void listDayList(TodoList l, int day) {
		System.out.println("---------------In " + day + "days List---------------");
		int count = 0;
		for (TodoItem item : l.getDayList(day)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n�� %d���� �׸��� �Ϸ��ؾ� �մϴ�.\n", count);
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[��ü ���, �� %d��]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, int comp) {
		int count = 0;
		for (TodoItem item : l.getList(comp)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n�� %d���� �׸��� �Ϸ�Ǿ����ϴ�.\n", count);
	}

	
	public static void ls_cate(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n�� %d���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.\n", count);
	}
	
	
	public static void find(TodoList l, String keyword) {
		int count = 0;
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}

	public static void find_cate(TodoList l, String cate) {
		int count = 0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n�� %d���� �׸��� ã�ҽ��ϴ�.\n", count);
	}	
}
