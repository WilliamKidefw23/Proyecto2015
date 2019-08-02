package apps.william.kid.EducaNotas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import apps.william.kid.EducaNotas.fragment.ListadoAlumnoFragment;
import apps.william.kid.EducaNotas.fragment.ListadoCursoFragment;
import apps.william.kid.EducaNotas.fragment.ListadoSeccionFragment;
import apps.william.kid.EducaNotas.fragment.PrincipalFragment;
import util.DialogCurso;
import util.Mensaje;


public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    //Navegation View
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private final static int RESULTADO =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        agregarToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_Principal);
        navigationView = (NavigationView) findViewById(R.id.nav_Principal);
        if (navigationView != null) {
            // Añadir carácteristicas
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;  }
                }
        );
    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new PrincipalFragment();
                break;
            case R.id.action_lista_cursos:
                fragmentoGenerico = new ListadoCursoFragment();
                break;
            case R.id.action_lista_seccion:
                fragmentoGenerico = new ListadoSeccionFragment();
                break;
            case R.id.action_lista_alumno:
                fragmentoGenerico = new ListadoAlumnoFragment();
                break;
            case R.id.action_lista_salir:
                Mensaje.MsgConfimacion
                        (this, "Salir del Sistema", "Desea Realmente Salir?",
                                R.drawable.ic_action_about,
                                new DialogInterface.OnClickListener() {
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
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico,"ListadoAlumnoFragment")
                    .commit();
        }
        //Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Log.d(TAG,"onCreateOptionsMenu");
        /*if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.nav_menu_Principal, menu);
            return true;
        }*/
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            /*
            case R.id.action_lista_logout:
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.d(TAG,"onActivityResult");
        if (requestCode==RESULTADO){
            if(resultCode==RESULT_OK){
                ListadoAlumnoFragment fragment = (ListadoAlumnoFragment) getSupportFragmentManager().
                        findFragmentByTag("ListadoAlumnoFragment");
                fragment.cargarAlumno();
            }
        }
    }
}
