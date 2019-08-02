package apps.william.kid.EducaNotas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import dao.CursoDao;
import dao.SeccionDao;
import dao.UsuarioDao;
import model.Curso;
import model.Seccion;
import model.Usuario;
import util.Mensaje;


public class RegitrarNotasActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spDocente,spCurso,spSeccion;
    private Button btnConsultar;
    private UsuarioDao usuarioDao;
    private CursoDao cursoDao;
    private SeccionDao seccionDao;
    private static String TAG = "RegistrarNotasActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regitrar_notas);

        agregarToolbar();

        //spDocente= (Spinner) findViewById(R.id.spnDocente_reg_notas);
        spCurso=(Spinner) findViewById(R.id.spnCurso_reg_notas);
        spSeccion=(Spinner) findViewById(R.id.spnSeccion_reg_notas);
        btnConsultar=(Button)findViewById(R.id.btnConsultar_reg_notas);

        //LlamarNombreCompleto();
        CargaCurso();
        CargaSeccion();

        btnConsultar.setOnClickListener(this);
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reg);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_regitrar_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            /*case R.id.action_lista_usuarios:
                startActivity(new Intent(this,ListadoUsuarioActivity.class));
                break;*/
            case R.id.action_lista_salir:
                Mensaje.MsgConfimacion(this, "Salir del Sistema",
                        "Desea Realmente Salir?", R.drawable.ic_action_about, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        finish();
                    }
                });
                break;
            /*case R.id.action_lista_logout:
                SharedPreferences preferences = getSharedPreferences("LoginActivityPreferencia",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                finish();
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(btnConsultar ==view){
            Curso curso=(Curso)spCurso.getSelectedItem();
            Seccion seccion=(Seccion)spSeccion.getSelectedItem();
            Intent intent=new Intent(this,ConsolidadoNotasActivity.class);
            intent.putExtra("idSeccion",seccion.getIdSeccion());
            intent.putExtra("idCurso",curso.getIdCurso());
            startActivity(intent);
            finish();
        }
    }

    public void LlamarNombreCompleto(){
        usuarioDao = new UsuarioDao(this);
        SharedPreferences preferences = getSharedPreferences("LoginActivityPreferencia",MODE_PRIVATE);
        String login = preferences.getString("Login","");
        List<Usuario> usuarios = usuarioDao.TraerUsuarioLogin(login);
        ArrayAdapter<Usuario> adaptador = new ArrayAdapter<Usuario>(this,android.R.layout.simple_spinner_item,usuarios);
        spDocente.setAdapter(adaptador);

    }

    private void CargaCurso(){
       cursoDao = new CursoDao(this);
       List<Curso> cursos = cursoDao.listarCursosActivos();
       ArrayAdapter<Curso> adapter=
                new ArrayAdapter<Curso>(this,android.R.layout.simple_spinner_item,cursos);
       spCurso.setAdapter(adapter);
    }

    private void CargaSeccion(){
        seccionDao = new SeccionDao(this);
        List<Seccion> secciones = seccionDao.listarSeccionActivos();
        ArrayAdapter<Seccion> adapter=
                new ArrayAdapter<Seccion>(this,android.R.layout.simple_spinner_item,secciones);
        spSeccion.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        seccionDao.Cerrar();
        cursoDao.Cerrar();
    }
}
