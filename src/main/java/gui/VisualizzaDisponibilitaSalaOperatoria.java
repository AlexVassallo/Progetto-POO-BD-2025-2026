package gui;

import controller.Controller;

import javax.swing.*;

public class VisualizzaDisponibilitaSalaOperatoria {

    //attributi
    private static JFrame frame;
    private JPanel panel1;
    private JList list1;

    //costruttore
    public VisualizzaDisponibilitaSalaOperatoria(JFrame frameChimante, Controller controller){

        //creazione della frame
        frame=new JFrame("disponibilita delle sale operatorie");
        frame.setContentPane(this.panel1);
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frameChimante.setVisible(false);
        frame.setVisible(true);

        //crea e stampa la lista delle sale operatorie disponibili
        DefaultListModel<String> listasale = new DefaultListModel<>();
        listasale.addAll(controller.getDisponibiliSaleRicovero());
        list1.setModel(listasale);
    }


}
