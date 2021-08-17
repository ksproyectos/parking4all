package com.ksproyectos.parking4all;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ksproyectos.parking4all.DAO.SQL.Parking4AllDatabase;
import com.ksproyectos.parking4all.DAO.SQL.AppDatabaseSingleton;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Configuraciones;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Usuarios;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.LicenciaService;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_DEVICE_STATE_PERMISION = 1;
    Button buttonLogin;
    EditText editTextUsername;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    1);
        }

        buttonLogin = findViewById(R.id.buttonLogin);

        editTextUsername =findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        validarLicencia();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autenticarUsuario();
            }
        });

        Usuarios usuarioSoporte = new Usuarios();
        usuarioSoporte.setIdUsuario(1);
        usuarioSoporte.setNombre("Kevin Suaza");
        usuarioSoporte.setUsername("support");
        usuarioSoporte.setPassword("support");
        usuarioSoporte.setAdministrador(true);
        usuarioSoporte.setInactivo(false);

        AppDatabaseSingleton.getInstance().getAppDatabase(getApplicationContext()).usuariosRoomDAO().add(usuarioSoporte);

    }
    private void validarLicencia(){

        if(!LicenciaService.comprobarLicencia(this)){

            final String[] licenciaNueva = {""};

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Atencion");
            builder.setMessage("La licencia no es valida, para obtener una licencia valida, contáctese con nosotros a la linea única de atención 300-233-62-35 o al email kevinsuazaproyectos@gmail.com y proporcione el siguiete codigo: " + LicenciaService.getID(this));


            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    licenciaNueva[0] = input.getText().toString();

                    Configuraciones configuracion = new Configuraciones();
                    configuracion.setNombre("Licencia");
                    configuracion.setValor(licenciaNueva[0]);
                    configuracion.setIdConfiguracion(1);
                    AppDatabaseSingleton.getInstance().getAppDatabase().customQueriesRoomDAO().add(configuracion);

                    validarLicencia();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    throw new RuntimeException("Licencia invalida");

                }
            });

            android.app.AlertDialog dialog = builder.create();

            dialog.show();

        };
    }
    private void autenticarUsuario(){
        Context _this = this;
        Usuarios usuario = AppDatabaseSingleton
                .getInstance()
                .getAppDatabase()
                .usuariosRoomDAO()
                .getUsuarioByUsernameAndPassword(
                        editTextUsername.getText().toString()
                        ,editTextPassword.getText().toString());

        if(usuario != null && LicenciaService.comprobarLicencia(this)){

            AuthService.getInstance().setUsuario(usuario);

            Intent intent = new Intent(_this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            startActivity(intent);
            finish();

        }else{
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(_this);

            dlgAlert.setMessage("Accesos incorrectos");
            dlgAlert.setTitle("Atencion");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }
    }


}
