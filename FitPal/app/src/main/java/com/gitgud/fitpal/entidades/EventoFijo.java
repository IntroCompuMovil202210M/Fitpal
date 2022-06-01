package com.gitgud.fitpal.entidades;

public class EventoFijo extends Evento{
    private float latitud;
    private float longitud;

    public EventoFijo() {
        this.latitud = 4;
        this.longitud = 72;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }
}
