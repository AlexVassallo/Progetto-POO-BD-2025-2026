package gui;

import controller.Controller;
import exceptions.ChiaveException;
import exceptions.ParameterMissingException;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.DateTimeException;

public class InserirePaziente {
    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField7;
    private JTextField textField6;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField2;
    private JButton confermaButton;
    private JButton resettaButton;
    private JButton tornaIndietroButton;


    //costruttore
    public InserirePaziente(JFrame frameChiamante,Controller controller){
        frame=new JFrame("inserire paziente");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frameChiamante.setVisible(false);
        frame.setVisible(true);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    java.time.format.DateTimeFormatter dateFormatter= java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dataDiNascitaString= textField4.getText().trim();
                    LocalDate dataDiNascita=LocalDate.parse(dataDiNascitaString, dateFormatter);

                    controller.creaPaziente(textField1.getText(), textField2.getText(), textField3.getText(),
                            dataDiNascita, textField5.getText(), textField6.getText(), textField7.getText(),
                            textField8.getText(), textField9.getText());
                    frame.setVisible(false);
                    frameChiamante.setVisible(true);
                    frame.dispose();
                }
                catch (ParameterMissingException | ChiaveException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                }
                catch (DateTimeException ex){
                    JOptionPane.showMessageDialog(frame, "data non valida", "errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        resettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textField3.setText("");
                textField4.setText("");
                textField5.setText("");
                textField7.setText("");
                textField6.setText("");
                textField8.setText("");
                textField9.setText("");
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
