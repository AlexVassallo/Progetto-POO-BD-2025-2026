package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.Controller;
import exceptions.ChiaveException;

public class DeallocaSalaOperatoriaMedico {

    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    private JButton tornaIndietroButton;
    Controller controller;

    //costruttore
    public DeallocaSalaOperatoriaMedico(JFrame frameChiamante, Controller controller){
        //creazione della frame
        frame= new JFrame("dealloca medico dalla sala operatoria");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        //listener
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.deallocaMedicoSalaOperatoria(textField1.getText());
                    frameChiamante.setVisible(true);
                    frame.dispose();
                }
                catch (IllegalStateException | ChiaveException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
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
