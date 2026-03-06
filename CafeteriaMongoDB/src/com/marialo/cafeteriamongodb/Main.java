package com.marialo.cafeteriamongodb;

import com.marialo.cafeteriamongodb.gui.Controlador;
import com.marialo.cafeteriamongodb.gui.Modelo;
import com.marialo.cafeteriamongodb.gui.Vista;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Vista vista = new Vista();
                Modelo modelo = new Modelo();
                Controlador controlador = new Controlador(modelo, vista);
            }
        });
    }
}
