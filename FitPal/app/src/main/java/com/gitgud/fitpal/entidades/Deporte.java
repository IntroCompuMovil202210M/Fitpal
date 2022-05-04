package com.gitgud.fitpal.entidades;

public class Deporte {
    private String IdDeporte;
    private String Diminutivo;
    private String Tipo;

    String getIdDeporte(){
        return IdDeporte;
    }

    public void setIdDeporte(String idDeporte) {
        IdDeporte = idDeporte;
    }

    public String getDiminutivo() {
        return Diminutivo;
    }

    public void setDiminutivo(String diminutivo) {
        Diminutivo = diminutivo;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
}



