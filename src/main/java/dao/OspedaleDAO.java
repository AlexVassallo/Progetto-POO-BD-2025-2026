package dao;
import model.Ospedale;
import database.ConnessioneDatabase;

import java.sql.*;
import java.util.List;

public class OspedaleDAO {
    Connection connection;
    public  OspedaleDAO() throws SQLException {
        try{
            connection= ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Salva un ospedale nel database
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     * @see Ospedale
     * @param o ospedale da salvare nel database
     * @return se l'operazione e andata a buon fine
     * @throws SQLException se si verifica un errore durante la connessione al database oppure durante l'esecuzione della query
     */
    public boolean salvaOspedale(Ospedale o){
        String query= """
                INSERT INTO Ospedale (identificativo_ospedale,nome_ospedale)
                VALUES (?,?); 
                """;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, o.getIdentificativoOspedale());
            ps.setString(2, o.getNomeOspedale());
            return ps.execute();
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


    /**
     *
     * @param idOspedale
     * @return
     * @throws SQLException
     */
    public Ospedale getOspedale(String idOspedale) throws SQLException {
        String query= """
                SELECT identificativo_ospedale, nome_ospedale 
                FROM Ospedale
                WHERE identificativo_Ospedale = ?;
                """;

        PreparedStatement ps= connection.prepareStatement(query);
        ps.setString(1, idOspedale);
        ResultSet rs= ps.executeQuery();
        if(!rs.next()){
            throw new SQLDataException("Ospedale Non Trovato");
        }
        return new Ospedale(rs.getString(1),
                rs.getString(2));

    }


    public boolean rimuoviOspedale(String idOspedale){
        String query= """ 
                DELETE 
                FROM Ospedale
                WHERE (identificativo_Ospedale= ?);
                """;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, idOspedale);
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Chiude la connessione al database
     *
     * @author Alessio riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     *
     * @see ConnessioneDatabase
     * @see Connection
     *
     * @throws SQLException se si verifica un errore durante la chiusura della connessione
     */
    public void closeConnection() throws SQLException {
        connection.close();
    }
}
