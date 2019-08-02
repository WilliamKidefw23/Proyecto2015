package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import apps.william.kid.EducaNotas.R;

import java.util.List;

import model.Alumno;


public class AlumnoAdapter extends BaseAdapter {

    private Context context;
    private List<Alumno> listaAlumno;
    private Alumno alumno;

    public AlumnoAdapter(Context ctx, List<Alumno> alumno){
            this.context=ctx;
            this.listaAlumno=alumno;
    }

    @Override
    public int getCount() {
        if (listaAlumno != null)
            return listaAlumno.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return listaAlumno.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        alumno = listaAlumno.get(position);
        if(view==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.mantener_alumnos,null);
            holder = new ViewHolder();
            holder.txtNombre = (TextView) view.findViewById(R.id.man_alumno_lista);
            view.setTag(holder);
        }else
        {
            holder = (ViewHolder) view.getTag();
        }
        holder.txtNombre.setText(alumno.getCompleto());
        return  view;
    }

    public class ViewHolder
    {
        TextView txtNombre;
    }

    public void swapAdapter(List<Alumno> nuevoAlumno) {
        if (nuevoAlumno.size()>0) {
            listaAlumno = nuevoAlumno;
            notifyDataSetChanged();
        }
    }

}
