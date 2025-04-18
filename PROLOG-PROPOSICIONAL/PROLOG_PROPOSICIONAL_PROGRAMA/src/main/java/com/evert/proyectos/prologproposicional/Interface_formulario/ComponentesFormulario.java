package com.evert.proyectos.prologproposicional.Interface_formulario;

import javax.swing.*;

public class ComponentesFormulario extends JFrame {
    public ComponentesFormulario() {

    }

    public static JPanel crearPanel(String nombre, int x, int y, int width, int heigth) {
        JPanel panel = new JPanel(null);
        panel.setBorder(BorderFactory.createTitledBorder(nombre));
        panel.setBounds(x, y, width, heigth);
        return panel;
    }

    public static JButton crearBoton(String nombre, int x, int y, int width, int heigth) {
        JButton boton = new JButton(nombre);
        boton.setBounds(x, y, width, heigth);
        return boton;
    }

    public static JLabel crearLabel(String mensaje, int x, int y, int width, int heigth) {
        JLabel label = new JLabel(mensaje);
        label.setBounds(x, y, width, heigth);
        return label;
    }

    public static JTextField crearTextField(String nombre, int x, int y, int width, int heigth) {
        JTextField field = new JTextField(nombre);
        field.setBounds(x, y, width, heigth);
        return field;
    }

}
