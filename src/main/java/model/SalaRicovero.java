
public class SalaRicovero {
private String codiceSala;
private String tipoSala;
private int numeroLetti;
private int lettiLiberi;

public SalaRicovero() {
	
}

//setters
public void setCodiceSala(String codiceSala) {
	this.codiceSala=codiceSala;
}
public void setTipoSala(String tipoSala) {
	this.tipoSala=tipoSala;
}
public void setNumeroLetti(int numeroLetti) {
	this.numeroLetti=numeroLetti;
}
public void setLettiLiberi(int lettiLiberi) {
	this.lettiLiberi=lettiLiberi;
}

//getters
public String getCodiceSala() {
	return codiceSala;
}
public String getTipoSala() {
	return tipoSala;
}
public int getNumeroLetti() {
	return numeroLetti;
}
public int getLettiLiberi() {
	return lettiLiberi;
}

//metodi
public void occupaLetto() {
	if(isDisponibile()) {
		lettiLiberi--;
	}
	else {
		System.err.println("spiacente, questa sala è piena");
	}
}
public void liberaLetto() {
	if(lettiLiberi<numeroLetti) {
		lettiLiberi++;
	}
	else {
		System.err.println("spiacente, ma in questa sala tutti i letti solo liberi oppure già stati liberati, forse volevi liberare i letti di un altra stanza?");
	}
}
public boolean isDisponibile() {
	if(lettiLiberi==0) {
		return false;
	}
	else {
		return true;
	}
}
}
