import java.time.*;

public class Persona {
	//attributi
	protected String codiceFiscale;
	protected String nomePersona;
	protected String cognomePersona;
    protected LocalDate dataDiNascita;
    protected String luogoDiNascita;
    protected String indirizzo;
    
//costruttore    
public Persona(String codiceFiscale, String nomePersona, String cognomePersona, LocalDate dataDiNascita, String luogoDiNascita, String indirizzo) {
	setCodiceFiscale(codiceFiscale);
	setNomePersona(nomePersona);
	setCognomePersona(cognomePersona);
	setDataDiNascita(dataDiNascita);
    setLuogoDiNascita(luogoDiNascita);
    setIndirizzo(indirizzo);	
}

//setters
public void setCodiceFiscale(String codiceFiscale) {
this.codiceFiscale=codiceFiscale;	
}

public void setNomePersona(String nomePersona) {
this.nomePersona=nomePersona;	
}

public void setCognomePersona(String cognomePersona) {
	this.cognomePersona=cognomePersona;
}

public void setDataDiNascita(LocalDate dataDiNascita) {
	this.dataDiNascita=dataDiNascita;
}

public void setLuogoDiNascita(String luogoDiNascita) {
	this.luogoDiNascita=luogoDiNascita;
}

public void setIndirizzo(String indirizzo) {
	this.indirizzo=indirizzo;
}

//getters
public String getCodiceFiscale() {
	return codiceFiscale;
}

public String getNomePersona() {
	return nomePersona;
}

public String getCognomePersona() {
	return cognomePersona;
}

public LocalDate getDataDiNascita() {
	return dataDiNascita;
}

public String getLuogoDiNascita() {
	return luogoDiNascita;
}

public String getIndirizzo() {
	return indirizzo;
}


}
