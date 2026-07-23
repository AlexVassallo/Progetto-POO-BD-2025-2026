package gui;

import controller.Controller;
import exceptions.ChiaveException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualizzaSalaRicovero {

    //attributi
    private static JFrame frame;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    private JButton tornaIndietroButton;
    private JPanel mainPanel;
    private JLabel tipoSala;
    private JLabel lettiLiberi;
    private JLabel numeroLetti;
    private JLabel codiceSala;

    //costruttore
    public VisualizzaSalaRicovero(JFrame frameChiamante, Controller controller){
        frame= new JFrame("Visualizza sale ricovero");
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
                String idSalaRicovero= textField1.getText();
                String[] salaRicovero;
                try{
                    salaRicovero = controller.getSalaRicovero(idSalaRicovero);
                }
                catch (ChiaveException ex){
                    JOptionPane.showMessageDialog(frame,ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                tipoSala.setText(salaRicovero[0]);
                numeroLetti.setText(salaRicovero[1]);
                lettiLiberi.setText(salaRicovero[2]);
                codiceSala.setText(salaRicovero[3]);
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
