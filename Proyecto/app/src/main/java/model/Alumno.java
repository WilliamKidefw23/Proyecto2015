package model;


public class Alumno {

    private int idAlumno;
    private String nombre;
    private String apellido;
    private Integer nota;
    private String fechaNacimiento;
    private Integer idSeccion;
    private Integer Total;
    private String completo;

    private Seccion seccion;

    public Alumno(){
    }

    public Alumno(int idAlumno, String nombre, String apellido, Integer nota, String fechaNacimiento) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nota = nota;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Alumno(int idAlumno, String nombre, String apellido, Integer nota, String fechaNacimiento,Seccion seccion) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nota = nota;
        this.fechaNacimiento = fechaNacimiento;
        this.seccion = seccion;
    }

    public String toString(){
        return idAlumno+" " + apellido + " " + nombre;}

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {   this.fechaNacimiento = fechaNacimiento;  }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {    this.nota = nota;   }

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }

    public Integer getTotal() {     return Total;   }

    public void setTotal(Integer total) {      Total = total;   }

    public String getCompleto() {   return nombre +" "+ apellido;  }

    public void setCompleto(String completo) {    this.completo = completo;  }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }
}
