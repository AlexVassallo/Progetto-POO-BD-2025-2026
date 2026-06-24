package gui;

import javax.swing.*;
import java.awt.event.*;

public class Home {
    //attributi
    private static JFrame frame;
    JPanel mainPanel;
    JButton button1;
    JButton button2;
    JTextField field1;
    JLabel label1;
    JPasswordField inserirePasswordPasswordField;
    private JTextField textField1;
    JLabel label2;
    //costruttore
    public Home() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*
                qua ci sara la parte del codice che verificherà i dati inseriti
                 */
                frame.setVisible(false);
                PaginaPrincipale paginaPrincipale=new PaginaPrincipale();
            }
        });
        button2.addActionListener(new ActionListener() {
@Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Registrati registrati=new Registrati();
            }
        });
    }
    //metodi
    public static void creaFrame() {
        frame = new JFrame("login");
        frame.setContentPane(new Home().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
        public static void main (String[]args){
            creaFrame();
        }

    }
