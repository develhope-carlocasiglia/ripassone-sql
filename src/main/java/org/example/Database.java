package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String host = "jdbc:mysql://localhost:3306/nomeschema";
    private static final String user = "inserireusername";
    private static final String password = "inserirepassword";

    Connection connection;

    public Database() {
        System.out.println("Connecting to " + host + "...");
        try {
            connection = DriverManager.getConnection(host, user, password);
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.out.println("Connection failed :(");
            System.out.println(e.getMessage());
        }
    }

    public void createTable(String tableName, String[] fields) throws SQLException {
        String query = "create table if not exists " + tableName + " (" +
                "id INT AUTO_INCREMENT,";
        for (String field : fields) {
            query += field + " VARCHAR(30),";
        }
        query += "PRIMARY KEY (id));";
        connection.createStatement().executeUpdate(query);
        System.out.println("Table " + tableName + " created!");
    }

    public void insertStudent(String name, String surname) throws SQLException {
        String query = String.format("insert into students (first_name, last_name) values (\"%s\", \"%s\");", name, surname);
        connection.createStatement().executeUpdate(query);
        System.out.printf("Added %s %s to table students\n", name, surname);
    }

    public void addColumnToTable(String tableName, String columnName, String columnType) throws SQLException {
        String query = String.format("alter table %s add column %s %s", tableName, columnName, columnType);
        connection.createStatement().executeUpdate(query);
        System.out.printf("Column %s added to %s\n", columnName, tableName);
    }

    public void updateStudentCountry(int id, String country) throws SQLException {
        connection.createStatement().executeUpdate(String.format("update students set country = \"%s\" where id = %d", country, id));
        System.out.printf("Student %d updated with country: %s\n", id, country);
    }

    public void createFilterByCountryOnStudents(String country) throws SQLException {
        String query = String.format("create view %s_students as (select * from students where country = \"%s\")", country, country);
        connection.createStatement().executeUpdate(query);
        System.out.printf("Create filtered view on %s students\n", country);
    }

    public List<Student> createStudentList(String country) throws SQLException {
        ResultSet rows = connection.createStatement().executeQuery(String.format("select * from %s_students", country));
        List<Student> studentList = new ArrayList<>();
        // per ogni riga dentro al risultato vogliamo prendere le singole celle al loro interno
        // e usarle per costruire degli oggetti di tipo Student, e metterli dentro alla lista
        while (rows.next()) { // "finché ce n'è un'altra ..."
            studentList.add(new Student(
                    rows.getInt("id"),
                    rows.getString("first_name"),
                    rows.getString("last_name"),
                    rows.getString("country")));
        }
        return studentList;
    }

}
