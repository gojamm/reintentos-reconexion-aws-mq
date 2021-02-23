package com.cwtest.model;

public class TrazaDesconexion {
    String evento;
    String mensajeOriginal;
    String destino;

    public TrazaDesconexion(String evento, String mensajeOriginal, String destino) {
        this.evento = evento;
        this.mensajeOriginal = mensajeOriginal;
        this.destino = destino;
    }
}
