package com.gitgud.fitpal.entidades;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Evento {
    private String id;
    private String organizador;
    private Timestamp fechahora;
    private float duracion;
    private String deporte;
    private ArrayList<String> Asistentes;
    private String descrpicion;
    private String tipo;

    public String getOrganizador() {
        return organizador;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public float getDuracion() {
        return duracion;
    }

    public void setDuracion(float duracion) {
        this.duracion = duracion;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public ArrayList<String> getAsistentes() {
        return Asistentes;
    }

    public void setAsistentes(ArrayList<String> asistentes) {
        Asistentes = asistentes;
    }

    public String getDescrpicion() {
        return descrpicion;
    }

    public void setDescrpicion(String descrpicion) {
        this.descrpicion = descrpicion;
    }

    public Timestamp getFechahora() {
        return fechahora;
    }

    public void setFechahora(Timestamp fechahora) {
        this.fechahora = fechahora;
    }

    public String getTipo() {return tipo;}
    public void setTipo(String tipo){this.tipo = tipo;}
}
