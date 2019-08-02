package apps.william.kid.EducaNotas.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import adapter.AlumnoAdapter;
import apps.william.kid.EducaNotas.CrudAlumnoActivity;
import apps.william.kid.EducaNotas.ListadoAlumnoCursoActivity;
import apps.william.kid.EducaNotas.R;
import dao.AlumnoDao;
import model.Alumno;
import util.Mensaje;

public class ListadoAlumnoFragment extends Fragment
        implements AdapterView.OnItemClickListener,DialogInterface.OnClickListener {

    private ListView listView;
    private List<Alumno> alumnosLista;
    private AlumnoAdapter alumnoAdapter;
    private AlumnoDao alumnoDao;
    private int idposicion,idAlumno;
    private AlertDialog alertDialog, alertConfirmacion;
    private static String TAG = ListadoAlumnoFragment.class.getSimpleName();
    private final static int RESULTADO =1;
    private static String ALUMNO = "Alumno_id";

    public ListadoAlumnoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        alertDialog = Mensaje.CrearDialogAlumno(this);
        alertConfirmacion = Mensaje.CrearDialogConfirmacion(this);
        alumnoDao = new AlumnoDao(getActivity());
        alumnoAdapter =  new AlumnoAdapter(getActivity(), null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listado_alumno, container, false);
        listView = view.findViewById(R.id.lvAlumnos);
        listView.setAdapter(alumnoAdapter);
        cargarAlumno();
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_listado_alumnos, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_registrar_alumnos) {
            getActivity().startActivityForResult
                    (new Intent(getActivity(), CrudAlumnoActivity.class),RESULTADO);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        alumnoDao.Cerrar();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idposicion = position;
        alertDialog.show();
    }

    public void cargarAlumno() {
        new ListadoAlumnoTask().execute();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        idAlumno= alumnosLista.get(idposicion).getIdAlumno();
        switch (which){
            case 0:
                Intent intent = new Intent(getActivity(),ListadoAlumnoCursoActivity.class);
                intent.putExtra(ALUMNO,idAlumno);
                startActivity(intent);
                break;
            case 1:
                Intent i = new Intent(getActivity(),CrudAlumnoActivity.class);
                i.putExtra(ALUMNO,idAlumno);
                getActivity().startActivityForResult(i,RESULTADO);
                break;
            case 2 :
                alertConfirmacion.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                new DeleteAlumnoTask().execute();
                alumnosLista.remove(idposicion);
                listView.invalidateViews();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertConfirmacion.dismiss();
                break;
        }
    }

    private class ListadoAlumnoTask extends AsyncTask<Void,Void,List<Alumno>> {

        @Override
        protected List<Alumno> doInBackground(Void... voids) {
            return alumnoDao.listarAlumnos();
        }

        @Override
        protected void onPostExecute(List<Alumno> alumnos) {
            if (alumnos != null && alumnos.size() > 0) {
                alumnosLista = alumnos;
                alumnoAdapter.swapAdapter(alumnos);
            } else {
                showError();
            }
        }
    }

    private void showError() {
        Toast.makeText(getActivity(),"Error al cargar listado",Toast.LENGTH_LONG).show();
    }

    private class DeleteAlumnoTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            return alumnoDao.EliminarAlumno(idAlumno);
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            if(resultado) showDeleteTrue();
            else showDeleteError();
        }
    }

    private void showDeleteTrue() {
        Toast.makeText(getActivity(),"Se elimino alumno", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),"Error al eliminar alumno", Toast.LENGTH_SHORT).show();
    }
}
