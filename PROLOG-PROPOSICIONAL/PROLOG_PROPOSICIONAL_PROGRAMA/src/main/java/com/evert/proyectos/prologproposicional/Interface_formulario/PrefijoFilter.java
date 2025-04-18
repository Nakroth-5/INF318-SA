package com.evert.proyectos.prologproposicional.Interface_formulario;
import javax.swing.text.*;

public class PrefijoFilter extends DocumentFilter {
    private final String prefijo;

    public PrefijoFilter(String prefijo) {
        this.prefijo = prefijo;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (offset < prefijo.length()) return;  // Evitar insertar antes del prefijo
        super.insertString(fb, offset, string, attr);
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        if (offset < prefijo.length()) return;  // Evitar borrar el prefijo
        super.remove(fb, offset, length);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (offset < prefijo.length()) return;  // Evitar reemplazar el prefijo
        super.replace(fb, offset, length, text, attrs);
    }
}
