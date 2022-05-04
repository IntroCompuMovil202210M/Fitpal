package com.gitgud.fitpal.entidades;

import java.util.ArrayList;

public class Usuario {
    private String arroba;
    private String correo;
    private String biografia;
    private ArrayList<String> listaDeportes;

    public void setArroba(String arroba) {
        this.arroba = arroba;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setListaDeportes(ArrayList<String> listaDeportes) {
        this.listaDeportes = listaDeportes;
    }
}
