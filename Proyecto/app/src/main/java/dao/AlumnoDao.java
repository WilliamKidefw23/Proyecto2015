package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import conexion.DataBaseHelper;
import model.Alumno;
import model.AlumnoCurso;
import model.Seccion;

public class AlumnoDao {

    private DataBaseHelper db;
    private SQLiteDatabase data;

    public AlumnoDao(Context context){
        db =  new DataBaseHelper(context);
    }

    private SQLiteDatabase getDataBase(){
        if(data==null){
            data= db.getWritableDatabase();
        }
        return  data;
    }

    public Alumno RegistrarAlumno(Cursor cursor,Seccion seccion){
        Alumno alumno = new Alumno(
                cursor.getInt(cursor.getColumnIndex(ProyectoData.KEY_ALUMNO)),
                cursor.getString(cursor.getColumnIndex(ProyectoData.NOMBRES_ALUMNO)),
                cursor.getString(cursor.getColumnIndex(ProyectoData.APELLIDO_ALUMNO)),
                cursor.getInt(cursor.getColumnIndex(ProyectoData.NOTA_ALUMNO)),
                cursor.getString(cursor.getColumnIndex(ProyectoData.FECHA_ALUMNO)),seccion
        );
        return  alumno;
    }

    public List<Alumno> listarAlumnos(){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_ALUMNO,
                ProyectoData.COLUMNS_ALUMNO,null,null,null,null,null);
        List<Alumno> alumnos  = new ArrayList<>();
        while (cursor.moveToNext()){
            Seccion seccion = new Seccion();
            seccion.setIdSeccion(cursor.getColumnIndex(ProyectoData.SECCION_ALUMNO));
            Alumno alumno = RegistrarAlumno(cursor,seccion);
            alumnos.add(alumno);
        }
        cursor.close();
        return alumnos;
    }

    public long GuardarAlumno(Alumno alumno){
        ContentValues valores =  new ContentValues();
        valores.put(ProyectoData.NOMBRES_ALUMNO,alumno.getNombre());
        valores.put(ProyectoData.APELLIDO_ALUMNO,alumno.getApellido());
        valores.put(ProyectoData.NOTA_ALUMNO,alumno.getNota());
        valores.put(ProyectoData.FECHA_ALUMNO,alumno.getFechaNacimiento());
        valores.put(ProyectoData.SECCION_ALUMNO,alumno.getSeccion().getIdSeccion());
        if(alumno.getIdAlumno()!= 0){
            return  getDataBase().update(ProyectoData.TABLE_NAME_ALUMNO, valores,
                    "idAlumno = ?", new String[]{String.valueOf(alumno.getIdAlumno())});
        }
        return  getDataBase().insert(ProyectoData.TABLE_NAME_ALUMNO,null,valores);
    }

    public boolean EliminarAlumno(int id){
        return  getDataBase().delete(ProyectoData.TABLE_NAME_ALUMNO,
                "idAlumno = ?", new String[]{Integer.toString(id)}) > 0;
    }

    public Alumno BuscarAlumno(int id){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_ALUMNO,
                ProyectoData.COLUMNS_ALUMNO,"idAlumno = ?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor.moveToNext()){
            Seccion seccion = new Seccion();
            seccion.setIdSeccion(cursor.getInt(cursor.getColumnIndex(ProyectoData.SECCION_ALUMNO)));
            Alumno alumno = RegistrarAlumno(cursor,seccion);
            cursor.close();
            return alumno;
        }

        return null;
    }

    public List<Alumno> obtenerAlumnosNotas(int idSeccion,int idCurso){
        List<Alumno> alumnos=new ArrayList<Alumno>();
        Alumno alumno;
        Cursor c=getDataBase().rawQuery("select a.idAlumno,a.nombre,a.apellido,ac.nota " +
                        "from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno " +
                        "where a.idSeccion =? and ac.idCurso=?",
                new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)});
        while(c.moveToNext()){
            alumno=new Alumno();
            alumno.setIdAlumno(c.getInt(0));
            alumno.setNombre(c.getString(1));
            alumno.setApellido(c.getString(2));
            alumno.setNota(c.getInt(3));
            alumnos.add(alumno);
        }
        return alumnos;
    }

    public Integer obtenerTotalAlumnosNotas(int idSeccion,int idCurso){
        int total;
        Cursor c=getDataBase().rawQuery("select count(*) from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno " +
                        "where a.idSeccion =? and ac.idCurso=?",
                new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)});
        if(c.moveToNext()){
            total=c.getInt(0);
            return total;
        }
        return 0;
    }

    public Integer SumaNotaAlumnos(int idSeccion,int idCurso){
        int total;
        Cursor c=getDataBase().rawQuery("select SUM(ac.nota) from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno " +
                        "where a.idSeccion =? and ac.idCurso=?",
                new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)});
        if(c.moveToNext()){
            total=c.getInt(0);
            return total;
        }
        return 0;
    }

    public Integer PromedioNotaAlumno(int idSeccion,int idCurso){
        int total;
        Cursor c=getDataBase().rawQuery("select AVG(ac.nota) from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno " +
                        "where a.idSeccion =? and ac.idCurso=?",
                new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)});
        if(c.moveToNext()){
            total=c.getInt(0);
            return total;
        }
        return 0;
    }

    public Integer MayorNotaAlumno(int idSeccion,int idCurso){
        int total;
        Cursor c=getDataBase().rawQuery("select MAX(ac.nota) from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno " +
                        "where a.idSeccion =? and ac.idCurso=?",
                new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)});
        if(c.moveToNext()){
            total=c.getInt(0);
            return total;
        }
        return 0;
    }

    public Integer MinimaNotaAlumno(int idSeccion,int idCurso){
        int total;
        Cursor c=getDataBase().rawQuery("select MIN(ac.nota) from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno " +
                        "where a.idSeccion =? and ac.idCurso=?",
                new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)});
        if(c.moveToNext()){
            total=c.getInt(0);
            return total;
        }
        return 0;
    }

    public Integer NumeroAprobadoAlumno(int idSeccion,int idCurso){
        int total;
        Cursor c=getDataBase().rawQuery("select count(*) from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno " +
                        "where a.idSeccion =? and ac.idCurso=? and ac.nota>=13",
                new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)});
        if(c.moveToNext()){
            total=c.getInt(0);
            return total;
        }
        return 0;
    }

    public Integer NumeroDesaprobadoAlumno(int idSeccion,int idCurso){
        int total;
        Cursor c=getDataBase().rawQuery("select count(*) from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno " +
                        "where a.idSeccion =? and ac.idCurso=? and ac.nota<13",
                new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)});
        if(c.moveToNext()){
            total=c.getInt(0);
            return total;
        }
        return 0;
    }

    public String DistribuccionNotasAlumno(int idSeccion,int idCurso){
        List<Alumno> alumnos=new ArrayList<Alumno>();
        Alumno alumno;
        String nota="";
        Cursor c=getDataBase().rawQuery("select a.idAlumno,a.nombre,a.apellido,ac.nota " +
                        "from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno " +
                        "where a.idSeccion =? and ac.idCurso=?",
                new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)});
        while(c.moveToNext()){
            alumno=new Alumno();
            alumno.setIdAlumno(c.getInt(0));
            alumno.setNombre(c.getString(1));
            alumno.setApellido(c.getString(2));
            alumno.setNota(c.getInt(3));
            alumnos.add(alumno);
        }

        for (int i=0;i<alumnos.size();i++){
            if(alumnos.get(i).getNota()==0)
                nota="NR";
            else{
                nota=alumnos.get(i).getNota().toString();
            }
        }
        return nota;
    }

    public void Cerrar(){
        db.close();
        data=null;
    }

}
