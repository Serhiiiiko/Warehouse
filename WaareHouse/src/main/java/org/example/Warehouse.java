package org.example;

import java.util.List;

public class Warehouse {
    public void addMaterial(MaterialType type, int quantity, double price) {
        try {
            Material material = Material.findByType(type);
            material.setQuantity(material.getQuantity() + quantity);
            material.setPrice(price);
            Material.save(material);
        } catch (MaterialNotFoundException e) {
            Material newMaterial = new Material(type, quantity, price);
            Material.save(newMaterial);
        }
    }

    public void removeMaterial(MaterialType type) throws MaterialNotFoundException {
        Material.delete(type);
    }

    public Material checkAvailability(MaterialType type) throws MaterialNotFoundException {
        return Material.findByType(type);
    }

    public double getPrice(MaterialType type) throws MaterialNotFoundException {
        return Material.findByType(type).getPrice();
    }

    public void printReceipt(List<Material> order) throws MaterialNotFoundException {
        double total = 0;
        System.out.println("Receipt:");
        for (Material material : order) {
            Material storedMaterial = checkAvailability(material.getType());
            int quantity = material.getQuantity();
            double price = storedMaterial.getPrice();
            double cost = quantity * price;
            total += cost;
            System.out.println(storedMaterial.getType() + ": " + quantity + " x " + price + " = " + cost);
        }
        System.out.println("Total: " + total);
    }
}
