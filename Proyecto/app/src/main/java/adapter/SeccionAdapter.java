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

import dao.SeccionDao;
import model.Seccion;


public class SeccionAdapter extends BaseAdapter {

    private Context context;
    private List<Seccion> seccionLista;
    private Seccion seccion;
    private SeccionDao seccionDao;
    private static String TAG ="SeccionAdapter";

    public SeccionAdapter(Context ctx, List<Seccion> seccion, SeccionDao seccionDao){
            this.context=ctx;
            this.seccionLista=seccion;
            this.seccionDao=seccionDao;
    }

    @Override
    public int getCount() {
        if (seccionLista != null)
            return seccionLista.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return seccionLista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        seccion = seccionLista.get(position);
        if(view==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.mantener_seccion,null);
            holder = new ViewHolder();
            holder.checkBox = view.findViewById(R.id.chk_man_Seccion);
            holder.txtNombre = (TextView) view.findViewById(R.id.man_seccion_lista);
            view.setTag(holder);
        }else
        {
            holder = (ViewHolder) view.getTag();
        }

        holder.txtNombre.setText(seccion.getSeccion());
        if(seccionLista.get(position).getEstado()==0){
            holder.checkBox.setChecked(false);
        }else holder.checkBox.setChecked(true);

        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG,seccion.getIdSeccion().toString());
                Seccion s = new Seccion();
                Integer pos = (Integer)  holder.checkBox.getTag();
                s.setIdSeccion(seccionLista.get(pos).getIdSeccion());
                if (holder.checkBox.isChecked()) {
                    s.setEstado(1);
                }
                else{
                    s.setEstado(0);
                }
                new SeccionAdapterTask().execute(s);
            }
        });
        return  view;
    }


    public class ViewHolder
    {
        TextView txtNombre;
        CheckBox checkBox;
    }

    private class SeccionAdapterTask extends AsyncTask<Seccion,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Seccion... seccion) {
            return seccionDao.CheckSeccion(seccion[0]);
        }

        @Override
        protected void onPostExecute(Boolean l) {
            super.onPostExecute(l);
            //Log.d(TAG,"Modificado : "+l);
        }
    }

    public void swapAdapter(List<Seccion> nuevaSeccion) {
        if (nuevaSeccion.size()>0) {
            seccionLista = nuevaSeccion;
            notifyDataSetChanged();
        }
    }

}
