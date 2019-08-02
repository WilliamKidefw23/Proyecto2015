package apps.william.kid.EducaNotas.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import apps.william.kid.EducaNotas.ExportarNotasActivity;
import apps.william.kid.EducaNotas.ImportarNotasActivity;
import apps.william.kid.EducaNotas.R;
import apps.william.kid.EducaNotas.RegitrarNotasActivity;
import dao.UsuarioDao;


public class PrincipalFragment extends Fragment {

    private EditText txtUsuarioInicio;
    private Button btnImportar,btnExportar,btnRegistrar;
    private static String TAG = PrincipalFragment.class.getSimpleName();

    private static final String NOMBRE_PREFERENCIA= "MainActivityPreferencia";
    private UsuarioDao dao;
    private int codigoUsuario;

    public PrincipalFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);

        //txtUsuarioInicio = (EditText) view.findViewById(R.id.txtNombre_main);
        //LlamarUsuario();

        btnExportar = view.findViewById(R.id.btnExportar_main);
        btnImportar = view.findViewById(R.id.btnImportar_main);
        btnRegistrar = view.findViewById(R.id.btnRegistrar_main);

        btnExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ExportarNotasActivity.class));
            }
        });
        btnImportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ImportarNotasActivity.class));
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RegitrarNotasActivity.class));
                //getActivity().finish();
            }
        });

        return  view;
    }


    public void LlamarUsuario(){
        //Log.d(TAG,"LlamarUsuario");
        dao = new UsuarioDao(getActivity());
        SharedPreferences preferences = getActivity().getSharedPreferences("LoginActivityPreferencia",getActivity().MODE_PRIVATE);
        String login = preferences.getString("Login","");
        txtUsuarioInicio.setText(dao.TraeNombreUsuario(login));
        codigoUsuario = dao.TraerCodigoUsuario(login);
        //Log.d(TAG,"Usuario : "+String.valueOf(codigoUsuario));
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(NOMBRE_PREFERENCIA,getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("CodUsuario",codigoUsuario);
        editor.commit();
    }

}
