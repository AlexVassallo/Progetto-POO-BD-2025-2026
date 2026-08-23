package gui;

import controller.Controller;
import exceptions.ChiaveException;
import exceptions.ParameterMissingException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CreaSalaOperatoria {
    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton resettaButton;
    private JButton confermaButton;
    private JButton tornaIndietroButton;


    //costruttore
    public CreaSalaOperatoria(JFrame frameChiamante, Controller controller){
        frame=new JFrame("crea sala operatoria");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    controller.creaSalaOperatoria(textField1.getText(), textField2.getText());
                    frameChiamante.setVisible(true);
                    frame.dispose();
                }
                catch (ParameterMissingException | ChiaveException | SQLException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        resettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textField2.setText("");
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
