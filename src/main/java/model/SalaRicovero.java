package model;

public class SalaRicovero{

//attributi
private String codiceSala;
private String tipoSala;
private int numeroLetti;
private int lettiLiberi;

//costruttore della sala ricovero
public SalaRicovero(String codiceSala,
					String tipoSala,
					int numeroLetti) {
	setCodiceSala(codiceSala);
	setTipoSala(tipoSala);
	setNumeroLetti(numeroLetti);
	lettiLiberi=getNumeroLetti();
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
	/**
	 * occupa un letto all'interno della sala ricovero
	 * lancia un eccezione se la sala ricovero è piena
	 *
	 * @throws IllegalStateException
	 *
	 * @see IllegalStateException
	 *
	 * @author Alessio Riccio
	 * @author Alessandro Vassallo
	 * @author Emanuele Todisco
	 */
public void occupaLetto()throws  IllegalStateException {
	if(!isDisponibile()) {
		throw new IllegalStateException("la sala ricovero" + getCodiceSala() + "è piena");
	}
	lettiLiberi--;
}

	/**
	 * libera un letto all'interno della sala ricovero
	 * lancia un eccezione se la sala ricovero è vuota
	 *
	 * @throws IllegalStateException
	 *
	 * @see IllegalStateException
	 *
	 * @author Alessio Riccio
	 * @author Alessandro Vassallo
	 * @author Emanuele Todisco
	 */
public void liberaLetto() throws IllegalStateException {
	if(lettiLiberi<numeroLetti) {
		lettiLiberi++;
	}
    else {
		throw new IllegalStateException("non ci sono letti occupati in questa sala, forse volevi liberare i letti di un altra sala?");
	     }
}

	/**
	 * verifica se la sala ricovero è disponibile
	 *
	 *
	 * @author Alessio Riccio
	 * @author Alessandro Vassallo
	 * @author Emanuele Todisco
	 */
public boolean isDisponibile() {
	if(lettiLiberi==0) {
		return false;
	}
	return true;
}
}
