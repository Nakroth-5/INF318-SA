package com.evert.proyectos.prologproposicional.Clases;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ArchivoTexto {
    BaseDeConocimiento baseDeConocimiento;
    private final File archivo;

    public ArchivoTexto(String nombre) {
        Objects.requireNonNull(nombre, "El nombre del archivo no puede ser null");
        this.archivo = new File(nombre);
    }

    public void guardar(String contenido) throws IOException {
        Objects.requireNonNull(contenido, "El contenido a guardar no puede ser null");

        try (BufferedWriter escritura = new BufferedWriter(new FileWriter(archivo))) {
            escritura.write(contenido);
        }
    }

}
