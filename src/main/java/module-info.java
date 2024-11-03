module app.localizeddatabasehomeassigment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;  // Add this line for SQL-related classes
    requires org.mariadb.jdbc;  // Ensure you declare MariaDB if you're using modules

    opens app.localizeddatabasehomeassigment to javafx.fxml;  // Adjust package names as needed
    exports app.localizeddatabasehomeassigment;
}
