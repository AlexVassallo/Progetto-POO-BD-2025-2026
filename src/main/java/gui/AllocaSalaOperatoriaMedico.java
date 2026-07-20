package gui;

import javax.swing.*;
import java.awt.event.*;
import controller.Controller;
import exceptions.ChiaveException;

public class AllocaSalaOperatoriaMedico {

    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    private JButton tornaIndietroButton;
    private JTextField textField2;


    //costruttore
    public AllocaSalaOperatoriaMedico(JFrame frameChiamante,Controller controller){
        //creazione della frame
        frame= new JFrame("alloca medico alla sala operatoria");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //listener
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    controller.allocaMedicoSalaOperatoria(textField1.getText(), textField2.getText());
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
