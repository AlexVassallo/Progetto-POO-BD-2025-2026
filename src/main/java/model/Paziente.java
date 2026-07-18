package model;

import java.time.LocalDate;

public class Paziente extends Persona {

	//attributi della sottoclasse Paziente
	private String identificativoPaziente;
	private String triagePaziente;
	private SalaRicovero salaAssociata;


    //costruttore della classe Paziente
	public Paziente(String codiceFiscale,
					String nomePersona,
					String cognomePersona,
					LocalDate dataDiNascita,
			        String luogoDiNascita,
					String indirizzo,
					String identificativoPaziente,
					String triagePaziente,
					SalaRicovero salaAssociata) {
		super(codiceFiscale, nomePersona, cognomePersona, dataDiNascita, luogoDiNascita, indirizzo);//riferimento alla superclasse persona
		setIdentificativoPaziente(identificativoPaziente);
		setTriagePaziente(triagePaziente);
		setSalaAssociata(salaAssociata);
	}


//setters
public void setIdentificativoPaziente(String identificativoPaziente) {
	this.identificativoPaziente=identificativoPaziente;
}
public void setTriagePaziente(String triagePaziente) {
	this.triagePaziente=triagePaziente;
}
public void setSalaAssociata(SalaRicovero salaAssociata) {
	this.salaAssociata=salaAssociata;
}

//getters
public String getIdentificativoPaziente() {
	return identificativoPaziente;
}
public String getTriagePaziente() {
	return triagePaziente;
}
public SalaRicovero getSalaAssociata() {
	return salaAssociata;
}

}
