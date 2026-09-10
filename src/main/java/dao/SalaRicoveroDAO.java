package dao;

import database.ConnessioneDatabase;
import model.SalaRicovero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaRicoveroDAO {
    Connection connection;

    /**
     * Connette la SalaRicovero al database
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     * @throws RuntimeException
     */
    public SalaRicoveroDAO() {
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Aggiunge la SalaRicovero al database con le sue info
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     * @author Alessio Riccio
     * @throws RuntimeException
     * @param s sala da aggiungere
     * @param identificativoOspedale l'ospedale in cui verrà aggiunta la sala
     * @return l'esito dell'operazione
     */
    public boolean aggiungiSala(SalaRicovero s, String identificativoOspedale){
        String query = """
                INSERT INTO Sala_ricovero (codice_sala, 
                identificativo_ospedale, 
                tipo_sala,
                numero_letti,
                letti_liberi)
                VALUES(?, ?, ?, ?, ?);""";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, s.getCodiceSala());
            ps.setString(2, identificativoOspedale);
            ps.setString(3, s.getTipoSala());
            ps.setInt(4, s.getNumeroLetti());
            ps.setInt(5, s.getLettiLiberi());

            return ps.execute();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    /**
     * restituisce la SalaRicovero con anche le info a lei associate E la loro disponibilità
     * @author Alessio Ricco
     * @author Emanuele Todisco
     * @author Alessandro Vassallo
     * @throws RuntimeException
     * @param identificativoSala
     * @return la sala ricovero selezionata
     * @throws SQLException
     */
    public SalaRicovero getSalaRicovero(String identificativoSala) throws SQLException {
        String query = """
                SELECT codice_sala,
                tipo_sala,
                numero_letti,
                letti_liberi
                FROM sala_ricovero
                WHERE codice_sala= ?;
                """;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, identificativoSala);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                 SalaRicovero sala=new SalaRicovero(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3));
                sala.setLettiLiberi(rs.getInt(4));
                return sala;
            }
        }
    }

    /**
     * restituisce una lista delle SaleRicovero con le info a loro associate
     * @author Alessandro Vassallo
     * @author Alessio Riccio
     * @author Emanuele Todisco
     *
     * @throws RuntimeException
     * @return la lista delle sale
     */
    public List<SalaRicovero> getSaleRicovero(){
        String query= """
                SELECT codice_sala,
                tipo_sala,
                numero_letti,
                letti_liberi
                FROM Sala_ricovero
                """;
        try{
            PreparedStatement ps= connection.prepareStatement(query);
            List<SalaRicovero> saleRicovero= new ArrayList<>();
            try{
                ResultSet rs =ps.executeQuery();
                while (rs.next()){
                    SalaRicovero salaRicovero= new SalaRicovero(rs.getString(1),
                            rs.getString(2),
                            rs.getInt(3));
                    salaRicovero.setLettiLiberi(rs.getInt(4));
                    saleRicovero.add(salaRicovero);
                }

            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return saleRicovero;

        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Tale funzione aggiorna il numero dei letti di una sala
     * @author Alessandro Vassallo
     * @author Alessio Riccio
     * @author Emanuele Todisco
     * @throws RuntimeException
     * @param codiceSala
     * @param lettiLiberi
     * @return l'esito dell'operazione
     */
    public boolean aggiornaLetti(String codiceSala, int lettiLiberi){
        String query= """
                UPDATE sala_ricovero
                SET letti_liberi=?
                WHERE codice_sala=?;
                """;
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1, lettiLiberi);
            preparedStatement.setString(2, codiceSala);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Restituisce una lista delle SaleRicovero di uno specifico Ospedale
     * @author Alessio Riccio
     * @author Emanuele Todisco
     * @author Alessandro Vassallo
     * @throws RuntimeException
     * @param identificativoOspedale
     * @return la lista delle sale
     */
    public List<SalaRicovero> getSalaRicoveroPerOspedale(String identificativoOspedale){
        String query= """
                SELECT codice_sala,
                tipo_sala,
                numero_letti,
                letti_liberi
                FROM Sala_ricovero
                WHERE identificativo_ospedale = ?;
                """;
        try{
            PreparedStatement ps= connection.prepareStatement(query);
            ps.setString(1, identificativoOspedale);
            List<SalaRicovero> saleRicovero= new ArrayList<>();
            try{
                ResultSet rs =ps.executeQuery();
                while (rs.next()){
                    SalaRicovero salaRicovero= new SalaRicovero(rs.getString(1),
                            rs.getString(2),
                            rs.getInt(3));
                    salaRicovero.setLettiLiberi(rs.getInt(4));
                    saleRicovero.add(salaRicovero);
                }

            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }

        return saleRicovero;

        }
        catch (SQLException e){
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
    public void closeConnection() throws SQLException {
        connection.close();
    }
}


