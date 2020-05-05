import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import jdk.nashorn.internal.parser.JSONParser;
import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static class LoginData {
        static String dataBaseUrl = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
        static String user = "kochnik";
        static String password = "kochnik";
    }

    public static void main(String[] args) {

        String driverName = "oracle.jdbc.driver.OracleDriver";

        try {
            Class<?> c = Class.forName(driverName);
            System.out.println("Pakiet     : " + c.getPackage());
            System.out.println("Nazwa klasy: " + c.getName());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        System.out.println("Sterownik za³adowano poprawnie. Sukces.");

        String sqlCreateTableSubject = "CREATE TABLE przedmiot (idp int not null, nazwa_przedmiotu char(20) not null)";
        String sqlCreateTableTeacher = "CREATE TABLE nauczyciel (idn int not null, nazwisko_nauczyciela char(30) not null, imie_nauczyciela char(20) not null)";
        String sqlCreateTableRating = "CREATE TABLE ocena (ido int not null, wartosc_opisowa char(20) not null, wartosc_numeryczna float not null)";
        String sqlCreateTableStudent = "CREATE TABLE uczen (idu int not null, nazwisko_ucznia char(30) not null, imie_ucznia char(20) not null)";
        String sqlCreateTableEvaluation = "CREATE TABLE ocenianie (idn int not null, idu int not null, idp int not null, ido int not null, rodzaj_oceny char(1) not null)";

        String sqlTestDataForSubject1 = "INSERT INTO przedmiot (idp, nazwa_przedmiotu) VALUES (1, 'Matematyka')";
        String sqlTestDataForSubject2 = "INSERT INTO przedmiot (idp, nazwa_przedmiotu) VALUES (5, 'Fizyka')";
        String sqlTestDataForTeacher1 = "INSERT INTO nauczyciel (idn, nazwisko_nauczyciela, imie_nauczyciela) VALUES (1, 'Kowalski', 'Adam')";
        String sqlTestDataForTeacher2 = "INSERT INTO nauczyciel (idn, nazwisko_nauczyciela, imie_nauczyciela) VALUES (5, 'Bednarska', 'Ewa')";
        String sqlTestDataForRating1 = "INSERT INTO ocena (ido, wartosc_opisowa, wartosc_numeryczna) VALUES (1, 'niedostateczny i pó³', 1.5)";
        String sqlTestDataForRating2 = "INSERT INTO ocena (ido, wartosc_opisowa, wartosc_numeryczna) VALUES (5, 'dostateczny', 3)";
        String sqlTestDataForStudent1 = "INSERT INTO uczen (idu, nazwisko_ucznia, imie_ucznia) VALUES (1, 'Wiœniewski', 'Karol')";
        String sqlTestDataForStudent2 = "INSERT INTO uczen (idu, nazwisko_ucznia, imie_ucznia) VALUES (5, 'Pawlak', 'Waldemar')";

        String sqlDataForEvaluation = "INSERT INTO ocenianie (idn, idu, idp, ido, rodzaj_oceny) VALUES (?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(LoginData.dataBaseUrl, LoginData.user,
                LoginData.password);
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDataForEvaluation);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("AutoCommit: " + connection.getAutoCommit());
            System.out.println("Test po³¹czenia z baz¹ wykonany. Sukces.");

            if (checkIfTableExistInDataBase("PRZEDMIOT", connection)) {
                System.out.println("Tabela PRZEDMIOT ju¿ istnieje w bazie danych.");
            } else {
                System.out.println("Tworzenie tabeli PRZEDMIOT w bazie danych. execute: "
                        + statement.execute(sqlCreateTableSubject));
            }

            if (checkIfTableExistInDataBase("NAUCZYCIEL", connection)) {
                System.out.println("Tabela NAUCZYCIEL ju¿ istnieje w bazie danych.");
            } else {
                System.out.println("Tworzenie tabeli NAUCZYCIEL w bazie danych. execute: "
                        + statement.execute(sqlCreateTableTeacher));
            }

            if (checkIfTableExistInDataBase("OCENA", connection)) {
                System.out.println("Tabela OCENA ju¿ istnieje w bazie danych.");
            } else {
                System.out.println(
                        "Tworzenie tabeli OCENA w bazie danych. execute: " + statement.execute(sqlCreateTableRating));
            }

            if (checkIfTableExistInDataBase("UCZEN", connection)) {
                System.out.println("Tabela UCZEN ju¿ istnieje w bazie danych.");
            } else {
                System.out.println(
                        "Tworzenie tabeli UCZEN w bazie danych. execute: " + statement.execute(sqlCreateTableStudent));
            }

            if (checkIfTableExistInDataBase("OCENIANIE", connection)) {
                System.out.println("Tabela OCENIANIE ju¿ istnieje w bazie danych.");
            } else {
                System.out.println("Tworzenie tabeli OCENIANIE w bazie danych. execute: "
                        + statement.execute(sqlCreateTableEvaluation));
            }

            if (checkIfTableHaveData("PRZEDMIOT", statement)) {
                System.out.println("Tabela PRZEDMIOT zawiera dane.");
            } else {
                System.out.println("Wstawianie testowych danych do tabeli PRZEDMIOT 1. execute:"
                        + statement.executeUpdate(sqlTestDataForSubject1));
                System.out.println("Wstawianie testowych danych do tabeli PRZEDMIOT 2. execute:"
                        + statement.executeUpdate(sqlTestDataForSubject2));
            }

            if (checkIfTableHaveData("NAUCZYCIEL", statement)) {
                System.out.println("Tabela NAUCZYCIEL zawiera dane.");
            } else {
                System.out.println("Wstawianie testowych danych do tabeli NAUCZYCIEL 1. execute:"
                        + statement.executeUpdate(sqlTestDataForTeacher1));
                System.out.println("Wstawianie testowych danych do tabeli NAUCZYCIEL 2. execute:"
                        + statement.executeUpdate(sqlTestDataForTeacher2));
            }

            if (checkIfTableHaveData("OCENA", statement)) {
                System.out.println("Tabela OCENA zawiera dane.");
            } else {
                System.out.println("Wstawianie testowych danych do tabeli OCENA 1. execute:"
                        + statement.executeUpdate(sqlTestDataForRating1));
                System.out.println("Wstawianie testowych danych do tabeli OCENA 2. execute:"
                        + statement.executeUpdate(sqlTestDataForRating2));
            }

            if (checkIfTableHaveData("UCZEN", statement)) {
                System.out.println("Tabela UCZEN zawiera dane..");
            } else {
                System.out.println("Wstawianie testowych danych do tabeli UCZEN 1. execute:"
                        + statement.executeUpdate(sqlTestDataForStudent1));
                System.out.println("Wstawianie testowych danych do tabeli UCZEN 2. execute:"
                        + statement.executeUpdate(sqlTestDataForStudent2));
            }

            System.out.println("Wype³nianie tabeli ocenianie");
            System.out.println(" ");

            boolean rating = true;
            ratingLoop:
            while (rating) {

                int inputTeacherId = -1;
                int inputStudentId = -1;
                int inputSubjectId = -1;
                int inputRatingId = -1;
                String inputRatingTypeId;

                System.out.println("Jeœli chcesz przerwaæ program podaj 0");
                System.out.println("Podaj id nauczyciela:");

                boolean teacherIdIsInt = false;
                while (!teacherIdIsInt) {
                    try {
                        inputTeacherId = Integer.parseInt(scanner.next());
                    } catch (NumberFormatException e) {
                        System.out.println("Dane nie s¹ typu INT. Podaj jeszcze raz");
                    }
                    if (inputTeacherId == 0) {
                        break ratingLoop;
                    } else if (inputTeacherId != -1) {
                        preparedStatement.setInt(1, inputTeacherId);
                        teacherIdIsInt = true;
                    } else {
                        //TODO
                        System.out.println("a kuku");}
                }

                System.out.println("Jeœli chcesz przerwaæ program podaj 0");
                System.out.println("Podaj id ucznia:");

                boolean studentIdIsInt = false;
                while (!studentIdIsInt) {
                    try {
                        inputStudentId = Integer.parseInt(scanner.next());
                    } catch (NumberFormatException e) {
                        System.out.println("Dane nie s¹ typu INT. Podaj jeszcze raz");
                    }
                    if (inputStudentId == 0) {
                        break ratingLoop;
                    } else if (inputStudentId != -1) {
                        preparedStatement.setInt(2, inputStudentId);
                        studentIdIsInt = true;
                    }
                }

                System.out.println("Jeœli chcesz przerwaæ program podaj 0");
                System.out.println("Podaj id przedmiotu:");

                boolean subjectIdIsInt = false;
                while (!subjectIdIsInt) {
                    try {
                        inputSubjectId = Integer.parseInt(scanner.next());
                    } catch (NumberFormatException e) {
                        System.out.println("Dane nie s¹ typu INT. Podaj jeszcze raz");
                    }
                    if (inputSubjectId == 0) {
                        break ratingLoop;
                    } else if (inputSubjectId != -1) {
                        preparedStatement.setInt(3, inputSubjectId);
                        subjectIdIsInt = true;
                    }
                }

                System.out.println("Jeœli chcesz przerwaæ program podaj 0");
                System.out.println("Podaj id oceny:");

                boolean ratingIdIsInt = false;
                while (!ratingIdIsInt) {
                    try {
                        inputRatingId = Integer.parseInt(scanner.next());
                    } catch (NumberFormatException e) {
                        System.out.println("Dane nie s¹ typu INT. Podaj jeszcze raz");
                    }
                    if (inputRatingId == 0) {
                        break ratingLoop;
                    } else if (inputRatingId != -1) {
                        preparedStatement.setInt(4, inputRatingId);
                        ratingIdIsInt = true;
                    }
                }

                boolean correctRattingType = false;
                while (!correctRattingType) {

                    System.out.println("Jeœli chcesz przerwaæ program podaj 0");
                    System.out.println("Podaj typ oceny: „C” –cz¹stkowa, „S” –semestralna");

                    if (scanner.hasNext()) {
                        inputRatingTypeId = scanner.next().toUpperCase();
                        if (inputRatingTypeId.equals("C")) {
                            preparedStatement.setString(5, "C");
                            correctRattingType = true;
                        } else if (inputRatingTypeId.equals("S")) {
                            preparedStatement.setString(5, "S");
                            correctRattingType = true;
                        } else if (inputRatingTypeId.equals("0")) {
                            break ratingLoop;
                        } else {
                            System.out
                                    .println("Podano nieprawid³owe dane: " + inputRatingTypeId + ", podaj jeszcze raz");
                        }
                    }
                }

                if (checkIfIdExistInDataBase("OCENA", "ido", inputRatingId, statement)) {
                    if (checkIfIdExistInDataBase("PRZEDMIOT", "idp", inputSubjectId, statement)) {
                        if (checkIfIdExistInDataBase("UCZEN", "idu", inputStudentId, statement)) {
                            if (checkIfIdExistInDataBase("NAUCZYCIEL", "idn", inputTeacherId, statement)) {
                                System.out.println("Wstawiono dane do tabeli OCENIANIE. execute:"
                                        + preparedStatement.executeUpdate());
                            } else {
                                System.out.println("Error report -\n"
                                        + "ORA-02291: naruszono wiêzy spójnoœci (idn) - nie znaleziono klucza nadrzêdnego");
                            }
                        } else {
                            System.out.println("Error report -\n"
                                    + "ORA-02291: naruszono wiêzy spójnoœci (idu) - nie znaleziono klucza nadrzêdnego");
                        }
                    } else {
                        System.out.println("Error report -\n"
                                + "ORA-02291: naruszono wiêzy spójnoœci (idp) - nie znaleziono klucza nadrzêdnego");
                    }
                } else {
                    System.out.println("Error report -\n"
                            + "ORA-02291: naruszono wiêzy spójnoœci (ido) - nie znaleziono klucza nadrzêdnego");
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        System.out.println("Koniec programu!");
    }

    private static boolean checkIfTableHaveData(String tableName, Statement statement) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
        boolean result = rs.next();
        rs.close();
        return result;
    }

    private static boolean checkIfTableExistInDataBase(String tableName, Connection connection) throws SQLException {
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, tableName, null);
        boolean result = tables.next();
        tables.close();
        return result;
    }

    private static boolean checkIfIdExistInDataBase(String table, String id, int inputId, Statement s)
            throws SQLException {
        String sql = "SELECT * FROM " + table + " WHERE " + id + " = " + inputId;
        ResultSet rs = s.executeQuery(sql);
        boolean result = rs.next();
        rs.close();
        return result;
    }
}
