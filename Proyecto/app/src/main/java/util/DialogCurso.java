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

import dao.CursoDao;
import model.Curso;

/**
 * Created by Usuario on 10/03/2018.
 */

public class DialogCurso extends DialogFragment {

    private EditText txtNombre;
    private CursoDao cursoDao;
    private Curso curso;
    private int idCurso;
    private OnDialogListener listener;

    public DialogCurso() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (getArguments() != null) {
            idCurso = getArguments().getInt("curso",0);
        }
        cursoDao = new CursoDao(getActivity());
        return createSimpleDialog();
    }

    public static DialogCurso newInstance(int valor) {
        DialogCurso fragmento = new DialogCurso();
        Bundle args = new Bundle();
        args.putInt("curso", valor);
        fragmento.setArguments(args);
        return fragmento;
    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_man_curso, null);
        builder.setView(v);
        Button guardar = v.findViewById(R.id.dialog_guardar_curso);
        txtNombre = v.findViewById(R.id.txtNombre_Curso);

        if(idCurso>0){
            Curso model = cursoDao.BuscarCurso(idCurso);
            txtNombre.setText(model.getNomCurso());
        }

        guardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Validacion()){
                            dismiss();
                            listener.onGuardarClick(txtNombre.getText().toString(),idCurso);
                        }
                    }
                }
        );
        return builder.create();
    }

    public interface OnDialogListener {
        void onGuardarClick(String nombre,int curso);
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
