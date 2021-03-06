package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<TodoList 관리 명령어 사용법>");
        System.out.println("add - 항목 추가");
        System.out.println("del - 항목 삭제");
        System.out.println("edit - 항목 수정");
        System.out.println("comp <번호> - 항목 완료");
        System.out.println("not_comp <번호> - 항목 완료 취소");
        System.out.println("del_comp - 완료된 항목 전체 삭제");
        System.out.println("ach_change <번호> - 달성도 변경");
        System.out.println("imp_change <번호> - 중요도 변경");
        System.out.println("ls - 전체 목록");
        System.out.println("ls_week - WeekList 보기");
        System.out.println("ls_day <일 수> - 해당 일 수 안에 해야할 항목보기");
        System.out.println("ls_cate - 카테고리 목록");
        System.out.println("ls_comp - 완료된 항목 목록");
        System.out.println("find <검색어> - 항목 검색");
        System.out.println("find_cate <검색어> - 카테고리 검색");
        System.out.println("ls_name - 제목순 정렬");
        System.out.println("ls_name_desc - 제목역순 정렬");
        System.out.println("ls_date - 날짜순 정렬");
        System.out.println("ls_date_desc - 최신순 목록");
        System.out.println("help - 명령어 사용법 리스트");
        System.out.println("exit - 종료");
    }
    public static void prompt() 
    {
    	System.out.print("\nEnter your choice > ");
    }
}
