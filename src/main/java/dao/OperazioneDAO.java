package dao;

import database.ConnessioneDatabase;
import model.Medico;
import model.Operazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperazioneDAO {
    Connection connection;

    public OperazioneDAO(){
        try{
            connection= ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void salvaOperazione(Operazione o)  throws SQLException {
        String queryOperazione= """
                INSERT INTO Operazione(id_operazione, id_paziente, codice_sala_operatoria, tipo_operazione,
                data_ora_inizio, data_ora_fine, esito)
                VALUES(?,?,?,?,?,?,?);
                """;
        String queryMediciOperazione= """
                INSERT INTO Operazione_medico(id_operazione, id_medico)
                VALUES(?,?);
                """;

        Connection conn=null;
        try {
            conn= ConnessioneDatabase.getInstance().connection;

            conn.setAutoCommit(false);
            try(PreparedStatement preparedStatement = conn.prepareStatement(queryOperazione)){

                preparedStatement.setString(1, o.getIdOperazione());
                preparedStatement.setString(2, o.getPazienteOperato().getIdentificativoPaziente());
                preparedStatement.setString(3, o.getSalaUtilizzata().getCodiceSala());
                preparedStatement.setString(4, o.getTipoOperazione());
                preparedStatement.setTimestamp(5, o.getDataOraInizio() != null ? java.sql.Timestamp.valueOf(o.getDataOraInizio()) : null);
                preparedStatement.setTimestamp(6, o.getDataOraFine() != null ? java.sql.Timestamp.valueOf(o.getDataOraFine()) : null);
                preparedStatement.setString(7, o.getEsito());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try(PreparedStatement preparedStatement = conn.prepareStatement(queryMediciOperazione)){
                for(Medico m:o.getMediciPartecipanti()){
                    preparedStatement.setString(1, o.getIdOperazione());
                    preparedStatement.setString(2, m.getIdentificativoMedico());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
            conn.commit();
        }
        catch (SQLException e) {
            if(conn != null){
                conn.rollback();
            }
            throw e;
        }
        finally {
            if(conn != null){
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public Operazione getOperazione(String idOperazione){
        String query = """
                SELECT id_operazione, id_paziente, 
                codice_sala_operatoria, tipo_operazione, data_ora_inizio,
                data_ora_fine, esito
                FROM Operazione
                WHERE id_operazione = ?;
                """;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,idOperazione);
            ResultSet rs= preparedStatement.executeQuery();
            if(!rs.next()){
                throw new RuntimeException("operazione non trovata");
            }

            String idPaziente= rs.getString(2);
            String idSalaOperatoria= rs.getString(3);

            Operazione operazione = new Operazione(rs.getString(1),
                    getMediciOperazione(idOperazione),
                    (idPaziente!= null) ? new PazienteDAO().getPaziente(idPaziente) : null,
                    (idSalaOperatoria != null) ? new SalaOperatoriaDAO().getSalaOperatoria(idSalaOperatoria) : null,
                    rs.getString(4),
                    rs.getTimestamp(5) != null ? rs.getTimestamp(5).toLocalDateTime() : null);
            operazione.setDataOraFine(rs.getTimestamp(6) != null ? rs.getTimestamp(6).toLocalDateTime() : null);
            operazione.setEsito(rs.getString(7));
            return operazione;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Medico> getMediciOperazione(String idOperazione){
        String query= """
                SELECT id_operazione, id_medico
                FROM operazione_medico
                WHERE id_operazione=?;
                """;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idOperazione);
            ResultSet rs= preparedStatement.executeQuery();
            List<Medico> listaMediciOperazione=new ArrayList<>();
            MedicoDAO medicoDAO= new MedicoDAO();
            while (rs.next()){
                Medico medicoDaAggiungere = medicoDAO.getMedico(rs.getString(2));
                listaMediciOperazione.add(medicoDaAggiungere);
            }
            return listaMediciOperazione;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void closeConnection() throws SQLException {
        connection.close();
    }
}
