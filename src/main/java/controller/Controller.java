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
        for (Medico me : medici) {
            if (me.getIdentificativoMedico().equals(identificativoMedico)) {
                throw new ChiaveException("identificatico medico già esistente");
            }
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

        if (identidicativoOspedale.isBlank()) {
            throw new ChiaveException("identificativo mancante");
        }

        if (esisteIdentificativoOspedale(identidicativoOspedale)) {
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

        for (Ospedale o : ospedali) {
            List<SalaRicovero> listaSale = o.getSaleRicovero();
            for (SalaRicovero sr : listaSale) {
                if (sr.getCodiceSala().equals(idSalaAssociata)) {
                    salaRicovero = sr;
                    salaTrovata = true;
                    sr.occupaLetto();
                    break;
                }
            }
        }

        if (!salaTrovata) {
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

    public boolean esisteIdentificativoOspedale(String identificativoOspedale) {
        for (Ospedale o : ospedali) {
            if (o.getIdentificativoOspedale().equals(identificativoOspedale)) {
                return true;
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
        for (Paziente p : pazienti) {
            if (p.getIdentificativoPaziente().equals(idPaziente)) {
                paziente = p;
                pazienteTrovato = true;
                break;
            }
        }
        if (!pazienteTrovato) {
            throw new ChiaveException("Paziente mancante");
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
            List<SalaOperatoria> listaSale=o.getSaleOperatorie();
            for (SalaOperatoria so:listaSale){
                if(so.getCodiceSala().equals(codiceSala)){
                    throw new ChiaveException("identificativo sala già esistente");
                }
            }
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
            List<SalaRicovero> listaSale = o.getSaleRicovero();
            for (SalaRicovero sr : listaSale) {
                if (sr.getCodiceSala().equals(codiceSala)) {
                    throw new ChiaveException("identificativo sala già esistente");
                }
            }
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
                } catch (Exception e) {
                    medico[10] = "Nessuna sala";
                }
                medico[11] = Boolean.valueOf(me.getIsAmministratore()).toString();
                medico[12] = me.getPassword();

                return medico;
            }
        }
        throw new ChiaveException("id medico non trovato");
    }

    public String[] getPaziente(String idPaziente) throws ChiaveException {
        for (Paziente pa : pazienti) {
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


                return paziente;
            }
        }
        throw new ChiaveException("paziente non trovato");
    }

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

    public String[] getSalaOperatoria(String idSalaRicovero) throws ChiaveException {

        for (Ospedale o : ospedali) {
            String[] salaOperatoria = new String[2];
            List<SalaOperatoria> listaSale = o.getSaleOperatorie();
            for (SalaOperatoria so : listaSale) {
                if (so.getCodiceSala().equals(idSalaRicovero)) {
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
        throw new ChiaveException("sala ricovero non trovata");
    }

    public List<String> getIdMediciSalaOperatoria(String idSalaRicovero) throws ChiaveException {
        List<String> idMediciAssociati = new ArrayList<>();
        for (Ospedale o : ospedali) {
            List<SalaOperatoria> listaSale = o.getSaleOperatorie();
            for (SalaOperatoria so : listaSale) {
                if (so.getCodiceSala().equals(idSalaRicovero)) {
                    List<Medico> listaMedici = so.getMediciAssociati();
                    for (Medico me : listaMedici) {
                        idMediciAssociati.add(me.getIdentificativoMedico());
                    }
                    return idMediciAssociati;
                }
            }
        }
        throw new ChiaveException("sala ricovero non trovata");
    }

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

    public void rimuoviMedico(String idMedico)throws IllegalStateException, ChiaveException{
        if(idMedico.isBlank()){
            throw new IllegalStateException("id medico vuoto");
        }
        Medico medicoTrovato=null;

        for(Medico m: medici){
            if(m.getIdentificativoMedico().equals(idMedico)){
                medicoTrovato=m;
                break;
            }
        }
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
            medici.remove(medicoTrovato);
        }
        if(sala!=null){
            throw new IllegalStateException("il medico si trova in una sala ricovero, impossibile rimuoverlo");
        }
    }

    public void rimuoviOspedale(String idOspedale) throws ChiaveException, IllegalStateException{
        if(idOspedale.isBlank()){
            throw new ChiaveException("id ospedale vuoto");
        }

        Ospedale ospedaleTrovato=null;
        for(Ospedale o: ospedali){
            if(o.getIdentificativoOspedale().equals(idOspedale)){
                ospedaleTrovato=o;
                break;
            }
        }
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
        List<SalaOperatoria> listaSaleOperatorie= ospedaleTrovato.getSaleOperatorie();
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
        ospedali.remove(ospedaleTrovato);
    }
}