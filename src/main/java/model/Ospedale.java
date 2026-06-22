public class Ospedale {
	//attributi
protected String nomeOspedale;

//costruttore
public Ospedale(String nomeOspedale) {
	setNomeOspedale(nomeOspedale);
}

//metodi
public void setNomeOspedale(String nomeOspedale){
	this.nomeOspedale=nomeOspedale;
}
public String getNomeOspedale() {
	return nomeOspedale;
}
}
