package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<TodoList ���� ���ɾ� ����>");
        System.out.println("add - �׸� �߰�");
        System.out.println("del - �׸� ����");
        System.out.println("edit - �׸� ����");
        System.out.println("comp <��ȣ> - �׸� �Ϸ�");
        System.out.println("not_comp <��ȣ> - �׸� �Ϸ� ���");
        System.out.println("del_comp - �Ϸ�� �׸� ��ü ����");
        System.out.println("ach_change <��ȣ> - �޼��� ����");
        System.out.println("imp_change <��ȣ> - �߿䵵 ����");
        System.out.println("ls - ��ü ���");
        System.out.println("ls_week - WeekList ����");
        System.out.println("ls_day <�� ��> - �ش� �� �� �ȿ� �ؾ��� �׸񺸱�");
        System.out.println("ls_cate - ī�װ��� ���");
        System.out.println("ls_comp - �Ϸ�� �׸� ���");
        System.out.println("find <�˻���> - �׸� �˻�");
        System.out.println("find_cate <�˻���> - ī�װ��� �˻�");
        System.out.println("ls_name - ����� ����");
        System.out.println("ls_name_desc - ���񿪼� ����");
        System.out.println("ls_date - ��¥�� ����");
        System.out.println("ls_date_desc - �ֽż� ���");
        System.out.println("help - ���ɾ� ���� ����Ʈ");
        System.out.println("exit - ����");
    }
    public static void prompt() 
    {
    	System.out.print("\nEnter your choice > ");
    }
}