package gui;

import controller.Controller;
import exceptions.ChiaveException;
import exceptions.ParameterMissingException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreaOspedale {
    //attributi
    JFrame frame;
    private JTextField textField2;
    private JButton confermaButton;
    private JPanel mainPanel;
    private JLabel label1;
    private JButton tornaIndietroButton;
    private JTextField textField1;


    //costruttore
    public CreaOspedale(JFrame frameChiamante, Controller controller){
        frame=new JFrame("crea ospedale");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

             try{
                 controller.creaOspedale(textField1.getText(), textField2.getText());
                 frameChiamante.setVisible(true);
                 frame.dispose();
             }
             catch (ChiaveException | ParameterMissingException ex){
                 JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
             }

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
