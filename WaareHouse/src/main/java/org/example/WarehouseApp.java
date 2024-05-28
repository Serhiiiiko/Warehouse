package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WarehouseApp {
    private static Warehouse warehouse = new Warehouse();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        DatabaseManager.createNewDatabase();

        SeedData.seed();

        while (true) {
            System.out.println("Warehouse Management System");
            System.out.println("1. Add Material");
            System.out.println("2. Remove Material");
            System.out.println("3. Check Material Availability");
            System.out.println("4. Get Material Price");
            System.out.println("5. Print Receipt");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addMaterial();
                    break;
                case 2:
                    removeMaterial();
                    break;
                case 3:
                    checkAvailability();
                    break;
                case 4:
                    getPrice();
                    break;
                case 5:
                    printReceipt();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addMaterial() {
        System.out.print("Enter material type (CEMENT, SAND, GRAVEL, BRICKS, WOOD): ");
        MaterialType type = MaterialType.valueOf(scanner.next().toUpperCase());
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        warehouse.addMaterial(type, quantity, price);
        System.out.println("Material added successfully.");
    }

    private static void removeMaterial() {
        try {
            System.out.print("Enter material type (CEMENT, SAND, GRAVEL, BRICKS, WOOD): ");
            MaterialType type = MaterialType.valueOf(scanner.next().toUpperCase());
            warehouse.removeMaterial(type);
            System.out.println("Material removed successfully.");
        } catch (MaterialNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void checkAvailability() {
        try {
            System.out.print("Enter material type (CEMENT, SAND, GRAVEL, BRICKS, WOOD): ");
            MaterialType type = MaterialType.valueOf(scanner.next().toUpperCase());
            Material material = warehouse.checkAvailability(type);
            System.out.println("Material available: " + material);
        } catch (MaterialNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getPrice() {
        try {
            System.out.print("Enter material type (CEMENT, SAND, GRAVEL, BRICKS, WOOD): ");
            MaterialType type = MaterialType.valueOf(scanner.next().toUpperCase());
            double price = warehouse.getPrice(type);
            System.out.println("Material price: " + price);
        } catch (MaterialNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printReceipt() {
        List<Material> order = new ArrayList<>();
        while (true) {
            System.out.print("Enter material type (CEMENT, SAND, GRAVEL, BRICKS, WOOD) or 'done' to finish: ");
            String input = scanner.next();
            if (input.equalsIgnoreCase("done")) {
                break;
            }
            MaterialType type = MaterialType.valueOf(input.toUpperCase());
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            order.add(new Material(type, quantity, 0));
        }
        try {
            warehouse.printReceipt(order);
        } catch (MaterialNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}