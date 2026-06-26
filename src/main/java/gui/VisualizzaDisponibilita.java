package gui;

import javax.swing.*;
import java.awt.event.*;

public class VisualizzaDisponibilita {
    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JButton mediciButton;
    private JButton saleOperatorieButton;
    private JButton saleRicoveratorieButton;
    private JTextField textField1;

    //costruttore
    public VisualizzaDisponibilita(){

        //creazione della frame
        frame=new JFrame("visualizza disponibilità ospedale");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //listener
        mediciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new VisualizzaDisponibilitaMedici();
            }
        });
        saleOperatorieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new VisualizzaDisponibilitaSalaOperatoria();
            }
        });
        saleRicoveratorieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new VisualizzaDisponibilitaSalaRicovero();
            }
        });
    }
}
