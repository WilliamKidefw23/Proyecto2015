package apps.william.kid.EducaNotas;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import java.util.List;

import adapter.NotasAdapter;
import conexion.DataBaseHelper;
import dao.AlumnoDao;
import dao.ProyectoData;
import model.Alumno;


public class ConsolidadoNotasActivity extends AppCompatActivity implements View.OnClickListener  {

    private EditText txtTotal,txtNota;
    private Button btnConfirmar;
    private int idSeccion,idCurso;
    private List<Alumno> listaAlumnos;
    private Alumno alumno;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private NotasAdapter notasAdapter;
    private AlumnoDao alumnoDao;
    private static String TAG="ConsolidadoNotasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consolidado_notas);

        btnConfirmar=(Button)findViewById(R.id.btnConfirmar_con_notas);
        txtTotal = (EditText) findViewById(R.id.txtAlumnoTotal_con_notas);
        recyclerView = (RecyclerView) findViewById(R.id.rv_alumnos_notas);
        alumnoDao = new AlumnoDao(this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        notasAdapter = new NotasAdapter(null,this,recyclerView);
        recyclerView.setAdapter(notasAdapter);
        agregarToolbar();
        obtenerDatos();
        CargaAlumnos();
        btnConfirmar.setOnClickListener(this);
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_con_notas);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void Registra(){
        //Log.d(TAG,"Registra()");
        new GuardarTask().execute();
       /* for (int i = 0; i< listaAlumnos.size(); i++){
            DataBaseHelper cn=new DataBaseHelper(this);
            SQLiteDatabase db=cn.getReadableDatabase();
            ContentValues valores =  new ContentValues();
            valores.put("nota", listaAlumnos.get(i).getNota().toString());

            Log.d("Notas : ", listaAlumnos.get(i).getCompleto() + " - "+ listaAlumnos.get(i).getNota().toString() + " - "+idCurso);

            db.update(ProyectoData.TABLE_NAME_ALUMNO_CURSO,
                    valores, "idAlumno = ? and idCurso=?",
                    new String[]{String.valueOf(listaAlumnos.get(i).getIdAlumno()),String.valueOf(idCurso)});
             /*  db.rawQuery("update Alumno set nota=? where idAlumno=?",
                     new String[]{String.valueOf(listaAlumnos.get(i).getIdAlumno()),listaAlumnos.get(i).getNota().toString()});
        }
        Toast.makeText(this, "Se Guardo", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_consolodidado_notas, menu);
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
    public void onClick(View view) {
        //Log.d(TAG,"onClick()");
        int id = view.getId();
        switch (id){
            case R.id.btnConfirmar_con_notas :
                Registra();
                Intent intent=new Intent(this,ResultadoAlumnosActivity.class);
                intent.putExtra("idSeccion",idSeccion);
                intent.putExtra("idCurso",idCurso);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void CargaAlumnos(){
        new AlumnosTask().execute();
        new AlumnosIntTask().execute();
    }

    public void obtenerDatos(){
        idSeccion=getIntent().getExtras().getInt("idSeccion",0);
        idCurso=getIntent().getExtras().getInt("idCurso",0);
    }

    public class AlumnosTask extends AsyncTask<Void,Void,List<Alumno>>{

        @Override
        protected List<Alumno> doInBackground(Void... voids) {
            return alumnoDao.obtenerAlumnosNotas(idSeccion,idCurso);
        }

        @Override
        protected void onPostExecute(List<Alumno> alumnos) {
            if (alumnos != null && alumnos.size() > 0) {
                listaAlumnos = alumnos;
                notasAdapter.swapLista(listaAlumnos);
            } else {
                //Log.d(TAG,"Error al obtener obtenerAlumnosNotas");
            }
        }
    }

    public class AlumnosIntTask extends AsyncTask<Void,Void,Integer>{

        @Override
        protected Integer doInBackground(Void... voids) {
            return alumnoDao.obtenerTotalAlumnosNotas(idSeccion,idCurso);
        }

        @Override
        protected void onPostExecute(Integer resultado) {
            if(resultado!=0){
                txtTotal.setText(String.valueOf(resultado));
            }
            else
                Toast.makeText(ConsolidadoNotasActivity.this, "Error al obtener obtenerAlumnosTotal", Toast.LENGTH_SHORT).show();
                //Log.d(TAG,"Error al obtener obtenerAlumnosTotal");
        }
    }

    public class GuardarTask extends AsyncTask<Void,Void,Long>{

        @Override
        protected Long doInBackground(Void... voids) {
            long t0 = System.currentTimeMillis();
            for (int i = 0; i< listaAlumnos.size(); i++){
                DataBaseHelper cn=new DataBaseHelper(ConsolidadoNotasActivity.this);
                SQLiteDatabase db=cn.getReadableDatabase();
                ContentValues valores =  new ContentValues();
                valores.put("nota", listaAlumnos.get(i).getNota().toString());
                //Log.d(TAG, "Notas : "+listaAlumnos.get(i).getCompleto() + " - "+ listaAlumnos.get(i).getNota().toString() + " - "+idCurso);
                db.update(ProyectoData.TABLE_NAME_ALUMNO_CURSO,
                        valores, "idAlumno = ? and idCurso = ?",
                        new String[]{String.valueOf(listaAlumnos.get(i).getIdAlumno()),String.valueOf(idCurso)});
                cn.close();
            }
            return t0;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            Toast.makeText(ConsolidadoNotasActivity.this, "Se Guardo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alumnoDao.Cerrar();
    }
}
