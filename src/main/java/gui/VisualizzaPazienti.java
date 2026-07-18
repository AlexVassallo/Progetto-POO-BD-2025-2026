package gui;

import controller.Controller;
import exceptions.ChiaveException;

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
    private JButton tornaIndietroButton;
    private JLabel identificativo;
    private JLabel indirizzo;


    //costruttore
    public VisualizzaPazienti(JFrame frameChiamante, Controller controller){
        //creazione della frame
        frame= new JFrame("Visualizza pazienti");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);


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

                String idPaziente=textField1.getText();
                String[] paziente;

                try {
                    paziente = controller.getPaziente(idPaziente);
                }
                catch (ChiaveException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                codiceFiscale.setText(paziente[0]);
                nome.setText(paziente[1]);
                cognome.setText(paziente[2]);
                dataDiNascita.setText(paziente[3]);
                luogoDiNascita.setText(paziente[4]);
                indirizzo.setText(paziente[5]);
                identificativo.setText(paziente[6]);
                triage.setText(paziente[7]);
                salaAssociata.setText(paziente[8]);
            }
        });

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameChiamante.setVisible(true);
            }
        });

    }
}
