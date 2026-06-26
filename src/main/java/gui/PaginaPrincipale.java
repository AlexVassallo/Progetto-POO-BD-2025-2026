package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaPrincipale {
    //attributi
    private static JFrame frame;
    private JPanel createPanel;
    //bottoni creazione
    private JButton creaOspedaleButton;
    private JButton creaSalaOperatoriaButton;
    private JButton creaSalaRicoveroButton;
    private JButton creaRefertoButton;


    private JPanel insertPanel;
    //bottoni inserimento
    private JButton inserirePaziente;


    private JPanel visualizzaPanel;
    //bottoni visualizza
    private JButton visualizzaMediciButton;
    private JButton visualizzaPazientiButton;
    private JButton visualizzaDisponibilitaButton;
    private JButton visualizzaSalaOperatoriaButton;
    private JButton visualizzaSalaRicoveroButton;


    private JPanel finalPanel;
    //bottoni finali
    private JButton gestioneOspedaleButton;
    private JButton logOutButton;
    private Controller controller;

    //costruttore
    public PaginaPrincipale(Controller controller){
        this.controller = controller;
        //creazione frame
        frame= new JFrame("pagina principale");
        frame.setContentPane(this.createPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        //listener di creazione
        creaOspedaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                CreaOspedale creaOspedale= new CreaOspedale();
            }
        });
        //listener dei bottoni creazione
        creaRefertoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                CreaReferto referto=new CreaReferto();
            }
        });
        creaSalaOperatoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                CreaSalaOperatoria salaOperatoria=new CreaSalaOperatoria();
            }
        });
        creaSalaRicoveroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                CreaSalaRicovero salaRicovero= new CreaSalaRicovero();
            }
        });


        //listener del bottone di inserimento
        inserirePaziente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                InserirePaziente paziente= new InserirePaziente();
            }
        });

        //listener dei bottoni visualizza
        visualizzaMediciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                VisualizzaMedici medici=new VisualizzaMedici();
            }
        });
        visualizzaPazientiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                VisualizzaPazienti pazienti=new VisualizzaPazienti();
            }
        });
        visualizzaDisponibilitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }
        });
        visualizzaSalaRicoveroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                VisualizzaSalaRicovero salaRicovero= new VisualizzaSalaRicovero();
            }
        });
        visualizzaSalaOperatoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                VisualizzaSalaOperatoria salaOperatoria= new VisualizzaSalaOperatoria();
            }
        });

        //listener finali
        gestioneOspedaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                GestioneOspedale ospedale=new GestioneOspedale();
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Home nuovoLogin= new Home();
                nuovoLogin.creaFrame();
            }
        });
    }


}
