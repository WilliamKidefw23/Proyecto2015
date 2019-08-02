package adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import apps.william.kid.EducaNotas.R;

import java.util.List;

import model.Alumno;

/**
 * Created by Usuario on 22/02/2018.
 */

public class NotasAdapter extends  RecyclerView.Adapter<NotasAdapter.ViewHolder>{

    private Context contexto;
    private Cursor items;
    private List<Alumno> alumnosLista;
    private RecyclerView recyclerView;
    private static String TAG="NotasAdapter";

    public NotasAdapter(List<Alumno> alumnosLista, Context context, RecyclerView recyclerView) {
        this.alumnosLista = alumnosLista;
        this.contexto = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =  inflater.inflate(R.layout.alumnos_notas, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alumno alumno = alumnosLista.get(position);
        holder.codigo.setText(String.valueOf(alumno.getIdAlumno()));
        holder.nombres.setText(alumno.getCompleto());
        //holder.modalidad.setText("Regular");
        holder.notas.setText(String.valueOf(alumno.getNota()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView codigo;
        public TextView nombres;
        public TextView modalidad;
        public EditText notas;

        public ViewHolder(final View v) {
            super(v);
            codigo = (TextView) v.findViewById(R.id.text_codigo);
            nombres = (TextView) v.findViewById(R.id.text_nombres);
            //modalidad = (TextView) v.findViewById(R.id.text_modalidad);
            notas = (EditText) v.findViewById(R.id.ed_notas);

            notas.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    int position = getAdapterPosition();
                   // Log.d("NotasAdapter","onTextChanged()");
                    //Log.d(TAG+position,"onTextChanged()");
                    if(TextUtils.isEmpty(notas.getText())) charSequence="0";
                    //Log.d("NotasAdapter New " +position,charSequence.toString());
                    //Log.d("NotasAdapter Before " +position,alumnosLista.get(position).getNota().toString());
                    alumnosLista.get(position).setNota(Integer.parseInt(charSequence.toString()));
                    //Log.d("NotasAdapter After" +position,alumnosLista.get(position).getNota().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(alumnosLista!=null && alumnosLista.size()>0){
            return alumnosLista.size();
        }
        return 0;
    }

    public void swapLista(List<Alumno> nuevaLista) {
        if (nuevaLista != null) {
            alumnosLista = nuevaLista;
            notifyDataSetChanged();
        }
    }


}
