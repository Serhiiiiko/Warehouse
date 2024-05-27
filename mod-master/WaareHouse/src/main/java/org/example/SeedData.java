package org.example;

public class SeedData {
    public static void seed() {
        Warehouse warehouse = new Warehouse();
        warehouse.addMaterial(MaterialType.CEMENT, 100, 12.5);
        warehouse.addMaterial(MaterialType.SAND, 200, 8.0);
        warehouse.addMaterial(MaterialType.GRAVEL, 150, 10.0);
        warehouse.addMaterial(MaterialType.BRICKS, 500, 1.2);
        warehouse.addMaterial(MaterialType.WOOD, 300, 15.0);
    }
}
