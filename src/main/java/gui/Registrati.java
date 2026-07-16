package gui;

import controller.Controller;
import exceptions.ChiaveException;
import exceptions.ParameterMissingException;

import javax.naming.AuthenticationException;
import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;


public class Registrati {
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField3;
    private JPasswordField passwordField1;
    private JButton registratiButton;
    private JButton resettaButton;
    private JTextField textField2;
    private JTextField textField6;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField1;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JCheckBox siCheckBox;
    private JButton tornaAllaPaginaLoginButton;

    public Registrati (JFrame frameChiamante, Controller controller) {
        frame = new JFrame("registrati");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    java.time.format.DateTimeFormatter dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dataNascitaString = textField5.getText().trim();
                    LocalDate dataNascita = LocalDate.parse(dataNascitaString, dateFormatter);
                    String dataAssunzioneString = textField10.getText().trim();
                    LocalDate dataAssunzioneSoloData = LocalDate.parse(dataAssunzioneString, dateFormatter);
                    LocalDateTime dataAssunzione = dataAssunzioneSoloData.atStartOfDay();

                    controller.creaMedico(textField1.getText(),
                            new String(passwordField1.getPassword()),
                            textField2.getText(),
                            textField3.getText(),
                            textField4.getText(),
                            dataNascita,
                            textField6.getText(),
                            textField7.getText(),
                            textField8.getText(),
                            textField9.getText(),
                            dataAssunzione,
                            null,
                            siCheckBox.isSelected());
                    frame.dispose();
                    frameChiamante.setVisible(true);
                }
                catch (ParameterMissingException | ChiaveException | AuthenticationException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
                catch (DateTimeParseException ex){
                    JOptionPane.showMessageDialog(frame, "Formato data non valido", "Errore", JOptionPane.ERROR_MESSAGE);
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
                textField8.setText("");
                textField9.setText("");
                textField10.setText("");
                passwordField1.setText("");
           }
       });

        tornaAllaPaginaLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameChiamante.setVisible(true);
            }
        });

    }
}