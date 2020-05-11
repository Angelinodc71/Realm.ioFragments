package com.example.realmiofragments.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Producto extends RealmObject {
    @PrimaryKey
    private int id;

    private String tienda, nombre;
    private boolean isMake;

    public Producto(Integer id, String author, String subject, boolean isMake) {
        this.id = id;
        this.tienda = author;
        this.nombre = subject;
        this.isMake = isMake;
    }

    public Producto(String author, String subject) {
        this.tienda = author;
        this.nombre = subject;
    }

    public Producto() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isMake() {
        return isMake;
    }

    public void setMake(boolean make) {
        isMake = make;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id='" + id + '\'' +
                ", tienda='" + tienda + '\'' +
                ", nombre='" + nombre + '\'' +
                ", isMake=" + isMake +
                '}';
    }
}
