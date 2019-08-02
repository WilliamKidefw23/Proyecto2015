package apps.william.kid.EducaNotas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import dao.UsuarioDao;
import util.Mensaje;


public class LoginActivity extends AppCompatActivity {

    private EditText txtLogin, txtPassword;
    private UsuarioDao dao;
    private CheckBox ckbConectado;
    private static final String CONECTADO= "conectado";
    private static final String NOMBRE_PREFERENCIA= "LoginActivityPreferencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtLogin = (EditText) findViewById(R.id.txtUsuario_login);
        txtPassword = (EditText) findViewById(R.id.txtPassword_login);
        ckbConectado = (CheckBox) findViewById(R.id.ckbConectado_login);
        dao = new UsuarioDao(this);

        SharedPreferences preferences = getSharedPreferences(NOMBRE_PREFERENCIA,MODE_PRIVATE);
        boolean conectado  = preferences.getBoolean(CONECTADO,false);

        if(conectado){
                LlamarMainActivity();
            }
        }

    public void logear(View view){
        String login = txtLogin.getText().toString();
        String password = txtPassword.getText().toString();

        boolean validar =true;
        if(login==null || login.equals("")){
            validar=false;
            txtLogin.setError(getString(R.string.login_valUsuario));
        }

        if(password==null || password.equals("")){
            validar=false;
            txtPassword.setError(getString(R.string.login_valPassword));
        }

        if(validar){
            if(dao.Login(login,password)){
                if(ckbConectado.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences(NOMBRE_PREFERENCIA,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Login",login);
                    editor.putBoolean(CONECTADO,true);
                    editor.commit();
                }
                else {
                    SharedPreferences sharedPreferences = getSharedPreferences(NOMBRE_PREFERENCIA,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Login",login);
                    editor.commit();
                }
                LlamarMainActivity();
            }
            else{
                //Mensaje de Error
                    Mensaje.Msg(this, getString(R.string.msg_invalido));
            }
        }
    }

    private void LlamarMainActivity(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
