package apps.william.kid.EducaNotas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import adapter.UsuarioAdapter;
import dao.UsuarioDao;
import model.Usuario;
import util.Mensaje;


public class ListadoUsuarioActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener,DialogInterface.OnClickListener {

    private ListView lista;
    private List<Usuario> usuarios;
    private UsuarioAdapter adapter;
    private UsuarioDao usuarioDao;

    private int idposicion;
    private AlertDialog alertDialog, alertConfirmacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_usuario);

        alertDialog = Mensaje.CrearAlertDialog(this);
        alertConfirmacion = Mensaje.CrearDialogConfirmacion(this);

        usuarioDao = new UsuarioDao(this);
        usuarios = usuarioDao.listarUsuario();
        adapter =  new UsuarioAdapter(this, usuarios);

        lista = (ListView) findViewById(R.id.lvUsuarios);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listado_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_registrar_usuarios) {
            startActivity(new Intent(this, CrudUsuarioActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
            int id = usuarios.get(idposicion).getIdUsuario();

            switch (which){
                case 0:
                    Intent intent = new Intent(this, CrudUsuarioActivity.class);
                    intent.putExtra("Usuario_id",id);
                    startActivity(intent);
                    break;
                case 1 :
                    alertConfirmacion.show();
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    usuarios.remove(idposicion);
                    usuarioDao.EliminarUsuario(id);
                    lista.invalidateViews();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    alertConfirmacion.dismiss();
                    break;
            }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idposicion = position;
        alertDialog.show();
    }
}
