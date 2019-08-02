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

import model.Ciclo;


public class CicloAdapter extends BaseAdapter {

    private Context context;
    private List<Ciclo> lista;

    public CicloAdapter(Context ctx, List<Ciclo> ciclos){
            this.context=ctx;
            this.lista=ciclos;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Ciclo ciclo = lista.get(position);
        if(view==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.mantener_ciclos,null);
        }

        /*CheckBox checkBox = (CheckBox) view.findViewById(R.id.valueCiclo);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("Prueba",String.valueOf(b));
            }
        });*/

        TextView txtNombre = (TextView) view.findViewById(R.id.man_ciclo_lista);
        txtNombre.setText(ciclo.getCiclo());
        return  view;

    }
}
