package gui;

import controller.Controller;
import exceptions.ChiaveException;
import exceptions.ParameterMissingException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ConcludiOperazione {
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton concludiECreaIlButton;
    private JButton resettaButton;
    private JButton tornaIndietroButton;
    private JTextField textField2;

    public ConcludiOperazione(JFrame frameChiamante, Controller controller){
        frame=new JFrame("Concludi operazione");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frameChiamante.setVisible(false);
        frame.setVisible(true);

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameChiamante.setVisible(true);
            }
        });

        resettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
                textField2.setText("");
            }
        });

        concludiECreaIlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Object> listaOggetti;
                try {
                   listaOggetti = controller.concludiOperazione(textField1.getText(), textField2.getText());
                    new CreaReferto(frame, controller, listaOggetti, textField1.getText());
                } catch (ChiaveException | ParameterMissingException | IllegalStateException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Errore ", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }
}
