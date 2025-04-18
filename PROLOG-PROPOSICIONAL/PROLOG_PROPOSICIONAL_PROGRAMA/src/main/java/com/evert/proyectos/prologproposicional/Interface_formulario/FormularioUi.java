package com.evert.proyectos.prologproposicional.Interface_formulario;

import com.evert.proyectos.prologproposicional.Clases.ArchivoTexto;
import com.evert.proyectos.prologproposicional.Clases.BaseDeConocimiento;
import com.evert.proyectos.prologproposicional.Clases.ResolverBC;
import com.evert.proyectos.prologproposicional.Nodo.NodoProlog;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

public class FormularioUi extends JFrame {
    private PanelArbolProlog panelArbolProlog;
    private BaseDeConocimiento baseDeConocimiento;
    private ResolverBC resolverBC;
    private ArchivoTexto archivo;
    private boolean archivoAbierto;
    private Map<String, JTextField> textFields;
    private Map<String, JLabel> labels;
    private JTextField textField;
    private JLabel label;
    private JFileChooser fileChooser;
    JTextArea txtBC;

    public FormularioUi() {
        baseDeConocimiento = new BaseDeConocimiento();
        resolverBC = new ResolverBC(baseDeConocimiento);
        textFields = new HashMap<>();
        labels = new HashMap<>();

        archivoAbierto = false;

        configurarVentana();
        agregarPanelArbol();
        agregarPanelOperaciones();
        menuini();

        setVisible(true);
    }

    private void menuini() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem mnAbrir = new JMenuItem("Abrir");
        mnAbrir.addActionListener(this::abrirArchivo);
        menuArchivo.add(mnAbrir);

        JMenuItem mnGuardar = new JMenuItem("Guardar");
        mnGuardar.addActionListener(this::guardarArchivo);
        menuArchivo.add(mnGuardar);

