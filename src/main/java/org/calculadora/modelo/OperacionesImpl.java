package org.calculadora.modelo;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;


import org.calculadora.interfaces.Operaciones;

/**
 *
 * @author joel
 */
public class OperacionesImpl implements Operaciones {

    private JLabel jlabel;

    public OperacionesImpl(JLabel jlabel) {
        this.jlabel = jlabel;
    }

    @Override
    public void operaciones() {

        String texto = actualizarPantalla("").toString();

        List<Integer> numeros = new ArrayList<>();

        Pattern patronNumeros = Pattern.compile("\\d+");
        Matcher matcherNumeros = patronNumeros.matcher(texto);

        while (matcherNumeros.find()) {
            String numeroStr = matcherNumeros.group();
            int numero = Integer.parseInt(numeroStr);
            numeros.add(numero);
        }

        Pattern patronOperadores = Pattern.compile("[+\\-*/]");
        Matcher matcherOperadores = patronOperadores.matcher(texto);

        double resultado = numeros.get(0);

        while (matcherOperadores.find()) {
            char operador = matcherOperadores.group().charAt(0);

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
                    if (numeros.get(1) == 0) {
                        jlabel.setText("Error División por cero");
                        jlabel.setFont(new Font("Arial", Font.BOLD, 30));
                        return; 
                    } else {
                        resultado /= numeros.get(1);
                    }
                    break;
            }

            numeros.remove(0);
        }

        if (resultado == Math.floor(resultado)) {
            int entero = (int) resultado;
            jlabel.setText(String.valueOf(entero));

        } else {
            jlabel.setText(String.valueOf(resultado));
        }

    }

    @Override
    public StringBuilder actualizarPantalla(String texto) {
        StringBuilder datosPantalla = new StringBuilder();
        datosPantalla.append(jlabel.getText());
        datosPantalla.append(texto);
        jlabel.setText(datosPantalla.toString());
        limiteNumeros(datosPantalla);

        return datosPantalla;
    }

    @Override
    public void limiteNumeros(StringBuilder datosPantalla) {
        if (datosPantalla.length() > 8) {
            jlabel.setFont(new Font("Arial", Font.BOLD, 50));
        }
        if (datosPantalla.length() > 12) {
            jlabel.setText("Limite superado");
            jlabel.setFont(new Font("Arial", Font.BOLD, 30));
        }
    }

    @Override
    public void limpiarPantalla() {
        jlabel.setText("");
    }

    @Override
    public void BorrarCaracter() {
        String textoActual = jlabel.getText();
        if (!textoActual.isEmpty()) {
            String textoNuevo = textoActual.substring(0, textoActual.length() - 1);
            jlabel.setText(textoNuevo);
        }
    }

}
