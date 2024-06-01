module com.mycompany.testg2store {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.testg2store to javafx.fxml;
    exports com.mycompany.testg2store;
    requires mysql.connector.java;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.commons.lang3;
}

