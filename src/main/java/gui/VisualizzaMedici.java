package gui;

import javax.swing.*;
import java.awt.event.*;

public class VisualizzaMedici {
    //attributi
    public static JFrame frame;
    private JTextField textField1;
    private JPanel mainPanel;
    private JLabel nomeMedico;
    private JLabel cognomeMedico;
    private JLabel tipologiaMedico;
    private JLabel dataAnnoAssunzione;
    private JLabel isAmministratore;
    private JLabel salaAssociata;
    private JButton resettaButton;
    private JButton confermaButton;
    private JButton tornaAllaPaginaPrincipaleButton;

    //costruttore
    public VisualizzaMedici(){
        frame= new JFrame("Visualizza medici");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

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
                nomeMedico.setText(".");
                cognomeMedico.setText(".");
                tipologiaMedico.setText(".");
                dataAnnoAssunzione.setText(".");
                isAmministratore.setText(".");
                salaAssociata.setText(".");
            }
        });
        tornaAllaPaginaPrincipaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new PaginaPrincipale();
            }
        });
    }

}
