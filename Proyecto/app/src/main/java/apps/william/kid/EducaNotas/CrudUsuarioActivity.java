package apps.william.kid.EducaNotas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import dao.UsuarioDao;
import model.Usuario;
import util.Mensaje;


public class CrudUsuarioActivity extends AppCompatActivity {

    private EditText txtNombre,txtApellidoP,txtApellidoM,txtLogin, txtPassword;
    private UsuarioDao usuarioDao;
    private Usuario usuario;
    private int idusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_usuario);

        usuarioDao = new UsuarioDao(this);

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellidoP = (EditText) findViewById(R.id.txtApellidoP);
        txtApellidoM = (EditText) findViewById(R.id.txtApellidoM);
        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtPassword = (EditText) findViewById(R.id.txtPass);

        //Modo de Editar
        idusuario = getIntent().getIntExtra("Usuario_id",0);

        if(idusuario>0){
            Usuario model = usuarioDao.BuscarUsuario(idusuario);
            txtNombre.setText(model.getNombres());
            txtApellidoP.setText(model.getApellidop());
            txtApellidoM.setText(model.getApellidom());
            txtLogin.setText(model.getLogin());
            txtPassword.setText(model.getPassword());
            setTitle("Actualizar Usuario");
        }
    }

    @Override
    protected void onDestroy() {
        usuarioDao.Cerrar();
        super.onDestroy();
    }

    private void Registro(){
        boolean validacion = true;
        String nombres = txtNombre.getText().toString();
        String apellidop = txtApellidoP.getText().toString();
        String apellidom = txtApellidoM.getText().toString();
        String login = txtLogin.getText().toString();
        String password = txtPassword.getText().toString();

        if(nombres==null || nombres.equals("")){
            validacion=false;
            txtNombre.setError(getString(R.string.campo_obligatorio));
        }
        if(apellidop==null || apellidop.equals("")){
            validacion=false;
            txtApellidoP.setError(getString(R.string.campo_obligatorio));
        }
        if(apellidom==null || apellidom.equals("")){
            validacion=false;
            txtApellidoM.setError(getString(R.string.campo_obligatorio));
        }
        if(login==null || login.equals("")){
            validacion=false;
            txtLogin.setError(getString(R.string.campo_obligatorio));
        }
        if(password==null || password.equals("")){
            validacion=false;
            txtPassword.setError(getString(R.string.campo_obligatorio));
        }

        if(validacion){
            usuario = new Usuario();
            usuario.setNombres(nombres);
            usuario.setApellidop(apellidop);
            usuario.setApellidom(apellidom);
            usuario.setLogin(login);
            usuario.setPassword(password);

            //Si fuera a Actualizar
            if(idusuario>0){
                usuario.setIdUsuario(idusuario);
            }
            long resultado = usuarioDao.GuardarUsuario(usuario);

            if(resultado != -1){
                if(idusuario > 0){
                    Mensaje.Msg(this, getString(R.string.msg_actualizar));
                }else {
                    Mensaje.Msg(this, getString(R.string.msg_registra));
                }
                finish();
                startActivity(new Intent(this,MainActivity.class));
            }else{
                Mensaje.Msg(this,getString(R.string.msg_error));
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_crud, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_menu_guardar:
                this.Registro();
                break;
            case R.id.action_menu_salir:
                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
