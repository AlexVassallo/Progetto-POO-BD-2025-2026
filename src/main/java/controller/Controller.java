package controller;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import exceptions.ChiaveException;
import exceptions.ParameterMissingException;
import javax.naming.AuthenticationException;
import java.security.InvalidParameterException;



public class Controller {
    private ArrayList<Medico> medici = new ArrayList<Medico>();
    private ArrayList<Ospedale> ospedali = new ArrayList<Ospedale>();
    private ArrayList<Paziente> pazienti = new ArrayList<Paziente>();
    private ArrayList<Referto> referti = new ArrayList<Referto>();

    public String getMedicoSelezionato() {
        return medicoSelezionato;
    }

    public void setMedicoSelezionato(String medicoSelezionato) {
        this.medicoSelezionato = medicoSelezionato;
    }

    private String medicoSelezionato;


    //metodo che crea il medico
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
                           boolean isAmministratore) throws ParameterMissingException, AuthenticationException, ChiaveException {
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
        medici.add(m);
    }


    public void creaOspedale(String identidicativoOspedale,
                             String nomeOspedale) throws ParameterMissingException, ChiaveException {

        if(identidicativoOspedale.isBlank()){
            throw  new ChiaveException("identificativo mancante");
        }

        if(esisteIdentificativoOspedale(identidicativoOspedale)){
            throw new ChiaveException("ospedale gia esistente");
        }

        if (nomeOspedale.isBlank()) {
            throw new ParameterMissingException("nome ospedale mancante");
        }

        Ospedale o = new Ospedale(identidicativoOspedale, nomeOspedale);
        ospedali.add(o);
    }


    public void creaPaziente(String codiceFiscale,
                             String nomePersona,
                             String cognomePersona,
                             LocalDate dataDiNascita,
                             String luogoDiNascita,
                             String indirizzo,
                             String identificativoPaziente,
                             String triagePaziente,
                             String idSalaAssociata) throws ParameterMissingException, ChiaveException {
        if (codiceFiscale.length() < 16) {
            throw new ParameterMissingException("codice fiscale vuoto");
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

        SalaRicovero salaRicovero=null;
        boolean salaTrovata=false;

        for(Ospedale o: ospedali){
           List<SalaRicovero> listaSale= o.getSaleRicovero();
           for(SalaRicovero sr:listaSale){
               if(sr.getCodiceSala().equals(idSalaAssociata)){
                   salaRicovero=sr;
                   salaTrovata=true;
                   break;
               }
           }
        }

        if(!salaTrovata){
            throw new ChiaveException("id sala non trovata");
        }

        Paziente p = new Paziente(codiceFiscale,
                nomePersona,
                cognomePersona,
                dataDiNascita,
                luogoDiNascita,
                indirizzo,
                identificativoPaziente,
                triagePaziente,
                salaRicovero);

        pazienti.add(p);
    }

    public boolean esisteIdentificativo(String identificativoPaziente) {
        for (Paziente p : pazienti) {
            if (p.getIdentificativoPaziente().equals(identificativoPaziente)) {
                return true;
            }
        }
        return false;
    }

    public boolean esisteIdentificativoMedico(String identificativoMedico) {
        for (Medico m : medici) {
            if (m.getIdentificativoMedico().equals(identificativoMedico)) {
                return true;
            }
        }
        return false;
    }

    public boolean esisteIdentificativoOspedale(String identificativoOspedale){
        for(Ospedale o: ospedali){
            if(o.getIdentificativoOspedale().equals(identificativoOspedale)){
                return  true;
            }
        }
        return false;
    }

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
        for(Paziente p : pazienti){
            if(p.getIdentificativoPaziente().equals(idPaziente)){
                paziente = p;
                pazienteTrovato = true;
                break;
            }
        }
        if(!pazienteTrovato){
            throw  new ChiaveException("Paziente mancante");
        }


        Medico medico= null;
        boolean medicoTrovato=false;

        if (idMedico.isBlank()) {
            throw new ParameterMissingException("medico mancante");
        }

        for(Medico me: medici) {
            if(me.getIdentificativoMedico().equals(idMedico)){
                medico=me;
                medicoTrovato=true;
                break;
            }
        }

        if(!medicoTrovato){
            throw new ChiaveException("medico mancante");
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

    public void creaSalaOperatoria(String identificativOspedale,
                                   String codiceSala) throws ParameterMissingException, ChiaveException {
        if (identificativOspedale.isBlank() || !esisteIdentificativoOspedale(identificativOspedale)) {
            throw new ChiaveException("nome ospedale non trovato oppure vuoto");
        }
        if (codiceSala.isBlank()) {
            throw new ParameterMissingException("codice sala vuoto");
        }

        for (Ospedale o : ospedali) {
            if (o.getIdentificativoOspedale().equals(identificativOspedale)) {
                o.addSalaOperatoria(codiceSala);
            }
        }
    }

    public boolean esisteOspedale(String identificativoOspedale) {
        for (Ospedale o : ospedali) {
            if (o.getIdentificativoOspedale().equals(identificativoOspedale)) {
                return true;
            }
        }
        return false;
    }

    public void creaSalaRicovero(String identificativoOspedale,
                                 String codiceSala,
                                 String tipoSala,
                                 int numeroLetti) throws ParameterMissingException, ChiaveException {

        if (identificativoOspedale.isBlank() || !esisteOspedale(identificativoOspedale)) {
            throw new ChiaveException("identificativo ospedale inesistente oppure vuoto");
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

        for (Ospedale o : ospedali) {
            if (o.getIdentificativoOspedale().equals(identificativoOspedale)) {
                o.addSalaRicovero(codiceSala, tipoSala, numeroLetti);
            }
        }
    }

    public boolean login(String identificativo,
                         String password) throws ChiaveException, AuthenticationException, InvalidParameterException {

        Medico medicoTrovato = null;

        if (identificativo.isBlank()) {
            throw new ChiaveException("identificativo vuoto");
        }
        if (password.isBlank()) {
            throw new ChiaveException("password vuota");
        }
        for (Medico me : medici) {
            if (me.getIdentificativoMedico().equals(identificativo)) {
                medicoTrovato = me;
                break;
            }
        }

        if (medicoTrovato == null) {
            throw new AuthenticationException("medico non trovato, prova a fare registrati");
        }

        if (!medicoTrovato.getPassword().equals(password)) {
            throw new AuthenticationException("password incorretta");
        }

        setMedicoSelezionato(identificativo);
        return true;
    }


    public List<String> getDisponibiliSaleRicovero() {

        return new ArrayList<>();
    }

    public String[] getMedico(String idMedico) throws ChiaveException {
        for (Medico me : medici) {
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
                }catch (Exception e){
                    medico[10] = "Nessuna sala";
                }
                medico[11] = Boolean.valueOf(me.getIsAmministratore()).toString();
                medico[12] = me.getPassword();

                return medico;
            }
        }
        throw new ChiaveException("id medico non trovato");
    }
    public String[] getPaziente(String idPaziente) throws ChiaveException{
        for(Paziente pa: pazienti){
            if(pa.getIdentificativoPaziente().equals(idPaziente)){
                String[] paziente = new String[9];
                paziente[0] = pa.getCodiceFiscale();
                paziente[1] = pa.getNomePersona();
                paziente[2] = pa.getCognomePersona();
                paziente[3] = pa.getDataDiNascita().toString();
                paziente[4] = pa.getLuogoDiNascita();
                paziente[5] = pa.getIndirizzo();
                paziente[6] = pa.getIdentificativoPaziente();
                paziente[7] = pa.getTriagePaziente();
                paziente[8] = pa.getSalaAssociata().getCodiceSala();

                return paziente;
            }
        }
        throw new ChiaveException("paziente non trovato");
    }


}