package apps.william.kid.EducaNotas;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import conexion.DataBaseHelper;
import dao.UsuarioDao;
import model.Ciclo;
import model.Modalidad;


public class ImportarNotasActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    private Spinner spModalidad,spCiclo;
    private EditText txtDocente;
    private UsuarioDao dao;
    private void CargaModalidad(){
        DataBaseHelper cn=new DataBaseHelper(this);
        ArrayList<Modalidad> modalidad=new ArrayList<Modalidad>();
        Modalidad mod;
        SQLiteDatabase db = cn.getReadableDatabase();
        Cursor c=db.rawQuery("select * from Modalidad", null);
        while (c.moveToNext()) {
                mod = new Modalidad();
                mod.setIdModalidad(c.getInt(0));
                mod.setModalidad(c.getString(1));
                modalidad.add(mod);
            }
        ArrayAdapter<Modalidad> adaptador = new ArrayAdapter<Modalidad>(this,android.R.layout.simple_spinner_item,modalidad);
        spModalidad.setAdapter(adaptador);
    }
    private void CargaCiclo(){
        DataBaseHelper cn=new DataBaseHelper(this);
        ArrayList<Ciclo> ciclos=new ArrayList<Ciclo>();
        Ciclo ci;
        SQLiteDatabase db = cn.getReadableDatabase();
        Cursor q=db.rawQuery("select * from Ciclo",null);
        while(q.moveToNext()){
            ci=new Ciclo();
            ci.setIdCiclo(q.getInt(0));
            ci.setCiclo(q.getString(1));
            ciclos.add(ci);
        }
        ArrayAdapter<Ciclo> adapter=new ArrayAdapter<Ciclo>(this,android.R.layout.simple_list_item_1,ciclos);
        spCiclo.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importar_notas);

        spModalidad=(Spinner)findViewById(R.id.spnModalidad);
        spCiclo=(Spinner)findViewById(R.id.spnCiclo);
        //txtDocente=(EditText)findViewById(R.id.txtDocente);
        CargaModalidad();
        CargaCiclo();
        LlamarUsuario();

    }

    public void LlamarUsuario(){
        dao = new UsuarioDao(this);
        SharedPreferences preferences = getSharedPreferences("LoginActivityPreferencia",MODE_PRIVATE);
        String nombre = preferences.getString("Nombre", "");
        txtDocente.setText(dao.TraeNombreUsuario(nombre));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_importar_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {      return true;     }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {

    }
}
