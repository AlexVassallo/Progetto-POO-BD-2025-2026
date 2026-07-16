package gui;

import controller.Controller;
import exceptions.ChiaveException;
import exceptions.ParameterMissingException;

import javax.naming.AuthenticationException;
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
    Controller controller=new Controller();
    //costruttore
    public Home() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    controller.login(textField1.getText(), new String(inserirePasswordPasswordField.getPassword()));
                    frame.dispose();
                    PaginaPrincipale paginaPrincipale=new PaginaPrincipale(frame,controller);
                }
                catch (AuthenticationException | ParameterMissingException | ChiaveException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);

                }

            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Registrati registrati=new Registrati(frame, controller);
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
