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


		try (
				Connection connection = DriverManager.getConnection(LoginData.dataBaseUrl, LoginData.user,
						LoginData.password);
				Statement statement = connection.createStatement();
				){

			String sqlCreateTableSubject = "CREATE TABLE przedmiot (idp int not null, nazwa_przedmiotu char(20) not null)";
			String sqlCreateTableTeacher = "CREATE TABLE nauczyciel (idn int not null, nazwisko_nauczyciela char(30) not null, imie_nauczyciela char(20) not null)";
			String sqlCreateTableRating = "CREATE TABLE ocena (ido int not null, wartosc_opisowa char(20) not null, wartosc_numeryczna float not null)";
			String sqlCreateTableStudent = "CREATE TABLE uczen (idu int not null, nazwisko_ucznia char(30) not null, imie_ucznia char(20) not null)";
			String sqlCreateTableEvaluation = "CREATE TABLE ocenianie (idn int not null, idu int not null, idp int not null, ido int not null, rodzaj_oceny char(1) not null)";

			System.out.println("AutoCommit: " + connection.getAutoCommit());

			DatabaseMetaData dbm = connection.getMetaData();

			ResultSet tables = dbm.getTables(null, null, "PRZEDMIOT", null);
			if (tables.next()) {
				System.out.println("Tabela PRZEDMIOT ju¿ istnieje w bazie danych.");
			} else {
				System.out.println(
						"Tworzenie tabeli PRZEDMIOT w bazie danych. execute: " + statement.execute(sqlCreateTableSubject));
			}

			tables = dbm.getTables(null, null, "NAUCZYCIEL", null);
			if (tables.next()) {
				System.out.println("Tabela NAUCZYCIEL ju¿ istnieje w bazie danych.");
			} else {
				System.out.println(
						"Tworzenie tabeli NAUCZYCIEL w bazie danych. execute: " + statement.execute(sqlCreateTableTeacher));
			}

			tables = dbm.getTables(null, null, "OCENA", null);
			if (tables.next()) {
				System.out.println("Tabela OCENA ju¿ istnieje w bazie danych.");
			} else {
				System.out.println(
						"Tworzenie tabeli OCENA w bazie danych. execute: " + statement.execute(sqlCreateTableRating));
			}

			tables = dbm.getTables(null, null, "UCZEN", null);
			if (tables.next()) {
				System.out.println("Tabela UCZEN ju¿ istnieje w bazie danych.");
			} else {
				System.out.println(
						"Tworzenie tabeli UCZEN w bazie danych. execute: " + statement.execute(sqlCreateTableStudent));
			}

			tables = dbm.getTables(null, null, "OCENIANIE", null);
			if (tables.next()) {
				System.out.println("Tabela OCENIANIE ju¿ istnieje w bazie danych.");
			} else {
				System.out.println(
						"Tworzenie tabeli OCENIANIE w bazie danych. execute: " + statement.execute(sqlCreateTableEvaluation));
			}

			String sqlTestDataForSubject1 = "INSERT INTO przedmiot (idp, nazwa_przedmiotu) VALUES (1, 'Matematyka')";
			String sqlTestDataForSubject2 = "INSERT INTO przedmiot (idp, nazwa_przedmiotu) VALUES (5, 'Fizyka')";

			String sqlTestDataForTeacher1 = "INSERT INTO nauczyciel (idn, nazwisko_nauczyciela, imie_nauczyciela) VALUES (1, 'Kowalski', 'Adam')";
			String sqlTestDataForTeacher2 = "INSERT INTO nauczyciel (idn, nazwisko_nauczyciela, imie_nauczyciela) VALUES (5, 'Bednarska', 'Ewa')";

			String sqlTestDataForRating1 = "INSERT INTO ocena (ido, wartosc_opisowa, wartosc_numeryczna) VALUES (1, 'niedostateczny i pó³', 1.5)";
			String sqlTestDataForRating2 = "INSERT INTO ocena (ido, wartosc_opisowa, wartosc_numeryczna) VALUES (5, 'dostateczny', 3)";

			String sqlTestDataForStudent1 = "INSERT INTO uczen (idu, nazwisko_ucznia, imie_ucznia) VALUES (1, 'Wiœniewski', 'Karol')";
			String sqlTestDataForStudent2 = "INSERT INTO uczen (idu, nazwisko_ucznia, imie_ucznia) VALUES (5, 'Pawlak', 'Waldemar')";

			ResultSet rs = statement.executeQuery("SELECT * FROM przedmiot");
			if (rs.next()) {
				System.out.println("Tabela PRZEDMIOT zawiera dane.");
			} else {
				System.out.println("Wstawianie testowych danych do tabeli PRZEDMIOT 1. execute:"
						+ statement.executeUpdate(sqlTestDataForSubject1));
				System.out.println("Wstawianie testowych danych do tabeli PRZEDMIOT 2. execute:"
						+ statement.executeUpdate(sqlTestDataForSubject2));
			}

			rs = statement.executeQuery("SELECT * FROM nauczyciel");
			if (rs.next()) {
				System.out.println("Tabela NAUCZYCIEL zawiera dane.");
			} else {
				System.out.println("Wstawianie testowych danych do tabeli NAUCZYCIEL 1. execute:"
						+ statement.executeUpdate(sqlTestDataForTeacher1));
				System.out.println("Wstawianie testowych danych do tabeli NAUCZYCIEL 2. execute:"
						+ statement.executeUpdate(sqlTestDataForTeacher2));
			}

			rs = statement.executeQuery("SELECT * FROM ocena");
			if (rs.next()) {
				System.out.println("Tabela OCENA zawiera dane.");
			} else {
				System.out.println("Wstawianie testowych danych do tabeli OCENA 1. execute:"
						+ statement.executeUpdate(sqlTestDataForRating1));
				System.out.println("Wstawianie testowych danych do tabeli OCENA 2. execute:"
						+ statement.executeUpdate(sqlTestDataForRating2));
			}

			rs = statement.executeQuery("SELECT * FROM uczen");
			if (rs.next()) {
				System.out.println("Tabela UCZEN zawiera dane..");
			} else {
				System.out.println("Wstawianie testowych danych do tabeli UCZEN 1. execute:"
						+ statement.executeUpdate(sqlTestDataForStudent1));
				System.out.println("Wstawianie testowych danych do tabeli UCZEN 2. execute:"
						+ statement.executeUpdate(sqlTestDataForStudent2));
			}

			rs.close();
		} catch (SQLException e) {

			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return;
		}

		String sqlDataForEvaluation = "INSERT INTO ocenianie (idn, idu, idp, ido, rodzaj_oceny) VALUES (?,?,?,?,?)";

		try (
				Connection connection = DriverManager.getConnection(LoginData.dataBaseUrl, LoginData.user,
						LoginData.password);
				PreparedStatement preparedStatement = connection.prepareStatement(sqlDataForEvaluation);

				Scanner scanner = new Scanner(System.in);
				) {

			System.out.println("AutoCommit: " + connection.getAutoCommit());

			String inputTeacherId = "";
			String inputStudentId = "";
			String inputSubjectId = "";
			String inputRatingId = "";
			String inputRatingTypeId = "";

			System.out.println("Wype³nianie tabeli ocenianie");
			System.out.println(" ");

			boolean rating = true;

			while (rating) {
				System.out.println("Jeœli chcesz przerwaæ program podaj 0");
				System.out.println("Podaj id nauczyciela:");

				if (scanner.hasNext()) {
					inputTeacherId = scanner.next();
					if (inputTeacherId.contentEquals("0")) {
						rating = false;
						scanner.close();
						break;
					}
					preparedStatement.setString(1, inputTeacherId);
				}
				System.out.println("Jeœli chcesz przerwaæ program podaj 0");
				System.out.println("Podaj id ucznia:");

				if (scanner.hasNext()) {
					inputStudentId = scanner.next();
					if (inputStudentId.contentEquals("0")) {
						rating = false;
						scanner.close();
						break;
					}
					preparedStatement.setString(2, inputStudentId);
				}

				System.out.println("Jeœli chcesz przerwaæ program podaj 0");
				System.out.println("Podaj id przedmiotu:");

				if (scanner.hasNext()) {
					inputSubjectId = scanner.next();
					if (inputSubjectId.contentEquals("0")) {
						rating = false;
						scanner.close();
						break;
					}
					preparedStatement.setString(3, inputSubjectId);
				}
				System.out.println("Jeœli chcesz przerwaæ program podaj 0");
				System.out.println("Podaj id oceny:");

				if (scanner.hasNext()) {
					inputRatingId = scanner.next();
					if (inputRatingId.contentEquals("0")) {
						rating = false;
						scanner.close();
						break;
					}
					preparedStatement.setString(4, inputRatingId);
				}

				System.out.println("Jeœli chcesz przerwaæ program podaj 0");
				System.out.println("Podaj typ oceny: „C” –cz¹stkowa, „S” –semestralna");

				if (scanner.hasNext()) {
					inputRatingTypeId = scanner.next().toUpperCase();
					if (inputRatingTypeId.equals("C")) {
						preparedStatement.setString(5, "C");
					} else if (inputRatingTypeId.equals("S")) {
						preparedStatement.setString(5, "S");
					} else if (inputRatingTypeId.equals("0")) {
						rating = false;
						scanner.close();
						break;
					}
				}

				if (checkIfIdExistInDataBase("OCENA", "ido", inputRatingId, connection)) {
					if (checkIfIdExistInDataBase("PRZEDMIOT", "idp", inputSubjectId, connection)) {
						if (checkIfIdExistInDataBase("UCZEN", "idu", inputStudentId, connection)) {
							if (checkIfIdExistInDataBase("NAUCZYCIEL", "idn", inputTeacherId, connection)) {
								System.out.println("Insert data to table evaluation. execute:"
										+ preparedStatement.executeUpdate());
							} else {
								System.out.println("Error report -\n" +
												"ORA-02291: naruszono wiêzy spójnoœci (idn) - nie znaleziono klucza nadrzêdnego"
										);
							}
						} else {
							System.out.println("Error report -\n" +
									"ORA-02291: naruszono wiêzy spójnoœci (idu) - nie znaleziono klucza nadrzêdnego");
						}
					} else {
						System.out.println("Error report -\n" +
								"ORA-02291: naruszono wiêzy spójnoœci (idp) - nie znaleziono klucza nadrzêdnego");
					}
				} else {
					System.out.println("Error report -\n" +
							"ORA-02291: naruszono wiêzy spójnoœci (ido) - nie znaleziono klucza nadrzêdnego");
				}

			}
		} catch (SQLException e) {
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		System.out.println("Koniec programu!");
	}

	private static boolean checkIfIdExistInDataBase(String table, String id, String inputTeacherId,
			Connection connection) throws SQLException {

		String sql = "SELECT * FROM " + table + " WHERE " + id + " = " + inputTeacherId;
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery(sql);
		return rs.next();
	}
}
