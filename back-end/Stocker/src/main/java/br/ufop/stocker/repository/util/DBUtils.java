package br.ufop.stocker.repository.util;

import br.ufop.stocker.general.PropertiesManager;
import br.ufop.stocker.general.PropertyError;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    private static String getDatabaseURL(String host, String port, String db) {
        return String.format("jdbc:postgresql://%s:%s/%s", host, port, db);
    }

    public static Connection getDatabaseConnection() throws PropertyError, SQLException {
//        Class.forName("org.postgresql.Driver");
        PropertiesManager propertiesManager = PropertiesManager.GetInstance();
        String host = propertiesManager.getProperty("database.host");
        String port = propertiesManager.getProperty("database.port");
        String database = propertiesManager.getProperty("database.dbname");
        String user = propertiesManager.getProperty("database.user");
        String password = propertiesManager.getProperty("database.password");
        String url = getDatabaseURL(host, port, database);
        return DriverManager.getConnection(url, user, password);
    }

}
