package dao;
import java.sql.*;
import database.ConnessioneDatabase;
import model.Referto;

public class RefertoDAO {
    Connection connection;
    public RefertoDAO(){
        try{
            connection= ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean salvaReferto(Referto r) {
        String query = """
                INSERT INTO Referto(id_referto, id_operazione, diagnosi,
                 data_emissione, trattamento_effettuato, note, prescrizioni_terapeutiche, esito_finale)
                 VALUES(?,?,?,?,?,?,?,?);
                """;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, r.getIdReferto());
            preparedStatement.setString(2, r.getOperazioneEffettuata().getIdOperazione());
            preparedStatement.setString(3, r.getDiagnosi());
            preparedStatement.setTimestamp(4, r.getDataEmissione() != null ? java.sql.Timestamp.valueOf(r.getDataEmissione()) : null);
            preparedStatement.setString(5, r.getTrattamentoEffettuato());
            preparedStatement.setString(6, r.getNoteMedico());
            preparedStatement.setString(7, r.getPrescrizioni());
            preparedStatement.setString(8, r.getEsitoFinale());
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Referto getReferto(String idReferto){
            String query= """
                    SELECT id_referto, id_operazione, diagnosi, data_emissione, trattamento_effettuato,
                    note, prescrizioni_terapeutiche, esito_finale
                    FROM referto
                    WHERE Id_referto=?;
                    """;
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, idReferto);
                ResultSet rs=preparedStatement.executeQuery();
                if(!rs.next()){
                    throw new RuntimeException("referto non trovato");
                }
                String codiceOperazione= rs.getString(2);

                return new Referto(rs.getString(1),
                        (codiceOperazione!=null) ? new OperazioneDAO().getOperazione(codiceOperazione) : null,
                        rs.getString(3),
                        rs.getTimestamp(4) != null ? rs.getTimestamp(4 ).toLocalDateTime() : null,
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }


}