        JMenuItem mnGuardarComo = new JMenuItem("Guardar como");
        mnGuardarComo.addActionListener(this::guardarComoArchivo);
        menuArchivo.add(mnGuardarComo);


        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);
    }

    private void configurarVentana() {
        setTitle("PROLOG Proposicional");
        setSize(1050, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFont(new Font("Consolas", Font.PLAIN, 16));

        setLayout(null);
    }

    private void agregarPanelArbol() {

        panelArbolProlog = new PanelArbolProlog(null);
        panelArbolProlog.setBounds(320, 60, 700, 500);
        add(panelArbolProlog);

        label = ComponentesFormulario
                .crearLabel("Árbol de refutación", 600, 30, 200, 30);
        add(label);
    }

    private void agregarPanelOperaciones() {

        JPanel panelOperaciones = ComponentesFormulario
                .crearPanel("Operaciones", 10, 20, 300, 150);
        add(panelOperaciones);

        label = ComponentesFormulario
                .crearLabel("Ingrese su consulta", 10, 20, 150, 30);
        labels.put("consulta", label);
        panelOperaciones.add(label);

        textField = ComponentesFormulario
                .crearTextField("?-", 170, 20, 100, 30);
        textFields.put("txtVerificar", textField);
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new PrefijoFilter("?-"));
        panelOperaciones.add(textField);

        JButton btnVerificar = ComponentesFormulario
                .crearBoton("Verificar", 10, 60, 120, 30);
        panelOperaciones.add(btnVerificar);
        btnVerificar.addActionListener(this::verificar);

        label = ComponentesFormulario
                .crearLabel("Base de Conocimiento", 20, 180, 150, 30);
        labels.put("BC", label);
        add(label);

        txtBC = new JTextArea();
        txtBC.setBounds(10, 220, 300, 400);
        add(txtBC);

        label = ComponentesFormulario
                .crearLabel("", 170, 60, 80, 30);
        labels.put("labelVerificador", label);
        panelOperaciones.add(label);

        JButton btnCerrar = ComponentesFormulario
                .crearBoton("Cerrar", 10, 100, 120, 30);
        panelOperaciones.add(btnCerrar);
        btnCerrar.addActionListener(e -> dispose());

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos prolog (*.pl)", "pl"));

    }


    private void verificar(ActionEvent e) {
        if (!archivoAbierto)
            JOptionPane.showMessageDialog(this,
                    "Para verificar la solución tiene que crear o abrir un archivo.pl");
        else {
            String metass = textFields.get("txtVerificar").getText().substring(2).trim();
            metass = metass.endsWith(".") ? metass.substring(0, metass.length() - 1) : metass;
            List<String> metas = Arrays.stream(metass.split(","))
                    .toList();
            NodoProlog arbol = resolverBC.resolver(metas);
            Boolean resultado = arbol.isResuelto();
            labels.get("labelVerificador").setText(resultado != null ? resultado.toString() : "falso");
            panelArbolProlog.setArbol(arbol);
            panelArbolProlog.repaint();
        }
    }

    private void abrirArchivo(ActionEvent actionEvent) {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String contenido = Files.readString(fileChooser.getSelectedFile().toPath());
                txtBC.setText(contenido);

                procesarArchivo(contenido);

                archivoAbierto = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void guardarArchivo(ActionEvent e) {
        if (fileChooser.getSelectedFile() == null) {
            guardarComoArchivo(e);
        } else {
            try {
                archivo = new ArchivoTexto(fileChooser.getSelectedFile().getPath());
                archivo.guardar(txtBC.getText());
                procesarArchivo(txtBC.getText());
                JOptionPane.showMessageDialog(this, "Archivo guardado con éxito",
                        "Guardar", JOptionPane.INFORMATION_MESSAGE);
                archivoAbierto = true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarComoArchivo(ActionEvent e) {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getPath();
                if (!filePath.toLowerCase().endsWith(".pl")) {
                    filePath += ".pl";
                }

                archivo = new ArchivoTexto(filePath);
                archivo.guardar(txtBC.getText());
                procesarArchivo(txtBC.getText());
                JOptionPane.showMessageDialog(this, "Archivo guardado con éxito",
                        "Guardar", JOptionPane.INFORMATION_MESSAGE);

                archivoAbierto = true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void procesarArchivo(String contenido) {
        baseDeConocimiento = new BaseDeConocimiento();
        resolverBC = new ResolverBC(baseDeConocimiento);

        int lineasProcesadas = 0;
        int errores = 0;
        StringBuilder erroresMsg = new StringBuilder();

        String[] lineas = contenido.split("\n");
        for (String linea : lineas) {
            linea = linea.trim();
            if (!linea.isEmpty()) {
                try {
                    procesarLinea(linea);
                    lineasProcesadas++;
                } catch (Exception e) {
                    errores++;
                    erroresMsg.append("Error en línea: ").append(linea)
                            .append(" - ").append(e.getMessage()).append("\n");
                }
            }
        }
        if (errores > 0) {
            JOptionPane.showMessageDialog(this,
                    "Procesadas: " + lineasProcesadas + " líneas\n" +
                            "Errores: " + errores + "\n\n" +
                            erroresMsg.toString(),
                    "Resultado del procesamiento",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void procesarLinea(String linea) {
        linea = linea.endsWith(".") ? linea.substring(0, linea.length() - 1) : linea;
        String[] partes = linea.split(":-", 2);
        String cabeza = partes[0].trim();
        List<String> cuerpo = new ArrayList<>();

        if (partes.length > 1) {
            cuerpo = Arrays.stream(partes[1].split(",\\s*"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(s -> s.endsWith(".") ? s.substring(0, s.length() - 1) : s)
                    .filter(s -> !s.chars().allMatch(Character::isDigit))
                    .filter(s -> (s.length() == 1) || s.equals("fail"))
                    .filter(s -> isalpha(s.charAt(0)))
                    .toList();
        }

        if (partes.length > 1 && cuerpo.isEmpty()) {
            showWarning("Sintaxis incorrecta en: " + linea + " (Por tanto no se tomara en cuenta esta linea)");
        } else {
            if (cabeza.length() == 1) {
                baseDeConocimiento.insertarFinal(cabeza, cuerpo);
            }
        }
    }

    private boolean isalpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '!';
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }



    public static void main(String[] arg) {

        SwingUtilities.invokeLater(FormularioUi::new);
    }
}
