package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Controller;
import exceptions.ChiaveException;

public class RimuoviOspedale {

    //attributi
    private static JFrame frame;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    private JButton tornaIndietroButton;
    private JPanel mainPanel;

    //costruttore
    public RimuoviOspedale(JFrame frameChiamante, Controller controller){

        frame=new JFrame("rimuovi ospedale");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    controller.rimuoviOspedale(textField1.getText());
                    frameChiamante.setVisible(true);
                    frame.dispose();
                }
                catch (IllegalStateException | ChiaveException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }

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
