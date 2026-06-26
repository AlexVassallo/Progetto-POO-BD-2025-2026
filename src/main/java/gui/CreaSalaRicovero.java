package gui;

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

    //costruttore
    public CreaSalaRicovero(){
        frame=new JFrame("Crea sala ricovero");
        frame.setContentPane(this.mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //qua verra fatta la query al database
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
            }
        });
    }
}
