import java.time.*;

public class Medico extends Persona{
//Attributi	
    String identificativoMedico;
    String tipoMedico;
    String rango;
    LocalDateTime dataAnnoAssunzione;
    SalaRicovero salaAssociata;
	boolean isAmministratore;
	String password;

//costruttore
public Medico(String codiceFiscale, String nomePersona, String cognomePersona, LocalDate dataDiNascita, 
		      String luogoDiNascita, String indirizzo, String identificativoMedico, String tipoMedico, 
		      String rango, LocalDateTime dataAnnoAssunzione, SalaRicovero salaAssociata, boolean isAmministratore, String password)
{
	super(codiceFiscale, nomePersona, cognomePersona, dataDiNascita, luogoDiNascita, indirizzo);
	setIdentificativoMedico(identificativoMedico);
	setTipoMedico(tipoMedico);
	setRango(rango);
	setDataAnnoAssunzione(dataAnnoAssunzione);
	setSalaAssociata(salaAssociata);
	setIsAmministratore(isAmministratore);
	setPassword(password);
	
}

//setters
public void setIdentificativoMedico(String identificativoMedico) {
	this.identificativoMedico=identificativoMedico;
	
}
public void setTipoMedico(String tipoMedico) {
	this.tipoMedico=tipoMedico;
}

public void setRango(String rango) {
	this.rango=rango;
}

public void setDataAnnoAssunzione(LocalDateTime dataAnnoAssunzione) {
	this.dataAnnoAssunzione=dataAnnoAssunzione;
}
public void setSalaAssociata(SalaRicovero salaAssociata) {
	this.salaAssociata=salaAssociata;
}

public void setIsAmministratore(boolean isAmministratore){
	this.isAmministratore=isAmministratore;
}
public void setPassword(String password){
	this.password=password;
}

//getters
public String getIdentificativoMedico() {
	return identificativoMedico;
}
public String getPassword(){return password;}
public boolean getIsAmministratore(){
	return isAmministratore;
}
public String getTipoMedico() {
	return tipoMedico;
}
public String getRango() {
	return rango;
}
public LocalDateTime getDataAnnoAssunzione() {
	return dataAnnoAssunzione;
}
public SalaRicovero getSalaAssociata() {
	return salaAssociata;
}


}
