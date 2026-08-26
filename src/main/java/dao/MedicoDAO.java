package dao;

import database.ConnessioneDatabase;
import model.Medico;
import java.time.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;


public class MedicoDAO {
        Connection connection;

        public MedicoDAO() {
            try{
                connection= ConnessioneDatabase.getInstance().connection;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Salva un medico nel database
         * @author Alessio Riccio
         * @author Alessandro Vassallo
         * @author Emanuele Todisco
         * @see Medico
         * @param m medico da salvare nel database
         * @return se l'operazione e andata a buon fine
         * @throws SQLException se si verifica un errore durante la connessione al database oppure durante l'esecuzione della query
         */
        public void aggiungiMedico(Medico m) throws SQLException {
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
            String queryMedico  = """
                    INSERT INTO Medico (identificativo_medico, 
                    codice_fiscale, 
                    tipo_medico, 
                    rango, data_anno_assunzione, 
                    is_amministratore, 
                    password, 
                    codice_sala_ricovero) 
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """;

            Connection conn = null;
            try {
                conn = ConnessioneDatabase.getInstance().connection;
                // Disabilito l'autocommit per gestire la transazione manualmente
                conn.setAutoCommit(false);

                // faccio la prima INSERT dei dati anagrafici (dalla tabella persona)
                try (PreparedStatement ps = conn.prepareStatement(queryPersona)) {
                    ps.setString(1, m.getCodiceFiscale());
                    ps.setString(2, m.getNomePersona());
                    ps.setString(3, m.getCognomePersona());
                    ps.setDate(4, java.sql.Date.valueOf(m.getDataDiNascita()));
                    ps.setString(5, m.getLuogoDiNascita());
                    ps.setString(6, m.getIndirizzo());
                    ps.executeUpdate();
                }

                // Seconda INSERT dove inserisco i dati specifici del Medico(salvati sulla tabella medici)
                try (PreparedStatement ps = conn.prepareStatement(queryMedico)) {
                    ps.setString(1, m.getIdentificativoMedico());
                    ps.setString(2, m.getCodiceFiscale()); // Fa da Foreign Key verso Persona
                    ps.setString(3, m.getTipoMedico());
                    ps.setString(4, m.getRango());
                    ps.setTimestamp(5, m.getDataAnnoAssunzione() != null ? java.sql.Timestamp.valueOf(m.getDataAnnoAssunzione()) : null);
                    ps.setBoolean(6, m.getIsAmministratore());
                    ps.setString(7, m.getPassword());

                    // Gestione relazione con SalaRicovero (se null inserisce NULL in SQL)
                    try {
                        if (m.getSalaAssociata() != null) {
                            ps.setString(8, m.getSalaAssociata().getCodiceSala());
                        } else {
                            ps.setNull(8, Types.VARCHAR);
                        }
                    } catch (Exception e) {
                        ps.setNull(8, Types.VARCHAR);
                    }

                    ps.executeUpdate();
                }

                // 4. Se entrambe le query hanno successo, conferma la transazione
                conn.commit();

            } catch (SQLException e) {
                // In caso di errore, annulla qualsiasi modifica fatta sul DB
                if (conn != null) {
                    conn.rollback();
                }
                throw e;
            }
            finally {
                if (conn != null) {
                    conn.setAutoCommit(true); // Ripristina il comportamento standard
                    conn.close();
                }
            }
        }

        public boolean rimuoviMedico(String identificativoMedico) throws SQLException{
            String queryMedico= """
                    DELETE 
                    FROM medico
                    WHERE (identificativo_medico=?);
                    """;
            try {
                PreparedStatement ps = connection.prepareStatement(queryMedico);
                ps.setString(1, identificativoMedico);
                return ps.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        public Medico getMedico(String identificativoMedico) throws SQLException{
            String query= """
                    SELECT
                    p.codice_fiscale,
                    p.nome_persona,
                    p.cognome_persona,
                    p.data_di_nascita,
                    p.luogo_di_nascita,
                    p.indirizzo,
                    m.identificativo_medico,
                    m.tipo_medico,
                    m.rango,
                    m.data_anno_assunzione,
                    m.is_amministratore,
                    m.password,
                    m.codice_sala_ricovero
                    FROM medico m
                    JOIN Persona p ON m.codice_fiscale = p.codice_fiscale
                    WHERE m.identificativo_medico=?;
                    """;
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, identificativoMedico);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new SQLDataException("medico non trovato");

                }
                String codiceSala = rs.getString(13);

                return new Medico(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4) != null ? rs.getDate(4).toLocalDate() : null,
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getTimestamp(10) != null ? rs.getTimestamp(10).toLocalDateTime() : null,
                        (codiceSala != null) ? new SalaRicoveroDAO().getSalaRicovero(codiceSala) : null,
                        rs.getBoolean(11),
                        rs.getString(12));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        public List<Medico> getMedici() throws SQLException {
            List<Medico> listaMedici= new ArrayList<>();
            String query= """
                    SELECT
                    p.codice_fiscale,
                    p.nome_persona,
                    p.cognome_persona,
                    p.data_di_nascita,
                    p.luogo_di_nascita,
                    p.indirizzo,
                    m.identificativo_medico,
                    m.tipo_medico,
                    m.rango,
                    m.data_anno_assunzione,
                    m.is_amministratore,
                    m.password,
                    m.codice_sala_ricovero
                    FROM Medico m
                    JOIN Persona p ON m.codice_fiscale= p.codice_fiscale
                    """;
            PreparedStatement pr= connection.prepareStatement(query);
            ResultSet resultSet=pr.executeQuery();
            while(resultSet.next()){
                listaMedici.add(new Medico(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4)!= null ? resultSet.getDate(4).toLocalDate() : null,
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getTimestamp(10) != null ? resultSet.getTimestamp(10).toLocalDateTime() : null,
                        resultSet.getString(13)!= null ? new SalaRicoveroDAO().getSalaRicovero(resultSet.getString(13)) : null,
                        resultSet.getBoolean(11),
                        resultSet.getString(12)));
            }
            return listaMedici;
        }

        public void updateMedico(Medico medico){
            String query= """
                    UPDATE medico
                    SET Codice_sala_ricovero=?
                    WHERE identificativo_medico=?;
                    """;
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                try{
                    ps.setString(1, medico.getSalaAssociata().getCodiceSala());
                } catch (Exception e) {
                    ps.setNull(1, Types.VARCHAR);
                }
                ps.setString(2, medico.getIdentificativoMedico());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        public void closeConnection() throws SQLException {
            connection.close();
        }

}
