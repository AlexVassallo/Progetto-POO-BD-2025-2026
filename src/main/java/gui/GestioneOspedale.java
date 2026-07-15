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

    //costruttore
    public GestioneOspedale(JFrame frameChiamante, Controller controller){
        frame=new JFrame("gestione ospedale;");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);

       //listener
       allocaSalaOperatoriaPaziente.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               frame.setVisible(false);
               new AllocaSalaOperatoriaPaziente(frame, controller);

           }
       });

       allocaSalaOperatoriaMedico.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               frame.setVisible(false);
               new AllocaSalaOperatoriaMedico(frame, controller);
           }
       });

       allocaSalaRicoveroPaziente.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               frame.setVisible(false);
               new AllocaSalaRicoveroPaziente(frame, controller);
           }
        });

       allocaSalaRicoveroMedico.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               frame.setVisible(false);
               new AllocaSalaRicoveroMedico(frame, controller);
           }
       });

       deallocaSalaOperatoriaPaziente.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               frame.setVisible(false);
               new DeallocaSalaOperatoriaPaziente(frame, controller);
           }
        });

       deallocaSalaOperatoriaMedico.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               frame.setVisible(false);
               new DeallocaSalaOperatoriaMedico(frame, controller);
           }
       });

       deallocaSalaRicoveroPaziente.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               frame.setVisible(false);
               new DeallocaSalaRicoveroPaziente(frame, controller);
           }
       });

       deallocaSalaRicoveroMedico.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               frame.setVisible(false);
               new DeallocaSalaRicoveroMedico(frame, controller);
           }
       });

       tornaIndietroButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               frameChiamante.setVisible(true);
               frame.dispose();
           }
       });
    }
}
