package gui;

import controller.Controller;
import exceptions.ChiaveException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualizzaOperazione {
    private static JFrame frame;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    private JButton tornaIndietroButton;
    private JLabel idOperazione;
    private JList list1;
    private JLabel salaUtilizzata;
    private JLabel pazienteOperato;
    private JLabel tipoOperazione;
    private JLabel dataOraInizio;
    private JLabel dataOraFine;
    private JPanel mainPanel;
    private JLabel esito;

    public  VisualizzaOperazione(JFrame frameChiamante, Controller controller){
        //creazione della frame
        frame= new JFrame("Visualizza operazione");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        resettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
            }
        });

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String OperazioneId =textField1.getText();
                String[] operazione;
                DefaultListModel<String> modello;
                modello=new DefaultListModel<String>();


                try {
                    operazione = controller.getOperazione(OperazioneId);
                    modello.addAll(controller.getIdMediciOperazione(OperazioneId));
                    list1.setModel(modello);
                }
                catch (ChiaveException | IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                idOperazione.setText(operazione[0]);
                salaUtilizzata.setText(operazione[1]);
                pazienteOperato.setText(operazione[2]);
                tipoOperazione.setText(operazione[3]);
                dataOraInizio.setText(operazione[4]);
                dataOraFine.setText(operazione[5]);
                esito.setText(operazione[6]);


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
