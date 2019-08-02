package apps.william.kid.EducaNotas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import java.util.Calendar;
import java.util.List;
import apps.william.kid.EducaNotas.fragment.CrudAlumnoFragment;
import dao.AlumnoDao;
import dao.SeccionDao;
import model.Alumno;
import model.Seccion;
import util.Mensaje;

public class CrudAlumnoActivity extends AppCompatActivity  {

    private int idAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_alumno);

        agregarToolbar();
        //Modo de Editar
        idAlumno = getIntent().getIntExtra("Alumno_id",0);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contenedor_principal, CrudAlumnoFragment.createInstance(idAlumno), "CrudAlumno")
                    .commit();
        }

    }

    private void agregarToolbar() {
        Toolbar toolbar =findViewById(R.id.toolbar_man_alumno);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        /*if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crud, menu);
        return true;
    }

}
