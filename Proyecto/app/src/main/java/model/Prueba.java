package model;


public class Prueba {

    private Integer idPrueba;
    private String cantidad;

    public Prueba(){

    }


    public String toString(){
        return cantidad;
    }

    public Integer getIdPrueba() {   return idPrueba;  }

    public void setIdPrueba(Integer idPrueba) {     this.idPrueba = idPrueba;   }

    public String getCantidad() {   return cantidad;  }

    public void setCantidad(String cantidad) {   this.cantidad = cantidad;  }
}
