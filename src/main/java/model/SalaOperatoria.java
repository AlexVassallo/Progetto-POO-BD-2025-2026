package model;

import exceptions.ChiaveException;

import java.util.ArrayList;
import java.util.List;


public class SalaOperatoria {

//attributi
private String codiceSala;
private List<Medico> mediciAssociati= new ArrayList<Medico>();
private Paziente pazienteAssociato;
private boolean isDisponibile=true;

//costruttore della classe sala operatoria
	public SalaOperatoria(String codiceSala) {
		setCodiceSala(codiceSala);
	}
	
	
//setters
public void setCodiceSala(String codiceSala) {
	this.codiceSala=codiceSala;
}
public void setMediciAssociati(ArrayList<Medico> mediciAssociati) {
	this.mediciAssociati=mediciAssociati;
}
public void setPazienteAssociato(Paziente pazienteAssociato) {
	this.pazienteAssociato=pazienteAssociato;
}
public void setIsDisponibile(boolean isDisponibile) {
	this.isDisponibile=isDisponibile;
}


public String getCodiceSala() {
	return codiceSala;
}

public void stampaMediciAssociati() {
	for(int i=0; i<mediciAssociati.size(); i++) {
	mediciAssociati.get(i);	
	}
	}

	public List<Medico> getMediciAssociati(){
		return mediciAssociati;
	}
	public Medico getMedico(String codiceMedico) throws ChiaveException {
		for(Medico me : mediciAssociati){
			if(me.getIdentificativoMedico().equals(codiceMedico)){
				return me;
			}
		}
		throw new ChiaveException("Medico " + codiceMedico + " non trovata");
	}
public Paziente getPazienteAssociato() {
	return pazienteAssociato;
}
public boolean getIsDisponibile() {
	return isDisponibile;
}

public void occupaSala(Paziente pazienteCheOccupa) {
	if(pazienteAssociato==null) {
		setPazienteAssociato(pazienteCheOccupa);
		setIsDisponibile(false);
		System.out.println("il paziente è entrato in sala operatoria, la stanza" + getCodiceSala() + " è occupata");
	}
	else
	{
		System.err.println("la stanza " + getCodiceSala() + "è al momento occupata, alloca il paziente in un altra sala disponibile");
	}
}
public void liberaSala() {
	if(pazienteAssociato==null) {
		System.err.println("questa sala è vuota, volevi forse liberare un'altra sala?");
	}
	else {
		setPazienteAssociato(null);
		setIsDisponibile(true);
		System.out.println("il paziente è uscito dalla sala operatoria, la stanza" + getCodiceSala() + " è libera");
	}
}

	/**
	 * Aggiunge un medico alla lista dei medici associati
	 *
	 * @param medicoDaAggiungere l'oggetto {@link Medico} da aggiungere alla lista di medici associati
	 * @see Medico
	 *
	 * @author Alessio Riccio
	 * @author Alessandro Vassallo
	 * @author Emanuele Todisco
	 */
	public void aggiungiMedico(Medico medicoDaAggiungere) {
	mediciAssociati.add(medicoDaAggiungere);
}

public void rimuoviMedico(Medico medicoDaRimuovere) {
	if(medicoDaRimuovere!=null) {
	mediciAssociati.remove(medicoDaRimuovere);
	}
	else {
		System.err.println("non esiste quel medico registrato nel database");
		}
	}
}
