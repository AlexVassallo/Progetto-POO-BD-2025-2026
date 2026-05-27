import java.time.*;
public class Referto {
private Paziente paziente;
private Medico medicoAffidato;
private LocalDateTime dataOraArrivo;
private LocalDateTime dataOraUscita;
private String diagnosi;
private String trattamentoEffettuato;
private String esitoFinale;
public Referto(Paziente paziente, Medico medicoAffidato, LocalDateTime dataOraArrivo,
		       LocalDateTime dataOraUscita, String diagnosi, String trattamentoEffettuato, String esitoFinale) {
	setPaziente(paziente);
	setMedicoAffidato(medicoAffidato);
	setDataOraArrivo(dataOraArrivo);
	setDiagnosi(diagnosi);
	setTrattamentoEffettuato(trattamentoEffettuato);
	setEstitofinale(esitoFinale);
}

//setters
public void setPaziente(Paziente paziente) {
	this.paziente=paziente;
}
public void setMedicoAffidato(Medico medicoAffidato) {
	this.medicoAffidato=medicoAffidato;
}
public void setDataOraArrivo(LocalDateTime dataOraArrivo) {
	this.dataOraArrivo=dataOraArrivo;
}
public void setDataOraUscita(LocalDateTime dataOraUscita) {
	this.dataOraUscita=dataOraUscita;
}
public void setDiagnosi(String diagnosi) {
	this.diagnosi=diagnosi;
}
public void setTrattamentoEffettuato(String trattamentoEffettuato) {
	this.trattamentoEffettuato=trattamentoEffettuato;
}
public void setEstitofinale(String esitoFinale) {
	this.esitoFinale=esitoFinale;
}

//getters
public Paziente getPaziente() {
	return paziente;
}
public Medico getMedicoAffidato() {
	return medicoAffidato;
}
public LocalDateTime getDataOraArrivo() {
	return dataOraArrivo;
}
public LocalDateTime getDataOraUscita() {
	return dataOraUscita;
}
public String getDiagnosi() {
	return diagnosi;
}
public String getTrattamentoEffettuato() {
	return trattamentoEffettuato;
}
public String getEsitoFinale() {
	return esitoFinale;
}
}
