package org.calculadora.contenedor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;

import org.calculadora.interfaces.Operaciones;
import org.calculadora.modelo.OperacionesImpl;

/**
 *
 * @author joel
 */
public class Artefacto extends JFrame {

    private PanelPantalla panelPantalla = new PanelPantalla(); // Primero se crea la instancia de PanelPantalla
    private PanelNumeros panelnumeros = new PanelNumeros(panelPantalla); // Luego se crea la instancia de PanelNumeros
    private Operaciones operacionesTop;

    public Artefacto() {
        this.operacionesTop = new OperacionesImpl(panelPantalla.getJLabel());
        this.setTitle("Calculadora");
        this.setSize(400, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(this);
        setLayout(new BorderLayout()); // Agregado para establecer el layout del JFrame
        this.add(panelnumeros, BorderLayout.CENTER);
        this.add(panelPantalla, BorderLayout.NORTH);
    }
    
}

class PanelPantalla extends JPanel {

    private JLabel jlabel;
   

    public PanelPantalla() {
        
        
        setPreferredSize(new Dimension(200, 100));
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        jlabel = new JLabel();
        jlabel.setFont(new Font("Arial", Font.BOLD, 70));
        jlabel.setBackground(Color.WHITE);
        jlabel.setForeground(Color.BLACK);
        add(jlabel);
    }
    public JLabel getJLabel() {
        return jlabel;
    }
}

class PanelNumeros extends JPanel {

    private JButton[] botonesNumeros = new JButton[10];
    private PanelPantalla pantalla;
    private Operaciones operacionesTop;

    public PanelNumeros(PanelPantalla pantalla) {
        this.pantalla = pantalla; 
        this.operacionesTop = new OperacionesImpl(pantalla.getJLabel()); 
        setLayout(new GridLayout(6, 3));

        for (int i = 0; i < 10; i++) {
            final int numero = i; // Crea una copia final de i
            botonesNumeros[i] = new JButton(Integer.toString(i));
            botonesNumeros[i].setFont(new Font("Arial", Font.BOLD, 20)); 
            botonesNumeros[i].setBackground(Color.LIGHT_GRAY); 
            botonesNumeros[i].setForeground(Color.BLACK); 
            add(botonesNumeros[i]);
            botonesNumeros[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   operacionesTop.actualizarPantalla(Integer.toString(numero));                    
                   
                }
            });
        }
        String[] operaciones = {"+", "-", "*", "/"};
        JButton[] botonesOperaciones = new JButton[operaciones.length];
        for (int j = 0; j < operaciones.length; j++) {
            botonesOperaciones[j] = new JButton(operaciones[j]);
            botonesOperaciones[j].setFont(new Font("Arial", Font.BOLD, 20));
            botonesOperaciones[j].setBackground(Color.ORANGE);
            botonesOperaciones[j].setForeground(Color.BLACK);
            add(botonesOperaciones[j]);


            botonesOperaciones[j].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton botonPresionado = (JButton) e.getSource();
                    String textoBoton = botonPresionado.getText();

                    operacionesTop.actualizarPantalla(textoBoton);
                }
            });
        }
        String[] acciones = {"=", "CLEAR", "DEL", " "};
        JButton[] botonesAcciones = new JButton[acciones.length];
        for (int i = 0; i < acciones.length; i++) {
            botonesAcciones[i] = new JButton(acciones[i]);
            botonesAcciones[i].setFont(new Font("Arial", Font.BOLD, 20));
            botonesAcciones[i].setBackground(Color.WHITE);
            botonesAcciones[i].setForeground(Color.BLACK);
            add(botonesAcciones[i]);

            final int index = i;
            botonesAcciones[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton botonPresionado = (JButton) e.getSource();
                    String textoBoton = botonPresionado.getText();
                    if (textoBoton.equals("CLEAR")) {
                        operacionesTop.limpiarPantalla();
                    }else if (textoBoton.equals("DEL")) {
                        operacionesTop.BorrarCaracter();
                    } else if (textoBoton.equals("=")) {                 
                        operacionesTop.operaciones();

                    } else if(textoBoton.equals(" ")){
                      
                                            
                    }
                }
            });
        }

    }
}
