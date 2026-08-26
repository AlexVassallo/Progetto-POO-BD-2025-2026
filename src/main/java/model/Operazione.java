package model;

import java.time.LocalDateTime;
import java.util.List;

public class Operazione {
    private String idOperazione;
    private List<Medico> mediciPartecipanti;
    private SalaOperatoria salaUtilizzata;
    private Referto referto;
    private Paziente pazienteOperato;
    private String tipoOperazione;
    private LocalDateTime dataOraInizio;
    private LocalDateTime dataOraFine;
    private String esito;

    public Operazione(String idOperazione,
                      List<Medico> mediciPartecipanti,
                      Paziente pazienteOperato,
                      SalaOperatoria salaUtilizzata,
                      String tipoOperazione,
                      LocalDateTime dataOraInizio){
        setIdOperazione(idOperazione);
        setMediciPartecipanti(mediciPartecipanti);
        setPazienteOperato(pazienteOperato);
        setSalaUtilizzata(salaUtilizzata);
        setTipoOperazione(tipoOperazione);
        setDataOraInizio(dataOraInizio);
    }

    //setters
    public void setIdOperazione(String idOperazione) {
        this.idOperazione = idOperazione;
    }

    public void setSalaUtilizzata(SalaOperatoria salaUtilizzata) {
        this.salaUtilizzata = salaUtilizzata;
    }

    public void setTipoOperazione(String tipoOperazione) {
        this.tipoOperazione = tipoOperazione;
    }

    public void setPazienteOperato(Paziente pazienteOperato) {
        this.pazienteOperato = pazienteOperato;
    }

    public void setMediciPartecipanti(List<Medico> mediciPartecipanti) {
        this.mediciPartecipanti = mediciPartecipanti;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public void setDataOraInizio(LocalDateTime dataOraInizio) {
        this.dataOraInizio = dataOraInizio;
    }

    public void setDataOraFine(LocalDateTime dataOraFine) {
        this.dataOraFine = dataOraFine;
    }

    //getters
    public SalaOperatoria getSalaUtilizzata() {
        return salaUtilizzata;
    }

    public String getEsito() {
        return esito;
    }

    public Paziente getPazienteOperato() {
        return pazienteOperato;
    }

    public String getTipoOperazione() {
        return tipoOperazione;
    }

    public LocalDateTime getDataOraInizio() {
        return dataOraInizio;
    }

    public LocalDateTime getDataOraFine() {
        return dataOraFine;
    }


    public String getIdOperazione() {
        return idOperazione;
    }

    public List<Medico> getMediciPartecipanti() {
        return mediciPartecipanti;
    }
}
