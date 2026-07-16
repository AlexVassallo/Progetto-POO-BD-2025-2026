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
import javax.naming.AuthenticationException;



public class Controller {
    private ArrayList<Medico> medici = new ArrayList<Medico>();
    private ArrayList<Ospedale> ospedali = new ArrayList<Ospedale>();
    private ArrayList<Paziente> pazienti = new ArrayList<Paziente>();
    private ArrayList<Referto> referti = new ArrayList<Referto>();



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
                           SalaRicovero salaAssociata,
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
                             String nomeOspedale) throws ParameterMissingException {

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
                             SalaRicovero salaAssociata) throws ParameterMissingException, ChiaveException {
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
        if (identificativoPaziente.isBlank() || !esisteIdentificativo(identificativoPaziente)) {
            throw new ChiaveException("identificativo vuoto oppure gia esistente");
        }
        if (indirizzo.isBlank()) {
            throw new ParameterMissingException("indirizzo vuota");
        }
        if (triagePaziente.isBlank()) {
            throw new ParameterMissingException("triage paziente vuota");
        }
        if (salaAssociata == null) {
            throw new ParameterMissingException("sala associata inesistente");
        }
        //da vedere come trovare effetivamente l'oggetto sala
        Paziente p = new Paziente(codiceFiscale,
                nomePersona,
                cognomePersona,
                dataDiNascita,
                luogoDiNascita,
                indirizzo,
                identificativoPaziente,
                triagePaziente,
                salaAssociata);

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

    public void creaReferto(Paziente paziente,
                            Medico medicoAffidato,
                            LocalDateTime dataOraArrivo,
                            LocalDateTime dataOraUscita,
                            String diagnosi,
                            String trattamentoEffettuato,
                            String esitoFinale) throws ParameterMissingException, ChiaveException{
        if(!esisteIdentificativo(paziente.getIdentificativoPaziente())){
            throw new ChiaveException("identificativo inesistente o mancante");
        }
        if(medicoAffidato==null){
            throw new ParameterMissingException("medico mancante");
        }
        if(dataOraArrivo==null){
            throw new ParameterMissingException("data ora di arrivo vuoto");
        }
        if(dataOraUscita==null){
            throw new ParameterMissingException("data ora di uscita vuoto");
        }
        if(diagnosi.isBlank()) {
            throw new ParameterMissingException("diagnosi vuota");
        }
        if (trattamentoEffettuato.isBlank()){
            throw new ParameterMissingException("trattamento inserito vuoto");
        }
        if (esitoFinale.isBlank()){
            throw new ParameterMissingException("esito inserito vuoto");
        }
        Referto r= new Referto(paziente,
                medicoAffidato,
                dataOraArrivo,
                dataOraUscita,
                diagnosi,
                trattamentoEffettuato,
                esitoFinale);
        referti.add(r);
    }

    public void creaSalaOperatoria(String identificativOspedale,
                                   String codiceSala)throws ParameterMissingException, ChiaveException{
        if(identificativOspedale.isBlank() || !esisteIdentificativo(identificativOspedale)){
            throw new ChiaveException("nome ospedale non trovato oppure vuoto");
        }
        if (codiceSala.isBlank()){
            throw new ParameterMissingException("codice sala vuoto");
        }

        for(Ospedale o : ospedali){
            if(o.getIdentificativoOspedale().equals(identificativOspedale)){
                o.addSalaOperatoria(codiceSala);
            }
        }
    }
    public boolean esisteOspedale(String nomeOspedale) {
        for (Ospedale o : ospedali) {
            if (o.getNomeOspedale().equals(nomeOspedale)) {
                return true;
            }
        }
        return false;
    }
    public void creaSalaRicovero(String identificativoOspedale,
                                 String codiceSala,
                                 String tipoSala,
                                 int numeroLetti)throws ParameterMissingException, ChiaveException {

        if(identificativoOspedale.isBlank() || !esisteOspedale(identificativoOspedale)){
            throw new ChiaveException("identificativo ospedale ");
        }
        if(codiceSala.isBlank()){
            throw new ChiaveException("codice sala vuoto");
        }
        if (tipoSala.isBlank()){
            throw new ParameterMissingException("tipo sala vuoto");
        }
        if(numeroLetti<1){
            throw new ParameterMissingException("campo numero letti vuoto, oppure minore di 1");
        }
        //da verificare il funzionamento
        for (Ospedale o : ospedali) {
            if (o.getIdentificativoOspedale().equals(identificativoOspedale)) {
                o.addSalaRicovero(codiceSala, tipoSala, numeroLetti);
            }
        }
    }

    public boolean login(String identificativo,
                      String password)throws ChiaveException, AuthenticationException, InvalidParameterException{

        Medico medicoTrovato=null;

        if(identificativo.isBlank()){
            throw new ChiaveException("identificativo vuoto");
        }
        if(password.isBlank()){
            throw new ChiaveException("password vuota");
        }
        for(Medico me: medici){
            if(me.getIdentificativoMedico().equals(identificativo)){
                me=medicoTrovato;
                break;
            }
            }

        if(medicoTrovato==null){
            throw new AuthenticationException("medico non trovato, prova a fare registrati");
        }

        if(!medicoTrovato.getPassword().equals(password)){
            throw new AuthenticationException("password incorretta");
        }
        return true;
        }


    public List<String> getDisponibiliSaleRicovero(){

        return new ArrayList<>();
    }
    }

