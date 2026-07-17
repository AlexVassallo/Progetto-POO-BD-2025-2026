package gui;

import controller.Controller;
import exceptions.ChiaveException;
import exceptions.ParameterMissingException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreaSalaRicovero {
    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton confermaButton;
    private JButton resettaButton;
    private JButton tornaIndietroButton;

    //costruttore
    public CreaSalaRicovero(JFrame frameChiamante, Controller controller){
        frame=new JFrame("Crea sala ricovero");
        frame.setContentPane(this.mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    controller.creaSalaRicovero(textField1.getText(), textField2.getText(),
                            textField3.getText(), Integer.parseInt(textField4.getText()));
                    frameChiamante.setVisible(true);
                    frame.dispose();

                }
                catch (ParameterMissingException | ChiaveException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE );
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(frame, "inserire un numero valido", "errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        resettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
                textField4.setText("");
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
