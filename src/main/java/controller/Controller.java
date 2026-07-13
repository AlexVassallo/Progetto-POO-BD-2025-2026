package controller;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import model.*;
import exceptions.ChiaveException;
import exceptions.ParameterMissingException;
import javax.naming.AuthenticationException;
import java.security.InvalidParameterException;

import javax.naming.AuthenticationException;




public class Controller {
private ArrayList<Medici> medici= new ArrayList<>();
private ArrayList<Ospedale> ospedali= new ArrayList<>();
private ArrayList<Paziente> pazienti= new ArrayList<>();
private ArrayList<Referto> referti= new ArrayList<>();
private ArrayList<SalaOperatoria> saleOperatorie= new ArrayList<>();
private ArrayList<SalaRicovero> saleRicovero= new ArrayList<>();


//metodo che crea il medico
public void creaMedico(String identificativoMedico,
                       String password,
                       String codiceFiscale,
                       String nome,
                       String cognome,
                       LocalDateTime dataDiNascita,
                       String luogoDiNascita,
                       String indirizzo,
                       String tipoMedico,
                       String rango,
                       LocalDateTime dataAnnoAssunzione,
                       SalaRicovero salaAssociata,
                       boolean isAmministratore) throws ParameterMissingException, AuthenticationException, ChiaveException {
    if(identificativoMedico.isBlank()){
        throw new ChiaveException("identificativo mancante");
    }
    if(password.isBlank()|| password.length()<9){
        throw new AuthenticationException("password vuota o troppo corta(la password deve avere almeno 9 caratteri)")
    }
    if(codiceFiscale.length() != 16){
        throw new ParameterMissingException("formato codice fiscale non corretto(il formato corretto è di 16 caratteri)");
    }
    if(nome.isBlank()){
        throw new ParameterMissingException("nome mancante");
    }
    if(cognome.isBlank()){
        throw new ParameterMissingException("cognome mancante");
    }
    if(dataDiNascita.isBlank()){
        throw new ParameterMissingException("data di nascita mancante");
    }
    if(luogoDiNascita.isBlank()){
        throw new ParameterMissingException("luogo di nascita mancante");
    }
    if(indirizzo.isBlank()){
        throw new ParameterMissingException("indirizzo mancante");
    }
    if(tipoMedico.isBlank()){
        throw new ParameterMissingException("tipologia medico mancante");
    }
    if(rango.isBlank()){
        throw new ParameterMissingException("rango mancante");
    }
    if(dataAnnoAssunzione.isBlank()){
        throw new ParameterMissingException("data e anno di assunzione mancante");
    }

}
}
