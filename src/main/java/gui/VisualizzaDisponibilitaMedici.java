package gui;

import controller.Controller;

import javax.swing.*;

public class VisualizzaDisponibilitaMedici {

    //attributi
    private static JFrame frame;
    private JPanel panel1;
    private JList list1;

    //costruttore
    public VisualizzaDisponibilitaMedici(JFrame frameChiamante, Controller controller){

        //creazione della frame
        frame=new JFrame("disponibilita dei medici");
        frame.setContentPane(this.panel1);
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        //crea e stampa la lista dei medici disponibili
        DefaultListModel<String> listasale = new DefaultListModel<>();
        listasale.addAll(controller.getDisponibiliSaleRicovero());
        list1.setModel(listasale);
    }

}
