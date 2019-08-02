package apps.william.kid.EducaNotas.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.List;

import apps.william.kid.EducaNotas.CrudAlumnoActivity;
import apps.william.kid.EducaNotas.MainActivity;
import apps.william.kid.EducaNotas.R;
import dao.AlumnoDao;
import dao.SeccionDao;
import model.Alumno;
import model.Seccion;
import util.Mensaje;

public class CrudAlumnoFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private EditText txtNombre,txtApellido,txtFechaNac;
    private ImageButton btnFecha;
    private Spinner spnSeccion;
    private AlumnoDao alumnoDao;
    private SeccionDao seccionDao;
    private Alumno alumno;
    private int idAlumno;
    private List<Seccion> seccionLista;
    private static String TAG = CrudAlumnoFragment.class.getSimpleName();
    private String nombres,apellido,fechanac;
    private static String ALUMNO = "Alumno_id";

    public CrudAlumnoFragment() {
    }

    public static Fragment createInstance(int valor){
        CrudAlumnoFragment crudAlumnoFragment = new CrudAlumnoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ALUMNO,valor);
        crudAlumnoFragment.setArguments(bundle);
        return crudAlumnoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        alumnoDao = new AlumnoDao(getActivity());
        seccionDao = new SeccionDao(getActivity());
        //Modo de Editar
        idAlumno = getArguments().getInt(ALUMNO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crud_alumno, container, false);
        txtNombre = view.findViewById(R.id.txtNombre_Alumno);
        txtApellido = view.findViewById(R.id.txtApellido_Alumno);
        txtFechaNac = view.findViewById(R.id.txtFechaNac_Alumno);
        spnSeccion = view.findViewById(R.id.txtSeccion_Alumno);
        btnFecha = view.findViewById(R.id.alumno_btnFechaNac);

        btnFecha.setOnClickListener(this);
        cargarSeccion();
        if(idAlumno>0){
            cargaAlumno();
        }
        return view;
    }

    private void cargarSeccion(){
        seccionLista = seccionDao.listarSeccion();
        ArrayAdapter<Seccion> adapter = new ArrayAdapter<Seccion>(getActivity(),android.R.layout.simple_list_item_1,seccionLista);
        spnSeccion.setAdapter(adapter);
    }

    private void cargaAlumno(){
        Alumno model = alumnoDao.BuscarAlumno(idAlumno);
        txtNombre.setText(model.getNombre());
        txtApellido.setText(model.getApellido());
        txtFechaNac.setText(model.getFechaNacimiento());
        spnSeccion.setSelection(model.getSeccion().getIdSeccion()-1);
        getActivity().setTitle("Actualizar Alumno");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_menu_guardar:
                this.Registro();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validacion(){
        boolean validacion = true;
        nombres = txtNombre.getText().toString();
        apellido = txtApellido.getText().toString();
        fechanac = txtFechaNac.getText().toString();

        if(nombres==null || nombres.equals("")){
            validacion=false;
            txtNombre.setError(getString(R.string.campo_obligatorio));
        }
        if(apellido==null || apellido.equals("")){
            validacion=false;
            txtApellido.setError(getString(R.string.campo_obligatorio));
        }
        if(fechanac==null || fechanac.equals("")){
            validacion=false;
            txtFechaNac.setError(getString(R.string.campo_obligatorio));
        }
        return validacion;
    }

    private void Registro(){

        if(validacion()){
            alumno = new Alumno();
            alumno.setNombre(nombres);
            alumno.setApellido(apellido);
            alumno.setFechaNacimiento(fechanac);
            Seccion seccion = (Seccion) spnSeccion.getSelectedItem();
            alumno.setSeccion(seccion);
            //Si fuera a Actualizar
            if(idAlumno>0){
                alumno.setIdAlumno(idAlumno);
            }
            new CrudAlumnoTask().execute(alumno);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        alumnoDao.Cerrar();
        seccionDao.Cerrar();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.alumno_btnFechaNac :
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity(),this,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth+"/"+(month+1)+"/"+year;
        txtFechaNac.setText(date);
    }

    private class CrudAlumnoTask extends AsyncTask<Alumno, Void, Long> {

        @Override
        protected Long doInBackground(Alumno... alumnos) {
            return alumnoDao.GuardarAlumno(alumnos[0]);
        }

        @Override
        protected void onPostExecute(Long resultado) {
            showAlumnoResultado(resultado);
        }
    }

    private void showAlumnoResultado(Long resultado) {
        Log.d(TAG,"Crud");
        if(resultado != -1){
            if(idAlumno > 0){
                Mensaje.Msg(getActivity(), getString(R.string.msg_actualizar));
            }else {
                Mensaje.Msg(getActivity(), getString(R.string.msg_registra));
            }
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }else{
            Mensaje.Msg(getActivity(),getString(R.string.msg_error));
        }
    }
}
