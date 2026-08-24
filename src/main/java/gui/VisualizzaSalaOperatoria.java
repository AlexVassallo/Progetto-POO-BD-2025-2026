package gui;

import controller.Controller;
import exceptions.ChiaveException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisualizzaSalaOperatoria {

    //attributi
    private static  JFrame frame;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    private JButton tornaIndietroButton;
    private JList list1;
    private JLabel pazienteAssociato;
    private JPanel mainPanel;
    private JLabel eDisponibile;

    //costruttore
    public VisualizzaSalaOperatoria(JFrame frameChiamante, Controller controller){

        //creazione della frame
        frame= new JFrame("Visualizza sale operatorie");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        //listener
        resettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
            }
        });

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idSalaOperatoria=textField1.getText();
                String[] salaOperatoria;
                DefaultListModel<String> modello;
                modello=new DefaultListModel<String>();


                try {
                    salaOperatoria = controller.getSalaOperatoria(idSalaOperatoria);
                    modello.addAll(controller.getIdMediciSalaOperatoria(idSalaOperatoria));
                    list1.setModel(modello);
                }
                catch (ChiaveException | IllegalArgumentException | SQLException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }



                pazienteAssociato.setText(salaOperatoria[0]);
                eDisponibile.setText(salaOperatoria[1]);

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
