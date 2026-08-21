package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {

	/**
	 * La classe ConnessioneDatabase implementa il pattern Singleton per gestire
	 * la connessione al database PostgreSQL utilizzato dall'applicazione.
	 * Garantisce che ci sia una sola istanza attiva della connessione durante
	 * il ciclo di vita dell'applicazione. Fornisce inoltre metodi per accedere
	 * alla connessione stessa in modo sicuro.
	 * @author Alessio Riccio
	 * @author Alessandro Vassallo
	 * @author Emanuele Todisco
	 * @see Connection
	 * @see DriverManager
	 * @see SQLException
	 *
	 */

	// ATTRIBUTI
	private static ConnessioneDatabase instance;
	public Connection connection = null;
	private String nome = "postgres";
	private String password = "<Alericcio44>";
	private String url = "jdbc:postgresql://127.0.0.1:5432/pronto soccorso";
	private String driver = "org.postgresql.Driver";

	// COSTRUTTORE
	private ConnessioneDatabase() throws SQLException {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, nome, password);

		} catch (ClassNotFoundException ex) {
			System.out.println("Database Connection Creation Failed : " + ex.getMessage());
			ex.printStackTrace();
		}

	}


	public static ConnessioneDatabase getInstance() throws SQLException {
		if (instance == null) {
			instance = new ConnessioneDatabase();
		} else if (instance.connection.isClosed()) {
			instance = new ConnessioneDatabase();
		}
		return instance;
	}
}