package model;


public class Ciclo {

    private int idCiclo;
    private String ciclo;

    public Ciclo(){

    }

    public Ciclo(int idCiclo, String ciclo) {
        this.idCiclo = idCiclo;
        this.ciclo = ciclo;
    }

    public String toString(){
        return ciclo;
    }

    public int getIdCiclo() {
        return idCiclo;
    }

    public void setIdCiclo(int idCiclo) {
        this.idCiclo = idCiclo;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }
}
