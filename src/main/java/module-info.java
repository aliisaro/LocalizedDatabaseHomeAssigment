module app.localizeddatabasehomeassigment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Add this line
    requires org.mariadb.jdbc; // If you're using MariaDB JDBC

    opens app.localizeddatabasehomeassigment to javafx.fxml;
    exports app.localizeddatabasehomeassigment;
}
