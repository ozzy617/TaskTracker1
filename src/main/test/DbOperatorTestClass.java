import com.example.tasktracker1.database.DbOperator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DbOperatorTestClass {
    private final static String TEST_TABLE_NAME = "test_table";
    private final static String TEST_TASK = "test_Task";


    DbOperator dbOperator;

    @BeforeEach
    void setupAppWindowObject() {
        dbOperator = new DbOperator();
    }
    @AfterEach
    void deleteAddedValues () throws SQLException, ClassNotFoundException {
        ArrayList<String> tableList = dbOperator.loadListNames();
        if (tableList.get(tableList.size() - 1).equals("test_table")) {
            dbOperator.deleteTable("test_table");
        }
    }

    @Test
    void writeTaskTest() throws SQLException, ClassNotFoundException {
        dbOperator.createList("test_table");
        dbOperator.writeTask("test_Task", "test_table");
        ArrayList<String> resultList = dbOperator.loadListValues("test_table");
        String value = resultList.get(0);
        assertEquals("test_Task", value);
    }
    @Test
    void createTableTest() throws SQLException, ClassNotFoundException {
        dbOperator.createList("test_table");
        ArrayList<String> tables = dbOperator.loadListNames();
        String value = tables.get(tables.size() - 1);
        assertEquals("test_table",value);
    }

    @Test
    void deleteTableTest() throws SQLException, ClassNotFoundException {
        dbOperator.createList("test_table");
        dbOperator.deleteTable("test_table");
        ArrayList<String> tableList = dbOperator.loadListNames();
        String value = tableList.get(tableList.size() - 1);
        assertNotEquals("test_table", value);
    }

    @Test
    void deleteTaskTest() throws SQLException, ClassNotFoundException {
        dbOperator.createList("test_table");
        dbOperator.writeTask("test_Task", "test_table");
        dbOperator.deleteTask("test_Task", "test_table");
        ArrayList<String> taskList = dbOperator.loadListValues("test_table");
        int value = taskList.size();
        assertEquals(0, value);
    }

    @Test
    void loadListNameTest() throws SQLException, ClassNotFoundException {
        dbOperator.createList(TEST_TABLE_NAME);
        ArrayList<String> listNames = dbOperator.loadListNames();
        int value = listNames.size();
        assertNotEquals(0, value);
    }

    @Test
    void loadListValuesTest() throws SQLException, ClassNotFoundException {
        dbOperator.createList(TEST_TABLE_NAME);
        dbOperator.writeTask(TEST_TASK, TEST_TABLE_NAME);
        ArrayList<String> listValues = dbOperator.loadListValues(TEST_TABLE_NAME);
        int value = listValues.size();
        assertNotEquals(0 , value);
    }

    @Test
    void countRowsTest() throws SQLException, ClassNotFoundException {
        dbOperator.createList(TEST_TABLE_NAME);
        dbOperator.writeTask(TEST_TASK, TEST_TABLE_NAME);
        ArrayList<String> listNames = new ArrayList<>();
        listNames.add(TEST_TABLE_NAME);
        ArrayList<String> rowsVal = dbOperator.countRows(listNames);
        int value = Integer.parseInt(rowsVal.get(0));
        assertEquals(1, value);
    }
}
