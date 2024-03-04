import com.example.tasktracker1.DbOperator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DbOperatorTestClass {

    DbOperator dbOperator;

    @BeforeEach
    void setupAppWindowObject() {
        dbOperator = new DbOperator();
    }
    @AfterEach
    void deleteAddedValues () throws SQLException, ClassNotFoundException {
        ArrayList<String> tableList= dbOperator.loadListNames();
        dbOperator.deleteTable(tableList.get(tableList.size() - 1),tableList.size());
    }

    @Test
    void writeTaskTest() throws SQLException, ClassNotFoundException {
        dbOperator.createList("test_table");
        ArrayList<String> tables = dbOperator.loadListNames();
        String tableName = tables.get(tables.size() - 1);
        dbOperator.writeTask("test_Task", tableName.substring(4));
        ArrayList<String> resultList = dbOperator.loadListValues(tableName.substring(4));
        String value = resultList.get(0);
        assertEquals("test_Task", value);
    }
    @Test
    void createTableTest() throws SQLException, ClassNotFoundException {
        dbOperator.createList("test_table");
        ArrayList<String> tables = dbOperator.loadListNames();
        String value = tables.get(tables.size() - 1);
        assertEquals("test_table",value.substring(4));
    }
    @Test
    void addTableTest() throws SQLException, ClassNotFoundException {
        dbOperator.createList("test_table");
        ArrayList<String> tablesNames = dbOperator.loadListNames();
        String value = tablesNames.get(tablesNames.size() - 1);
        assertNotEquals("test_table",value);
    }

}
