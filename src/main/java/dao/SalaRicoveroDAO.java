package dao;

import database.ConnessioneDatabase;
import model.SalaRicovero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaRicoveroDAO {
    Connection connection;

    public SalaRicoveroDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean aggiungiSala(SalaRicovero s, String identificativoOspedale) throws SQLException {
        String query = """
                INSERT INTO Sala_ricovero (codice_sala, 
                identificativo_ospedale, 
                tipo_sala,
                numero_letti,
                letti_liberi)
                VALUES(?, ?, ?, ?, ?);""";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, s.getCodiceSala());
        ps.setString(2, identificativoOspedale);
        ps.setString(3, s.getTipoSala());
        ps.setInt(4, s.getNumeroLetti());
        ps.setInt(5, s.getLettiLiberi());

        return ps.execute();
    }

    public boolean rimuoviSala(String identificativoSala) throws SQLException {
        String query = """
                DELETE FROM Sala_ricovero
                WHERE (codice_sala= ?);
                """;
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, identificativoSala);
        return ps.execute();
    }


    public SalaRicovero getSalaRicovero(String identificativoSala) throws SQLException {
        String query = """
                SELECT codice_sala,
                tipo_sala,
                numero_letti
                FROM sala_ricovero
                WHERE codice_sala= ?;""";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, identificativoSala);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null; // Ritorna null se la sala non esiste ancora nel DB
                }
                return new SalaRicovero(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3)
                );
            }
        }
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}


