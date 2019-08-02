package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import conexion.DataBaseHelper;
import model.Ciclo;
import model.Curso;

public class CicloDao {

    private DataBaseHelper db;
    private SQLiteDatabase data;

    public CicloDao(Context context){
        db =  new DataBaseHelper(context);
    }

    private SQLiteDatabase getDataBase(){
        if(data==null){
            data= db.getWritableDatabase();
        }
        return  data;
    }

    public Ciclo RegistrarCiclo(Cursor cursor){
        Ciclo ciclo = new Ciclo(
                cursor.getInt(cursor.getColumnIndex(ProyectoData.KEY_CICLO)),
                cursor.getString(cursor.getColumnIndex(ProyectoData.NOMBRES_CICLO))
        );
        return  ciclo;
    }

    public List<Ciclo> listarCiclos(){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_CICLO,
                ProyectoData.COLUMNS_CICLO,null,null,null,null,null);
        List<Ciclo> ciclos  = new ArrayList<Ciclo>();
        while (cursor.moveToNext()){
            Ciclo ciclo = RegistrarCiclo(cursor);
            ciclos.add(ciclo);
        }
        cursor.close();
        return ciclos;
    }

    public long GuardarCiclo(Ciclo ciclo){
        ContentValues valores =  new ContentValues();
        valores.put(ProyectoData.NOMBRES_CICLO,ciclo.getCiclo());
        if(ciclo.getIdCiclo()!= 0){
            return  getDataBase().update(ProyectoData.TABLE_NAME_CICLO, valores,
                    "idCiclo = ?", new String[]{String.valueOf(ciclo.getIdCiclo())});
        }
        return  getDataBase().insert(ProyectoData.TABLE_NAME_CICLO,null,valores);
    }

    public boolean EliminarCiclo(int id){
        return  getDataBase().delete(ProyectoData.TABLE_NAME_CICLO,
                "idCiclo = ?", new String[]{Integer.toString(id)}) > 0;
    }

    public Ciclo BuscarCiclo(int id){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_CICLO,
                ProyectoData.COLUMNS_CICLO,"idCiclo = ?",new String[]{Integer.toString(id)},null,null,null);
        if(cursor.moveToNext()){
            Ciclo ciclo = RegistrarCiclo(cursor);
            cursor.close();
            return ciclo;
        }

        return null;
    }

    public List<Ciclo> ListarCicloAlumno(int codigoUsuario){
        ArrayList<Ciclo> ciclos=new ArrayList<Ciclo>();
        Ciclo ciclo;
        Cursor q=getDataBase().rawQuery("select c.idCiclo,c.ciclo from Ciclo c inner join Usuario_Ciclo u on c.idCiclo=u.idCiclo where u.idUsuario=?",
                new String[]{String.valueOf(codigoUsuario)});
        while(q.moveToNext()){
            ciclo=new Ciclo();
            ciclo.setIdCiclo(q.getInt(0));
            ciclo.setCiclo(q.getString(1));
            ciclos.add(ciclo);
        }
        return ciclos;
    }

    public void Cerrar(){
        db.close();
        data=null;
    }

}
