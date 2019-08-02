package model;

/**
 * Created by Usuario on 4/03/2018.
 */

public class AlumnoCurso {

    private int idAlumno,idCurso;
    private int nota;

    public AlumnoCurso(){  }

    public AlumnoCurso(int idAlumno, int idCurso) {
        this.idAlumno = idAlumno;
        this.idCurso = idCurso;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
