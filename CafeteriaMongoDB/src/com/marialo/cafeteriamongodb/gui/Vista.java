package com.marialo.cafeteriamongodb.gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.marialo.cafeteriamongodb.base.enums.Cargo;
import com.marialo.cafeteriamongodb.base.enums.Categoria;

import javax.swing.*;

public class Vista extends JFrame {
    private JPanel panel;
    private JTabbedPane tabbedPane1;
    private JFrame frame;
    JPanel JPanelTienda;
    JPanel JPanelProducto;
    JPanel JPanelEmpleados;

    // EMPLEADO
    JLabel coidgoEmpleadoLbl;
    JTextField codigoEmpleadoTxt;
    JTextField nombreEmpleadoTxt;
    JLabel nombreEmpleadoLbl;
    JLabel apellidosLbl;
    JTextField apellidosTxt;
    JComboBox cargoCombo;
    JLabel cargoLbl;
    JLabel tiendaLbl;
    JComboBox tiendaEmpleadoCombo;
    JButton annadirEmpleadoButton;
    JLabel fechaContrLbl;
    DatePicker fechaContrDP;
    JList listaEmpleados;
    JButton modificarEmpleadoButton;
    JButton eliminarEmpleadoButton;
    JButton limpiarEmpleadoButton;

    // PRODUCTO
    JTextField codigoProductoTxt;
    JLabel codigoProductoLbl;
    JComboBox categoriaCombo;
    JLabel categoriaLbl;
    JTextField nombreProductoTxt;
    JTextField precioTxt;
    JTextField stockTxt;
    JComboBox tiendaProductoCombo;
    JLabel nombreProductoLbl;
    JLabel precioLbl;
    JLabel stockLbl;
    JLabel tiendaProductoLbl;
    JButton annadirProductoButton;
    JList listaProductos;
    JButton modificarProductoButton;
    JButton eliminarProductoButton;
    JButton limpiarProductoButton;

    // TIENDA
    JTextField nombreTiendaTxt;
    JTextField direccionTxt;
    JTextField telefonoTxt;
    JLabel nombreTiendaLbl;
    JLabel direccionLbl;
    JLabel fechaInauguracionLbl;
    DatePicker fechaInauguracionDP;
    JLabel telefonoLbl;
    JButton annadirTiendaButton;
    JList listaTiendas;
    JButton modificarTiendaButton;
    JButton eliminarTiendaButton;
    JTextField buscarEmpleadoTxt;
    JButton mostrarProductosButton;
    JList listaProductosTienda;
    JButton mostrarEmpleadosButton;
    JList listaEmpleadosTienda;
    JButton limpiarTiendaButton;

    // LISTAS
    DefaultListModel dlmEmpleados;
    DefaultListModel dlmProductos;
    DefaultListModel dlmTiendas;
    DefaultListModel dlmProductosTienda;
    DefaultListModel dlmEmpleadosTienda;

    // MENU
    JMenuItem conexionItem;
    JMenuItem salirItem;

    public Vista() {
        setTitle("Cafetería María");
        setContentPane(panel);
        //ImageIcon img = new ImageIcon("./assets/coffee-beans.png");
        //frame.setIconImage(img.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        setMenu();
        setModelos();
        setComboBox();
    }

    private void setMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu menu = new JMenu("Archivo");

        conexionItem = new JMenuItem("Conectar");
        conexionItem.setActionCommand("Conectar");

        salirItem = new JMenuItem("Salir");
        salirItem.setActionCommand("Salir");

        menu.add(conexionItem);
        menu.add(salirItem);
        barra.add(menu);
        setJMenuBar(barra);
    }

    private void setModelos() {
        dlmEmpleados = new DefaultListModel();
        listaEmpleados.setModel(dlmEmpleados);

        dlmProductos = new DefaultListModel();
        listaProductos.setModel(dlmProductos);

        dlmTiendas = new DefaultListModel();
        listaTiendas.setModel(dlmTiendas);

        dlmProductosTienda = new DefaultListModel();
        listaProductosTienda.setModel(dlmProductosTienda);

        dlmEmpleadosTienda = new DefaultListModel();
        listaEmpleadosTienda.setModel(dlmEmpleadosTienda);
    }

    private void setComboBox(){
        for (Cargo c : Cargo.values()) {
            cargoCombo.addItem(c.getCargo());
        }
        cargoCombo.setSelectedIndex(-1);

        for (Categoria c : Categoria.values()) {
            categoriaCombo.addItem(c.getCategoria());
        }
        categoriaCombo.setSelectedIndex(-1);
    }
}
