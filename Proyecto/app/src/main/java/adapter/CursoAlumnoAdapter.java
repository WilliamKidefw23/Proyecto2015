package adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import apps.william.kid.EducaNotas.R;

import java.util.List;

import dao.CursoDao;
import model.AlumnoCurso;
import model.Curso;


public class CursoAlumnoAdapter extends BaseAdapter {

    private Context context;
    private List<Curso> listaCurso;
    private Curso curso;
    private CursoDao cursoDao;
    private List<Curso> filtroCurso;
    private int idAlumno;
    private static String TAG="CursoAlumnoAdapter";

    public CursoAlumnoAdapter(Context ctx, List<Curso> cursos, CursoDao cursoDao,List<Curso> filtro,int idAlumno){
            this.context=ctx;
            this.listaCurso=cursos;
            this.cursoDao=cursoDao;
            this.filtroCurso=filtro;
            this.idAlumno=idAlumno;
    }

    @Override
    public int getCount() {
        if (listaCurso != null)
            return listaCurso.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return listaCurso.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        curso = listaCurso.get(position);
        //curso2 = filtro.get(position);
        if(view==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.mantener_cursos,null);
            holder = new ViewHolder();
            holder.checkBox = view.findViewById(R.id.chk_man_Cursos);
            holder.txtNombre = (TextView) view.findViewById(R.id.man_curso_lista);
            view.setTag(holder);
        }else
        {
            holder = (ViewHolder) view.getTag();
        }

        holder.txtNombre.setText(curso.getNomCurso());
        //Log.d(ListadoCursoActivity.TAG +" "+ position,curso.getIdCurso()+ " - "+curso.getNomCurso()+" - "+String.valueOf(curso.getEstado()));
        //Log.d(ListadoCursoActivity.TAG +" "+ position,curso2.getIdCurso()+ " - "+curso2.getNomCurso()+" - "+String.valueOf(curso2.getEstado()));
        for(Curso aux : filtroCurso){
            //Log.d(TAG , aux.getIdCurso().toString());
            if(aux.getIdCurso()==curso.getIdCurso()){
                //Log.d(TAG,"cruce");
                //listacurso.remove(curso);
                //notifyDataSetChanged();
                holder.checkBox.setChecked(true);
                break;
            }else holder.checkBox.setChecked(false);
        }
        holder.checkBox.setTag(position);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG,curso.getIdCurso().toString());
                Integer pos = (Integer)  holder.checkBox.getTag();
                AlumnoCurso model = new AlumnoCurso(idAlumno,listaCurso.get(pos).getIdCurso());
                //c.setIdCurso(listacurso.get(pos).getIdCurso());
                //Log.d(TAG,listaCurso.get(pos).getIdCurso().toString());
                /*if (holder.checkBox.isChecked()) {
                    c.setEstado(1);
                    //Log.d(ListadoCursoActivity.TAG+pos,"isChecked");
                }
                else{
                    c.setEstado(0);
                    //Log.d(ListadoCursoActivity.TAG+pos,"NOTisChecked");
                }*/
                new CursoAlumnoAdapterTask().execute(model);
            }
        });

        return  view;
    }

    public class ViewHolder
    {
        TextView txtNombre;
        CheckBox checkBox;
    }

    private class CursoAlumnoAdapterTask extends AsyncTask<AlumnoCurso,Void,Boolean>{

        @Override
        protected Boolean doInBackground(AlumnoCurso... alumnoCursos) {
            //Log.d(TAG,"doInBackground");
            return cursoDao.GuardarAlumnoCurso(alumnoCursos[0]);
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            super.onPostExecute(resultado);
            if(resultado){
                //Log.d(ListadoAlumnoCursoActivity.TAG,"Procesado");
            }else{
                //Log.d(ListadoAlumnoCursoActivity.TAG,"Error");
            }
        }
    }

    public void swapAdapter(List<Curso> nuevoCurso) {
        if (nuevoCurso.size()>0) {
            listaCurso = nuevoCurso;
            notifyDataSetChanged();
        }
    }

}
