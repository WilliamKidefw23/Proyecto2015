package dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import conexion.DataBaseHelper;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private DataBaseHelper db;
    private SQLiteDatabase data;

    public UsuarioDao(Context context){
        db =  new DataBaseHelper(context);
    }

    private SQLiteDatabase getDataBase(){
        if(data==null){
            data= db.getWritableDatabase();
        }
        return  data;
    }

    public Usuario RegistrarUsuario(Cursor cursor){
       Usuario usu = new Usuario(
               cursor.getInt(cursor.getColumnIndex(ProyectoData.KEY_USUARIO)),
               cursor.getString(cursor.getColumnIndex(ProyectoData.NOMBRES_USUARIO)),
               cursor.getString(cursor.getColumnIndex(ProyectoData.APELLIDOP_USUARIO)),
               cursor.getString(cursor.getColumnIndex(ProyectoData.APELLIDOM_USUARIO)),
               cursor.getString(cursor.getColumnIndex(ProyectoData.LOGIN_USUARIO)),
               cursor.getString(cursor.getColumnIndex(ProyectoData.PASSWORD_USUARIO)),
               cursor.getString(cursor.getColumnIndex(ProyectoData.CREADO_USUARIO))
               );
        return  usu;
    }

    public List<Usuario> listarUsuario(){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_USUARIO,
                ProyectoData.COLUMNS_USUARIO,null,null,null,null,null);

        List<Usuario> usuarios  = new ArrayList<Usuario>();
        while (cursor.moveToNext()){
            Usuario usu = RegistrarUsuario(cursor);
            usuarios.add(usu);
        }
        cursor.close();
        return usuarios;
    }

    public long GuardarUsuario(Usuario usuario){
        ContentValues valores =  new ContentValues();
        valores.put(ProyectoData.NOMBRES_USUARIO,usuario.getNombres());
        valores.put(ProyectoData.APELLIDOP_USUARIO,usuario.getApellidop());
        valores.put(ProyectoData.APELLIDOM_USUARIO,usuario.getApellidom());
        valores.put(ProyectoData.LOGIN_USUARIO,usuario.getLogin());
        valores.put(ProyectoData.PASSWORD_USUARIO, usuario.getPassword());
        valores.put(ProyectoData.CREADO_USUARIO, usuario.getCreado());
        if(usuario.getIdUsuario()!= null){
            return  getDataBase().update(ProyectoData.TABLE_NAME_USUARIO, valores,
                    "idUsuario = ?", new String[]{usuario.getIdUsuario().toString()});
        }

        return  getDataBase().insert(ProyectoData.TABLE_NAME_USUARIO,null,valores);
    }

    public boolean EliminarUsuario(int id){
        return  getDataBase().delete(ProyectoData.TABLE_NAME_USUARIO,
                "idUsuario = ?", new String[]{Integer.toString(id)}) > 0;
    }

    public Usuario BuscarUsuario(int id){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_USUARIO,
                ProyectoData.COLUMNS_USUARIO,"idUsuario = ?",new String[]{Integer.toString(id)},null,null,null);
        if(cursor.moveToNext()){
            Usuario usu = RegistrarUsuario(cursor);
            cursor.close();
            return usu;
        }

        return null;
    }

    public boolean Login(String usuario,String password){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_USUARIO,
                null, "login = ? AND password = ?",new String[]{usuario,password},null,null,null);
        if(cursor.moveToFirst()){
            return  true;
        }
        return false;
    }

    public void Cerrar(){
        db.close();
        data=null;
    }

    public String TraeNombreUsuario(String login) {
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_USUARIO, null,
                "login = ?", new String[]{login}, null, null, null);
        if(cursor.moveToNext()) {
            Usuario usu = RegistrarUsuario(cursor);
            cursor.close();
            return usu.getNombres()+ " " + usu.getApellidop() ;
        }
        return null;
    }

    public int TraerCodigoUsuario(String login) {
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_USUARIO, null,
                "login = ?", new String[]{login}, null, null, null);
        if(cursor.moveToNext()) {
            Usuario usu = RegistrarUsuario(cursor);
            cursor.close();
            return usu.getIdUsuario() ;
        }
        return 0;
    }

    public Usuario BuscarUsuarioLogin(String login){
        Cursor cursor = getDataBase().query(ProyectoData.TABLE_NAME_USUARIO,
                ProyectoData.COLUMNS_USUARIO,"login = ?",new String[]{login},null,null,null);
        if(cursor.moveToNext()){
            Usuario usu = RegistrarUsuario(cursor);
            cursor.close();
            return usu;
        }
        return null;
    }

    public List<Usuario> TraerUsuarioLogin(String login){
        ArrayList<Usuario> usuarios=new ArrayList<Usuario>();
        Usuario usu;
        Cursor c=getDataBase().rawQuery("select * from Usuario where login = ?", new String[]{login});
        while (c.moveToNext()) {
            usu = new Usuario();
            usu.setIdUsuario(c.getInt(0));
            usu.setNombres(c.getString(2) +" "+ c.getString(3) + ","+ c.getString(1));
            usuarios.add(usu);
        }
        return usuarios;
    }
}


