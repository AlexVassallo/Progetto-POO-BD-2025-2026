import java.time.LocalDate;

public class Paziente extends Persona {
	private String identificativoPaziente;
	private String triagePaziente;
	private SalaRicovero salaAssociata;

	public Paziente(String codiceFiscale, String nomePersona, String cognomePersona, LocalDate dataDiNascita,
			String luogoDiNascita, String indirizzo, String identificativoPaziente,String triagePaziente, SalaRicovero salaAssociata) {
		super(codiceFiscale, nomePersona, cognomePersona, dataDiNascita, luogoDiNascita, indirizzo);
		setIdentificativoPaziente(identificativoPaziente);
		setTriagePaziente(triagePaziente);
		setSalaAssociata(salaAssociata);
	}
public void setIdentificativoPaziente(String identificativoPaziente) {
	this.identificativoPaziente=identificativoPaziente;
}
public void setTriagePaziente(String triagePaziente) {
	this.triagePaziente=triagePaziente;
}
public void setSalaAssociata(SalaRicovero salaAssociata) {
	this.salaAssociata=salaAssociata;
}
public String getIdentificativoPaziente() {
	return identificativoPaziente;
}
public String getTriagePaziente() {
	return triagePaziente;
}
public SalaRicovero setSalaAssociata() {
	return salaAssociata;
}

}
