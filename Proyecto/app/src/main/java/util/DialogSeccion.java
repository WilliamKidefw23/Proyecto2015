package util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import apps.william.kid.EducaNotas.R;

import dao.SeccionDao;
import model.Seccion;

/**
 * Created by Usuario on 10/03/2018.
 */

public class DialogSeccion extends DialogFragment {

    private EditText txtNombre;
    private SeccionDao seccionDao;
    private Seccion seccion;
    private int idSeccion;
    private OnDialogListener listener;

    public DialogSeccion() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (getArguments() != null) {
            idSeccion = getArguments().getInt("seccion",0);
        }
        seccionDao = new SeccionDao(getActivity());
        return createSimpleDialog();
    }

    public static DialogSeccion newInstance(int valor) {
        DialogSeccion fragmento = new DialogSeccion();
        Bundle args = new Bundle();
        args.putInt("seccion", valor);
        fragmento.setArguments(args);
        return fragmento;
    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_man_seccion, null);
        builder.setView(v);
        Button guardar = v.findViewById(R.id.dialog_guardar_seccion);
        txtNombre = v.findViewById(R.id.txtNombre_Seccion);

        if(idSeccion>0){
            Seccion model = seccionDao.BuscarSeccion(idSeccion);
            txtNombre.setText(model.getSeccion());
        }
        guardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Validacion()){
                            dismiss();
                            listener.onGuardarClick(txtNombre.getText().toString(),idSeccion);
                        }
                    }
                }
        );
        return builder.create();
    }

    public interface OnDialogListener {
        void onGuardarClick(String nombre, int seccion);
    }

    private boolean Validacion(){
        boolean valida=true;
        String nombres = txtNombre.getText().toString();

        if(nombres==null || nombres.equals("")){
            txtNombre.setError(getString(R.string.campo_obligatorio));
            valida=false;
        }
        return valida;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnDialogListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    getParentFragment().toString() +
                            " no implementó OnDialogListener");
        }
    }


}
