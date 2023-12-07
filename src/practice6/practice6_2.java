package practice6;

import java.util.Scanner;

class Car {
    private String model;
    private String name;
    private String type;
    private String color;
    private int price;

    public Car(String model, String name, String type, String color, int price) {
        this.model = model;
        this.name = name;
        this.type = type;
        this.color = color;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "[" + model + "]" + name + "(" + type + ", " + color + ")";
    }

    @Override
    public boolean equals(Object obj) {
        Car car = (Car) obj;
        return model.equals(car.model) && name.equals(car.name) && type.equals(car.type) && color.equals(car.color);
    }
}

public class practice6_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String car = scanner.nextLine();
            if (car.equals("exit"))
                return;
            String[] tmpString = car.split("&");
            String[] car1 = tmpString[0].split("/");
            car1[car1.length - 1] = car1[car1.length - 1].strip();
            String[] car2 = tmpString[1].split("/");
            car2[0] = car2[0].strip();

            Car firstCar = new Car(car1[0], car1[1], car1[2], car1[3], Integer.parseInt(car1[4]));
            Car secondCar = new Car(car2[0], car2[1], car2[2], car2[3], Integer.parseInt(car2[4]));

            if (firstCar.equals(secondCar) && firstCar.getPrice() == secondCar.getPrice()) {
                System.out.println("Two cars are the same.");
                System.out.println(firstCar);
            } else if (firstCar.equals(secondCar)) {
                System.out.println("Two cars are the same.");
                System.out.println(firstCar);
                System.out.println(
                        "(The prices of two cars are " + firstCar.getPrice() + " and " + secondCar.getPrice() + ".)");
            } else {
                System.out.println("Two cars are different.");
                System.out.println("<First Car>");
                System.out.println(firstCar + " : " + firstCar.getPrice());
                System.out.println("<Second Car>");
                System.out.println(secondCar + " : " + secondCar.getPrice());
            }
        }
    }
}
