package org.calculadora.contenedor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author joel
 */
public class Artefacto extends JFrame {

    private PanelPantalla panelPantalla = new PanelPantalla(); // Primero se crea la instancia de PanelPantalla
    private PanelNumeros panelnumeros = new PanelNumeros(panelPantalla); // Luego se crea la instancia de PanelNumeros

    public Artefacto() {
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

    // Método para actualizar el texto mostrado en el JLabel
    public StringBuilder actualizarPantalla(String texto) {
        // Construir la cadena de texto con todos los números ingresados
        StringBuilder datosPantalla = new StringBuilder();
        datosPantalla.append(jlabel.getText()); // Agregar el texto actual del JLabel
        // Agregar el nuevo texto al final
        datosPantalla.append(texto);
        // Establecer el texto del JLabel con la cadena resultante
        jlabel.setText(datosPantalla.toString());
        limiteNumeros(datosPantalla);

        return datosPantalla;
    }

    public void limiteNumeros(StringBuilder datosPantalla) {
        if (datosPantalla.length() > 8) {
            jlabel.setFont(new Font("Arial", Font.BOLD, 50));
        }
        if (datosPantalla.length() > 12) {
            JOptionPane.showMessageDialog(null, "Superaste el limite");
            limpiarPantalla();
        }

    }

    public void operaciones() {
        String texto = actualizarPantalla("").toString(); // Obtener el contenido actual de la pantalla

        // Variables para almacenar los números encontrados
        List<Integer> numeros = new ArrayList<>();

        // Definir el patrón para buscar números en la expresión
        Pattern patronNumeros = Pattern.compile("\\d+"); // Buscar uno o más dígitos
        Matcher matcherNumeros = patronNumeros.matcher(texto);

        // Iterar sobre los números encontrados en la expresión
        while (matcherNumeros.find()) {
            String numeroStr = matcherNumeros.group(); // Obtener el número encontrado como cadena
            int numero = Integer.parseInt(numeroStr); // Convertir la cadena a entero
            numeros.add(numero); // Agregar el número a la lista
        }

        // Definir el patrón para buscar operadores en la expresión
        Pattern patronOperadores = Pattern.compile("[+\\-*/]"); // Buscar los operadores +, -, * y /
        Matcher matcherOperadores = patronOperadores.matcher(texto);

        // Variable para almacenar el resultado de la operación
        double resultado = numeros.get(0); // Inicializar con el primer número

        // Iterar sobre los operadores encontrados en la expresión
        while (matcherOperadores.find()) {
            char operador = matcherOperadores.group().charAt(0); // Obtener el operador encontrado

            // Realizar la operación correspondiente
            switch (operador) {
                case '+':
                    resultado += numeros.get(1);
                    break;
                case '-':
                    resultado -= numeros.get(1);
                    break;
                case '*':
                    resultado *= numeros.get(1);
                    break;
                case '/':
                    resultado /= numeros.get(1);
                    break;
            }
            // Remover el primer número ya utilizado en la operación
            numeros.remove(0);
        }

        // Imprimir el resultado de la operación
        //verdificar su esta realizando la operacion System.out.println("El resultado de la operación es: " + resultado);
        // Establecer el resultado de la operación en el JLabel
        jlabel.setText(String.valueOf(resultado));

    }

    public void limpiarPantalla() {
        jlabel.setText(""); 
    }
}

class PanelNumeros extends JPanel {

    private JButton[] botonesNumeros = new JButton[10];
    private PanelPantalla pantalla;

    public PanelNumeros(PanelPantalla pantalla) {
        setLayout(new GridLayout(6, 3));

        this.pantalla = pantalla;

        // Cambiar el estilo de los botones de números
        for (int i = 0; i < 10; i++) {
            final int numero = i; // Crea una copia final de i
            botonesNumeros[i] = new JButton(Integer.toString(i));
            botonesNumeros[i].setFont(new Font("Arial", Font.BOLD, 20)); // Cambia la fuente y tamaño del texto
            botonesNumeros[i].setBackground(Color.LIGHT_GRAY); // Cambia el color de fondo
            botonesNumeros[i].setForeground(Color.BLACK); // Cambia el color del texto
            add(botonesNumeros[i]);
            botonesNumeros[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pantalla.actualizarPantalla(Integer.toString(numero)); // Si deseas actualizar la pantalla con el número presionado
                }
            });
        }
        String[] operaciones = {"+", "-", "*", "/"};
        JButton[] botonesOperaciones = new JButton[operaciones.length];
        for (int j = 0; j < operaciones.length; j++) {
            botonesOperaciones[j] = new JButton(operaciones[j]);
            // Configura el estilo de los botones como lo desees
            botonesOperaciones[j].setFont(new Font("Arial", Font.BOLD, 20));
            botonesOperaciones[j].setBackground(Color.ORANGE);
            botonesOperaciones[j].setForeground(Color.BLACK);
            add(botonesOperaciones[j]);

            // ActionListener para manejar los clics en los botones de operaciones
            botonesOperaciones[j].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton botonPresionado = (JButton) e.getSource();
                    String textoBoton = botonPresionado.getText();
                    // Actualiza la pantalla con el texto del botón de operación
                    pantalla.actualizarPantalla(textoBoton);
                }
            });
        }
        String[] acciones = {"=", "C", "<x", "."};
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
                    // Aquí puedes manejar la acción del botón según su texto
                    // Por ejemplo:
                    if (textoBoton.equals("C")) {
                        // Acción para el botón "C"
                        pantalla.limpiarPantalla();
                    } else if (textoBoton.equals("<x")) {
                        // Acción para el botón "<x"
                    } else if (textoBoton.equals("=")) {
                        // Acción para el botón "="
                        pantalla.operaciones();

                    }
                }
            });
        }

    }
}
