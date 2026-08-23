package dao;

import database.ConnessioneDatabase;
import model.Medico;
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
                throw new SQLDataException("sala operatoria non trovata");
            }
            return new SalaOperatoria(rs.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void closeConnection() throws SQLException{
        connection.close();
    }

}
