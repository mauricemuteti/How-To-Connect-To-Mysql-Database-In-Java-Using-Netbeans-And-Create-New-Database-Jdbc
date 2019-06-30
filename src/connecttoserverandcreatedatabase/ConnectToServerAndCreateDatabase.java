/*
 * How To Connect To Mysql Database In Java Using Netbeans And Create New Database Jdbc
 */
package connecttoserverandcreatedatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Genuine
 */
public class ConnectToServerAndCreateDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Declaring all variables
        String jdbcDriver = "com.mysql.cj.jdbc.Driver";
        Connection conn = null;
        String username = "root";
        String password = "";
        String serverUrl = "jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Statement stmt = null;
        String dbName = "products_db2";
        String checkDb = "SELECT SCHEMA_NAME FROM `information_schema`.`SCHEMATA` WHERE `SCHEMA_NAME` = '" + dbName + "'";
        ResultSet rs = null;
        boolean dbFound = false;

        try {
            //check jdbc driver (mysql connector / j) make sure the connector is configured correctly (added to libraries) before checking it.
            Class.forName(jdbcDriver);
            System.out.println("Driver Loaded");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Failed To Load");
            System.out.println(ex.getMessage());
        }

        try {
            //connecting to xampp server (Apache Server)
            conn = DriverManager.getConnection(serverUrl, username, password);
            System.out.println("Connected To Server Successfully");
        } catch (SQLException ex) {
            System.out.println("Failed To Connect To Server Successfully");
            System.out.println(ex.getMessage());

        }

        try {
            // Output sql query for checking if the database exists 
            System.out.println("Check if database exists query - " + checkDb);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(checkDb);
            // Output the resultset value
            System.out.println("Result Set Value " + rs.next());
            //Move the cursor one row from its current position
            if (rs.next()) {
                //If the database is found in the information_schema, set the boolean value to true
                dbFound = true;
            }

            //If the database is no found create new database
            if (!dbFound) {
                String createNewDatabase = "CREATE DATABASE IF NOT EXISTS " + dbName + "";
                //Display database creation query in the console section.
                System.out.println("Database creation query - " + createNewDatabase);
                //Execute database creation query and store the result in integer variable. Expected reults are 0 and 1.
                int createdb = stmt.executeUpdate(createNewDatabase);
                if (createdb > 0) {
                    System.out.println(dbName + " Created Successfully");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }

    }

}
