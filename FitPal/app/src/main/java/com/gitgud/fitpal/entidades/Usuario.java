package com.gitgud.fitpal.entidades;

import java.util.ArrayList;

public class Usuario {
    private String username;
    private String userid;
    private String email;
    private String password;
    private String bio;
    private ArrayList<String> deportes;
    private String nombre;
    private String apellido;
    private String telefono;
    private ArrayList<String> eventos;
    private boolean perfilCompleto;

    public Usuario(){
        this.username= "";
        this.userid= "";
        this.email="";
        this.password= "";
        this.bio = "";
        this.deportes = new ArrayList<>();
        this.nombre = "";
        this.apellido = "";
        this.telefono = "";
        this.eventos = new ArrayList<>();
        this.perfilCompleto = false;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String arroba) {
        this.username = arroba;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setDeportes(ArrayList<String> deportes) {
        this.deportes = deportes;
    }

    public boolean isPerfilCompleto() {
        return perfilCompleto;
    }

    public void setPerfilCompleto(boolean perfilCompleto) {
        this.perfilCompleto = perfilCompleto;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    public String getUserId() {
        return this.userid;
    }

    public void setUserId(String userid) {
        this.userid= userid;
    }
}
