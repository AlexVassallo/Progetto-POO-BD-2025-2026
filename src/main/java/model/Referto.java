package model;

import java.time.*;

public class Referto {

	//attributi
	private String idReferto;
	private Operazione operazioneEffettuata;
	private String diagnosi;
	private LocalDateTime dataEmissione;
	private String trattamentoEffettuato;
	private String noteMedico;
	private String prescrizioni;
	private String esitoFinale;

	//costruttore della classe referto
	public Referto(String idReferto,
				   Operazione operazioneEffettuata,
				   String diagnosi,
				   LocalDateTime dataEmissione,
				   String trattamentoEffettuato,
				   String noteMedico,
				   String prescrizioni,
				   String esitoFinale) {
		setIdReferto(idReferto);
		setOperazioneEffettuata(operazioneEffettuata);
		setDiagnosi(diagnosi);
		setDataEmissione(dataEmissione);
		setTrattamentoEffettuato(trattamentoEffettuato);
		setNoteMedico(noteMedico);
		setPrescrizioni(prescrizioni);
		setEsitoFinale(esitoFinale);
	}

	//setters
	public void setIdReferto(String idReferto) {
		this.idReferto = idReferto;
	}

	public void setDataEmissione(LocalDateTime dataEmissione) {
		this.dataEmissione = dataEmissione;
	}

	public void setEsitoFinale(String esitoFinale) {
		this.esitoFinale = esitoFinale;
	}

	public void setNoteMedico(String noteMedico) {
		this.noteMedico = noteMedico;
	}

	public void setOperazioneEffettuata(Operazione operazioneEffettuata) {
		this.operazioneEffettuata = operazioneEffettuata;
	}

	public void setPrescrizioni(String prescrizioni) {
		this.prescrizioni = prescrizioni;
	}


	public void setDiagnosi(String diagnosi) {
		this.diagnosi = diagnosi;
	}

	public void setTrattamentoEffettuato(String trattamentoEffettuato) {
		this.trattamentoEffettuato = trattamentoEffettuato;
	}

	//getters
	public String getIdReferto() {
		return idReferto;
	}

	public Operazione getOperazioneEffettuata() {
		return operazioneEffettuata;
	}

	public LocalDateTime getDataEmissione() {
		return dataEmissione;
	}

	public String getDiagnosi() {
		return diagnosi;
	}

	public String getTrattamentoEffettuato() {
		return trattamentoEffettuato;
	}

	public String getEsitoFinale() {
		return esitoFinale;
	}

	public String getNoteMedico() {
		return noteMedico;
	}

	public String getPrescrizioni() {
		return prescrizioni;
	}

}
