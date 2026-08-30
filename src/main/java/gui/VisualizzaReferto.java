package gui;

import controller.Controller;
import exceptions.ChiaveException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualizzaReferto {
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    private JButton tornaIndietroButton;
    private JLabel operazioneEffettuata;
    private JLabel dataEmissione;
    private JLabel trattamentoEffettuato;
    private JScrollPane note;
    private JScrollPane prescrizioniTeraupetiche;
    private JScrollPane diagnosii;
    private JLabel esitoFinale;
    private JLabel idReferto;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;

    public  VisualizzaReferto(JFrame frameChiamante, Controller controller){
        frame= new JFrame("Visualizza Referto");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        textArea1.setEditable(false);
        textArea1.setFocusable(false);

        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);
        textArea2.setEditable(false);
        textArea2.setFocusable(false);

        textArea3.setLineWrap(true);
        textArea3.setWrapStyleWord(true);
        textArea3.setEditable(false);
        textArea3.setFocusable(false);


        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] referto;
                try {
                    referto=controller.visualizzaReferto(textField1.getText());
                }
                catch (ChiaveException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                idReferto.setText(referto[0]);
                operazioneEffettuata.setText(referto[1]);
                textArea1.setText(referto[2]);
                dataEmissione.setText(referto[3]);
                trattamentoEffettuato.setText(referto[4]);
                textArea2.setText(referto[5]);
                textArea3.setText(referto[6]);
                esitoFinale.setText(referto[7]);

            }
        });

        resettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
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
