package gui;

import controller.Controller;
import exceptions.ChiaveException;

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
    private JLabel codiceFiscale;
    private JLabel dataDiNascita;
    private JLabel luogoDiNascita;
    private JLabel indirizzo;
    private JLabel identificativo;
    private JLabel rangoMedico;

    //costruttore
    public VisualizzaMedici(JFrame frameChiamante, Controller controller){
        frame= new JFrame("Visualizza medici");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frameChiamante.setVisible(false);
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

                String idmedico = textField1.getText();
                String[] medico;
                try {
                    medico = controller.getMedico(idmedico);
                }catch (ChiaveException ex){
                    JOptionPane.showMessageDialog(frame,ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                codiceFiscale.setText(medico[0]);
                nomeMedico.setText(medico[1]);
                cognomeMedico.setText(medico[2]);
                dataDiNascita.setText(medico[3]);
                luogoDiNascita.setText(medico[4]);
                indirizzo.setText(medico[5]);
                identificativo.setText(medico[6]);
                tipologiaMedico.setText(medico[7]);
                rangoMedico.setText(medico[8]);
                dataAnnoAssunzione.setText(medico[9]);
                salaAssociata.setText(medico[10]);
                isAmministratore.setText(medico[11]);

            }
        });
        tornaAllaPaginaPrincipaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }

}
