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
import model.Curso;


public class CursoAdapter extends BaseAdapter {

    private Context context;
    private List<Curso> listaCurso;
    private Curso curso;
    private CursoDao cursoDao;
    private static String TAG="CursoAdapter";

    public CursoAdapter(Context ctx, List<Curso> cursos, CursoDao cursoDao){
            this.context=ctx;
            this.listaCurso=cursos;
            this.cursoDao=cursoDao;
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
        if(view==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.mantener_cursos,null);
            holder = new ViewHolder();
            holder.checkBox = view.findViewById(R.id.chk_man_Cursos);
            holder.txtNombre = (TextView) view.findViewById(R.id.man_curso_lista);
            view.setTag(holder);
        }
        else holder = (ViewHolder) view.getTag();

        holder.txtNombre.setText(curso.getNomCurso());
        //Log.d(ListadoCursoActivity.TAG +" "+ position,curso.getIdCurso()+ " - "+curso.getNomCurso()+" - "+String.valueOf(curso.getEstado()));
        if(listaCurso.get(position).getEstado()==0){
            holder.checkBox.setChecked(false);
        }else holder.checkBox.setChecked(true);

        //holder.checkBox.setTag(1,view);
        holder.checkBox.setTag(position);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG,curso.getIdCurso().toString());
                Curso model = new Curso();
                Integer pos = (Integer)  holder.checkBox.getTag();
                model.setIdCurso(listaCurso.get(pos).getIdCurso());
                if (holder.checkBox.isChecked()) {
                    model.setEstado(1);
                    //Log.d(ListadoCursoActivity.TAG+pos,"isChecked");
                }
                else{
                    model.setEstado(0);
                    //Log.d(ListadoCursoActivity.TAG+pos,"NOTisChecked");
                }
                new CursoAdapterTask().execute(model);
            }
        });
        /*holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Integer pos = (Integer)compoundButton.getTag();
                if (b) {
                    Log.d("OnCheckedChange "+pos,"isChecked");
                    //Toast.makeText(CursoAdapter.this,"Si",Toast.LENGTH_SHORT).show();
                } else if (!b) {
                    //c.setEstado(0);
                    Log.d("OnCheckedChange "+pos,"isNOTChecked");
                    //Toast.makeText(CursoAdapter.this,"No",Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        return  view;
    }

    /*@Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean bol) {

        Integer currentPosition = (Integer)compoundButton.getTag();
        curso.setIdCurso(currentPosition);
        if (bol) {
            curso.setEstado(1);
            Log.d(ListadoCursoActivity.TAG+currentPosition,"isChecked");
            //Toast.makeText(CursoAdapter.this,"Si",Toast.LENGTH_SHORT).show();
        } else if (!bol) {
            //c.setEstado(0);
            Log.d(ListadoCursoActivity.TAG+currentPosition,"isNOTChecked");
            //Toast.makeText(CursoAdapter.this,"No",Toast.LENGTH_SHORT).show();
            curso.setEstado(0);
        }
        //new CursoAdapterTask().execute(curso);
    }*/

    public class ViewHolder
    {
        TextView txtNombre;
        CheckBox checkBox;
    }

    private class CursoAdapterTask extends AsyncTask<Curso,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Curso... cursos) {
            //Log.d(TAG,"doInBackground");
            //Log.d(ListadoCursoActivity.TAG,cursos[0].getIdCurso().toString());
            //Log.d(ListadoCursoActivity.TAG,String.valueOf(cursos[0].getEstado()));
            return cursoDao.CheckCurso(cursos[0]);
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            super.onPostExecute(resultado);
            //Log.d(TAG,"Modificado : "+resultado);
            //notifyDataSetChanged();
        }
    }

    public void swapAdapter(List<Curso> nuevoCurso) {
        if (nuevoCurso.size()>0) {
            listaCurso = nuevoCurso;
            notifyDataSetChanged();
        }
    }
}
