package util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

public class Mensaje {

    public static void Msg(Activity activity,String msg){
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
    }

    public static void addMsgOk(Activity activity,String titulo,String msg,int icono){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(titulo);
        alert.setMessage(msg);
        alert.setNeutralButton("Ok",null);
        alert.setIcon(icono);
        alert.show();
    }

    public  static void MsgConfimacion(Activity activity,String titulo,String msg,int icono, DialogInterface.OnClickListener listener){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(titulo);
        alert.setMessage(msg);
        alert.setPositiveButton("Si", listener);
        alert.setNegativeButton("No", null);
        alert.setIcon(icono);
        alert.show();
    }

    public static AlertDialog CrearAlertDialog(Activity activity){
        final CharSequence[] items ={
                "Editar",
                "Eliminar"
        };
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Opciones");
        alert.setItems(items, (DialogInterface.OnClickListener) activity);
        return alert.create();
    }

    public static AlertDialog CrearAlertDialog(Fragment fragment){
        final CharSequence[] items ={
                "Editar",
                "Eliminar"
        };
        AlertDialog.Builder alert = new AlertDialog.Builder(fragment.getActivity());
        alert.setTitle("Opciones");
        alert.setItems(items, (DialogInterface.OnClickListener) fragment);
        return alert.create();
    }

    public static AlertDialog CrearDialogConfirmacion(Activity activity){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setMessage("Desea realmente Eliminar ?");
        alert.setPositiveButton("Si", (DialogInterface.OnClickListener) activity);
        alert.setNegativeButton("No", (DialogInterface.OnClickListener) activity);

        return alert.create();
    }

    public static AlertDialog CrearDialogConfirmacion(Fragment fragment){
        AlertDialog.Builder alert = new AlertDialog.Builder(fragment.getActivity());
        alert.setMessage("Desea realmente Eliminar ?");
        alert.setPositiveButton("Si", (DialogInterface.OnClickListener) fragment);
        alert.setNegativeButton("No", (DialogInterface.OnClickListener) fragment);

        return alert.create();
    }

    public static AlertDialog CrearDialogCiclo(Activity activity){
        final CharSequence[] items ={
                "Agregar",
                "Eliminar"
        };
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Ciclos");
        alert.setItems(items, (DialogInterface.OnClickListener) activity);
        return alert.create();
    }

    public static AlertDialog CrearDialogCiclo(Fragment fragment){
        final CharSequence[] items ={
                "Agregar",
                "Eliminar"
        };
        AlertDialog.Builder alert = new AlertDialog.Builder(fragment.getActivity());
        alert.setTitle("Ciclos");
        alert.setItems(items, (DialogInterface.OnClickListener) fragment);
        return alert.create();
    }

    public static AlertDialog CrearDialogAlumno(Activity activity){
        final CharSequence[] items ={
                "Asignar Curso",
                "Editar",
                "Eliminar"
        };
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Alumnos");
        alert.setItems(items, (DialogInterface.OnClickListener) activity);
        return alert.create();
    }

    public static AlertDialog CrearDialogAlumno(Fragment fragment){
        final CharSequence[] items ={
                "Asignar Curso",
                "Editar",
                "Eliminar"
        };
        AlertDialog.Builder alert = new AlertDialog.Builder(fragment.getActivity());
        alert.setTitle("Alumnos");
        alert.setItems(items, (DialogInterface.OnClickListener) fragment);
        return alert.create();
    }
}
