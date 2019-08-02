package model;


public class Seccion {

    private Integer idSeccion;
    private String seccion;
    private int estado;

    public Seccion(){

    }

    public Seccion(Integer idSeccion, String seccion, int estado) {
        this.idSeccion = idSeccion;
        this.seccion = seccion;
        this.estado = estado;
    }

    public String toString(){
        return seccion;
    }

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
