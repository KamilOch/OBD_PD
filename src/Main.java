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

		try {

			Connection connection = DriverManager.getConnection(LoginData.dataBaseUrl, LoginData.user,
					LoginData.password);
			System.out.println("AutoCommit: " + connection.getAutoCommit());
			connection.close();
		} catch (SQLException e) {

			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		System.out.println("Test po³¹czenia z baz¹ wykonany. Sukces.");

		try {

			String sqlCreateTableSubject = "CREATE TABLE przedmiot (idp int not null, nazwa_przedmiotu char(20) not null)";
			String sqlCreateTableTeacher = "CREATE TABLE nauczyciel (idn int not null, nazwisko_nauczyciela char(30) not null, imie_nauczyciela char(20) not null)";
			String sqlCreateTableRating = "CREATE TABLE ocena (ido int not null, wartosc_opisowa char(20) not null, wartosc_numeryczna float not null)";
			String sqlCreateTableStudent = "CREATE TABLE uczen (idu int not null, nazwisko_ucznia char(30) not null, imie_ucznia char(20) not null)";
			String sqlCreateTableEvaluation = "CREATE TABLE ocenianie (idn int not null, idu int not null, idp int not null, ido int not null, rodzaj_oceny char(1) not null)";

			Connection connection = DriverManager.getConnection(LoginData.dataBaseUrl, LoginData.user,
					LoginData.password);
			System.out.println("AutoCommit: " + connection.getAutoCommit());
			Statement statement = connection.createStatement();

			DatabaseMetaData dbm = connection.getMetaData();

			ResultSet tables = dbm.getTables(null, null, "PRZEDMIOT", null);
			if (tables.next()) {
				System.out.println("Table subject exist in database.");
			} else {
				System.out.println(
						"Create table subject in database.  execute: " + statement.execute(sqlCreateTableSubject));
			}

			tables = dbm.getTables(null, null, "NAUCZYCIEL", null);
			if (tables.next()) {
				System.out.println("Table teacher exist in database.");
			} else {
				System.out.println(
						"Create table teacher in database. execute: " + statement.execute(sqlCreateTableTeacher));
			}

			tables = dbm.getTables(null, null, "OCENA", null);
			if (tables.next()) {
				System.out.println("Table rating exist in database.");
			} else {
				System.out.println(
						"Create table rating in database. execute: " + statement.execute(sqlCreateTableRating));
			}

			tables = dbm.getTables(null, null, "UCZEN", null);
			if (tables.next()) {
				System.out.println("Table student exist in database.");
			} else {
				System.out.println(
						"Create table student in database. execute: " + statement.execute(sqlCreateTableStudent));
			}

			tables = dbm.getTables(null, null, "OCENIANIE", null);
			if (tables.next()) {
				System.out.println("Table evaluation exist in database.");
			} else {
				System.out.println(
						"Create table evaluation in database. execute: " + statement.execute(sqlCreateTableEvaluation));
			}

			String sqlTestDataForSubject1 = "INSERT INTO przedmiot (idp, nazwa_przedmiotu) VALUES (1, 'Matematyka')";
			String sqlTestDataForSubject2 = "INSERT INTO przedmiot (idp, nazwa_przedmiotu) VALUES (5, 'Fizyka')";

			String sqlTestDataForTeacher1 = "INSERT INTO nauczyciel (idn, nazwisko_nauczyciela, imie_nauczyciela) VALUES (1, 'Kowalski', 'Adam')";
			String sqlTestDataForTeacher2 = "INSERT INTO nauczyciel (idn, nazwisko_nauczyciela, imie_nauczyciela) VALUES (5, 'Bednarska', 'Ewa')";

			String sqlTestDataForRating1 = "INSERT INTO ocena (ido, wartosc_opisowa, wartosc_numeryczna) VALUES (1, 'niedostateczny', 1.5)";
			String sqlTestDataForRating2 = "INSERT INTO ocena (ido, wartosc_opisowa, wartosc_numeryczna) VALUES (5, 'dostateczny', 3)";

			String sqlTestDataForStudent1 = "INSERT INTO uczen (idu, nazwisko_ucznia, imie_ucznia) VALUES (1, 'Wiœniewski', 'Karol')";
			String sqlTestDataForStudent2 = "INSERT INTO uczen (idu, nazwisko_ucznia, imie_ucznia) VALUES (5, 'Pawlak', 'Waldemar')";

			ResultSet rs = statement.executeQuery("SELECT * FROM przedmiot");
			if (rs.next()) {
				System.out.println("Table subject have data in database.");
			} else {
				System.out.println("Insert example data to table subject 1. execute:"
						+ statement.executeUpdate(sqlTestDataForSubject1));
				System.out.println("Insert example data to table subject 2. execute:"
						+ statement.executeUpdate(sqlTestDataForSubject2));
			}

			rs = statement.executeQuery("SELECT * FROM nauczyciel");
			if (rs.next()) {
				System.out.println("Table teacher have data in database.");
			} else {
				System.out.println("Insert example data to table teacher 1. execute:"
						+ statement.executeUpdate(sqlTestDataForTeacher1));
				System.out.println("Insert example data to table teacher 2. execute:"
						+ statement.executeUpdate(sqlTestDataForTeacher2));
			}

			rs = statement.executeQuery("SELECT * FROM ocena");
			if (rs.next()) {
				System.out.println("Table rating have data in database.");
			} else {
				System.out.println("Insert example data to table rating 1. execute:"
						+ statement.executeUpdate(sqlTestDataForRating1));
				System.out.println("Insert example data to table rating 2. execute:"
						+ statement.executeUpdate(sqlTestDataForRating2));
			}

			rs = statement.executeQuery("SELECT * FROM uczen");
			if (rs.next()) {
				System.out.println("Table student have data in database.");
			} else {
				System.out.println("Insert example data to table student 1. execute:"
						+ statement.executeUpdate(sqlTestDataForStudent1));
				System.out.println("Insert example data to table student 2. execute:"
						+ statement.executeUpdate(sqlTestDataForStudent2));
			}

			connection.close();
		} catch (SQLException e) {

			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		try {

			String sqlDataForEvaluation = "INSERT INTO ocenianie (idn, idu, idp, ido, rodzaj_oceny) VALUES (?,?,?,?,?)";

			Connection connection = DriverManager.getConnection(LoginData.dataBaseUrl, LoginData.user,
					LoginData.password);
			System.out.println("AutoCommit: " + connection.getAutoCommit());
			PreparedStatement preparedStatement = connection.prepareStatement(sqlDataForEvaluation);

			Scanner scanner = new Scanner(System.in);
			int inputTeacherId;
			int inputStudentId;
			int inputSubjectId;
			int inputRatingId;
			String inputRatingTypeId;

			System.out.println("Wype³nianie tabeli ocenianie");
			System.out.println("Jeœli chcesz przerwaæ program podaj 0 lub b³êdne dane");

			while (true) {

				System.out.println("Podaj id nauczyciela");
				if (scanner.hasNextInt()) {
					inputTeacherId = scanner.nextInt();
					if (checkIfIdExistInDataBase("NAUCZYCIEL", "idn", inputTeacherId, connection)) {
						preparedStatement.setInt(1, inputTeacherId);
					} else {
						System.out.println("Nie znaleziono nauczyciela o podanym ID w bazie danych");
						break;
					}
				} else {
					System.out.println("B³êdne dane wejœciowe. Nie podano wartosci INT");
					break;
				}

				System.out.println("Podaj id ucznia:");
				if (scanner.hasNextInt()) {
					inputStudentId = scanner.nextInt();
					if (checkIfIdExistInDataBase("UCZEN", "idu", inputStudentId, connection)) {
						preparedStatement.setInt(2, inputStudentId);
					} else {
						System.out.println("Nie znaleziono studenta o podanym ID w bazie danych");
						break;
					}
				} else {
					System.out.println("B³êdne dane wejœciowe. Nie podano wartosci INT");
					break;
				}

				System.out.println("Podaj id przedmiotu");
				if (scanner.hasNextInt()) {
					inputSubjectId = scanner.nextInt();
					if (checkIfIdExistInDataBase("PRZEDMIOT", "idp", inputSubjectId, connection)) {
						preparedStatement.setInt(3, inputSubjectId);
					} else {
						System.out.println("Nie znaleziono przedmiotu o podanym ID w bazie danych");
						break;
					}
				} else {
					System.out.println("B³êdne dane wejœciowe. Nie podano wartosci INT");
					break;
				}

				System.out.println("Podaj id oceny");
				if (scanner.hasNextInt()) {
					inputRatingId = scanner.nextInt();
					if (checkIfIdExistInDataBase("OCENA", "ido", inputRatingId, connection)) {
						preparedStatement.setInt(4, inputRatingId);
					} else {
						System.out.println("Nie znaleziono oceny o podanym ID w bazie danych");
						break;
					}
				} else {
					System.out.println("B³êdne dane wejœciowe. Nie podano wartosci INT");
					break;
				}

				// TODO
				System.out.println("Podaj typ oceny: „C” –cz¹stkowa, „S” –semestralna");
				if (scanner.hasNext()) {
					inputRatingTypeId = scanner.next().toUpperCase();
					if (inputRatingTypeId.equals("C")) {
						preparedStatement.setString(5, "C");
					} else if (inputRatingTypeId.equals("S")) {
						if (checkIfSRattingExistInDataBase(inputTeacherId, inputStudentId, inputSubjectId,
								connection)) {
							System.out.println("B³¹d! Ocena semestralna zosta³a ju¿ wystawiona");
							break;
						} else {
							preparedStatement.setString(5, "S");
						}
					} else {
						System.out.println(
								"B³êdne dane wejœciowe. Podano: " + inputRatingTypeId + " Nie podano wartosci C lub S");
						break;
					}
				}

				System.out.println("Insert data to table evaluation. execute:" + preparedStatement.executeUpdate());
			}

			connection.close();
		} catch (SQLException e) {

			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		System.out.println("Koniec programu!");

	}

	private static boolean checkIfSRattingExistInDataBase(int inputTeacherId, int inputStudentId, int inputSubjectId,
			Connection connection) throws SQLException {
		String sql = "SELECT * FROM ocenianie WHERE idn =" + inputTeacherId + "AND idu=" + inputStudentId + "AND idp="
				+ inputSubjectId+ "AND rodzaj_oceny = 'S'";

		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery(sql);
		return rs.next();
	}

	private static boolean checkIfIdExistInDataBase(String table, String id, int inputTeacherId, Connection connection)
			throws SQLException {

		String sql = "SELECT * FROM " + table + " WHERE " + id + " = " + inputTeacherId;

		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery(sql);
		return rs.next();
	}

}
