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


    //bottoni visualizza
    private JButton visualizzaMediciButton;
    private JButton visualizzaPazientiButton;
    private JButton visualizzaDisponibilitaButton;
    private JButton visualizzaSalaOperatoriaButton;
    private JButton visualizzaSalaRicoveroButton;


    //bottoni finali
    private JButton gestioneOspedaleButton;
    private JButton logOutButton;
    private JButton rimuoviMedicoButton;
    private JButton rimuoviOspedaleButton;
    private JLabel idUtente;
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
        idUtente.setText(controller.getMedicoSelezionato());


        //listener di creazione
        creaOspedaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.verificaAmministratore(idUtente.getText());
                    new CreaOspedale(frame, controller);
                } catch (IllegalAccessException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //listener dei bottoni creazione
        creaRefertoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.verificaAmministratore(idUtente.getText());
                    new CreaReferto(frame,controller);
                } catch (IllegalAccessException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        creaSalaOperatoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.verificaAmministratore(idUtente.getText());
                    new CreaSalaOperatoria(frame,controller);
                } catch (IllegalAccessException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        creaSalaRicoveroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.verificaAmministratore(idUtente.getText());
                    new CreaSalaRicovero(frame, controller);
                } catch (IllegalAccessException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        //listener del bottone di inserimento
        inserirePaziente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.verificaAmministratore(idUtente.getText());
                    new InserirePaziente(frame,controller);
                } catch (IllegalAccessException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        //listener dei bottoni rimuovi
        rimuoviMedicoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.verificaAmministratore(idUtente.getText());
                    new RimuoviMedico(frame, controller);
                } catch (IllegalAccessException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        rimuoviOspedaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.verificaAmministratore(idUtente.getText());
                    new RimuoviOspedale(frame, controller);
                } catch (IllegalAccessException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                }
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
                new VisualizzaPazienti(frame, controller);
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
                new VisualizzaSalaRicovero(frame, controller);
            }
        });

        visualizzaSalaOperatoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizzaSalaOperatoria(frame, controller);
            }
        });


        //listener finali
        gestioneOspedaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.verificaAmministratore(idUtente.getText());
                    new GestioneOspedale(frame,controller);
                } catch (IllegalAccessException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                }
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
