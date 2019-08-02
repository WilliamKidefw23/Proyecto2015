package apps.william.kid.EducaNotas.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import adapter.SeccionAdapter;
import apps.william.kid.EducaNotas.R;
import dao.SeccionDao;
import model.Seccion;
import util.DialogSeccion;
import util.Mensaje;


public class ListadoSeccionFragment extends Fragment
        implements AdapterView.OnItemClickListener,DialogInterface.OnClickListener,DialogSeccion.OnDialogListener {

    private ListView listView;
    private List<Seccion> seccionLista;
    private SeccionAdapter seccionAdapter;
    private SeccionDao seccionDao;
    private int idposicion,idSeccion;
    private AlertDialog alertDialog, alertConfirmacion;
    public static String TAG = ListadoSeccionFragment.class.getSimpleName();
    private Seccion seccion;

    public ListadoSeccionFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        seccionDao = new SeccionDao(getActivity());
        seccionAdapter =  new SeccionAdapter(getActivity(), null,seccionDao);
        alertDialog = Mensaje.CrearAlertDialog(this);
        alertConfirmacion = Mensaje.CrearDialogConfirmacion(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listado_seccion, container, false);
        listView =  view.findViewById(R.id.lvSeccion);
        listView.setAdapter(seccionAdapter);
        cargarSeccion();
        listView.setOnItemClickListener(this);
        return view;
    }

    private void cargarSeccion() {
        new ListadoSeccionTask().execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idposicion = position;
        alertDialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_listado_seccion,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_registrar_seccion) {
            new DialogSeccion().show(getChildFragmentManager(),"Mantener Seccion");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        idSeccion = seccionLista.get(idposicion).getIdSeccion();

        switch (which) {
            case 0:
                DialogSeccion dialogCurso = DialogSeccion.newInstance(idSeccion);
                dialogCurso.show(getChildFragmentManager(), "Mantener Seccion");
                break;
            case 1:
                alertConfirmacion.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                new DeleteSeccionTask().execute();
                seccionLista.remove(idposicion);
                listView.invalidateViews();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertConfirmacion.dismiss();
                break;
        }
    }

    @Override
    public void onGuardarClick(String nombre, int seccion) {
        idSeccion=seccion;
        Registro(nombre,seccion);
    }

    private void Registro(String nombres,int idSeccion){
        seccion = new Seccion();
        seccion.setSeccion(nombres);
        seccion.setEstado(1);

        //Si fuera a Actualizar
        if(idSeccion>0){
            seccion.setIdSeccion(idSeccion);
        }
        new CrudSeccionTask().execute(seccion);
    }

    private class ListadoSeccionTask extends AsyncTask<Void,Void,List<Seccion>> {

        @Override
        protected List<Seccion> doInBackground(Void... voids) {
            return seccionDao.listarSeccion();
        }

        @Override
        protected void onPostExecute(List<Seccion> seccions) {
            if (seccions != null && seccions.size() > 0) {
                seccionLista = seccions;
                seccionAdapter.swapAdapter(seccions);
            } else {
                showError();
            }
        }
    }

    private void showError() {
        Toast.makeText(getActivity(),"Error al cargar listado",Toast.LENGTH_LONG).show();
    }

    private class DeleteSeccionTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            return seccionDao.EliminarSeccion(idSeccion);
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            if(resultado) showDeleteTrue();
            else showDeleteError();
        }
    }

    private void showDeleteTrue() {
        Toast.makeText(getActivity(),"Se elimino seccion", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),"Error al eliminar seccion", Toast.LENGTH_SHORT).show();
    }

    private class CrudSeccionTask extends AsyncTask<Seccion, Void, Long> {

        @Override
        protected Long doInBackground(Seccion... seccions) {
            return seccionDao.GuardarSeccion(seccions[0]);
        }

        @Override
        protected void onPostExecute(Long resultado) {
            showCursoResultado(resultado);
        }
    }

    private void showCursoResultado(Long resultado) {
        if(resultado != -1){
            if(idSeccion > 0){
                Mensaje.Msg(getActivity(), getString(R.string.msg_actualizar));
            }else {
                Mensaje.Msg(getActivity(), getString(R.string.msg_registra));
            }
            cargarSeccion();
        }else{
            Mensaje.Msg(getActivity(),getString(R.string.msg_error));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        seccionDao.Cerrar();
    }

}
