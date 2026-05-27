import java.util.ArrayList;
public class SalaOperatoria extends Ospedale {
private String codiceSala;
private ArrayList <Medico>mediciAssociati= new ArrayList<Medico>();
private Paziente pazienteAssociato;

private boolean isDisponibile=true;
	public SalaOperatoria(String nomeOspedale, String codiceSala) {
		super(nomeOspedale);
		setCodiceSala(codiceSala);
	}
	
	
	
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

public void StampaMediciAssociati() {
	for(int i=0; i<mediciAssociati.size(); i++) {
	mediciAssociati.get(i);	
	}
	}
public Paziente getPazienteAssociati() {
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
