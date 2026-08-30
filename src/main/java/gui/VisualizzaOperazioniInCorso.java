package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualizzaOperazioniInCorso {
    private static JFrame frame;
    private JTextArea textArea1;
    private JPanel mainPanel;
    private JButton tornaIndietroButton;
public  VisualizzaOperazioniInCorso(JFrame frameChiamante, Controller controller){
    frame=new JFrame("visualizza operazioni in corso");
    frame.setContentPane(this.mainPanel);
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    frame.pack();
    frameChiamante.setVisible(false);
    frame.setVisible(true);

    textArea1.setEditable(false);

    StringBuilder sb= new StringBuilder();

    for(String operazioni : controller.visualizzaOperazioniInCorso()){
        sb.append(operazioni).append("\n-----------------------------------\n");
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
