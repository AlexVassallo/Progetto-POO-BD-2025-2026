package gui;

import javax.swing.*;

public class VisualizzaDisponibilitaSalaOperatoria {

    //attributi
    private static JFrame frame;
    private JPanel panel1;
    private JList list1;

    //costruttore
    public VisualizzaDisponibilitaSalaOperatoria(){

        //creazione della frame
        frame=new JFrame("disponibilita delle sale operatorie");
        frame.setContentPane(this.panel1);
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //crea e stampa la lista delle sale operatorie disponibili
        DefaultListModel<String> listasale = new DefaultListModel<>();
        listasale.addAll(controller.getDisponibiliSaleRicovero());
        list1.setModel(listasale);
    }


}
