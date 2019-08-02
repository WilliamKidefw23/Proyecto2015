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

import model.Usuario;


public class UsuarioAdapter extends BaseAdapter {

    private Context context;
    private List<Usuario> lista;

    public UsuarioAdapter(Context ctx, List<Usuario> usuarios){
            this.context=ctx;
            this.lista=usuarios;
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
        Usuario usuario = lista.get(position);
        if(view==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.mantener_usuarios,null);
        }
        TextView txtNombre = (TextView) view.findViewById(R.id.man_usuario_lista);
        txtNombre.setText(usuario.getNombres());
        return  view;
    }
}
