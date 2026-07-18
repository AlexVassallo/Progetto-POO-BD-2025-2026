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

	public void addSalaOperatoria(String codiceSala) throws ChiaveException {
		for(SalaOperatoria so : saleOperatorie){
			if(so.getCodiceSala().equals(codiceSala)){
				throw new ChiaveException("La sala operatoria " + codiceSala + " esiste già");
			}
		}

		SalaOperatoria s = new SalaOperatoria(codiceSala);
		saleOperatorie.add(s);
	}
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
