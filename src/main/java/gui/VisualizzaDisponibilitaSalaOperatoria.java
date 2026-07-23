package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualizzaDisponibilitaSalaOperatoria {

    //attributi
    private static JFrame frame;
    private JPanel panel1;
    private JList list1;
    private JButton tornaIndietroButton;
    private JTextArea textArea1;

    //costruttore
    public VisualizzaDisponibilitaSalaOperatoria(JFrame frameChiamante, Controller controller, String identificativoOspedale){

        //creazione della frame
        frame=new JFrame("disponibilita delle sale operatorie");
        frame.setContentPane(this.panel1);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        //crea e stampa la lista delle sale operatorie disponibili
        textArea1.setEditable(false);

        StringBuilder sb = new StringBuilder();
        for (String medico : controller.getDisponibilitaSalaOperatoria(identificativoOspedale)) {
            sb.append(medico).append("\n-----------------------------------\n");
        }

        textArea1.setText(sb.toString());


        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameChiamante.setVisible(true);
            }
        });
    }


}
