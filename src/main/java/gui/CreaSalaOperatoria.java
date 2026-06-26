package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreaSalaOperatoria {
    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton resettaButton;
    private JButton confermaButton;


    //costruttore
    public CreaSalaOperatoria(){
        frame=new JFrame("crea sala operatoria");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //qua verrà aggiornato il dababase con la nuova sala
                frame.setVisible(false);
                new PaginaPrincipale();
            }
        });

        resettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textField2.setText("");
            }
        });
    }

}
