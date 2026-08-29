package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EffettuaUnOperazione {
    private static JFrame frame;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton confermaButton;
    private JButton resettaButton;
    private JButton tornaIndietroButton;
    private JPanel mainPanel;

    public EffettuaUnOperazione(JFrame frameChiamante, Controller controller) {
        frame = new JFrame("Effettua un operazione");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);


        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime oraOperazione= LocalDateTime.now();
                try{
                    controller.creaOperazione(textField1.getText(),
                            textField2.getText(),
                            textField3.getText(),
                            oraOperazione);
                    frame.dispose();
                    frameChiamante.setVisible(true);
                } catch (RuntimeException | SQLException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        resettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
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
