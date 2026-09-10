package dao;

import database.ConnessioneDatabase;
import model.Medico;
import model.Paziente;
import model.SalaOperatoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SalaOperatoriaDAO {
    Connection connection;
    /**
     * Connette la SalaOperatoria al database
     * @author Emanuele Todisco
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @throws RuntimeException
     */
    public  SalaOperatoriaDAO(){
        try{
            connection= ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Salva la SalaOperatoria nel database
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     * @author Alessio Riccio
     * @throws RuntimeException
     * @param s sala operatoria da aggiungere
     * @param identificativoOspedale l'ospedale dove appartiene la sala operatoria
     * @return l'esito dell'operazione
     */
    public boolean salvaSala(SalaOperatoria s, String identificativoOspedale){
        String query= """
                INSERT INTO Sala_operatoria(codice_sala, 
                identificativo_ospedale,
                is_disponibile,
                identificativo_paziente)
               VALUES(?,?,?,?);""";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, s.getCodiceSala());
            ps.setString(2, identificativoOspedale);
            ps.setBoolean(3, s.getIsDisponibile());

            if (s.getPazienteAssociato() != null) {
                ps.setString(4, s.getPazienteAssociato().getIdentificativoPaziente());
            } else {
                ps.setNull(4, Types.VARCHAR);
            }
            return ps.execute();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    /**
     * restituisce tutti i dati della SalaOperatoria
     * @author Alessandro Vassallo
     * @author Alessio Riccio
     * @author Emanuele Todisco
     * @see Medico
     * @see PazienteDAO
     * @see SalaOperatoriaDAO
     * @throws RuntimeException
     * @param identificativoSala l'identificativo della sala selezionata
     * @return la sala operatoria selezionata
     */
    public SalaOperatoria getSalaOperatoria(String identificativoSala){
        String querySala= """
                SELECT codice_sala,
                identificativo_ospedale,
                is_disponibile,
                identificativo_paziente
                FROM sala_operatoria
                WHERE codice_sala=?;
                """;
        try {
            PreparedStatement ps= connection.prepareStatement(querySala);
            ps.setString(1, identificativoSala);
            ResultSet rs= ps.executeQuery();
            if(!rs.next()){
                throw new RuntimeException("sala operatoria non trovata");
            }

            SalaOperatoria so= new SalaOperatoria(rs.getString(1));
            so.setIsDisponibile(rs.getBoolean(3));
            List<Medico> listaMedici=getMediciPerSalaOperatoria(identificativoSala);
            so.setMediciAssociati(listaMedici);
            PazienteDAO pazienteDAO = new PazienteDAO();
            String idPaziente=rs.getString(4);
            if(idPaziente==null){
                so.setPazienteAssociato(null);
            }
            else{
                Paziente paziente = pazienteDAO.getPaziente(idPaziente);
                so.setPazienteAssociato(paziente);
            }
            return so;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * restituisce la lista delle SaleOperatorie con le loro info associate E la loro disponibilità
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     * @throws RuntimeException
     * @see SalaOperatoriaDAO
     * @see Medico
     * @return la lista delle sale operatorie
     */
    public List<SalaOperatoria> getSaleOperatorie(){
        String query= """
                SELECT codice_sala, is_disponibile, identificativo_paziente
                FROM sala_operatoria
                """;
        try{
            PreparedStatement ps= connection.prepareStatement(query);
            List<SalaOperatoria> listaSale= new ArrayList<>();
            try{
                ResultSet rs=ps.executeQuery();
                while (rs.next()){
                    SalaOperatoria so= new SalaOperatoria(rs.getString(1));
                    so.setIsDisponibile(rs.getBoolean(2));
                    String idPaziente = rs.getString("identificativo_paziente");
                    Paziente paziente = null;
                    if (idPaziente != null) {
                        PazienteDAO pazienteDAO = new PazienteDAO();
                        paziente = pazienteDAO.getPaziente(idPaziente);
                    }
                    so.setPazienteAssociato(paziente);
                    List<Medico> mediciPerSala = getMediciPerSalaOperatoria(rs.getString(1));
                    so.setMediciAssociati(mediciPerSala);
                    listaSale.add(so);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return listaSale;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * restituisce la lista delle SaleOperartorie di uno specifico ospedale
     * @author Alessandro Vassallo
     * @author Alessio Riccio
     * @author Emanuele Todisco
     * @throws RuntimeException
     * @param identificativoOspedale
     * @return LA LISTA DELLE SALE
     */
    public List<SalaOperatoria> getSaleOperatoriePerOspedale(String identificativoOspedale){
        String query= """
                SELECT codice_sala, is_disponibile, identificativo_paziente
                FROM sala_operatoria
                WHERE identificativo_ospedale = ?;
                """;
        try{
            PreparedStatement ps= connection.prepareStatement(query);
            ps.setString(1,identificativoOspedale);
            List<SalaOperatoria> listaSale= new ArrayList<>();
            try{
                ResultSet rs=ps.executeQuery();
                while (rs.next()){
                    SalaOperatoria so= new SalaOperatoria(rs.getString(1));
                    String idPaziente = rs.getString("identificativo_paziente");
                    Paziente paziente = null;
                    if (idPaziente != null) {
                        PazienteDAO pazienteDAO = new PazienteDAO();
                        paziente = pazienteDAO.getPaziente(idPaziente);
                        so.setIsDisponibile(false);
                    }
                    else {
                        so.setIsDisponibile(true);
                    }
                    so.setPazienteAssociato(paziente);
                    List<Medico> mediciPerSala = getMediciPerSalaOperatoria(rs.getString(1));
                    so.setMediciAssociati(mediciPerSala);
                    listaSale.add(so);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return listaSale;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Restituisce la lista dei medici di una specifica SalaOperatoria associata
     * @author Emanuele Todisco
     * @author Alessandro Vassallo
     * @author Alessio Riccio
     * @throws RuntimeException
     * @see Medico
     * @see SalaOperatoriaDAO
     * @param idSalaOperatoria l'identificativo della sala operatoria selezionata
     * @return la lista dei medici
     */
    public List<Medico> getMediciPerSalaOperatoria(String idSalaOperatoria){
        List<Medico> medici= new ArrayList<>();
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
                JOIN Sala_Operatoria_Medico som ON m.identificativo_medico = som.identificativo_medico
                WHERE som.codice_sala = ?;
                """;
        try{
            PreparedStatement ps= connection.prepareStatement(query);
            ps.setString(1, idSalaOperatoria);
            try {
                ResultSet resultSet= ps.executeQuery();
                while (resultSet.next()){
                    Medico medico=new Medico(resultSet.getString(1),
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
                            resultSet.getString(12));
                    medici.add(medico);

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medici;
    }

    /**
     * aggiorna lo stato della sala operatoria, se il paziente è presente oppure è assente
     * @author Alessio Riccio
     * @author Emanuele Todisco
     * @author Alessandro Vassallo
     * @throws RuntimeException
     * @see SalaOperatoriaDAO
     * @param so la sala da aggiornare
     */
    public void aggiornaSala(SalaOperatoria so){
        String query= """
                UPDATE sala_operatoria
                SET identificativo_paziente = ?
                WHERE codice_sala = ?;
                """;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            if (so.getPazienteAssociato() != null) {
                ps.setString(1, so.getPazienteAssociato().getIdentificativoPaziente());
            } else {
                ps.setNull(1, java.sql.Types.VARCHAR);
            }
            ps.setString(2, so.getCodiceSala());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Serve per aggiornare la Disponibilità delle varie Sale
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     * @see SalaOperatoriaDAO
     * @throws RuntimeException
     * @param so la sala da aggiornare
     */
    public void aggiornaDisponibilitaSala(SalaOperatoria so){
        String query= """
                UPDATE sala_operatoria
                SET Is_disponibile = ?
                WHERE codice_sala = ?;
                """;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setBoolean(1, so.getIsDisponibile());
            ps.setString(2, so.getCodiceSala());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Aggiunge un Medico ad una SalaOperatoria associata
     * @author Emanuele Todisco
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @see Medico
     * @see SalaOperatoriaDAO
     * @throws RuntimeException
     * @param idMedico l'identificativo del medico
     * @param codiceSala il codice della sala
     */
    public void aggiungiMedicoAllaSala(String idMedico, String codiceSala){
        String query= """
                INSERT INTO Sala_operatoria_medico(codice_sala, identificativo_medico)
                VALUES (?,?);
                """;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1, codiceSala);
            preparedStatement.setString(2, idMedico);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Rimuove un Medico da una sala operatoia associata
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     * @see Medico
     * @see SalaOperatoriaDAO
     * @throws RuntimeException
     * @param idMedico l'identificativo del medico
     */
    public void rimuoviMedicoAllaSala(String idMedico){
        String query= """
                DELETE FROM sala_operatoria_medico
                WHERE identificativo_medico = ?
                """;
        try {
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1, idMedico);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Chiude la connessione col database
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     * @throws SQLException
     */
    public void closeConnection() throws SQLException{
        connection.close();
    }

}
