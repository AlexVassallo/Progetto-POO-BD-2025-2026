package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualizzaSalaOperatoria {

    //attributi
    private static  JFrame frame;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    private JButton tornaIndietroButton;
    private JList list1;
    private JLabel pazienteAssociato;
    private JPanel mainPanel;
    private JLabel eDisponibile;

    //costruttore
    public VisualizzaSalaOperatoria(JFrame frameChiamante, Controller controller){

        //creazione della frame
        frame= new JFrame("Visualizza sale operatorie");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        //listener
        resettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
            }
        });

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //al momento preparo solo il funzionamento del tasto, ma verra a prendere i dati nel database e li setta
                list1.setToolTipText(".");
                pazienteAssociato.setText(".");
                eDisponibile.setText(".");

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
