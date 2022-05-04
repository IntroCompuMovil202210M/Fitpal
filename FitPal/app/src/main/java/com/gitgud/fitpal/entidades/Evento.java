package com.gitgud.fitpal.entidades;

import java.util.ArrayList;

public class Evento {
    private String organizador;
    private String fechahora;
    private float duracion;
    private String deporte;
    private ArrayList<String> Asistentes;
    private String descrpicion;

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public String getFechahora() {
        return fechahora;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
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
}
