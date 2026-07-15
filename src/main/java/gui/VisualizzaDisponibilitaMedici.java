package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualizzaDisponibilitaMedici {

    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JList list1;
    private JButton tornaIndietroButton;

    //costruttore
    public VisualizzaDisponibilitaMedici(JFrame frameChiamante, Controller controller){

        //creazione della frame
        frame=new JFrame("disponibilita dei medici");
        frame.setContentPane(this.mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        //crea e stampa la lista dei medici disponibili
        DefaultListModel<String> listasale = new DefaultListModel<>();
        listasale.addAll(controller.getDisponibiliSaleRicovero());
        list1.setModel(listasale);

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameChiamante.setVisible(true);
            }
        });
    }

}
