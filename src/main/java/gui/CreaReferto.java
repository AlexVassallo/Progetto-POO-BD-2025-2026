package gui;

import controller.Controller;
import exceptions.ChiaveException;
import exceptions.ParameterMissingException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreaReferto {
    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JLabel label1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton confermaButton;
    private JButton resettaButton;
    private JButton tornaIndietroButton;

    //costruttore
    public CreaReferto(JFrame frameChiamante, Controller controller){
        frame=new JFrame("crea referto");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frameChiamante.setVisible(false);
        frame.setVisible(true);
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{

                    LocalDateTime dataEmissione = LocalDateTime.now();

                    controller.creaReferto(textField1.getText(), textField2.getText(), textField3.getText(),
                            dataEmissione, textField4.getText(),
                            textField5.getText(), textField6.getText(), textField7.getText());
                    frameChiamante.setVisible(true);
                    frame.dispose();
                }
                catch (ChiaveException | ParameterMissingException | IllegalStateException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                 }
                catch (DateTimeException ex){
                    JOptionPane.showMessageDialog(frame, "data e ora non valido", "errore", JOptionPane.ERROR_MESSAGE);
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
                textField5.setText("");
                textField6.setText("");
                textField7.setText("");
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
