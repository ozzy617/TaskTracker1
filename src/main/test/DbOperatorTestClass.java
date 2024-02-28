import com.example.tasktracker1.DbOperator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DbOperatorTestClass {

    DbOperator dbOperator;

    @BeforeEach
    void setupAppWindowObject() {
        dbOperator = new DbOperator();
    }
    @AfterEach
    void deleteAddedValues () throws SQLException, ClassNotFoundException {
        dbOperator.deleteTable("test_Table");
    }

    @Test
    void writeTaskTest() throws SQLException, ClassNotFoundException {
        dbOperator.createList("test_Table");
        dbOperator.writeTask("test_Task", "test_Table");
        ArrayList<String> resultList = dbOperator.loadListValues("test_Table");
        String value = resultList.get(resultList.size() - 1);
        assertEquals("test_Task", value);
    }
    @Test
    void createTableTest() throws SQLException, ClassNotFoundException {
        dbOperator.createList("test_Table");
        ArrayList<String> tables = dbOperator.loadListNames();
        String value = tables.get(tables.size()-1);
        assertEquals("test_table",value);
    }
}
