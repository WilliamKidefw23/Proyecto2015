package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import apps.william.kid.EducaNotas.fragment.ListadoCursoFragment;
import conexion.DataBaseHelper;
import model.AlumnoCurso;
import model.Curso;

public class CursoDao {

    private DataBaseHelper db;
    private SQLiteDatabase data;

    public CursoDao(Context context){
        db =  new DataBaseHelper(context);
    }

    private SQLiteDatabase getDataBase(){
        if(data==null){
            data= db.getWritableDatabase();
        }
        return  data;
    }

    public Curso RegistrarCurso(Cursor cursor){
        Curso curso = new Curso(
                cursor.getInt(cursor.getColumnIndex(ProyectoData.KEY_CURSO)),
                cursor.getString(cursor.getColumnIndex(ProyectoData.NOMBRES_CURSO)),
                cursor.getInt(cursor.getColumnIndex(ProyectoData.ESTADO_CURSO))
        );
        return  curso;
    }

    public List<Curso> listarCurso(){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_CURSO,
                ProyectoData.COLUMNS_CURSO,null,null,null,null,null);

        List<Curso> cursos  = new ArrayList<Curso>();
        while (cursor.moveToNext()){
            Curso curso = RegistrarCurso(cursor);
            cursos.add(curso);
        }
        cursor.close();
        return cursos;
    }

    public List<Curso> listarCursosActivos(){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_CURSO,
                ProyectoData.COLUMNS_CURSO,"estado=1",null,null,null,null);

        List<Curso> cursos  = new ArrayList<Curso>();
        while (cursor.moveToNext()){
            Curso curso = RegistrarCurso(cursor);
            cursos.add(curso);
        }
        cursor.close();
        return cursos;
    }

    public long GuardarCurso(Curso curso){
        ContentValues valores =  new ContentValues();
        valores.put(ProyectoData.NOMBRES_CURSO,curso.getNomCurso());
        valores.put(ProyectoData.ESTADO_CURSO,curso.getEstado());
        if(curso.getIdCurso()!= null){
            return  getDataBase().update(ProyectoData.TABLE_NAME_CURSO, valores,
                    "idCurso = ?", new String[]{curso.getIdCurso().toString()});
        }
        return  getDataBase().insert(ProyectoData.TABLE_NAME_CURSO,null,valores);
    }

    public boolean EliminarCurso(int id){
        return  getDataBase().delete(ProyectoData.TABLE_NAME_CURSO,
                "idCurso = ?", new String[]{Integer.toString(id)}) > 0;
    }

    public Curso BuscarCurso(int id){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_CURSO,
                ProyectoData.COLUMNS_CURSO,"idCurso = ?",new String[]{Integer.toString(id)},null,null,null);
        if(cursor.moveToNext()){
            Curso curso = RegistrarCurso(cursor);
            cursor.close();
            return curso;
        }

        return null;
    }

    public Boolean CheckCurso(Curso curso){
        //Log.d("CursoDAo",curso.getIdCurso().toString());
        //Log.d("CursoDAo",curso.getNomCurso().toString());
        //Log.d("CursoDAo",String.valueOf(curso.getEstado()));

        ContentValues valores =  new ContentValues();
        valores.put(ProyectoData.ESTADO_CURSO,curso.getEstado());
        return  getDataBase().update(ProyectoData.TABLE_NAME_CURSO, valores,
                    "idCurso = ?", new String[]{curso.getIdCurso().toString()})>0;
    }

    public List<Curso> listarCurso(int idAlumno){
        Cursor cursor = getDataBase().rawQuery("select * from Curso c left join Alumno_Curso ac on ac.idCurso=c.idCurso" +
                " where ac.idAlumno=?",new String[]{String.valueOf(idAlumno)});

        List<Curso> cursos  = new ArrayList<Curso>();
        while (cursor.moveToNext()){
            Curso curso = RegistrarCurso(cursor);
            cursos.add(curso);
        }
        cursor.close();
        return cursos;
    }

    public Boolean GuardarAlumnoCurso(AlumnoCurso alumno){
        ContentValues valores =  new ContentValues();
        valores.put(ProyectoData.KEY_ALUMNO_CURSO,alumno.getIdAlumno());
        valores.put(ProyectoData.KEY_CURSO_ALUMNO,alumno.getIdCurso());

        if(BuscarAlumnoCurso(alumno.getIdAlumno(),alumno.getIdCurso())!=null){
            //Log.d("Encontrado","Encontrado");
            return getDataBase().delete(ProyectoData.TABLE_NAME_ALUMNO_CURSO,
                    "idAlumno = ? and idCurso=?",
                    new String[]{Integer.toString(alumno.getIdAlumno()),Integer.toString(alumno.getIdCurso())})>0;
        }
        //Log.d("NO Encontrado","NO Encontrado");
        return  getDataBase().insert(ProyectoData.TABLE_NAME_ALUMNO_CURSO,null,valores)>0;
    }

    public AlumnoCurso BuscarAlumnoCurso(int idAlumno,int idCurso){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_ALUMNO_CURSO,
                ProyectoData.COLUMNS_ALUMNO_CURSO,"idAlumno = ? and idCurso=?",
                new String[]{Integer.toString(idAlumno),Integer.toString(idCurso)},
                null,null,null);
        if(cursor.moveToNext()){
            AlumnoCurso alumnoCurso =
                    new AlumnoCurso(cursor.getColumnIndex(ProyectoData.KEY_ALUMNO_CURSO),
                            cursor.getColumnIndex(ProyectoData.KEY_CURSO_ALUMNO));
            cursor.close();
            return alumnoCurso;
        }
        return null;
    }

    public void Cerrar(){
        db.close();
        data=null;
    }

}
