package apps.william.kid.EducaNotas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import dao.AlumnoDao;
import model.Alumno;


public class ResultadoAlumnosActivity extends AppCompatActivity implements View.OnClickListener  {

    private static final String TAG ="ResultadoAlumnos";
    private Button btnRegresar,btnActualizarAlu;
    private EditText txtTotalF,txtSumaNota,txtPromedioNota,txtMaximaNota,txtMinimaNota,txtAprobados,txtDesaprobados;
    private String nota;
    private int idSeccion,idCurso;
    private AlumnoDao alumnoDao;
    private List<Alumno> alumnoLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_alumnos);

        agregarToolbar();
        btnRegresar = (Button) findViewById(R.id.btnRegresar_res);
        btnActualizarAlu = (Button) findViewById(R.id.btnConfirmar_res);
        txtTotalF = (EditText) findViewById(R.id.txtTotalAlumno_res);
        txtSumaNota =(EditText) findViewById(R.id.txtSumaNota_res);
        txtPromedioNota =(EditText) findViewById(R.id.txtPromedioNota_res);
        txtMaximaNota =(EditText) findViewById(R.id.txtMaximaNota_res);
        txtMinimaNota =(EditText) findViewById(R.id.txtMinimaNota_res);
        txtAprobados=(EditText) findViewById(R.id.txtAprobados_res);
        txtDesaprobados=(EditText) findViewById(R.id.txtDesaprobados_res);
        alumnoDao = new AlumnoDao(this);
        obtenerDatos();
        CargaAlumno();
        txtTotalF.setText(String.valueOf(TotalAlumnos()));

        txtSumaNota.setText(String.valueOf(SumaNota()));
        txtPromedioNota.setText(String.valueOf(PromedioNota()));
        txtMaximaNota.setText(String.valueOf(MayorNota()));
        txtMinimaNota.setText(String.valueOf(MinimaNota()));
        txtAprobados.setText(String.valueOf(NumeroAprobado()));
        txtDesaprobados.setText(String.valueOf(NumeroDesaprobado()));
        /*txtAsistentes.setText(String.valueOf(NumeroDesaprobadoAsistentes(cod)));
        txtPorcentajeMatriculado.setText(String.valueOf(PorcentajeAprobadoMatriculados(cod)));
        txtPorcentajeAsistente.setText(String.valueOf(PorcentajeAprobadoMatriculados(cod)));
        */
        //txtNotaNR.setText(String.valueOf(DistribuccionNotas()));
        //txtNotaFinal.setText(String.valueOf(TotalAlumnos()));

        btnRegresar.setOnClickListener(this);
        btnActualizarAlu.setOnClickListener(this);
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_res_alumnos);
        setSupportActionBar(toolbar);
    }

    public void obtenerDatos(){
        idSeccion=getIntent().getExtras().getInt("idSeccion",0);
        idCurso=getIntent().getExtras().getInt("idCurso",0);
    }

    private void CargaAlumno(){
        alumnoLista = alumnoDao.obtenerAlumnosNotas(idSeccion,idCurso);

        TableLayout tabla=(TableLayout)findViewById(R.id.tb_resultado_alumno);
        TableRow row;
        TextView txtcod,txtnom,txtMatricula,txtnota;

        row = new TableRow(this);
        txtcod = new TextView(this);
        txtnom = new TextView(this);
        txtnota = new TextView(this);
        txtMatricula = new TextView(this);
        txtcod.setText("Alumno ");
        txtnom.setText("Nombres ");
        txtnota.setText("Nota");
        txtMatricula.setText("Estado de Matricula ");
        row.addView(txtcod);
        row.addView(txtnom);
        row.addView(txtMatricula);
        row.addView(txtnota);
        tabla.addView(row);

        for (int i=0;i<alumnoLista.size();i++){
            if(alumnoLista.get(i).getNota()==0)
                nota="NR";
            else nota=alumnoLista.get(i).getNota().toString();
        }

        for(Alumno a:alumnoLista) {
            if(a.getNota()==0)
                nota="NR";
            else nota=a.getNota().toString();
            row = new TableRow(this);
            txtcod = new TextView(this);
            txtnom = new TextView(this);
            txtnota = new TextView(this);
            txtMatricula = new TextView(this);
            txtcod.setText(String.valueOf(a.getIdAlumno()));
            txtnom.setText(a.getNombre());
            txtMatricula.setText("Regular");
            txtnota.setText(nota);
            row.addView(txtcod);
            row.addView(txtnom);
            row.addView(txtMatricula);
            row.addView(txtnota);
            tabla.addView(row);
        }
    }

    public Integer TotalAlumnos(){
        Integer total = alumnoDao.obtenerTotalAlumnosNotas(idSeccion,idCurso);
        return total;
    }

    public Integer SumaNota(){
        Integer total = alumnoDao.SumaNotaAlumnos(idSeccion,idCurso);
        return  total;
    }

    public Integer PromedioNota(){
        Integer total = alumnoDao.PromedioNotaAlumno(idSeccion,idCurso);
        return  total;
    }

    public Integer MayorNota(){
        Integer total = alumnoDao.MayorNotaAlumno(idSeccion,idCurso);
        return  total;
    }

    public Integer MinimaNota(){
        Integer total = alumnoDao.MinimaNotaAlumno(idSeccion,idCurso);
        return  total;
    }

    public Integer NumeroAprobado(){
        Integer total = alumnoDao.NumeroAprobadoAlumno(idSeccion,idCurso);
        return  total;
    }

    public Integer NumeroDesaprobado(){
        Integer total = alumnoDao.NumeroDesaprobadoAlumno(idSeccion,idCurso);
        return  total;
    }

    @Override
    public void onClick(View view) {
        //Log.d(TAG,"onClick()");
        int id = view.getId();
        switch (id){
            case R.id.btnRegresar_res :
                Intent intent=new Intent(this,ConsolidadoNotasActivity.class);
                intent.putExtra("idSeccion",idSeccion);
                intent.putExtra("idCurso",idCurso);
                startActivity(intent);
                finish();
                break;
            case R.id.btnConfirmar_res:
                //Intent ir=new Intent(this,MainActivity.class);
                //startActivity(ir);
                finish();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alumnoDao.Cerrar();
    }
}
