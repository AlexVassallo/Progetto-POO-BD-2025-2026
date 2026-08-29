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
    public  SalaOperatoriaDAO(){
        try{
            connection= ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    public boolean rimuoviSala(String identificativoSala){
        String query= """
                DELETE FROM Sala_operatoria
                WHERE (codice_sala=?);
                """;
        try{
            PreparedStatement ps= connection.prepareStatement(query);
            ps.setString(1, identificativoSala);
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
                return null;
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

    public void aggiornaSala(SalaOperatoria so){
        String query= """
                UPDATE sala_operatoria
                SET identificativo_paziente = ?, 
                    is_disponibile=?
                WHERE codice_sala = ?;
                """;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            if (so.getPazienteAssociato() != null) {
                ps.setString(1, so.getPazienteAssociato().getIdentificativoPaziente());
            } else {
                ps.setNull(1, java.sql.Types.VARCHAR);
            }
            ps.setBoolean(2, so.getIsDisponibile());

            ps.setString(3, so.getCodiceSala());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    public void closeConnection() throws SQLException{
        connection.close();
    }

}
