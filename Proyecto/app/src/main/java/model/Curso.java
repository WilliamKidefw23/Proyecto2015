package model;


public class Curso {

    private Integer idCurso;
    private String nomCurso;
    private int estado;

    public Curso(){

    }

    public Curso(Integer idCurso, String nomCurso,int estado) {
        this.idCurso = idCurso;
        this.nomCurso = nomCurso;
        this.estado=estado;
    }

    public String toString(){
        return nomCurso;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public String getNomCurso() {
        return nomCurso;
    }

    public void setNomCurso(String nomCurso) {
        this.nomCurso = nomCurso;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
