package com.todo;

import java.util.Scanner;
import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
				
		boolean isList = false;
		boolean quit = false;
		
		Menu.displaymenu();
		do {
			int index_i;
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "comp":
				String index = sc.nextLine().trim();
				TodoUtil.completeItem(l, index);
				break;
				
			case "not_comp":
				index = sc.nextLine().trim();
				TodoUtil.notCompleteItem(l, index);
				break;
				
			case "del_comp":
				TodoUtil.deleteComplete(l);
				break;
				
			case "ach_change":
				index_i = sc.nextInt();
				TodoUtil.changeAchievement(l, index_i);
				break;
				
			case "imp_change":
				index_i = sc.nextInt();
				TodoUtil.changeImportance(l, index_i);
				break;
				
			case "ls_week":
				TodoUtil.listWeekList(l);
				break;
				
			case "ls_day":
				index_i = sc.nextInt();
				TodoUtil.listDayList(l, index_i);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
			
			case "ls_cate":
				TodoUtil.ls_cate(l);
				break;
				
			case "ls_comp":
				TodoUtil.listAll(l, 1);
				break;
				
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.find(l, keyword);
				break;
				
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.find_cate(l, cate);
				break;

			case "ls_name":
				System.out.println("제목순으로 정렬하였습니다");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬하였습니다");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬하였습니다");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("최신순으로 정렬하였습니다");
				TodoUtil.listAll(l, "due_date", 0);
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				break;

			default:
				System.out.println("정확한 명령어를 입력하세요. (도움말 - help)");
				break;
			}
			
			if(isList) TodoUtil.listAll(l);
		} while (!quit);
		System.out.print("모든 데이터가 저장되었습니다.");
	}
}