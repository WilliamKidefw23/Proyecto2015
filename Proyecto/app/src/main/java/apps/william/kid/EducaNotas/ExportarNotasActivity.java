package apps.william.kid.EducaNotas;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import conexion.DataBaseHelper;
import dao.AlumnoDao;
import dao.CursoDao;
import dao.SeccionDao;
import dao.UsuarioDao;
import model.Curso;
import model.Seccion;


public class ExportarNotasActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Spinner spCursoExporta,spSeccionExporta;
    private EditText txtDocenteExporta;
    private UsuarioDao usuarioDao;
    private AlumnoDao alumnoDao;
    private Button btnExportar;
    private ArrayList<String> listaAlumnos;
    private CursoDao cursoDao;
    private SeccionDao seccionDao;
    private CheckBox chkCurso,chkSeccion;
    private int idSeccion=0,idCurso=0;
    private static String TAG="ExportarNotasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportar_notas);

        agregarToolbar();
        //txtDocenteExporta =(EditText)findViewById(R.id.txtDocente_exp_notas);
        spCursoExporta=(Spinner) findViewById(R.id.spnCurso_exp_notas);
        spSeccionExporta=(Spinner) findViewById(R.id.spnSeccion_exp_notas);
        btnExportar = (Button) findViewById(R.id.btnExporta_exp_notas);
        chkCurso = (CheckBox) findViewById(R.id.ckbCurso_exp);
        chkSeccion = (CheckBox) findViewById(R.id.ckbSeccion_exp);
        chkCurso.setOnCheckedChangeListener(this);
        chkSeccion.setOnCheckedChangeListener(this);

        //LlamarUsuario();
        LlenaCurso();
        LlenaSeccion();
        /*Curso obj= (Curso)spCursoExporta.getSelectedItem();
        obtenerAlumnos(String.valueOf(obj.getIdCurso()));*/
        btnExportar.setOnClickListener(this);
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_exp_notas);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        //Log.d("ExportarNotasActivity","onClick()");

        if(btnExportar==view){
            if(!chkCurso.isChecked()){
                Curso curso=(Curso)spCursoExporta.getSelectedItem();
                idCurso = curso.getIdCurso();
            }else idCurso = 0 ;
            if(!chkSeccion.isChecked()){
                Seccion seccion=(Seccion)spSeccionExporta.getSelectedItem();
                idSeccion = seccion.getIdSeccion();
            }else idSeccion=0;

            //listaAlumnos = obtenerAlumnos(idSeccion,idCurso);

            new ExportarTask().execute();

            /*String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/";
            File file = new File(directory_path);

            if (!file.exists()) {
                file.mkdirs();
            }

            SQLiteToExcel sqliteToExcel = new SQLiteToExcel(this, DataBaseHelper.DATABASE_NAME,directory_path);

            /*sqliteToExcel.exportAllTables("todos.xls", new SQLiteToExcel.ExportListener() {
                @Override
                public void onStart() {
                    Log.d("ExportarNotasActivity","onStart()");
                }

                @Override
                public void onCompleted(String filePath) {
                    Log.d("ExportarNotasActivity","onCompleted()");
                    Toast.makeText(ExportarNotasActivity.this,"Exportando",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Exception e) {
                    Log.d("ExportarNotasActivity","onError()" +e);
                }
            });*/

            /*sqliteToExcel.exportSingleTable(ProyectoData.TABLE_NAME_ALUMNO_CURSO, "Alumnos_Curso.xls",
                    new SQLiteToExcel.ExportListener() {
                @Override
                public void onStart() {

                }
                @Override
                public void onCompleted(String filePath) {
                    Log.d("ExportarNotasActivity","onCompleted()");
                    Toast.makeText(ExportarNotasActivity.this,"Exportando",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onError(Exception e) {

                }
            });

            sqliteToExcel.exportSpecificTables(listaAlumnos,"tablas.xls", new SQLiteToExcel.ExportListener() {
                @Override
                public void onStart() {
                    Log.d(TAG,"onStart()");
                }

                @Override
                public void onCompleted(String filePath) {
                    Log.d(TAG,"onCompleted()");
                    Toast.makeText(ExportarNotasActivity.this,"Exportado",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Exception e) {
                    Log.d(TAG,"onError() " + e);
                }
            });*/
        }
    }

    public void LlamarUsuario(){
        usuarioDao = new UsuarioDao(this);
        SharedPreferences preferences = getSharedPreferences("LoginActivityPreferencia",MODE_PRIVATE);
        String login = preferences.getString("Login", "");
        txtDocenteExporta.setText(usuarioDao.TraeNombreUsuario(login));
    }

    public void LlenaCurso(){
        cursoDao = new CursoDao(this);
        List<Curso> cursos = cursoDao.listarCursosActivos();
        ArrayAdapter<Curso> adapter=
                new ArrayAdapter<Curso>(this,android.R.layout.simple_spinner_item,cursos);
        spCursoExporta.setAdapter(adapter);
    }

    private void LlenaSeccion(){
        seccionDao = new SeccionDao(this);
        List<Seccion> secciones = seccionDao.listarSeccionActivos();
        ArrayAdapter<Seccion> adapter=
                new ArrayAdapter<Seccion>(this,android.R.layout.simple_spinner_item,secciones);
        spSeccionExporta.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_exportar_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (chkCurso.isChecked()) spCursoExporta.setEnabled(false);
        else spCursoExporta.setEnabled(true);

        if (chkSeccion.isChecked()) spSeccionExporta.setEnabled(false);
        else spSeccionExporta.setEnabled(true);
    }

    private class ExportarTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            File myFile;
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            String TimeStampDB = sdf.format(cal.getTime());

            try {
                String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/";
                myFile = new File(directory_path+"/Export_"+TimeStampDB+".csv");
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append("CodigoAlumno;NombreCompleto;Curso;Nota");
                myOutWriter.append("\n");

                String[] valores = new String[0];
                String query = "select a.idAlumno,a.nombre,a.apellido,c.nomcurso,ac.nota " +
                        "from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno join Curso c on c.idCurso=ac.idCurso ";

                if (idCurso!=0 && idSeccion!=0) {
                    query += "where a.idSeccion=? and ac.idCurso =? ";
                    valores = new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)};
                }else{
                    if (idCurso!=0) {
                        query += "where ac.idCurso=? ";
                        valores = new String[]{String.valueOf(idCurso)};
                    }else if (idSeccion!=0) {
                        query += "where a.idSeccion =? ";
                        valores = new String[]{String.valueOf(idSeccion)};
                    }
                }
                //query += "group by a.idAlumno,a.nombre,a.apellido,a.idSeccion";
                query +="order by a.idAlumno,c.nomcurso";

                DataBaseHelper cn=new DataBaseHelper(ExportarNotasActivity.this);
                SQLiteDatabase db=cn.getReadableDatabase();
                listaAlumnos =new ArrayList<String>();

                Cursor c=db.rawQuery(query,valores);
            /*
            Cursor c=db.rawQuery("select a.idAlumno,a.nombre,a.apellido,ac.nota " +
                            "from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno " +
                            "where a.idSeccion =? and ac.idCurso=?",
                    new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)});*/

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            String idAlumno = c.getString(0);
                            String Nombres = c.getString(2) + " " + c.getString(1);
                            String Curso = c.getString(3);
                            String Nota = String.valueOf(c.getInt(4));

                            myOutWriter.append(idAlumno+";"+Nombres+";"+Curso+";"+Nota);
                            myOutWriter.append("\n");
                        }
                        while (c.moveToNext());
                    }
                    c.close();
                    myOutWriter.close();
                    fOut.close();
                }
                cn.close();

            }catch (SQLiteException se) {
                Toast.makeText(ExportarNotasActivity.this,se.getMessage(),Toast.LENGTH_SHORT).show();
                //Log.d(TAG,se.getMessage());
            } catch (FileNotFoundException e) {
                Toast.makeText(ExportarNotasActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                //Log.d(TAG,e.getMessage());
            } catch (IOException e) {
                Toast.makeText(ExportarNotasActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
               //Log.d(TAG,e.getMessage());
            } finally {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(ExportarNotasActivity.this,"Exportado",Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<String> obtenerAlumnos(int idSeccion,int idCurso){
        //Log.d(TAG,"obtenerAlumnos()");
        //Log.d(TAG, "Seccion " +String.valueOf(idSeccion));
        //Log.d(TAG, "Curso "+ String.valueOf(idCurso));
        DataBaseHelper cn=new DataBaseHelper(this);
        SQLiteDatabase db=cn.getReadableDatabase();
        listaAlumnos =new ArrayList<String>();
        String[] valores = new String[0];
        String query = "select a.idAlumno,a.nombre,a.apellido,ac.nota " +
                "from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno ";

        if (idCurso!=0 && idSeccion!=0) {
            query += "where a.idSeccion=? and ac.idCurso =?";
            valores = new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)};
        }else{
            if (idCurso!=0) {
                query += "where ac.idCurso=?";
                valores = new String[]{String.valueOf(idCurso)};
            }else if (idSeccion!=0) {
                query += "where a.idSeccion =?";
                valores = new String[]{String.valueOf(idSeccion)};
            }
        }

        //Log.d(TAG,query);

        Cursor c=db.rawQuery(query,valores);
        while(c.moveToNext()){
            String tabla = "Alumno";
            String idAlumno = c.getString(0);
            String Nombres = c.getString(2) + " " + c.getString(1);
            String Nota = String.valueOf(c.getInt(3));

            listaAlumnos.add(tabla);
        }
        return listaAlumnos;
    }

    private ArrayList<String> obtenerAlumnosCSV(int idSeccion,int idCurso){
        //Log.d(TAG,"obtenerAlumnos()");
        //Log.d(TAG,String.valueOf(idSeccion));
        //Log.d(TAG,String.valueOf(idCurso));
        DataBaseHelper cn=new DataBaseHelper(this);
        SQLiteDatabase db=cn.getReadableDatabase();
        listaAlumnos =new ArrayList<String>();
        Cursor c=db.rawQuery("select a.idAlumno,a.nombre,a.apellido,ac.nota " +
                        "from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno " +
                        "where a.idSeccion =? and ac.idCurso=?",
                new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)});
        while(c.moveToNext()){
            String tabla = "Alumno";
            String idAlumno = c.getString(0);
            String Nombres = c.getString(2) + " " + c.getString(1);
            String Nota = String.valueOf(c.getInt(3));

            listaAlumnos.add(tabla);
        }
        return listaAlumnos;
    }

    private void exportTheDB(int idSeccion,int idCurso) throws IOException
    {
        File myFile;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String TimeStampDB = sdf.format(cal.getTime());

        try {
            String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/";
            myFile = new File(directory_path+"/Export_"+TimeStampDB+".csv");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append("CodigoAlumno;NombreCompleto;Curso;Nota");
            myOutWriter.append("\n");

            String[] valores = new String[0];
            String query = "select a.idAlumno,a.nombre,a.apellido,c.nomcurso,ac.nota " +
                    "from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno join Curso c on c.idCurso=ac.idCurso ";

            if (idCurso!=0 && idSeccion!=0) {
                query += "where a.idSeccion=? and ac.idCurso =? ";
                valores = new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)};
            }else{
                if (idCurso!=0) {
                    query += "where ac.idCurso=? ";
                    valores = new String[]{String.valueOf(idCurso)};
                }else if (idSeccion!=0) {
                    query += "where a.idSeccion =? ";
                    valores = new String[]{String.valueOf(idSeccion)};
                }
            }
            //query += "group by a.idAlumno,a.nombre,a.apellido,a.idSeccion";
            query +="order by a.idAlumno,c.nomcurso";

            DataBaseHelper cn=new DataBaseHelper(this);
            SQLiteDatabase db=cn.getReadableDatabase();
            listaAlumnos =new ArrayList<String>();

            Cursor c=db.rawQuery(query,valores);
            /*
            Cursor c=db.rawQuery("select a.idAlumno,a.nombre,a.apellido,ac.nota " +
                            "from Alumno a join Alumno_Curso ac on a.idAlumno=ac.idAlumno " +
                            "where a.idSeccion =? and ac.idCurso=?",
                    new String[]{String.valueOf(idSeccion),String.valueOf(idCurso)});*/

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String idAlumno = c.getString(0);
                        String Nombres = c.getString(2) + " " + c.getString(1);
                        String Curso = c.getString(3);
                        String Nota = String.valueOf(c.getInt(4));

                        myOutWriter.append(idAlumno+";"+Nombres+";"+Curso+";"+Nota);
                        myOutWriter.append("\n");
                    }
                    while (c.moveToNext());
                }
                c.close();
                myOutWriter.close();
                fOut.close();
            }
        }catch (SQLiteException se) {
            Toast.makeText(this,se.getMessage(),Toast.LENGTH_SHORT).show();
            //Log.e(TAG,se.getMessage());
        }finally {
            Toast.makeText(this,"Exportado",Toast.LENGTH_SHORT).show();
        }

    }
}
