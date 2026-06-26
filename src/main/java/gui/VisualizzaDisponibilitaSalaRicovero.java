package gui;

import javax.swing.*;

public class VisualizzaDisponibilitaSalaRicovero {

    //attributi
    private static JFrame frame;
    private JPanel panel1;
    private JList list1;

    //costruttore
    public VisualizzaDisponibilitaSalaRicovero(){

        //creazione della frame
        frame=new JFrame("disponibilita delle sale ricovero");
        frame.setContentPane(this.panel1);
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //crea una lista e stampa le sale ricovero disponibili
        DefaultListModel<String> listasale = new DefaultListModel<>();
        listasale.addAll(controller.getDisponibiliSaleRicovero());
        list1.setModel(listasale);
    }
}
