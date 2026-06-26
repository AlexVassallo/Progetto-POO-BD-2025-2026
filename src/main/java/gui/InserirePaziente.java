package gui;

import javax.swing.*;
import java.awt.event.*;
public class InserirePaziente {
    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JButton confermaButton;
    private JButton resettaButton;


    //costruttore
    public InserirePaziente(){
        frame=new JFrame("inserire paziente");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //qua verranno aggiunti al database i dati
                frame.setVisible(false);
                new PaginaPrincipale();
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
            }
        });
    }
}
