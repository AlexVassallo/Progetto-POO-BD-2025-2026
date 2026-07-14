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
    public PaginaPrincipale(JFrame frameChiamante,Controller controller){
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
                new CreaOspedale(frame,controller);
            }
        });


        //listener dei bottoni creazione
        creaRefertoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                CreaReferto referto=new CreaReferto(frame,controller);
            }
        });

        creaSalaOperatoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreaSalaOperatoria(frame,controller);
            }
        });

        creaSalaRicoveroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreaSalaRicovero(frame, controller);
            }
        });


        //listener del bottone di inserimento
        inserirePaziente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new InserirePaziente(frame,controller);
            }
        });


        //listener dei bottoni visualizza
        visualizzaMediciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizzaMedici(frame,controller);
            }
        });

        visualizzaPazientiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizzaPazienti();
            }
        });

        visualizzaDisponibilitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizzaDisponibilita(frame, controller);
            }
        });

        visualizzaSalaRicoveroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizzaSalaRicovero();
            }
        });

        visualizzaSalaOperatoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizzaSalaOperatoria();
            }
        });


        //listener finali
        gestioneOspedaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GestioneOspedale(frame,controller);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }


}
