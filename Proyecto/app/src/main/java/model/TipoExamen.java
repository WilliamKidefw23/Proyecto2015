package model;


public class TipoExamen {

    private Integer idTipoExamen;
    private String examen;

    public String toString(){
        return examen;
    }

    public Integer getIdTipoExamen() {
        return idTipoExamen;
    }

    public void setIdTipoExamen(Integer idTipoExamen) {
        this.idTipoExamen = idTipoExamen;
    }

    public String getExamen() {
        return examen;
    }

    public void setExamen(String examen) {
        this.examen = examen;
    }
}
