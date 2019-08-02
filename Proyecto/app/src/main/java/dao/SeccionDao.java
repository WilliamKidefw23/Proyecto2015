package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import conexion.DataBaseHelper;
import model.Curso;
import model.Seccion;

public class SeccionDao {

    private DataBaseHelper db;
    private SQLiteDatabase data;

    public SeccionDao(Context context){
        db =  new DataBaseHelper(context);
    }

    private SQLiteDatabase getDataBase(){
        if(data==null){
            data= db.getWritableDatabase();
        }
        return  data;
    }

    public Seccion RegistrarSeccion(Cursor cursor){
        Seccion seccion = new Seccion(
                cursor.getInt(cursor.getColumnIndex(ProyectoData.KEY_SECCION)),
                cursor.getString(cursor.getColumnIndex(ProyectoData.NOMBRES_SECCION)),
                cursor.getInt(cursor.getColumnIndex(ProyectoData.ESTADO_SECCION))
        );
        return  seccion;
    }

    public List<Seccion> listarSeccion(){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_SECCION,
                ProyectoData.COLUMNS_SECCION,null,null,null,null,null);

        List<Seccion> seccion  = new ArrayList<Seccion>();
        while (cursor.moveToNext()){
            Seccion sec = RegistrarSeccion(cursor);
            seccion.add(sec);
        }
        cursor.close();
        return seccion;
    }

    public List<Seccion> listarSeccionActivos(){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_SECCION,
                ProyectoData.COLUMNS_SECCION,"estado=1",null,null,null,null);

        List<Seccion> seccion  = new ArrayList<Seccion>();
        while (cursor.moveToNext()){
            Seccion sec = RegistrarSeccion(cursor);
            seccion.add(sec);
        }
        cursor.close();
        return seccion;
    }

    public long GuardarSeccion(Seccion seccion){
        ContentValues valores =  new ContentValues();
        valores.put(ProyectoData.NOMBRES_SECCION,seccion.getSeccion());
        valores.put(ProyectoData.ESTADO_SECCION,seccion.getEstado());
        if(seccion.getIdSeccion()!= null){
            return  getDataBase().update(ProyectoData.TABLE_NAME_SECCION, valores,
                    "idSeccion = ?", new String[]{seccion.getIdSeccion().toString()});
        }
        return  getDataBase().insert(ProyectoData.TABLE_NAME_SECCION,null,valores);
    }

    public boolean EliminarSeccion(int id){
        return  getDataBase().delete(ProyectoData.TABLE_NAME_SECCION,
                "idSeccion = ?", new String[]{Integer.toString(id)}) > 0;
    }

    public Seccion BuscarSeccion(int id){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_SECCION,
                ProyectoData.COLUMNS_SECCION,"idSeccion = ?",new String[]{Integer.toString(id)},null,null,null);
        if(cursor.moveToNext()){
            Seccion seccion = RegistrarSeccion(cursor);
            cursor.close();
            return seccion;
        }

        return null;
    }

    public Boolean CheckSeccion(Seccion seccion){
        ContentValues valores =  new ContentValues();
        valores.put(ProyectoData.ESTADO_SECCION,seccion.getEstado());
        return  getDataBase().update(ProyectoData.TABLE_NAME_SECCION, valores,
                    "idSeccion = ?", new String[]{seccion.getIdSeccion().toString()})>0;
    }

    public void Cerrar(){
        db.close();
        data=null;
    }

}
