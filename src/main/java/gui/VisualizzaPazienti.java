package gui;

import javax.swing.*;
import java.awt.event.*;

public class VisualizzaPazienti {

    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    private JLabel codiceFiscale;
    private JLabel nome;
    private JLabel cognome;
    private JLabel dataDiNascita;
    private JLabel luogoDiNascita;
    private JLabel triage;
    private JLabel salaAssociata;


    //costruttore
    public VisualizzaPazienti(){
        //creazione della frame
        frame= new JFrame("Visualizza pazienti");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        //listener
        resettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
            }
        });

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //al momento preparo solo il funzionamento del tasto, ma verra a prendere i dati nel database e li setta
                codiceFiscale.setText(".");
                nome.setText(".");
                cognome.setText(".");
                dataDiNascita.setText(".");
                luogoDiNascita.setText(".");
                triage.setText(".");
                salaAssociata.setText(".");
            }
        });
    }
}
