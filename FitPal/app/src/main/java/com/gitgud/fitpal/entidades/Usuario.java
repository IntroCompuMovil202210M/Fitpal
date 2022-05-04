package com.gitgud.fitpal.entidades;

import java.util.ArrayList;

public class Usuario {
    private String arroba;
    private String correo;
    private String bio;
    private ArrayList<String> deportes;
    private String nombre;
    private String apellido;
    private String telefono;
    private ArrayList<String> eventos;

    public String getArroba() {
        return arroba;
    }

    public String getCorreo() {
        return correo;
    }

    public String getBio() {
        return bio;
    }

    public ArrayList<String> getDeportes() {
        return deportes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<String> getEventos() {
        return eventos;
    }

    public void setEventos(ArrayList<String> eventos) {
        this.eventos = eventos;
    }

    public void setArroba(String arroba) {
        this.arroba = arroba;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setDeportes(ArrayList<String> deportes) {
        this.deportes = deportes;
    }
}
