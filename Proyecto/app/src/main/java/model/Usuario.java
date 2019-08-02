package model;


public class Usuario {
    private Integer idUsuario;
    private String nombres;
    private String apellidop;
    private String apellidom;
    private String login;
    private String password;
    private String creado;
    private Integer idModalidad;

    public Usuario() {

    }

    public Usuario(Integer idUsuario, String nombres, String apellidop, String apellidom, String login, String password, String creado) {
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.apellidop = apellidop;
        this.apellidom = apellidom;
        this.login = login;
        this.password = password;
        this.creado = creado;
    }

    public String toString(){
        return nombres;
    }

    public Integer getIdUsuario() {   return idUsuario;  }

    public void setIdUsuario(Integer idUsuario) {   this.idUsuario = idUsuario;  }

    public String getNombres() {   return nombres;   }

    public void setNombres(String nombres) {   this.nombres = nombres;  }

    public String getApellidop() {   return apellidop;  }

    public void setApellidop(String apellidop) {   this.apellidop = apellidop;   }

    public String getApellidom() {   return apellidom;  }

    public void setApellidom(String apellidom) {   this.apellidom = apellidom;  }

    public String getLogin() {   return login;   }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreado() {  return creado;  }

    public void setCreado(String creado) {    this.creado = creado;  }


    public Integer getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(Integer idModalidad) {
        this.idModalidad = idModalidad;
    }
}
