package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.Controller;

public class GestioneOspedale {
    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JButton allocaSalaOperatoriaMedico;
    private JButton allocaSalaRicoveroPaziente;
    private JButton allocaSalaRicoveroMedico;
    private JButton deallocaSalaOperatoriaPaziente;
    private JButton deallocaSalaOperatoriaMedico;
    private JButton deallocaSalaRicoveroPaziente;
    private JButton deallocaSalaRicoveroMedico;
    private JButton allocaSalaOperatoriaPaziente;
    private JButton tornaIndietroButton;
    Controller controller=new Controller();

    //costruttore
    public GestioneOspedale(){
        frame=new JFrame("gestione ospedale;");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

       //listener
       allocaSalaOperatoriaPaziente.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

           }
       });

       allocaSalaOperatoriaMedico.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

           }
       });

       allocaSalaRicoveroPaziente.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

           }
        });

       allocaSalaRicoveroMedico.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

           }
       });

       deallocaSalaOperatoriaPaziente.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

           }
        });

       deallocaSalaOperatoriaMedico.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

           }
       });

       deallocaSalaRicoveroPaziente.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

           }
       });

       deallocaSalaRicoveroMedico.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {

           }
       });

       tornaIndietroButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               new PaginaPrincipale(controller);
               frame.setVisible(false);
           }
       });
    }
}
