package com.gitgud.fitpal.entidades;

public class Deporte {
    private String IdDeporte;
    private String nombre;
    private String diminutivo;
    private String tipo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    String getIdDeporte(){
        return IdDeporte;
    }

    public void setIdDeporte(String idDeporte) {
        IdDeporte = idDeporte;
    }

    public String getDiminutivo() {
        return diminutivo;
    }

    public void setDiminutivo(String diminutivo) {
        this.diminutivo = diminutivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}



