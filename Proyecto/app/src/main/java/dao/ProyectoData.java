package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import conexion.DataBaseHelper;

public class ProyectoData {

        //Usuario
        public static final String TABLE_NAME_USUARIO= "Usuario";
        public static final String KEY_USUARIO = "idUsuario";
        public static final String NOMBRES_USUARIO = "nombres";
        public static final String APELLIDOP_USUARIO = "apellidop";
        public static final String APELLIDOM_USUARIO = "apellidom";
        public static final String LOGIN_USUARIO = "login";
        public static final String PASSWORD_USUARIO = "password";
        public static final String CREADO_USUARIO = "creado";
        public static final String[] COLUMNS_USUARIO ={ KEY_USUARIO, NOMBRES_USUARIO,APELLIDOP_USUARIO,
                APELLIDOM_USUARIO,LOGIN_USUARIO,PASSWORD_USUARIO,CREADO_USUARIO };

        //Alumno
        public static final String TABLE_NAME_ALUMNO= "Alumno";
        public static final String KEY_ALUMNO = "idAlumno";
        public static final String NOMBRES_ALUMNO = "nombre";
        public static final String APELLIDO_ALUMNO = "apellido";
        public static final String NOTA_ALUMNO = "nota";
        public static final String FECHA_ALUMNO= "fechaNacimiento";
        public static final String SECCION_ALUMNO = "idSeccion";
        public static final String[] COLUMNS_ALUMNO ={ KEY_ALUMNO, NOMBRES_ALUMNO,APELLIDO_ALUMNO,
                NOTA_ALUMNO,FECHA_ALUMNO,SECCION_ALUMNO };

        //Curso
        public static final String TABLE_NAME_CURSO= "Curso";
        public static final String KEY_CURSO = "idCurso";
        public static final String NOMBRES_CURSO = "nomCurso";
        public static final String ESTADO_CURSO = "estado";
        public static final String[] COLUMNS_CURSO ={ KEY_CURSO, NOMBRES_CURSO ,ESTADO_CURSO};

        //Alumno_Curso
        public static final String TABLE_NAME_ALUMNO_CURSO= "Alumno_Curso";
        public static final String KEY_ALUMNO_CURSO = "idAlumno";
        public static final String KEY_CURSO_ALUMNO = "idCurso";
        public static final String NOTA_ALUMNO_NOTA = "nota";
        public static final String[] COLUMNS_ALUMNO_CURSO ={ KEY_ALUMNO_CURSO, KEY_CURSO_ALUMNO ,NOTA_ALUMNO_NOTA};

        //Ciclo
        public static final String TABLE_NAME_CICLO= "Ciclo";
        public static final String KEY_CICLO = "idCiclo";
        public static final String NOMBRES_CICLO = "ciclo";
        public static final String[] COLUMNS_CICLO ={ KEY_CICLO, NOMBRES_CICLO };

        //Seccion
        public static final String TABLE_NAME_SECCION= "Seccion";
        public static final String KEY_SECCION = "idSeccion";
        public static final String NOMBRES_SECCION = "seccion";
        public static final String ESTADO_SECCION = "estado";
        public static final String[] COLUMNS_SECCION ={ KEY_SECCION, NOMBRES_SECCION,ESTADO_SECCION };

    private DataBaseHelper openHelper;
    private SQLiteDatabase database;


    public ProyectoData(Context context) {
        openHelper = new DataBaseHelper(context);
        database = openHelper.getWritableDatabase();
    }

}
