package gui;

import controller.Controller;

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
    private JButton tornaIndietroButton;

    //costruttore
    public VisualizzaDisponibilita(JFrame frameChiamante, Controller controller){

        //creazione della frame
        frame=new JFrame("visualizza disponibilità ospedale");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        //listener
        mediciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    new VisualizzaDisponibilitaMedici(frame,controller);
            }
        });

        saleOperatorieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.esisteIdentificativoOspedale(textField1.getText())) {
                    frame.setVisible(false);
                    new VisualizzaDisponibilitaSalaOperatoria(frame, controller, textField1.getText());
                } else {
                    JOptionPane.showMessageDialog(frame, "ospedale non trovato", "errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        saleRicoveratorieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controller.esisteIdentificativoOspedale(textField1.getText())){
                    frame.setVisible(false);
                    new VisualizzaDisponibilitaSalaRicovero(frame,controller, textField1.getText());
                }
                else {
                    JOptionPane.showMessageDialog(frame, "ospedale non trovato", "errore", JOptionPane.ERROR_MESSAGE);
                }
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
