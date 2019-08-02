package apps.william.kid.EducaNotas.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import adapter.CursoAlumnoAdapter;
import apps.william.kid.EducaNotas.R;
import dao.CursoDao;
import model.Curso;
import util.DialogCurso;
import util.Mensaje;


public class ListadoAlumnoCursoFragment extends Fragment
        implements AdapterView.OnItemClickListener,DialogInterface.OnClickListener,DialogCurso.OnDialogListener {

    private ListView listView;
    private List<Curso> listaCursos,cursos;
    private CursoAlumnoAdapter cursoAlumnoAdapter;
    private CursoDao cursoDao;
    private Curso curso;
    private int idposicion;
    private AlertDialog alertDialog, alertConfirmacion;
    public static String TAG = ListadoAlumnoCursoFragment.class.getSimpleName();
    private int idAlumno,idCurso;
    private static String ALUMNO = "Alumno_id";

    public ListadoAlumnoCursoFragment() {
    }

    public static ListadoAlumnoCursoFragment createInstance(int valor) {
        ListadoAlumnoCursoFragment fragment = new ListadoAlumnoCursoFragment();
        Bundle args = new Bundle();
        args.putInt(ALUMNO,valor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        alertDialog = Mensaje.CrearAlertDialog(this);
        alertConfirmacion = Mensaje.CrearDialogConfirmacion(this);
        cursoDao = new CursoDao(getActivity());

        if (getArguments() != null) {
            idAlumno = getArguments().getInt(ALUMNO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listado_alumno_curso, container, false);
        listView =  view.findViewById(R.id.lvCursosAlumnos);
        cursoAlumnoAdapter =  new CursoAlumnoAdapter(getActivity(), null,cursoDao,cursoDao.listarCurso(idAlumno),idAlumno);
        listView.setAdapter(cursoAlumnoAdapter);
        cargaCursos();
        listView.setOnItemClickListener(this);
        return view;
    }

    private void cargaCursos() {
        new ListadoCursoTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_registrar_cursos) {
            new DialogCurso().show(getChildFragmentManager(),"Mantener Curso");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idposicion = position;
        alertDialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        idCurso = listaCursos.get(idposicion).getIdCurso();

        switch (which){
            case 0:
                DialogCurso dialogCurso = DialogCurso.newInstance(idCurso);
                dialogCurso.show(getChildFragmentManager(), "Mantener Curso");
                break;
            case 1 :
                alertConfirmacion.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                new DeleteCursoTask().execute();
                listaCursos.remove(idposicion);
                listView.invalidateViews();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertConfirmacion.dismiss();
                break;
        }
    }

    @Override
    public void onGuardarClick(String nombre, int curso) {
        idCurso=curso;
        Registro(nombre,curso);
    }

    private void Registro(String nombres,int idCurso){
        curso = new Curso();
        curso.setNomCurso(nombres);
        curso.setEstado(1);

        //Si fuera a Actualizar
        if(idCurso>0){
            curso.setIdCurso(idCurso);
        }

        new CrudCursoTask().execute(curso);

    }

    private class ListadoCursoTask extends AsyncTask<Void,Void,List<Curso>> {

        @Override
        protected List<Curso> doInBackground(Void... voids) {
            return cursoDao.listarCurso();
        }

        @Override
        protected void onPostExecute(List<Curso> cursos) {
            if (cursos != null && cursos.size() > 0) {
                listaCursos = cursos;
                cursoAlumnoAdapter.swapAdapter(cursos);
            } else {
                showError();
            }
        }
    }

    private void showError() {
        Toast.makeText(getActivity(),"Error al cargar listado",Toast.LENGTH_LONG).show();
    }

    private class DeleteCursoTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            return cursoDao.EliminarCurso(idCurso);
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            if(resultado) showDeleteTrue();
            else showDeleteError();
        }
    }

    private void showDeleteTrue() {
        Toast.makeText(getActivity(),"Se elimino curso", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),"Error al eliminar curso", Toast.LENGTH_SHORT).show();
    }

    private class CrudCursoTask extends AsyncTask<Curso, Void, Long> {

        @Override
        protected Long doInBackground(Curso... cursos) {
            return cursoDao.GuardarCurso(cursos[0]);
        }

        @Override
        protected void onPostExecute(Long resultado) {
            showCursoResultado(resultado);
        }
    }

    private void showCursoResultado(Long resultado) {
        if(resultado != -1){
            if(idCurso > 0){
                Mensaje.Msg(getActivity(), getString(R.string.msg_actualizar));
            }else {
                Mensaje.Msg(getActivity(), getString(R.string.msg_registra));
            }
            cargaCursos();
        }else{
            Mensaje.Msg(getActivity(),getString(R.string.msg_error));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cursoDao.Cerrar();
    }
}
