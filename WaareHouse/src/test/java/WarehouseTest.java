import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {
    private Warehouse warehouse;

    @BeforeEach
    public void setUp() {
        DatabaseManager.createNewDatabase();
        warehouse = new Warehouse();
    }

    @AfterEach
    public void tearDown() {
        try (Connection conn = DatabaseManager.connect()) {
            if (conn != null) {
                conn.createStatement().execute("DROP TABLE IF EXISTS materials;");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testAddMaterial() {
        warehouse.addMaterial(MaterialType.CEMENT, 100, 12.5);
        try {
            Material material = Material.findByType(MaterialType.CEMENT);
            assertEquals(100, material.getQuantity());
            assertEquals(12.5, material.getPrice());
        } catch (MaterialNotFoundException e) {
            fail("Material should be found.");
        }
    }

    @Test
    public void testUpdateMaterial() {
        warehouse.addMaterial(MaterialType.CEMENT, 100, 12.5);
        warehouse.addMaterial(MaterialType.CEMENT, 50, 15.0);
        try {
            Material material = Material.findByType(MaterialType.CEMENT);
            assertEquals(100, material.getQuantity());
            assertEquals(12.5, material.getPrice());
        } catch (MaterialNotFoundException e) {
            fail("Material should be found.");
        }
    }

    @Test
    public void testDeleteMaterial() {
        warehouse.addMaterial(MaterialType.SAND, 200, 8.0);
        try {
            warehouse.removeMaterial(MaterialType.SAND);
            Material.findByType(MaterialType.SAND);
            fail("Material should be deleted.");
        } catch (MaterialNotFoundException e) {
            assertEquals("Material not found in inventory.", e.getMessage());
        }
    }

    @Test
    public void testCheckAvailability() {
        warehouse.addMaterial(MaterialType.CEMENT, 100, 12.5);
        try {
            Material material = warehouse.checkAvailability(MaterialType.CEMENT);
            assertNotNull(material);
        } catch (MaterialNotFoundException e) {
            fail("Material should be available.");
        }
    }

    @Test
    public void testGetPrice() {
        warehouse.addMaterial(MaterialType.CEMENT, 100, 12.5);
        try {
            double price = warehouse.getPrice(MaterialType.CEMENT);
            assertEquals(12.5, price);
        } catch (MaterialNotFoundException e) {
            fail("Material should be found.");
        }
    }

    @Test
    public void testPrintReceipt() {
        warehouse.addMaterial(MaterialType.CEMENT, 100, 12.5);
        warehouse.addMaterial(MaterialType.SAND, 200, 8.0);
        List<Material> order = new ArrayList<>();
        order.add(new Material(MaterialType.CEMENT, 10, 0));
        order.add(new Material(MaterialType.SAND, 20, 0));

        try {
            warehouse.printReceipt(order);
        } catch (MaterialNotFoundException e) {
            fail("Material should be available.");
        }
    }
}
