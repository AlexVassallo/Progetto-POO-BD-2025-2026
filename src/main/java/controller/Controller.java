package controller;

import dao.*;
import model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import exceptions.ChiaveException;
import exceptions.ParameterMissingException;

import javax.naming.AuthenticationException;
import java.security.InvalidParameterException;



public class Controller {

    //liste di oggetti
    private ArrayList<Medico> medici = new ArrayList<Medico>();
    private ArrayList<Ospedale> ospedali = new ArrayList<Ospedale>();
    private ArrayList<Paziente> pazienti = new ArrayList<Paziente>();
    private ArrayList<Referto> referti = new ArrayList<Referto>();

    private String medicoSelezionato;//medico che fa il login

    /*
    metodi che salva su una variabile l'identificativo del medico che fa il login
    e sulla pagina principale stamperà poi l'id del medico che sta usando il programma
     */
    public String getMedicoSelezionato() {
        return medicoSelezionato;
    }

    public void setMedicoSelezionato(String medicoSelezionato) {
        this.medicoSelezionato = medicoSelezionato;
    }


    /**
     * crea e aggiunge alla lista un medico,
     * lancia un eccezione se l'identificativo è vuoto esiste già,
     * lancia un eccezione se la password è incorretta,
     * lancia un eccezione se un parametro è vuoto
     *
     *
     * @param identificativoMedico codice identificativo del medico
     * @param password
     * @param codiceFiscale
     * @param nome
     * @param cognome
     * @param dataDiNascita
     * @param luogoDiNascita
     * @param indirizzo
     * @param tipoMedico
     * @param rango
     * @param dataAnnoAssunzione
     * @param isAmministratore
     * @throws ParameterMissingException
     * @throws AuthenticationException
     * @throws ChiaveException
     *
     * @see Medico
     * @see ChiaveException
     * @see AuthenticationException
     * @see ParameterMissingException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void creaMedico(String identificativoMedico,
                           String password,
                           String codiceFiscale,
                           String nome,
                           String cognome,
                           LocalDate dataDiNascita,
                           String luogoDiNascita,
                           String indirizzo,
                           String tipoMedico,
                           String rango,
                           LocalDateTime dataAnnoAssunzione,
                           boolean isAmministratore) throws ParameterMissingException, AuthenticationException, ChiaveException, SQLException {
        if (identificativoMedico.isBlank()) {
            throw new ChiaveException("identificativo mancante");
        }
        if (password.isBlank() || password.length() < 9) {
            throw new AuthenticationException("password vuota o troppo corta(la password deve avere almeno 9 caratteri)");
        }
        if (codiceFiscale.length() != 16) {
            throw new ParameterMissingException("formato codice fiscale non corretto(il formato corretto è di 16 caratteri)");
        }
        if (nome.isBlank()) {
            throw new ParameterMissingException("nome mancante");
        }
        if (cognome.isBlank()) {
            throw new ParameterMissingException("cognome mancante");
        }
        if (dataDiNascita == null) {
            throw new ParameterMissingException("data di nascita mancante");
        }
        if (luogoDiNascita.isBlank()) {
            throw new ParameterMissingException("luogo di nascita mancante");
        }
        if (indirizzo.isBlank()) {
            throw new ParameterMissingException("indirizzo mancante");
        }
        if (tipoMedico.isBlank()) {
            throw new ParameterMissingException("tipologia medico mancante");
        }
        if (rango.isBlank()) {
            throw new ParameterMissingException("rango mancante");
        }
        if (dataAnnoAssunzione == null) {
            throw new ParameterMissingException("data e anno di assunzione mancante");
        }
        for (Medico me : medici) {
            if (me.getIdentificativoMedico().equals(identificativoMedico)) {
                throw new ChiaveException("identificatico medico già esistente");
            }
        }
        //salvataggio obsoleto ma ancora funzionante
        Medico m = new Medico(codiceFiscale,
                nome,
                cognome,
                dataDiNascita,
                luogoDiNascita,
                indirizzo,
                identificativoMedico,
                tipoMedico,
                rango,
                dataAnnoAssunzione,
                null,
                isAmministratore,
                password);

        //salvataggio nuovo suldatabase
        MedicoDAO medicoDAO= new MedicoDAO();
        medicoDAO.aggiungiMedico(m);
        medicoDAO.closeConnection();
    }

    /**
     * crea e aggiunge alla lista un nuovo ospedale,
     * lancia un eccezione se l'identificativo è vuoto o esiste già,
     * lancia un eccezione se un parametro è vuoto
     *
     * @param identidicativoOspedale codice identificativo dell'ospedale
     * @param nomeOspedale
     * @throws ParameterMissingException
     * @throws ChiaveException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void creaOspedale(String identidicativoOspedale,
                             String nomeOspedale) throws ParameterMissingException, ChiaveException, SQLException {

        if (identidicativoOspedale.isBlank()) {
            throw new ChiaveException("identificativo mancante");
        }

        if (esisteIdentificativoOspedale(identidicativoOspedale)) {
            throw new ChiaveException("ospedale gia esistente");
        }

        if (nomeOspedale.isBlank()) {
            throw new ParameterMissingException("nome ospedale mancante");
        }
        //salvataggio obsoleto ma ancora funzionante
        Ospedale o = new Ospedale(identidicativoOspedale, nomeOspedale);
        ospedali.add(o);

        //salvataggio sul database
        OspedaleDAO ospedaleDAO = new OspedaleDAO();
        ospedaleDAO.salvaOspedale(o);
        ospedaleDAO.closeConnection();


    }

    /**
     * crea e aggiunge un nuovo paziente alla lista,
     * lancia un eccezione se l'identificativo è vuoto o esiste già,
     * lancia un eccezione se l'id della sala in cui il paziente è associato non esiste in nessun ospedale,
     * lancia un eccezione se un parametro è vuoto
     *
     * @param codiceFiscale
     * @param nomePersona
     * @param cognomePersona
     * @param dataDiNascita
     * @param luogoDiNascita
     * @param indirizzo
     * @param identificativoPaziente codice identificativo del paziente
     * @param triagePaziente
     * @param idSalaAssociata sala {@link SalaRicovero} in cui il paziente è associato
     * @throws ParameterMissingException
     * @throws ChiaveException
     *
     * @see SalaRicovero
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void creaPaziente(String codiceFiscale,
                             String nomePersona,
                             String cognomePersona,
                             LocalDate dataDiNascita,
                             String luogoDiNascita,
                             String indirizzo,
                             String identificativoPaziente,
                             String triagePaziente,
                             String idSalaAssociata) throws ParameterMissingException, ChiaveException, SQLException {
        if (codiceFiscale.length() != 16) {
            throw new ParameterMissingException("formato codice fiscale non rispettato(deve essere di 16)");
        }
        if (nomePersona.isBlank()) {
            throw new ParameterMissingException("nome persona vuota");
        }
        if (cognomePersona.isBlank()) {
            throw new ParameterMissingException("cognome persona vuota");
        }
        if (dataDiNascita == null) {
            throw new ParameterMissingException("data di nascita vuota");
        }
        if (luogoDiNascita.isBlank()) {
            throw new ParameterMissingException("luogo di nascita vuota");
        }
        if (indirizzo.isBlank()) {
            throw new ParameterMissingException("indirizzo vuota");
        }
        if (identificativoPaziente.isBlank() || esisteIdentificativo(identificativoPaziente)) {
            throw new ChiaveException("identificativo vuoto oppure gia esistente");
        }
        if (indirizzo.isBlank()) {
            throw new ParameterMissingException("indirizzo vuota");
        }
        if (triagePaziente.isBlank()) {
            throw new ParameterMissingException("triage paziente vuota");
        }

        if (idSalaAssociata.isBlank()) {
            throw new ParameterMissingException("campo id sala associata vuota");
        }

        SalaRicovero salaRicovero = null;
        boolean salaTrovata = false;
        PazienteDAO pazienteDAO=new PazienteDAO();
        SalaRicoveroDAO salaRicoveroDAO= new SalaRicoveroDAO();
        SalaRicovero sr= salaRicoveroDAO.getSalaRicovero(idSalaAssociata);

        if (sr==null) {
            throw new ChiaveException("id sala non trovata");
        }
        else {
            sr.occupaLetto();
        }
        Paziente p = new Paziente(codiceFiscale,
                nomePersona,
                cognomePersona,
                dataDiNascita,
                luogoDiNascita,
                indirizzo,
                identificativoPaziente,
                triagePaziente,
                sr);

        pazienteDAO.salvaPaziente(p);
    }

    /**
     * scorre la lista per vedere se esiste un paziente in specifico
     *
     * @param identificativoPaziente codice identificativo del paziente
     *
     * @return true se l'identificativo esiste
     * @return false se l'identificativo non esiste
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public boolean esisteIdentificativo(String identificativoPaziente) {
        for (Paziente p : pazienti) {
            if (p.getIdentificativoPaziente().equals(identificativoPaziente)) {
                return true;
            }
        }
        return false;
    }

    /**
     * scorre la lista e verifica se esiste già un ospedale con quel'identificativo
     *
     * @param identificativoOspedale codice identificativo dell'ospedale
     * @return true se l'identificativo esiste
     * @return false se l'identificativo non esiste
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public boolean esisteIdentificativoOspedale(String identificativoOspedale) {
        for (Ospedale o : ospedali) {
            if (o.getIdentificativoOspedale().equals(identificativoOspedale)) {
                return true;
            }
        }
        return false;
    }

    /**
     * crea e aggiunge un nuovo referto alla lista,
     * lancia un eccezione se gli id inseriti sono vuoti, oppure non esistono nelle liste,
     * lancia un eccezione se gli altri parametri sono vuoti
     *
     * @param idPaziente codice identificativo del paziente
     * @param idMedico
     * @param dataOraArrivo
     * @param dataOraUscita
     * @param diagnosi
     * @param trattamentoEffettuato
     * @param esitoFinale
     * @throws ParameterMissingException
     * @throws ChiaveException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void creaReferto(String idPaziente,
                            String idMedico,
                            LocalDateTime dataOraArrivo,
                            LocalDateTime dataOraUscita,
                            String diagnosi,
                            String trattamentoEffettuato,
                            String esitoFinale) throws ParameterMissingException, ChiaveException {

        Paziente paziente = null;

        if (idPaziente.isBlank()) {
            throw new ChiaveException("identificativo mancante");
        }
        boolean pazienteTrovato = false;
        for (Paziente p : pazienti) {
            if (p.getIdentificativoPaziente().equals(idPaziente)) {
                paziente = p;
                pazienteTrovato = true;
                break;
            }
        }
        if (!pazienteTrovato) {
            throw new ChiaveException("il paziente inserito non esiste");
        }


        Medico medico = null;
        boolean medicoTrovato = false;

        if (idMedico.isBlank()) {
            throw new ParameterMissingException("medico mancante");
        }

        for (Medico me : medici) {
            if (me.getIdentificativoMedico().equals(idMedico)) {
                medico = me;
                medicoTrovato = true;
                break;
            }
        }

        if (!medicoTrovato) {
            throw new ChiaveException("il medico inserito non esiste");
        }


        if (dataOraArrivo == null) {
            throw new ParameterMissingException("data ora di arrivo vuoto");
        }
        if (dataOraUscita == null) {
            throw new ParameterMissingException("data ora di uscita vuoto");
        }
        if (diagnosi.isBlank()) {
            throw new ParameterMissingException("diagnosi vuota");
        }
        if (trattamentoEffettuato.isBlank()) {
            throw new ParameterMissingException("trattamento inserito vuoto");
        }
        if (esitoFinale.isBlank()) {
            throw new ParameterMissingException("esito inserito vuoto");
        }
        Referto r = new Referto(paziente,
                medico,
                dataOraArrivo,
                dataOraUscita,
                diagnosi,
                trattamentoEffettuato,
                esitoFinale);
        referti.add(r);
    }

    /**
     * crea e aggiunge alla lista interna dell ospedale una nuova sala operatoria,
     * lancia un eccezione se l'id ospedale è vuoto oppure non esiste,
     * lancia un eccezione se l'id della sala operatoria è gia esistente oppure vuota,
     * lancia un eccezione se gli altri parametri sono vuoti
     *
     *
     * @param identificativOspedale codice identificativo dell'ospedale
     * @param codiceSala
     * @throws ParameterMissingException
     * @throws ChiaveException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void creaSalaOperatoria(String identificativOspedale,
                                   String codiceSala) throws ParameterMissingException, ChiaveException, SQLException {
        if (identificativOspedale.isBlank() || !esisteOspedale(identificativOspedale)) {
            throw new ChiaveException("nome ospedale non esistente oppure campo vuoto");
        }
        if (codiceSala.isBlank()) {
            throw new ParameterMissingException("codice sala vuoto");
        }
        SalaOperatoriaDAO salaOperatoriaDAO= new SalaOperatoriaDAO();
        try{
            if(salaOperatoriaDAO.getSalaOperatoria(identificativOspedale)!=null){
                throw new ChiaveException("identificativo sala già esistente");
            }
            SalaOperatoria salaDAO= new SalaOperatoria(codiceSala);
            salaOperatoriaDAO.salvaSala(salaDAO, identificativOspedale);
        }
        finally {
            salaOperatoriaDAO.closeConnection();
        }
    }

    /**
     * verifica se esiste gia uno specifico ospedale
     *
     * @param identificativoOspedale
     * @return true se l'ospedale esiste
     * @return false se l'ospedale non esiste
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public boolean esisteOspedale(String identificativoOspedale) throws SQLException {
        Ospedale ospedaleTrovato;
        OspedaleDAO ospedaleDAO= new OspedaleDAO();
        ospedaleTrovato=ospedaleDAO.getOspedale(identificativoOspedale);
        ospedaleDAO.closeConnection();
        if(ospedaleTrovato == null) {
            return false;
        }
         return true;
    }

    /**
     * crea e aggiunge alla lista interna dell'ospedale{@link Ospedale} una nuova sala ricovero,
     * lancia un eccezione se l'id ospedale è vuoto oppure non esiste nella lista,
     * lancia un eccezione se gli altri parametri sono vuoti
     *
     * @param identificativoOspedale codice identificativo dell'ospedale
     * @param codiceSala codice identificativo della nuova sala
     * @param tipoSala
     * @param numeroLetti
     * @throws ParameterMissingException
     * @throws ChiaveException
     *
     * @see Ospedale
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void creaSalaRicovero(String identificativoOspedale,
                                 String codiceSala,
                                 String tipoSala,
                                 int numeroLetti) throws ParameterMissingException, ChiaveException, SQLException {

        if (identificativoOspedale.isBlank() || !esisteOspedale(identificativoOspedale)) {
            throw new ChiaveException("identificativo ospedale inesistente oppure il campo inserito è vuoto");
        }
        if (codiceSala.isBlank()) {
            throw new ChiaveException("codice sala vuoto");
        }
        if (tipoSala.isBlank()) {
            throw new ParameterMissingException("tipo sala vuoto");
        }
        if (numeroLetti < 1) {
            throw new ParameterMissingException("campo numero letti vuoto, oppure minore di 1");
        }

        SalaRicoveroDAO salaRicoveroDAO=new SalaRicoveroDAO();
        try {
            if (salaRicoveroDAO.getSalaRicovero(codiceSala) != null) {
                throw new ChiaveException("Identificativo sala già esistente");
            }
            SalaRicovero salaDAO = new SalaRicovero(codiceSala, tipoSala, numeroLetti);
            salaRicoveroDAO.aggiungiSala(salaDAO, identificativoOspedale);
        }
        finally {
            salaRicoveroDAO.closeConnection();
        }
    }

    /**
     * effettua il login di un utente gia registrato,
     * lancia un eccezione se l'id e password sono vuoti,
     * lancia un eccezione se l'id del medico non esiste,
     * lancia un eccezione se la password inserita è sbagliata
     *
     * @param identificativo codice identificativo del medico
     * @param password password del medico
     *
     * @return true se il login va a buon fine senza lanciare eccezioni
     * @throws ChiaveException
     * @throws AuthenticationException
     * @throws InvalidParameterException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public boolean login(String identificativo,
                         String password) throws ChiaveException, AuthenticationException, InvalidParameterException, SQLException {
        if (identificativo.isBlank()) {
            throw new ChiaveException("identificativo vuoto");
        }
        if (password.isBlank()) {
            throw new ChiaveException("password vuota");
        }

        MedicoDAO medicoDAO=new MedicoDAO();
        Medico medicoTrovato= medicoDAO.getMedico(identificativo);

        if (medicoTrovato == null) {
            throw new AuthenticationException("medico non trovato, prova a fare registrati");
        }

        if (!medicoTrovato.getPassword().equals(password)) {
            throw new AuthenticationException("password incorretta");
        }

        setMedicoSelezionato(identificativo);
        medicoDAO.closeConnection();
        return true;
    }

    /**
     * ritorna una lista di stringhe contenente le informazioni di un medico,
     * lancia un eccezione se l'identificativo inserito non corrisponde alla lista di medico
     *
     * @param idMedico codice identificativo del medico
     * @return medico, l'array di stringhe delle informazioni della classe {@link Medico}
     * @throws ChiaveException
     *
     * @see Medico
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public String[] getMedico(String idMedico) throws ChiaveException, SQLException {
        MedicoDAO medicoDAO=new MedicoDAO();
        Medico me= medicoDAO.getMedico(idMedico);
            if (me.getIdentificativoMedico().equals(idMedico)) {
                String[] medico = new String[13];
                medico[0] = me.getCodiceFiscale();
                medico[1] = me.getNomePersona();
                medico[2] = me.getCognomePersona();
                medico[3] = me.getDataDiNascita().toString();
                medico[4] = me.getLuogoDiNascita();
                medico[5] = me.getIndirizzo();
                medico[6] = me.getIdentificativoMedico();
                medico[7] = me.getTipoMedico();
                medico[8] = me.getRango();
                medico[9] = me.getDataAnnoAssunzione().toString();
                try {
                    medico[10] = me.getSalaAssociata().getCodiceSala();
                } catch (Exception e) {
                    medico[10] = "Nessuna sala";
                }
                medico[11] = Boolean.valueOf(me.getIsAmministratore()).toString();
                medico[12] = me.getPassword();

                medicoDAO.closeConnection();

                return medico;
            }

        throw new ChiaveException("id medico non trovato");
    }

    /**
     * ritorna una lista di stringhe della classe {@link Paziente},
     * lancia un eccezione se l'idPaziente non esiste nella lista pazienti
     *
     * @param idPaziente codice identificativo del paziente
     * @return paziente, l'array di stringhe delle informazioni della classe{@link Paziente}
     * @throws ChiaveException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public String[] getPaziente(String idPaziente) throws ChiaveException, SQLException {
        PazienteDAO pazienteDAO= new PazienteDAO();
        Paziente pa= pazienteDAO.getPaziente(idPaziente);
            if (pa.getIdentificativoPaziente().equals(idPaziente)) {
                String[] paziente = new String[9];
                paziente[0] = pa.getCodiceFiscale();
                paziente[1] = pa.getNomePersona();
                paziente[2] = pa.getCognomePersona();
                paziente[3] = pa.getDataDiNascita().toString();
                paziente[4] = pa.getLuogoDiNascita();
                paziente[5] = pa.getIndirizzo();
                paziente[6] = pa.getIdentificativoPaziente();
                paziente[7] = pa.getTriagePaziente();
                try{
                    paziente[8] = pa.getSalaAssociata().getCodiceSala();
                }
                catch(Exception ex){
                    paziente[8]= "nessuna sala";
                }

                pazienteDAO.closeConnection();
                return paziente;
            }

        throw new ChiaveException("paziente non trovato");
    }

    /**
     * verifica se è gia allocato un medico in una sala operatoria
     *
     * @param identificativoMedico codice identificativo del medico
     * @return true se il medico è allocato
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public boolean eGiaAllocatoMedSalOp(String identificativoMedico) {
        for (Ospedale o : ospedali) {
            List<SalaOperatoria> listaSale = o.getSaleOperatorie();
            for (SalaOperatoria so : listaSale) {
                List<Medico> listaMedici = so.getMediciAssociati();
                for (Medico me : listaMedici) {
                    if (me.getIdentificativoMedico().equals(identificativoMedico)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * alloca il medico in una sala operatoria,
     * lancia un eccezione se i campi sono vuoti,
     * lancia un eccezione se l'id del medico non esiste nella lista di medici,
     * lancia un eccezione se l'id della sala operatoria non esiste nella lista interna delle sale operatorie,
     * lancia un eccezione se il medico esiste gia in un altra sala operatoria oppure in una sala ricovero
     *
     * @param idMedico codice identificativo del medico
     * @param idSalaOperatoria codice identificativo della sala operatoria
     * @throws ChiaveException
     * @throws IllegalStateException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void allocaMedicoSalaOperatoria(String idMedico, String idSalaOperatoria) throws ChiaveException, IllegalStateException {
        if (idMedico.isBlank()) {
            throw new ChiaveException("campo id paziente vuoto");
        }
        if (idSalaOperatoria.isBlank()) {
            throw new ChiaveException("campo id sala operatoria vuoto");
        }

        Medico medicoDaAllocare = null;
        for (Medico me : medici) {
            if (me.getIdentificativoMedico().equals(idMedico)) {
                medicoDaAllocare = me;
                break;
            }
        }

        if (medicoDaAllocare == null) {
            throw new ChiaveException("medico non trovato");
        }

        if (eGiaAllocatoMedSalOp(idMedico)) {
            throw new IllegalStateException("il medico esiste già in un altra sala operatoria");
        }

        boolean haGiaSala = true;
        try {
            medicoDaAllocare.getSalaAssociata();
        } catch (Exception e) {
            haGiaSala = false;
        }
        if (haGiaSala) {
            throw new IllegalStateException("il medico è già in una sala ricovero");
        }


        boolean salaTrovata = false;

        for (Ospedale o : ospedali) {
            List<SalaOperatoria> listaSale = o.getSaleOperatorie();
            for (SalaOperatoria so : listaSale) {
                if (so.getCodiceSala().equals(idSalaOperatoria)) {
                    so.aggiungiMedico(medicoDaAllocare);
                    medicoDaAllocare.setSalaAssociata(null);
                    salaTrovata = true;
                }
            }
        }
        if (!salaTrovata) {
            throw new ChiaveException("sala operatoria non trovata");
        }
    }
    /**
     * alloca il medico in una sala ricovero,
     * lancia un eccezione se i campi sono vuoti,
     * lancia un eccezione se l'id del medico non esiste nella lista di medici,
     * lancia un eccezione se l'id della sala ricovero non esiste nella lista interna delle sale ricovero,
     * lancia un eccezione se il medico esiste gia in un altra sala ricovero oppure in una sala operatoria
     *
     * @param idMedico codice identificativo del medico
     * @param idSalaRicovero codice identificativo della sala ricovero
     * @throws ChiaveException
     * @throws IllegalStateException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void allocaMedicoSalaRicovero(String idMedico, String idSalaRicovero) throws ChiaveException, IllegalStateException {
        if (idMedico.isBlank()) {
            throw new ChiaveException("campo id paziente vuoto");
        }
        if (idSalaRicovero.isBlank()) {
            throw new ChiaveException("campo id sala operatoria vuoto");
        }

        Medico medicoDaAllocare = null;
        for (Medico me : medici) {
            if (me.getIdentificativoMedico().equals(idMedico)) {
                medicoDaAllocare = me;
                break;
            }
        }

        if (medicoDaAllocare == null) {
            throw new ChiaveException("medico non trovato");
        }

        if (eGiaAllocatoMedSalOp(idMedico)) {
            throw new IllegalStateException("il medico è gia su una sala operatoria");
        }

        boolean haGiaSala = true;
        try {
            medicoDaAllocare.getSalaAssociata();
        } catch (Exception e) {
            haGiaSala = false;
        }
        if (haGiaSala) {
            throw new IllegalStateException("il medico è già in una sala ricovero");
        }

        boolean salaTrovata = false;
        for (Ospedale o : ospedali) {
            List<SalaRicovero> listaSale = o.getSaleRicovero();
            for (SalaRicovero sr : listaSale) {
                if (sr.getCodiceSala().equals(idSalaRicovero)) {
                    medicoDaAllocare.setSalaAssociata(sr);
                    salaTrovata = true;
                    break;
                }
            }
        }
        if (!salaTrovata) {
            throw new ChiaveException("sala non trovata");
        }
    }

    /**
     * verifica se è gia allocato un paziente in una sala operatoria
     *
     * @param identificativoPaziente codice identificativo del medico
     * @return true se il medico è allocato
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public boolean eGiaAllocatoPazSalOp(String identificativoPaziente) {
        for (Ospedale o : ospedali) {
            List<SalaOperatoria> listaSale = o.getSaleOperatorie();
            for (SalaOperatoria so : listaSale) {
                Paziente p = so.getPazienteAssociato();

                if (p != null && p.getIdentificativoPaziente().equals(identificativoPaziente)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * alloca il paziente in una sala ricovero,
     * lancia un eccezione se i campi sono vuoti,
     * lancia un eccezione se l'id del paziente non esiste nella lista di pazienti,
     * lancia un eccezione se l'id della sala ricovero non esiste nella lista interna delle sale ricovero,
     * lancia un eccezione se il paziente esiste gia in un altra sala ricovero oppure in una sala operatoria,
     * lancia un eccezione se la sala ricovero è piena
     *
     * @param idPaziente codice identificativo del paziente
     * @param idSalaRicovero codice identificativo della sala ricovero
     * @throws ChiaveException
     * @throws IllegalStateException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void allocaPazienteSalaRicovero(String idPaziente, String idSalaRicovero) throws ChiaveException, IllegalStateException {
        if (idPaziente.isBlank()) {
            throw new ChiaveException("campo id paziente vuoto");
        }
        if (idSalaRicovero.isBlank()) {
            throw new ChiaveException("campo id sala operatoria vuoto");
        }

        Paziente pazienteDaAllocare = null;
        SalaRicovero sala=null;

        for (Paziente pa : pazienti) {
            if (pa.getIdentificativoPaziente().equals(idPaziente)) {
                try{
                    sala=pa.getSalaAssociata();
                }
                catch (Exception e) {
                    pazienteDaAllocare = pa;
                    break;
                }

                if(sala!=null){
                    throw new IllegalStateException("il paziente è gia in una sala ricovero");
                }

            }
        }

        if (pazienteDaAllocare == null) {
            throw new ChiaveException("paziente non trovato");
        }

        if (eGiaAllocatoPazSalOp(idPaziente)) {
            throw new IllegalStateException("il paziente è gia su una sala operatoria");
        }

        boolean salaTrovata = false;

        for (Ospedale o : ospedali) {
            List<SalaRicovero> listaSale = o.getSaleRicovero();
            for (SalaRicovero sr : listaSale) {
                if (sr.getCodiceSala().equals(idSalaRicovero)) {
                    try {
                        sr.occupaLetto();
                    } catch (IllegalStateException e) {
                        throw new IllegalStateException("la sala ricovero è piena");
                    }
                    pazienteDaAllocare.setSalaAssociata(sr);
                    salaTrovata = true;
                    break;
                }
            }
        }
        if (!salaTrovata) {
            throw new ChiaveException("sala non trovata");
        }

    }

    /**
     * alloca il paziente in una sala operatoria,
     * lancia un eccezione se i campi sono vuoti,
     * lancia un eccezione se l'id del paziente non esiste nella lista di pazienti,
     * lancia un eccezione se l'id della sala operatoria non esiste nella lista interna delle sale operatorie,
     * lancia un eccezione se il paziente esiste gia in un altra sala operatoria oppure in una sala ricovero,
     * lancia un eccezione se la sala operatoria è già occupata da un altro paziente
     *
     * @param idPaziente codice identificativo del paziente
     * @param idSalaOperatoria codice identificativo della sala operatoria
     * @throws ChiaveException
     * @throws IllegalStateException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void allocaPazienteSalaOperatoria(String idPaziente, String idSalaOperatoria) throws ChiaveException, IllegalStateException {

        if (idPaziente.isBlank()) {
            throw new ChiaveException("campo id paziente vuoto");
        }
        if (idSalaOperatoria.isBlank()) {
            throw new ChiaveException("campo id sala operatoria vuoto");
        }

        Paziente pazienteDaAllocare = getPazienteDaAllocare(idPaziente);

        if (eGiaAllocatoPazSalOp(idPaziente)) {
            throw new IllegalStateException("il paziente è gia allocato in una sala operatoria");
        }


        boolean salaTrovata = false;

        for (Ospedale o : ospedali) {
            List<SalaOperatoria> listaSale = o.getSaleOperatorie();
            for (SalaOperatoria so : listaSale) {
                if (so.getCodiceSala().equals(idSalaOperatoria)) {
                    if (!so.getIsDisponibile()) {
                        throw new IllegalStateException("la sala è già occupata da un altro paziente");
                    }
                    so.occupaSala(pazienteDaAllocare);
                    pazienteDaAllocare.setSalaAssociata(null);
                    salaTrovata = true;
                    break;
                }
            }
        }
        if (!salaTrovata) {
            throw new ChiaveException("sala non trovata");
        }

    }

    /**
     * metodo che restituisce un oggetto{@link Paziente} da allocare in una sala ricovero
     *
     * @param idPaziente codice identificativo del paziente
     * @return pazienteDaAllocare, l'oggetto {@link Paziente}
     * @throws ChiaveException
     * @throws IllegalStateException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    private Paziente getPazienteDaAllocare(String idPaziente) throws ChiaveException, IllegalStateException {
        Paziente pazienteDaAllocare = null;
        for (Paziente pa : pazienti) {
            if (pa.getIdentificativoPaziente().equals(idPaziente)) {
                pazienteDaAllocare = pa;
                break;
            }
        }
        if (pazienteDaAllocare == null) {
            throw new ChiaveException("paziente non trovato");
        }

        try{
            pazienteDaAllocare.getSalaAssociata();
            throw new IllegalStateException("il paziente e gia in un altra sala ricovero");
        }
        catch (Exception e){
            return pazienteDaAllocare;
        }
    }

    /**
     * dealloca un paziente da una sala operatoria,
     * lancia un eccezione se i campi sono vuoti,
     * lancia un eccezione se non trova un paziente esistente nella lista,
     * lancia un eccezione se il paziente non si trova in una sala operatoria
     *
     *
     * @param idPaziente codice identificativo del paziente
     * @throws IllegalStateException
     * @throws ChiaveException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void deallocaPazienteSalaOperatoria(String idPaziente) throws IllegalStateException, ChiaveException {
        if (idPaziente.isBlank()) {
            throw new ChiaveException("id paziente vuoto");
        }
        Paziente pazienteDaDeallocare = null;
        for (Paziente pa : pazienti) {
            if (pa.getIdentificativoPaziente().equals(idPaziente)) {
                pazienteDaDeallocare = pa;
            }
        }
        if (pazienteDaDeallocare == null) {
            throw new ChiaveException("paziente non trovato");
        }
        if (!eGiaAllocatoPazSalOp(idPaziente)) {
            throw new IllegalStateException("il paziente non si trova attualmente in una sala operatoria");
        }

        for (Ospedale o : ospedali) {
            List<SalaOperatoria> listaSale = o.getSaleOperatorie();
            for (SalaOperatoria so : listaSale) {
                Paziente p = so.getPazienteAssociato();
                if (p != null && p.getIdentificativoPaziente().equals(idPaziente)) {
                    so.liberaSala();
                }
            }
        }

    }

    /**
     * dealloca un paziente da una sala ricovero,
     * lancia un eccezione se i campi sono vuoti,
     * lancia un eccezione se non trova un paziente esistente nella lista,
     * lancia un eccezione se il paziente non si trova in una sala ricovero
     *
     *
     * @param idPaziente codice identificativo del paziente
     * @throws IllegalStateException
     * @throws ChiaveException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void deallocaPazienteSalaRicovero(String idPaziente) throws IllegalStateException, ChiaveException {
        if (idPaziente.isBlank()) {
            throw new ChiaveException("campo paziente vuoto");
        }

        boolean pazienteTrovato=false;
        for (Paziente pa : pazienti) {
            if (pa.getIdentificativoPaziente().equals(idPaziente)) {
                pazienteTrovato=true;
                SalaRicovero sala=null;

                try {
                    sala=pa.getSalaAssociata();
                }
                catch (Exception e){
                    throw new IllegalStateException("il paziente non si trova in sala ricovero");
                }
                sala.liberaLetto();
                pa.setSalaAssociata(null);
                break;
            }

        }

        if(!pazienteTrovato) {
            throw new ChiaveException("paziente non trovato");
        }
    }

    /**
     * dealloca un medico da una sala operatoria,
     * lancia un eccezione se i campi sono vuoti,
     * lancia un eccezione se non trova un medico esistente nella lista,
     * lancia un eccezione se il medico non si trova in una sala operatoria
     *
     *
     * @param idMedico codice identificativo del medico
     * @throws IllegalStateException
     * @throws ChiaveException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void deallocaMedicoSalaOperatoria(String idMedico) throws ChiaveException, IllegalStateException {

        if (idMedico.isBlank()) {
            throw new ChiaveException("id medico vuoto");
        }
        boolean medicoTrovato = false;

        for (Ospedale o : ospedali) {
            List<SalaOperatoria> listaSale = o.getSaleOperatorie();
            for (SalaOperatoria so : listaSale) {
                for (Medico me : so.getMediciAssociati()) {
                    if (me.getIdentificativoMedico().equals(idMedico)) {
                        so.rimuoviMedico(me);
                        medicoTrovato = true;
                        break;
                    }
                }
            }
        }
        if (!medicoTrovato) {
            throw new ChiaveException("medico non esistente oppure non è in sala operatoria");
        }

    }

    /**
     * dealloca un medico da una sala ricovero,
     * lancia un eccezione se i campi sono vuoti,
     * lancia un eccezione se non trova un medico esistente nella lista,
     * lancia un eccezione se il medico non si trova in una sala ricovero
     *
     *
     * @param idMedico codice identificativo del medico
     * @throws IllegalStateException
     * @throws ChiaveException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void deallocaMedicoSalaRicovero(String idMedico) throws IllegalStateException, ChiaveException {
        Medico medicoDaDeallocare = null;
        for (Medico me : medici) {
            if (me.getIdentificativoMedico().equals(idMedico)) {
                medicoDaDeallocare = me;
                break;
            }
        }

        if (medicoDaDeallocare == null) {
            throw new ChiaveException("medico non trovato");
        }


        try {
            medicoDaDeallocare.getSalaAssociata();
        } catch (Exception e) {
            throw new IllegalStateException("il medico non si trova in una sala ricovero");
        }
        medicoDaDeallocare.setSalaAssociata(null);
    }

    /**
     * restituisce un array di stringhe contenente le informazioni della {@link SalaRicovero},
     * lancia un eccezione se la sala ricovero non esiste
     *
     * @param idSalaRicovero
     * @return salaRicovero, l'array di stringhe
     * @throws ChiaveException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public String[] getSalaRicovero(String idSalaRicovero) throws ChiaveException {

        for (Ospedale o : ospedali) {
            String[] salaRicovero = new String[4];
            List<SalaRicovero> listaSale = o.getSaleRicovero();
            for (SalaRicovero sr : listaSale) {
                if (sr.getCodiceSala().equals(idSalaRicovero)) {
                    salaRicovero[0] = sr.getCodiceSala();
                    salaRicovero[1] = sr.getTipoSala();
                    salaRicovero[2] = Integer.toString(sr.getNumeroLetti());
                    salaRicovero[3] = Integer.toString(sr.getLettiLiberi());
                    return salaRicovero;
                }
            }
        }
        throw new ChiaveException("sala ricovero non trovata");
    }

    /**
     * restituisce un array di stringhe contenente le informazioni della {@link SalaOperatoria},
     * lancia un eccezione se la sala operatoria non esiste
     *
     * @param idSalaOperatoria
     * @return salaOperatoria, l'array di stringhe
     * @throws ChiaveException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public String[] getSalaOperatoria(String idSalaOperatoria) throws ChiaveException {

        for (Ospedale o : ospedali) {
            String[] salaOperatoria = new String[2];
            List<SalaOperatoria> listaSale = o.getSaleOperatorie();
            for (SalaOperatoria so : listaSale) {
                if (so.getCodiceSala().equals(idSalaOperatoria)) {
                    if (so.getPazienteAssociato() != null) {
                        salaOperatoria[0] = so.getPazienteAssociato().getIdentificativoPaziente();
                    } else {
                        salaOperatoria[0] = "nessun paziente";
                    }
                    if(so.getIsDisponibile()){
                        salaOperatoria[1] = "si";
                    }
                    else{
                        salaOperatoria[1] = "no";
                    }

                    return salaOperatoria;
                }
            }
        }
        throw new ChiaveException("sala operatoria non trovata");
    }

    /**
     * ritorna una lista di stringhe di id dei medici associati alla sala operatoria
     * lancia un eccezione se la sala operatoria non esiste
     *
     * @param idSalaOperatoria codice identificativo della sala operatoria
     * @return idMediciAssociati, la lista di stringhe
     * @throws ChiaveException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public List<String> getIdMediciSalaOperatoria(String idSalaOperatoria) throws ChiaveException {
        List<String> idMediciAssociati = new ArrayList<>();
        for (Ospedale o : ospedali) {
            List<SalaOperatoria> listaSale = o.getSaleOperatorie();
            for (SalaOperatoria so : listaSale) {
                if (so.getCodiceSala().equals(idSalaOperatoria)) {
                    List<Medico> listaMedici = so.getMediciAssociati();
                    for (Medico me : listaMedici) {
                        idMediciAssociati.add(me.getIdentificativoMedico());
                    }
                    return idMediciAssociati;
                }
            }
        }
        throw new ChiaveException("sala operatoria non trovata");
    }

    /**
     * ritorna una lista di stringhe dei medici disponibili
     *
     * @return mediciDisponibili, la lista di stringhe
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public List<String> getDisponibilitaMedici() {

        List<String> mediciDisponibili = new ArrayList<>();

        for (Medico me : medici) {
            boolean haGiaSala = true;
            try {
                me.getSalaAssociata();
            } catch (Exception e) {
                haGiaSala = false;
            }
            if (!haGiaSala && !eGiaAllocatoMedSalOp(me.getIdentificativoMedico())) {
                String rigaMedico = "ID: " + me.getIdentificativoMedico() + "\n codice fiscale: " + me.getCodiceFiscale() +
                         "\n Dr." + me.getNomePersona() + " " + me.getCognomePersona() + "\n tipo medico: " +
                        me.getTipoMedico() + "\n rango: " + me.getRango() + "\n data assunzione: " + me.getDataAnnoAssunzione();
                mediciDisponibili.add(rigaMedico);
            }
        }
        return mediciDisponibili;
    }

    /**
     * ritorna una lista di stringhe delle sale operatorie disponibili
     *
     * @return saleDisponibili, la lista di stringhe
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public List<String>getDisponibilitaSalaOperatoria(String identificativoOspedale){
        List<String>saleDisponibili= new ArrayList<>();

        for(Ospedale o: ospedali){
            if(o.getIdentificativoOspedale().equals(identificativoOspedale)){
                List<SalaOperatoria> listaSale= o.getSaleOperatorie();
                for(SalaOperatoria so: listaSale){
                    if(so.getIsDisponibile()){
                        String rigaSalaOp= "ID:" + so.getCodiceSala() + "\n id medici associati: " + getIdMediciSalaOperatoria(so.getCodiceSala());
                        saleDisponibili.add(rigaSalaOp);
                    }
                }
            }
        }

        return saleDisponibili;
    }

    /**
     * ritorna una lista di stringhe delle sale ricovero disponibili
     *
     * @return saleDisponibili, la lista di stringhe
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public List<String>getDisponibilitaSalaRicovero(String identificativoOspedale){
        List<String>saleDisponibili= new ArrayList<>();

        for(Ospedale o: ospedali){
            if(o.getIdentificativoOspedale().equals(identificativoOspedale)){
                List<SalaRicovero> listaSale= o.getSaleRicovero();
                for(SalaRicovero sr: listaSale){
                    if(sr.isDisponibile()){
                        String rigaSalaRic= "ID:" + sr.getCodiceSala() + "\n tipologia sala: " + sr.getTipoSala() +
                                "\n numero letti: " + sr.getNumeroLetti() + "\n letti liberi " + sr.getLettiLiberi();
                        saleDisponibili.add(rigaSalaRic);
                    }
                }
            }
        }

        return saleDisponibili;
    }

    /**
     * rimuove un medico dalla lista,
     * lancia un eccezione se il campo medico è vuoto,
     * lancia un eccezione se il medico è impegnato in una sala operatoria o in una sala ricovero
     *
     * @param idMedico codice identificativo del medico
     * @throws IllegalStateException
     * @throws ChiaveException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void rimuoviMedico(String idMedico) throws IllegalStateException, ChiaveException, SQLException {
        if(idMedico.isBlank()){
            throw new IllegalStateException("id medico vuoto");
        }

        MedicoDAO medicoDAO=new MedicoDAO();
        Medico medicoTrovato= medicoDAO.getMedico(idMedico);

        if(medicoTrovato==null){
            throw new ChiaveException("medico non trovato");
        }


        if(eGiaAllocatoMedSalOp(medicoTrovato.getIdentificativoMedico())){
            throw new IllegalStateException("il medico si trova in una sala operatoria, impossibile rimuoverlo");
        }

        SalaRicovero sala=null;
        try{
            sala=medicoTrovato.getSalaAssociata();
        }
        catch (Exception e){
            medicoDAO.rimuoviMedico(medicoTrovato.getIdentificativoMedico());
            medicoDAO.closeConnection();
        }
        if(sala!=null){
            throw new IllegalStateException("il medico si trova in una sala ricovero, impossibile rimuoverlo");
        }
    }

    /**
     * rimuove un ospedale dalla lista,
     * lancia un eccezione se il campo id ospedale è vuoto,
     * lancia un eccezione se l'ospedale ha sale occupate da pazienti e medici
     *
     * @param idOspedale codice identificativo dell'ospedale
     * @throws IllegalStateException
     * @throws ChiaveException
     *
     * @author Alessio Riccio
     * @author Alessandro Vassallo
     * @author Emanuele Todisco
     */
    public void rimuoviOspedale(String idOspedale) throws ChiaveException, IllegalStateException, SQLException {
        if(idOspedale.isBlank()){
            throw new ChiaveException("id ospedale vuoto");
        }
        OspedaleDAO ospedaleDAO=new OspedaleDAO();
        Ospedale ospedaleTrovato=ospedaleDAO.getOspedale(idOspedale);
        if(ospedaleTrovato==null){
            throw new ChiaveException("ospedale non trovato");
        }

        List<SalaRicovero> listaSaleRicovero = ospedaleTrovato.getSaleRicovero();
        for(SalaRicovero sr: listaSaleRicovero){
            if(sr.getNumeroLetti()!=sr.getLettiLiberi()){
                throw new IllegalStateException("la sala ricovero " + sr.getCodiceSala() + " non è vuota");
            }

            SalaRicovero sala=null;
            for(Medico m: medici){
                try{
                    sala= m.getSalaAssociata();
                }
                catch (Exception e){
                    continue;
                }
                if(sala.getCodiceSala().equals(sr.getCodiceSala())){
                    throw new IllegalStateException("il medico " + m.getIdentificativoMedico() +
                            " è gia presente in una sala ricovero di questo ospedale");
                }
            }
        }
        SalaOperatoriaDAO salaOperatoriaDAO= new SalaOperatoriaDAO();
        List<SalaOperatoria> listaSaleOperatorie= salaOperatoriaDAO.getSaleOperatoriePerOspedale(ospedaleTrovato.getIdentificativoOspedale());
        for(SalaOperatoria so:listaSaleOperatorie){
            if(so.getPazienteAssociato()!=null){
                throw new IllegalStateException("il paziente " + so.getPazienteAssociato().getIdentificativoPaziente() +
                        " si trova nella sala operatoria " + so.getCodiceSala());
            }
            if(!so.getMediciAssociati().isEmpty()){
                throw new IllegalStateException("nella sala operatoria " + so.getCodiceSala() +
                        " si trovano ancora dei medici");
            }
        }
        ospedaleDAO.rimuoviOspedale(ospedaleTrovato.getIdentificativoOspedale());
        salaOperatoriaDAO.closeConnection();
        ospedaleDAO.closeConnection();
    }

    /**
     * verifica se l'utente è amministratore,
     * lancia un eccezione se non è un amministratore
     * @param idUtente codice identificativo dell'utente
     * @throws IllegalAccessException
     */
    public void verificaAmministratore(String idUtente) throws IllegalAccessException, SQLException {
        MedicoDAO medicoDAO= new MedicoDAO();
        Medico medico=medicoDAO.getMedico(idUtente);
        if(!medico.getIsAmministratore()) {
            throw new IllegalAccessException("solo gli amministratori possono usare questa funzione");
        }
        medicoDAO.closeConnection();
    }
}