import org.example.DatabaseManager;
import org.example.MaterialType;
import org.example.Warehouse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {
    private Warehouse warehouse;

    @BeforeEach
    public void setUp() {
        // Ініціалізуйте базу даних і створіть таблиці
        DatabaseManager.createNewDatabase();
        warehouse = new Warehouse();
    }

    @AfterEach
    public void tearDown() {
        // Закрийте з'єднання з базою даних та очистіть дані
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
        assertEquals(100, warehouse.getQuantity(MaterialType.CEMENT));
        assertEquals(12.5, warehouse.getPrice(MaterialType.CEMENT));
    }

    @Test
    public void testUpdateMaterial() {
        warehouse.addMaterial(MaterialType.CEMENT, 100, 12.5);
        warehouse.updateMaterial(MaterialType.CEMENT, 150, 13.0);
        assertEquals(150, warehouse.getQuantity(MaterialType.CEMENT));
        assertEquals(13.0, warehouse.getPrice(MaterialType.CEMENT));
    }

    @Test
    public void testDeleteMaterial() {
        warehouse.addMaterial(MaterialType.SAND, 200, 8.0);
        warehouse.deleteMaterial(MaterialType.SAND);
        assertEquals(0, warehouse.getQuantity(MaterialType.SAND));
    }

    @Test
    public void testCheckAvailability() {
        warehouse.addMaterial(MaterialType.CEMENT, 100, 12.5);
        assertTrue(warehouse.isAvailable(MaterialType.CEMENT));
        assertFalse(warehouse.isAvailable(MaterialType.SAND));
    }
}
