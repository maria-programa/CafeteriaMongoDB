package com.marialo.cafeteriamongodb.gui;

import com.marialo.cafeteriamongodb.base.Empleado;
import com.marialo.cafeteriamongodb.base.Producto;
import com.marialo.cafeteriamongodb.base.Tienda;
import com.marialo.cafeteriamongodb.util.Util;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class Controlador implements ActionListener, KeyListener, ListSelectionListener {
    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;

        inicializar();
    }

    private void inicializar() {
        addActionListeners(this);
        addListSelectionListeners(this);
        addKeyListeners(this);
        setBotonesActivos(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "Conectar":
                conectarBase();
                break;
            case "Desconectar":
                desconectarBase();
                break;
            case "Añadir Empleado":
                annadirEmpleado();
                break;
            case "Modificar Empleado":
                modificarEmpleado();
                break;
            case "Eliminar Empleado":
                eliminarEmpleado();
                break;
            case "Limpiar Empleado":
                limpiarCamposEmpleado();
                vista.listaEmpleados.clearSelection();
                break;
            case "Añadir Producto":
                annadirProducto();
                break;
            case "Modificar Producto":
                modificarProducto();
                break;
            case "Eliminar Producto":
                eliminarProducto();
                break;
            case "Limpiar Producto":
                limpiarCamposProducto();
                vista.listaProductos.clearSelection();
                break;
            case "Añadir Tienda":
                annadirTienda();
                break;
            case "Modificar Tienda":
                modificarTienda();
                break;
            case "Eliminar Tienda":
                eliminarTienda();
                break;
            case "Limpiar Tienda":
                limpiarCamposTienda();
                vista.listaTiendas.clearSelection();
                vista.dlmProductosTienda.clear();
                vista.dlmEmpleadosTienda.clear();
                break;
            case "Mostrar Productos Tienda":
                mostrarProductosTienda();
                break;
            case "Mostrar Empleados Tienda":
                mostrarEmpleadosTienda();
                break;
        }
    }

    private void conectarBase() {
        modelo.conectar();
        vista.conexionItem.setText("Desconectar");
        vista.conexionItem.setActionCommand("Desconectar");
        setBotonesActivos(true);
        cargarDatos();
    }

    private void desconectarBase() {
        modelo.desconectar();
        vista.conexionItem.setText("Conectar");
        vista.conexionItem.setActionCommand("Conectar");
        setBotonesActivos(false);
    }

    private void cargarDatos() {
        listarTiendas(modelo.getTiendas());
        listarEmpleados(modelo.getEmpleados());
        listarProductos(modelo.getProductos());
        cargarCombosTiendas();
    }

    private void cargarCombosTiendas() {
        vista.tiendaEmpleadoCombo.removeAllItems();
        vista.tiendaProductoCombo.removeAllItems();

        List<Tienda> tiendas = modelo.getTiendas();
        for (Tienda t : tiendas) {
            vista.tiendaEmpleadoCombo.addItem(t);
            vista.tiendaProductoCombo.addItem(t);
        }
    }

    private void annadirEmpleado() {
        if (!validarCamposEmpleado()) return;

        try {
            Tienda tienda = (Tienda) vista.tiendaEmpleadoCombo.getSelectedItem();
            Empleado empleado = new Empleado(
                    vista.codigoEmpleadoTxt.getText(),
                    vista.nombreEmpleadoTxt.getText(),
                    vista.apellidosTxt.getText(),
                    (String) vista.cargoCombo.getSelectedItem(),
                    vista.fechaContrDP.getDate(),
                    tienda.getId()
            );

            modelo.guardarObjeto(empleado);
            listarEmpleados(modelo.getEmpleados());
            limpiarCamposEmpleado();
            Util.mensajeInfo("Empleado añadido correctamente");
        } catch (Exception ex) {
            Util.mensajeError("Error al añadir empleado: " + ex.getMessage());
        }
    }

    private void modificarEmpleado() {
        Empleado empleado = (Empleado) vista.listaEmpleados.getSelectedValue();
        if (empleado == null) {
            Util.mensajeError("Debe seleccionar un empleado");
            return;
        }

        if (!validarCamposEmpleado()) return;

        try {
            Tienda tienda = (Tienda) vista.tiendaEmpleadoCombo.getSelectedItem();
            empleado.setCodigoEmpleado(vista.codigoEmpleadoTxt.getText());
            empleado.setNombre(vista.nombreEmpleadoTxt.getText());
            empleado.setApellidos(vista.apellidosTxt.getText());
            empleado.setCargo((String) vista.cargoCombo.getSelectedItem());
            empleado.setFechaContratacion(vista.fechaContrDP.getDate());
            empleado.setIdTienda(tienda.getId());

            modelo.modificarEmpleado(empleado);
            listarEmpleados(modelo.getEmpleados());
            limpiarCamposEmpleado();
            Util.mensajeInfo("Empleado modificado correctamente");
        } catch (Exception ex) {
            Util.mensajeError("Error al modificar empleado: " + ex.getMessage());
        }
    }

    private void eliminarEmpleado() {
        Empleado empleado = (Empleado) vista.listaEmpleados.getSelectedValue();
        if (empleado == null) {
            Util.mensajeError("Debe seleccionar un empleado");
            return;
        }

        int confirmacion = Util.mensajeConfirmacion(
                "¿Está seguro de eliminar el empleado " + empleado.getNombre() + "?",
                "Confirmar eliminación"
        );

        if (confirmacion == 0) {
            try {
                modelo.eliminarEmpleado(empleado);
                listarEmpleados(modelo.getEmpleados());
                limpiarCamposEmpleado();
                Util.mensajeInfo("Empleado eliminado correctamente");
            } catch (Exception ex) {
                Util.mensajeError("Error al eliminar empleado: " + ex.getMessage());
            }
        }
    }

    private void annadirProducto() {
        if (!validarCamposProducto()) return;

        try {
            Tienda tienda = (Tienda) vista.tiendaProductoCombo.getSelectedItem();
            Producto producto = new Producto(
                    (String) vista.categoriaCombo.getSelectedItem(),
                    vista.codigoProductoTxt.getText(),
                    vista.nombreProductoTxt.getText(),
                    Double.parseDouble(vista.precioTxt.getText()),
                    Integer.parseInt(vista.stockTxt.getText()),
                    tienda.getId()
            );

            modelo.guardarObjeto(producto);
            listarProductos(modelo.getProductos());
            limpiarCamposProducto();
            Util.mensajeInfo("Producto añadido correctamente");
        } catch (NumberFormatException ex) {
            Util.mensajeError("Precio y stock deben ser números válidos");
        } catch (Exception ex) {
            Util.mensajeError("Error al añadir producto: " + ex.getMessage());
        }
    }

    private void modificarProducto() {
        Producto producto = (Producto) vista.listaProductos.getSelectedValue();
        if (producto == null) {
            Util.mensajeError("Debe seleccionar un producto");
            return;
        }

        if (!validarCamposProducto()) return;

        try {
            Tienda tienda = (Tienda) vista.tiendaProductoCombo.getSelectedItem();
            producto.setCodigo(vista.codigoProductoTxt.getText());
            producto.setCategoria((String) vista.categoriaCombo.getSelectedItem());
            producto.setNombre(vista.nombreProductoTxt.getText());
            producto.setPrecio(Double.parseDouble(vista.precioTxt.getText()));
            producto.setStock(Integer.parseInt(vista.stockTxt.getText()));
            producto.setIdTienda(tienda.getId());

            modelo.modificarProducto(producto);
            listarProductos(modelo.getProductos());
            limpiarCamposProducto();
            Util.mensajeInfo("Producto modificado correctamente");
        } catch (NumberFormatException ex) {
            Util.mensajeError("Precio y stock deben ser números válidos");
        } catch (Exception ex) {
            Util.mensajeError("Error al modificar producto: " + ex.getMessage());
        }
    }

    private void eliminarProducto() {
        Producto producto = (Producto) vista.listaProductos.getSelectedValue();
        if (producto == null) {
            Util.mensajeError("Debe seleccionar un producto");
            return;
        }

        int confirmacion = Util.mensajeConfirmacion(
                "¿Está seguro de eliminar el producto " + producto.getNombre() + "?",
                "Confirmar eliminación"
        );

        if (confirmacion == 0) {
            try {
                modelo.eliminarProducto(producto);
                listarProductos(modelo.getProductos());
                limpiarCamposProducto();
                Util.mensajeInfo("Producto eliminado correctamente");
            } catch (Exception ex) {
                Util.mensajeError("Error al eliminar producto: " + ex.getMessage());
            }
        }
    }

    private void annadirTienda() {
        if (!validarCamposTienda()) return;

        try {
            Tienda tienda = new Tienda(
                    vista.nombreTiendaTxt.getText(),
                    vista.direccionTxt.getText(),
                    vista.fechaInauguracionDP.getDate(),
                    vista.telefonoTxt.getText()
            );

            modelo.guardarObjeto(tienda);
            listarTiendas(modelo.getTiendas());
            cargarCombosTiendas();
            limpiarCamposTienda();
            Util.mensajeInfo("Tienda añadida correctamente");
        } catch (Exception ex) {
            Util.mensajeError("Error al añadir tienda: " + ex.getMessage());
        }
    }

    private void modificarTienda() {
        Tienda tienda = (Tienda) vista.listaTiendas.getSelectedValue();
        if (tienda == null) {
            Util.mensajeError("Debe seleccionar una tienda");
            return;
        }

        if (!validarCamposTienda()) return;

        try {
            tienda.setNombre(vista.nombreTiendaTxt.getText());
            tienda.setDireccion(vista.direccionTxt.getText());
            tienda.setFechaInauguracion(vista.fechaInauguracionDP.getDate());
            tienda.setTelefono(vista.telefonoTxt.getText());

            modelo.modificarTienda(tienda);
            listarTiendas(modelo.getTiendas());
            cargarCombosTiendas();
            limpiarCamposTienda();
            Util.mensajeInfo("Tienda modificada correctamente");
        } catch (Exception ex) {
            Util.mensajeError("Error al modificar tienda: " + ex.getMessage());
        }
    }

    private void eliminarTienda() {
        Tienda tienda = (Tienda) vista.listaTiendas.getSelectedValue();
        if (tienda == null) {
            Util.mensajeError("Debe seleccionar una tienda");
            return;
        }

        int confirmacion = Util.mensajeConfirmacion(
                "¿Está seguro de eliminar la tienda " + tienda.getNombre() + "?",
                "Confirmar eliminación"
        );

        if (confirmacion == 0) {
            try {
                modelo.eliminarTienda(tienda);
                listarTiendas(modelo.getTiendas());
                cargarCombosTiendas();
                limpiarCamposTienda();
                Util.mensajeInfo("Tienda eliminada correctamente");
            } catch (Exception ex) {
                Util.mensajeError("Error al eliminar tienda: " + ex.getMessage());
            }
        }
    }

    private void mostrarProductosTienda() {
        Tienda tienda = (Tienda) vista.listaTiendas.getSelectedValue();
        if (tienda == null) {
            Util.mensajeError("Debe seleccionar una tienda");
            return;
        }

        listarProductosTienda(modelo.getProductosTienda(tienda.getId()));
    }

    private void mostrarEmpleadosTienda() {
        Tienda tienda = (Tienda) vista.listaTiendas.getSelectedValue();
        if (tienda == null) {
            Util.mensajeError("Debe seleccionar una tienda");
            return;
        }

        listarEmpleadosTienda(modelo.getEmpleadosTienda(tienda.getId()));
    }

    private boolean validarCamposEmpleado() {
        if (vista.codigoEmpleadoTxt.getText().trim().isEmpty()) {
            Util.mensajeError("El código del empleado no puede estar vacío");
            return false;
        }
        if (vista.nombreEmpleadoTxt.getText().trim().isEmpty()) {
            Util.mensajeError("El nombre del empleado no puede estar vacío");
            return false;
        }
        if (vista.apellidosTxt.getText().trim().isEmpty()) {
            Util.mensajeError("Los apellidos del empleado no pueden estar vacíos");
            return false;
        }
        if (vista.cargoCombo.getSelectedIndex() == -1) {
            Util.mensajeError("Debe seleccionar un cargo");
            return false;
        }
        if (vista.tiendaEmpleadoCombo.getSelectedIndex() == -1) {
            Util.mensajeError("Debe seleccionar una tienda");
            return false;
        }
        if (vista.fechaContrDP.getDate() == null) {
            Util.mensajeError("Debe seleccionar una fecha de contratación");
            return false;
        }
        return true;
    }

    private boolean validarCamposProducto() {
        if (vista.codigoProductoTxt.getText().trim().isEmpty()) {
            Util.mensajeError("El código del producto no puede estar vacío");
            return false;
        }
        if (vista.categoriaCombo.getSelectedIndex() == -1) {
            Util.mensajeError("Debe seleccionar una categoría");
            return false;
        }
        if (vista.nombreProductoTxt.getText().trim().isEmpty()) {
            Util.mensajeError("El nombre del producto no puede estar vacío");
            return false;
        }
        if (vista.precioTxt.getText().trim().isEmpty()) {
            Util.mensajeError("El precio no puede estar vacío");
            return false;
        }
        if (vista.stockTxt.getText().trim().isEmpty()) {
            Util.mensajeError("El stock no puede estar vacío");
            return false;
        }
        if (vista.tiendaProductoCombo.getSelectedIndex() == -1) {
            Util.mensajeError("Debe seleccionar una tienda");
            return false;
        }
        return true;
    }

    private boolean validarCamposTienda() {
        if (vista.nombreTiendaTxt.getText().trim().isEmpty()) {
            Util.mensajeError("El nombre de la tienda no puede estar vacío");
            return false;
        }
        if (vista.direccionTxt.getText().trim().isEmpty()) {
            Util.mensajeError("La dirección no puede estar vacía");
            return false;
        }
        if (vista.telefonoTxt.getText().trim().isEmpty()) {
            Util.mensajeError("El teléfono no puede estar vacío");
            return false;
        }
        if (vista.fechaInauguracionDP.getDate() == null) {
            Util.mensajeError("Debe seleccionar una fecha de inauguración");
            return false;
        }
        return true;
    }



    private void setBotonesActivos(boolean activar) {
        vista.annadirEmpleadoButton.setEnabled(activar);
        vista.modificarEmpleadoButton.setEnabled(activar);
        vista.eliminarEmpleadoButton.setEnabled(activar);
        vista.limpiarEmpleadoButton.setEnabled(activar);

        vista.annadirProductoButton.setEnabled(activar);
        vista.modificarProductoButton.setEnabled(activar);
        vista.eliminarProductoButton.setEnabled(activar);
        vista.limpiarProductoButton.setEnabled(activar);

        vista.annadirTiendaButton.setEnabled(activar);
        vista.modificarTiendaButton.setEnabled(activar);
        vista.eliminarTiendaButton.setEnabled(activar);
        vista.limpiarTiendaButton.setEnabled(activar);
        vista.mostrarProductosButton.setEnabled(activar);
        vista.mostrarEmpleadosButton.setEnabled(activar);
    }

    private void limpiarCamposEmpleado() {
        vista.codigoEmpleadoTxt.setText("");
        vista.nombreEmpleadoTxt.setText("");
        vista.apellidosTxt.setText("");
        vista.cargoCombo.setSelectedIndex(-1);
        vista.tiendaEmpleadoCombo.setSelectedIndex(-1);
        vista.fechaContrDP.setDate(null);
        vista.annadirEmpleadoButton.setEnabled(true);
        vista.listaEmpleados.clearSelection();
    }

    private void limpiarCamposProducto() {
        vista.codigoProductoTxt.setText("");
        vista.categoriaCombo.setSelectedIndex(-1);
        vista.nombreProductoTxt.setText("");
        vista.precioTxt.setText("");
        vista.stockTxt.setText("");
        vista.tiendaProductoCombo.setSelectedIndex(-1);
        vista.annadirProductoButton.setEnabled(true);
        vista.listaProductos.clearSelection();
    }

    private void limpiarCamposTienda() {
        vista.nombreTiendaTxt.setText("");
        vista.direccionTxt.setText("");
        vista.telefonoTxt.setText("");
        vista.fechaInauguracionDP.setDate(null);
        vista.annadirTiendaButton.setEnabled(true);
        vista.listaTiendas.clearSelection();
    }


    private void listarEmpleados(List<Empleado> lista) {
        vista.dlmEmpleados.clear();
        for (Empleado e : lista) {
            vista.dlmEmpleados.addElement(e);
        }
    }

    private void listarProductos(List<Producto> lista) {
        vista.dlmProductos.clear();
        for (Producto p : lista) {
            vista.dlmProductos.addElement(p);
        }
    }

    private void listarTiendas(List<Tienda> lista) {
        vista.dlmTiendas.clear();
        for (Tienda t : lista) {
            vista.dlmTiendas.addElement(t);
        }
    }

    private void listarProductosTienda(List<Producto> lista) {
        vista.dlmProductosTienda.clear();
        for (Producto p : lista) {
            vista.dlmProductosTienda.addElement(p);
        }
    }

    private void listarEmpleadosTienda(List<Empleado> lista) {
        vista.dlmEmpleadosTienda.clear();
        for (Empleado e : lista) {
            vista.dlmEmpleadosTienda.addElement(e);
        }
    }

    private void addActionListeners(ActionListener listener) {
        vista.conexionItem.addActionListener(listener);
        vista.salirItem.addActionListener(listener);

        vista.annadirEmpleadoButton.addActionListener(listener);
        vista.annadirEmpleadoButton.setActionCommand("Añadir Empleado");
        vista.modificarEmpleadoButton.addActionListener(listener);
        vista.modificarEmpleadoButton.setActionCommand("Modificar Empleado");
        vista.eliminarEmpleadoButton.addActionListener(listener);
        vista.eliminarEmpleadoButton.setActionCommand("Eliminar Empleado");
        vista.limpiarEmpleadoButton.addActionListener(listener);
        vista.limpiarEmpleadoButton.setActionCommand("Limpiar Empleado");

        vista.annadirProductoButton.addActionListener(listener);
        vista.annadirProductoButton.setActionCommand("Añadir Producto");
        vista.modificarProductoButton.addActionListener(listener);
        vista.modificarProductoButton.setActionCommand("Modificar Producto");
        vista.eliminarProductoButton.addActionListener(listener);
        vista.eliminarProductoButton.setActionCommand("Eliminar Producto");
        vista.limpiarProductoButton.addActionListener(listener);
        vista.limpiarProductoButton.setActionCommand("Limpiar Producto");

        vista.annadirTiendaButton.addActionListener(listener);
        vista.annadirTiendaButton.setActionCommand("Añadir Tienda");
        vista.modificarTiendaButton.addActionListener(listener);
        vista.modificarTiendaButton.setActionCommand("Modificar Tienda");
        vista.eliminarTiendaButton.addActionListener(listener);
        vista.eliminarTiendaButton.setActionCommand("Eliminar Tienda");
        vista.limpiarTiendaButton.addActionListener(listener);
        vista.limpiarTiendaButton.setActionCommand("Limpiar Tienda");

        vista.mostrarProductosButton.addActionListener(listener);
        vista.mostrarProductosButton.setActionCommand("Mostrar Productos Tienda");
        vista.mostrarEmpleadosButton.addActionListener(listener);
        vista.mostrarEmpleadosButton.setActionCommand("Mostrar Empleados Tienda");
    }

    private void addListSelectionListeners(ListSelectionListener listener) {
        vista.listaEmpleados.addListSelectionListener(listener);
        vista.listaProductos.addListSelectionListener(listener);
        vista.listaTiendas.addListSelectionListener(listener);
    }

    private void addKeyListeners(KeyListener listener) {
        vista.buscarEmpleadoTxt.addKeyListener(listener);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.buscarEmpleadoTxt) {
            String texto = vista.buscarEmpleadoTxt.getText();
            if (texto.length() >= 2 || texto.length() == 0) {
                if (texto.length() == 0) {
                    listarEmpleados(modelo.getEmpleados());
                } else {
                    listarEmpleados(modelo.getEmpleados(texto));
                }
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            if (e.getSource() == vista.listaEmpleados) {
                vista.annadirEmpleadoButton.setEnabled(false);
                Empleado emp = (Empleado) vista.listaEmpleados.getSelectedValue();
                vista.codigoEmpleadoTxt.setText(emp.getCodigoEmpleado());
                vista.nombreEmpleadoTxt.setText(emp.getNombre());
                vista.apellidosTxt.setText(emp.getApellidos());
                vista.cargoCombo.setSelectedItem(emp.getCargo());
                for (int i = 0; i < vista.tiendaEmpleadoCombo.getItemCount(); i++) {
                    Tienda t = (Tienda) vista.tiendaEmpleadoCombo.getItemAt(i);
                    if (t.getId().equals(emp.getIdTienda())) {
                        vista.tiendaEmpleadoCombo.setSelectedIndex(i);
                        break;
                    }
                }
                vista.fechaContrDP.setDate(emp.getFechaContratacion());
            }
            if (e.getSource() == vista.listaProductos) {
                vista.annadirProductoButton.setEnabled(false);
                Producto p = (Producto) vista.listaProductos.getSelectedValue();
                vista.codigoProductoTxt.setText(p.getCodigo());
                vista.categoriaCombo.setSelectedItem(p.getCategoria());
                vista.nombreProductoTxt.setText(p.getNombre());
                vista.precioTxt.setText(String.valueOf(p.getPrecio()));
                vista.stockTxt.setText(String.valueOf(p.getStock()));
                for (int i = 0; i < vista.tiendaProductoCombo.getItemCount(); i++) {
                    Tienda t = (Tienda) vista.tiendaProductoCombo.getItemAt(i);
                    if (t.getId().equals(p.getIdTienda())) {
                        vista.tiendaProductoCombo.setSelectedIndex(i);
                        break;
                    }
                }
            }
            if (e.getSource() == vista.listaTiendas) {
                vista.annadirTiendaButton.setEnabled(false);
                Tienda t = (Tienda) vista.listaTiendas.getSelectedValue();
                vista.nombreTiendaTxt.setText(t.getNombre());
                vista.direccionTxt.setText(t.getDireccion());
                vista.fechaInauguracionDP.setDate(t.getFechaInauguracion());
                vista.telefonoTxt.setText(t.getTelefono());
            }
        }
    }
}
