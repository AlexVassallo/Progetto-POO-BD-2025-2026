package model;

import exceptions.ChiaveException;

import java.util.ArrayList;
import java.util.List;

public class Ospedale {
		//attributi
	protected String identificativoOspedale;
	protected String nomeOspedale;
	private List<SalaRicovero> saleRicovero = new ArrayList<>();
	private List<SalaOperatoria> saleOperatorie = new ArrayList<>();
	//costruttore
	public Ospedale(String identificativoOspedale, String nomeOspedale) {
		setIdentificativoOspedale(identificativoOspedale);
		setNomeOspedale(nomeOspedale);
	}

	//setters
	public void setNomeOspedale(String nomeOspedale){
		this.nomeOspedale=nomeOspedale;
	}
	public void setIdentificativoOspedale(String identificativoOspedale){this.identificativoOspedale=identificativoOspedale;}
	//getters
	public String getNomeOspedale() {
		return nomeOspedale;
	}
	public String getIdentificativoOspedale(){return  identificativoOspedale;}

    //altri metodi

	/**
	 * aggiunge alla lista una nuova sala ricovero
	 * lancia un eccezione se esiste gia una sala con lo stesso codiceSala di quello inserito nel parametro
	 *
	 * @param codiceSala il codice della sala nuova {@link SalaRicovero}
	 * @param tipoSala la tipologia della sala nuova {@link SalaRicovero}
	 * @param numeroLetti il numero dei letti che avra la sala nuova {@link SalaRicovero}
	 * @throws ChiaveException
	 *
	 * @see SalaRicovero
	 * @see ChiaveException
	 *
	 * @author Alessio Riccio
	 * @author Alessandro Vassallo
	 * @author Emanuele Todisco
	 */
	public void addSalaRicovero(String codiceSala,
								String tipoSala,
								int numeroLetti) throws ChiaveException{
		for(SalaRicovero sr : saleRicovero){
			if(sr.getCodiceSala().equals(codiceSala)) {
				throw new ChiaveException("La Sala Ricovero " + codiceSala + " esiste già");
			}
		}

		SalaRicovero salaRicovero = new SalaRicovero(codiceSala,tipoSala,numeroLetti);
		saleRicovero.add(salaRicovero);
	}

	/**
	 * aggiunge alla lista una nuova sala operatoria
	 * lancia un eccezione se esiste gia una sala con lo stesso codiceSala di quello inserito nel parametro
	 *
	 * @param codiceSala il codice della sala nuova {@link SalaOperatoria}
	 * @throws ChiaveException
	 *
	 * @see SalaOperatoria
	 * @see ChiaveException
	 *
	 * @author Alessio Riccio
	 * @author Alessandro Vassallo
	 * @author Emanuele Todisco
	 */
	public void addSalaOperatoria(String codiceSala) throws ChiaveException {
		for(SalaOperatoria so : saleOperatorie){
			if(so.getCodiceSala().equals(codiceSala)){
				throw new ChiaveException("La sala operatoria " + codiceSala + " esiste già");
			}
		}

		SalaOperatoria s = new SalaOperatoria(codiceSala);
		saleOperatorie.add(s);
	}

	/**
	 * restituisce la lista delle sale operatorie
	 *
	 * @see SalaOperatoria
	 *
	 * @author Alessio Riccio
	 * @author Alessandro Vassallo
	 * @author Emanuele Todisco
	 */
	public List<SalaOperatoria> getSaleOperatorie(){
		return saleOperatorie;
	}

	public SalaOperatoria getSalaOperatoria(String codiceSala) throws ChiaveException{
		for(SalaOperatoria so : saleOperatorie){
			if(so.getCodiceSala().equals(codiceSala)){
				return so;
			}
		}
		throw new ChiaveException("Sala " + codiceSala + " non trovata");
	}

	/**
	 * restituisce la lista delle sale ricovero
	 *
	 * @see SalaRicovero
	 *
	 * @author Alessio Riccio
	 * @author Alessandro Vassallo
	 * @author Emanuele Todisco
	 */
	public List<SalaRicovero> getSaleRicovero(){
		return saleRicovero;
	}

	public SalaRicovero getSalaRicovero(String codiceSala) throws ChiaveException{
		for(SalaRicovero s : saleRicovero){
			if(s.getCodiceSala().equals(codiceSala)){
				return s;
			}
		}
		throw new ChiaveException("Sala " + codiceSala + " non trovata");
	}

}
