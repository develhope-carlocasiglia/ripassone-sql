package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        // mi connetto al db
        Database db = new Database();

        // creo una tabella
        String[] fields = {"first_name", "last_name"};
        db.createTable("students", fields);

        // aggiungo 4 studenti
        db.insertStudent("carlo", "casiglia");
        db.insertStudent("mario", "rossi");
        db.insertStudent("heinz", "schwartz");
        db.insertStudent("franz", "weiss");

        // aggiungo una colonna
        db.addColumnToTable("students", "country", "VARCHAR(30)");

        // aggiorno il campo country per tutti quegli studenti
        db.updateStudentCountry(1, "italy");
        db.updateStudentCountry(2, "italy");
        db.updateStudentCountry(3, "germany");
        db.updateStudentCountry(4, "germany");

        // creo delle visualizzazioni filtrate
        db.createFilterByCountryOnStudents("italy");
        db.createFilterByCountryOnStudents("germany");

        // interrogo le visualizzazioni filtrate, uso il risultato per costruire degli oggetti di tipo studente
        // e metterli dentro delle liste
        List<Student> italianStudents = db.createStudentList("italy");
        List<Student> germanStudents = db.createStudentList("germany");

        // stampo le liste
        System.out.println("Lista studenti italiani: " + italianStudents);
        System.out.println("Lista studenti tedeschi: " + germanStudents);


    }
}