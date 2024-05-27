package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Material {
    private int id;
    private MaterialType type;
    private int quantity;
    private double price;

    public Material(MaterialType type, int quantity, double price) {
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    public Material(int id, MaterialType type, int quantity, double price) {
        this.id = id;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public MaterialType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", type=" + type +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    public static void save(Material material) {
        String sql = "INSERT INTO materials(type, quantity, price) VALUES(?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, material.getType().name());
            pstmt.setInt(2, material.getQuantity());
            pstmt.setDouble(3, material.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Material> getAll() {
        String sql = "SELECT id, type, quantity, price FROM materials";
        List<Material> materials = new ArrayList<>();
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                materials.add(new Material(
                        rs.getInt("id"),
                        MaterialType.valueOf(rs.getString("type")),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return materials;
    }

    public static void delete(MaterialType type) {
        String sql = "DELETE FROM materials WHERE type = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type.name());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Material findByType(MaterialType type) throws MaterialNotFoundException {
        String sql = "SELECT id, type, quantity, price FROM materials WHERE type = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type.name());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Material(
                        rs.getInt("id"),
                        MaterialType.valueOf(rs.getString("type")),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
            } else {
                throw new MaterialNotFoundException("Material not found in inventory.");
            }
        } catch (SQLException e) {
            throw new MaterialNotFoundException("Database error: " + e.getMessage());
        }
    }
}
