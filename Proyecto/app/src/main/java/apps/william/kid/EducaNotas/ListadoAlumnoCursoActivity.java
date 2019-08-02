package apps.william.kid.EducaNotas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import adapter.CursoAlumnoAdapter;
import apps.william.kid.EducaNotas.fragment.CrudAlumnoFragment;
import apps.william.kid.EducaNotas.fragment.ListadoAlumnoCursoFragment;
import dao.CursoDao;
import model.Curso;
import util.DialogCurso;
import util.Mensaje;

public class ListadoAlumnoCursoActivity extends AppCompatActivity{

    private int idAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alumno_curso);

        agregarToolbar();

        idAlumno=getIntent().getIntExtra("Alumno_id",0);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contenedor_principal, ListadoAlumnoCursoFragment.createInstance(idAlumno), "AlumnoCurso")
                    .commit();
        }
    }

    private void agregarToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_alumno_curso);
        setSupportActionBar(toolbar);
        /*ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listado_cursos, menu);
        return true;
    }

}
