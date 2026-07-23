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
    private JTextArea textArea1;

    //costruttore
    public VisualizzaDisponibilitaMedici(JFrame frameChiamante, Controller controller){

        //creazione della frame
        frame=new JFrame("disponibilita dei medici");
        frame.setContentPane(this.mainPanel);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        //crea e stampa la lista dei medici disponibili
        textArea1.setEditable(false);

        StringBuilder sb = new StringBuilder();
        for (String medico : controller.getDisponibilitaMedici()) {
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
