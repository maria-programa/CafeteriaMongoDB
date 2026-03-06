package com.marialo.cafeteriamongodb.gui;

import com.marialo.cafeteriamongodb.base.Empleado;
import com.marialo.cafeteriamongodb.base.Producto;
import com.marialo.cafeteriamongodb.base.Tienda;
import com.marialo.cafeteriamongodb.base.enums.Cargo;
import com.marialo.cafeteriamongodb.util.Util;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Modelo {
    private final static String DATABASE = "CafeteriaMongoDB";
    private final static String COLECCION_EMPLEADOS = "Empleados";
    private final static String COLECCION_PRODUCTOS = "Productos";
    private final static String COLECCION_TIENDAS = "Tiendas";

    private MongoClient cliente;
    private MongoCollection<Document> empleados;
    private MongoCollection<Document> productos;
    private MongoCollection<Document> tiendas;

    public void conectar() {
        cliente = new MongoClient();

        MongoDatabase db = cliente.getDatabase(DATABASE);
        empleados = db.getCollection(COLECCION_EMPLEADOS);
        productos = db.getCollection(COLECCION_PRODUCTOS);
        tiendas = db.getCollection(COLECCION_TIENDAS);
    }

    public void desconectar() {
        cliente.close();
        cliente = null;
    }

    public void guardarObjeto(Object obj) {
        if (obj instanceof Empleado) {
            empleados.insertOne(objectToDocument(obj));
        } else if (obj instanceof Producto) {
            productos.insertOne(objectToDocument(obj));
        } else if (obj instanceof Tienda) {
            tiendas.insertOne(objectToDocument(obj));
        }
    }

    public Document objectToDocument(Object obj) {
        Document document =  new Document();

        if (obj instanceof Empleado) {
            Empleado emp = (Empleado) obj;

            document.append("codigoEmpleado", emp.getCodigoEmpleado());
            document.append("nombre", emp.getNombre());
            document.append("apellidos", emp.getApellidos());
            document.append("cargo", emp.getCargo());
            document.append("fechaContratacion", Util.formatearFecha(emp.getFechaContratacion()));
            document.append("idTienda", emp.getIdTienda());
        } else if (obj instanceof Producto) {
            Producto prod = (Producto) obj;

            document.append("codigo", prod.getCodigo());
            document.append("categoria", prod.getCategoria());
            document.append("nombre", prod.getNombre());
            document.append("precio", prod.getPrecio());
            document.append("stock", prod.getStock());
            document.append("idTienda", prod.getIdTienda());
        } else if (obj instanceof Tienda) {
            Tienda tien = (Tienda) obj;

            document.append("nombre", tien.getNombre());
            document.append("direccion", tien.getDireccion());
            document.append("fechaInauguracion", Util.formatearFecha(tien.getFechaInauguracion()));
            document.append("telefono", tien.getTelefono());
        } else {
            return null;
        }
        return document;
    }

    public ArrayList<Empleado> getEmpleados() {
        ArrayList<Empleado> lista = new ArrayList<>();

        for (Document d : empleados.find()) {
            lista.add(documentToEmpleado(d));
        }

        return lista;
    }

    public Empleado documentToEmpleado(Document document) {
        Empleado emp = new Empleado();
        Cargo c;
        emp.setId(document.getObjectId("_id"));
        emp.setCodigoEmpleado(document.getString("codigoEmpleado"));
        emp.setNombre(document.getString("nombre"));
        emp.setApellidos(document.getString("apellidos"));
        emp.setCargo(document.getString("cargo"));
        emp.setFechaContratacion(Util.parsearFecha(document.getString("fechaContratacion")));
        emp.setIdTienda(document.getObjectId("idTienda"));

        return emp;
    }

    public void modificarEmpleado(Empleado empleado) {
        empleados.replaceOne(new Document("_id", empleado.getId()), objectToDocument(empleado));
    }

    public void eliminarEmpleado (Empleado empleado) {
        empleados.deleteOne(objectToDocument(empleado));
    }

    public ArrayList<Producto> getProductos() {
        ArrayList<Producto> lista = new ArrayList<>();

        for (Document d : productos.find()) {
            lista.add(documentToProducto(d));
        }

        return lista;
    }

    public Producto documentToProducto(Document document) {
        Producto prod = new Producto();

        prod.setId(document.getObjectId("_id"));
        prod.setCodigo(document.getString("codigo"));
        prod.setCategoria(document.getString("categoria"));
        prod.setNombre(document.getString("nombre"));
        prod.setPrecio(document.getDouble("precio"));
        prod.setStock(document.getInteger("stock"));
        prod.setIdTienda(document.getObjectId("idTienda"));

        return prod;
    }

    public void modificarProducto(Producto producto) {
        productos.replaceOne(new Document("_id", producto.getId()), objectToDocument(producto));
    }

    public void eliminarProducto (Producto producto) {
        productos.deleteOne(objectToDocument(producto));
    }

    public ArrayList<Tienda> getTiendas() {
        ArrayList<Tienda> lista = new ArrayList<>();

        for (Document d : tiendas.find()) {
            lista.add(documentToTienda(d));
        }

        return lista;
    }

    public Tienda documentToTienda(Document document) {
        Tienda tienda = new Tienda();

        tienda.setId(document.getObjectId("_id"));
        tienda.setNombre(document.getString("nombre"));
        tienda.setDireccion(document.getString("direccion"));
        tienda.setFechaInauguracion(Util.parsearFecha(document.getString("fechaInauguracion")));
        tienda.setTelefono(document.getString("telefono"));

        return tienda;
    }

    public void modificarTienda(Tienda tienda) {
        tiendas.replaceOne(new Document("_id", tienda.getId()), objectToDocument(tienda));
    }

    public void eliminarTienda (Tienda tienda) {
        tiendas.deleteOne(objectToDocument(tienda));
    }

    public void serComboBoxTienda(ArrayList<Tienda> lista, JComboBox combo) {
        for (Tienda t : lista) {
            combo.addItem(t);
        }
        combo.setSelectedIndex(-1);
    }

    public ArrayList<Producto> getProductosTienda(ObjectId idTienda) {
        ArrayList<Producto> lista = new ArrayList<>();
        Document query = new Document();

        query.append("idTienda", idTienda);

        for (Document d : productos.find(query)) {
            lista.add(documentToProducto(d));
        }

        return lista;
    }

    public ArrayList<Empleado> getEmpleadosTienda(ObjectId idTienda) {
        ArrayList<Empleado> lista = new ArrayList<>();
        Document query = new Document();

        query.append("idTienda", idTienda);

        for (Document d : empleados.find(query)) {
            lista.add(documentToEmpleado(d));
        }

        return lista;
    }

    public ArrayList<Empleado> getEmpleados(String text) {
        ArrayList<Empleado> lista = new ArrayList<>();
        Document query = new Document();
        List<Document> listaCriterios = new ArrayList<>();
        listaCriterios.add(new Document("codigoEmpleado", new Document("$regex", "/*" + text + "/*")));
        listaCriterios.add(new Document("apellidos", new Document("$regex", "/*" + text + "/*")));
        query.append("$or", listaCriterios);

        Iterator<Document> iterator = empleados.find(query).iterator();
        while (iterator.hasNext()) {
            lista.add(documentToEmpleado(iterator.next()));
        }
        return lista;
    }
}
