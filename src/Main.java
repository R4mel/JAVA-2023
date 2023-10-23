import java.util.*;

class Menu {

    public Menu(int id, String menu, int price) {
        this.id = id;
        this.name = menu;
        this.price = price;
    }

    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void print() {
        System.out.println(id + ": " + name + "[" + price / 1000 + "]");
    }
}

class Menuboard {
    static String[] menus = {"Americano", "Latte", "Mocca", "Cappuccino",
            "Milk Tea", "Chi Tea", "Lemon Sweet", "Jamong Honey"};
    static int[] prices = {4100, 4300, 4300, 4800, 5100, 5300, 5800, 6100};

    Menu[] menu = new Menu[8];

    public Menuboard() {
        for (int i = 0; i < 8; i++) {
            menu[i] = new Menu(i + 1, menus[i], prices[i]);
        }
    }

    public static Menuboard makeBoard() {
        Menuboard mn = new Menuboard();
        return mn;
    }

    public void print() {
        System.out.println("***** Best Coffee *****");
        for (var m : menu) {
            m.print();
        }
        System.out.println("***********************");
    }
}

class Order {
    public int[] count;
    public int[] list = new int[8];

    public Order(int a) {
        count = new int[a];
    }

    public void addMenu(int a, int b, int index) {
        count[index] = a;
        list[index] = b;
    }

    public void print(int i) {
        System.out.println("Menu: " + Menuboard.menus[count[i] - 1] + " Qty: " + list[i] + " Price: " + Menuboard.prices[count[i] - 1] * list[i]);
    }
}

class MenuOrder {
    static Scanner in = new Scanner(System.in);

    public static void makeOrder() {
        Menuboard mn = new Menuboard();
        int index = 0;
        mn.print();
        System.out.print("How many kinds of drinks? ");
        int drinks = in.nextInt();
        Order order = new Order(drinks);
        while (true) {
            System.out.print("Menu no? ");
            int no = in.nextInt();
            System.out.println("Quantity? ");
            int drink_number = in.nextInt();
            if (no > 8) {
                System.out.println("Menu id " + no + " doesn't exist in our menuboard");
                continue;
            } else {
                order.addMenu(no, drink_number, index);
                System.out.println("Your selected menu is added to the order");
                index++;
            }
            if (index == drinks) break;
        }
        System.out.println("** Order details **");
        for (int i = 0; i < drinks; i++) {
            order.print(i);
        }
    }
}


public class Main {
    public static void main(String[] args) {
        MenuOrder.makeOrder();
    }
}

