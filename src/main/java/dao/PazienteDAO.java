package dao;

import database.ConnessioneDatabase;

import java.sql.*;
import model.Paziente;

public class PazienteDAO {
    Connection connection;

    public PazienteDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void salvaPaziente(Paziente p) throws SQLException{
            String queryPersona = """
                    INSERT INTO Persona (codice_fiscale,
                    nome_persona,
                    cognome_persona,
                    data_di_nascita,
                    luogo_di_nascita,
                    indirizzo)
                     VALUES (?, ?, ?, ?, ?, ?)
                     ON CONFLICT (codice_fiscale) DO UPDATE SET
                         nome_persona = EXCLUDED.nome_persona,
                         cognome_persona = EXCLUDED.cognome_persona,
                         data_di_nascita = EXCLUDED.data_di_nascita,
                         luogo_di_nascita = EXCLUDED.luogo_di_nascita,
                         indirizzo = EXCLUDED.indirizzo;""";

            String queryPaziente= """
                    INSERT INTO paziente (identificativo_paziente,
                     codice_fiscale,
                     triage_paziente,
                     codice_sala_ricovero)
                     VALUES (?,?,?,?);""";

        Connection conn = null;
        try {
            conn = ConnessioneDatabase.getInstance().connection;
            // Disabilito l'autocommit per gestire la transazione manualmente
            conn.setAutoCommit(false);

        // faccio la prima INSERT dei dati anagrafici (dalla tabella persona)
        try (PreparedStatement ps = conn.prepareStatement(queryPersona)) {
            ps.setString(1, p.getCodiceFiscale());
            ps.setString(2, p.getNomePersona());
            ps.setString(3, p.getCognomePersona());
            ps.setDate(4, java.sql.Date.valueOf(p.getDataDiNascita()));
            ps.setString(5, p.getLuogoDiNascita());
            ps.setString(6, p.getIndirizzo());
            ps.executeUpdate();
        }
        // faccio la seconda insert dei dati del paziente(dalla tabella paziente)
        try(PreparedStatement ps= conn.prepareStatement(queryPaziente)){
            ps.setString(1, p.getIdentificativoPaziente());
            ps.setString(2, p.getCodiceFiscale());
            ps.setString(3, p.getTriagePaziente());
            try {
                if (p.getSalaAssociata() != null) {
                    ps.setString(4, p.getSalaAssociata().getCodiceSala());
                } else {
                    ps.setNull(4, Types.VARCHAR);
                }
            } catch (Exception e) {
                ps.setNull(4, Types.VARCHAR);
            }
            ps.executeUpdate();
        }
        }
        finally {
            if (conn != null) {
                conn.setAutoCommit(true); // Ripristina il comportamento standard
            }
        }
    }
    public boolean rimuoviPaziente(String identificativoPaziente){
        String query= """
                DELETE
                FROM paziente
                WHERE (identificativo_paziente=?);""";
        try{
            PreparedStatement ps= connection.prepareStatement(query);
            ps.setString(1,identificativoPaziente);
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Paziente getPaziente(String identificativoPaziente){
        String query= """
                SELECT
                pe.codice_fiscale,
                pe.nome_persona,
                pe.cognome_persona,
                pe.data_di_nascita,
                pe.luogo_di_nascita,
                pe.indirizzo,
                pa.identificativo_paziente,
                pa.triage_paziente,
                pa.codice_sala_ricovero
                FROM paziente pa
                JOIN persona pe ON pa.codice_fiscale = pe.codice_fiscale
                WHERE identificativo_paziente=?;
                """;
        try{
         PreparedStatement ps= connection.prepareStatement(query);
         ps.setString(1, identificativoPaziente);
         ResultSet rs= ps.executeQuery();
         if(!rs.next()){
             throw new SQLDataException("paziente non trovato");
         }
         String codiceSala= rs.getString("codice_sala_ricovero");
         return new Paziente(rs.getString(1),
                 rs.getString(2),
                 rs.getString(3),
                 rs.getDate(4) != null ? rs.getDate(4).toLocalDate() : null,
                 rs.getString(5),
                 rs.getString(6),
                 rs.getString(7),
                 rs.getString(8),
                 (codiceSala != null) ? new SalaRicoveroDAO().getSalaRicovero(codiceSala) : null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePaziente(Paziente p){
        String query= """
                UPDATE paziente
                SET Codice_sala_ricovero = ?
                WHERE identificativo_paziente = ?;
                """;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            try {
                ps.setString(1, p.getSalaAssociata().getCodiceSala());
            } catch (Exception e) {
                ps.setNull(1, Types.VARCHAR);
            }
            ps.setString(2, p.getIdentificativoPaziente());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void closeConnection() throws SQLException {
        connection.close();
    }
}
