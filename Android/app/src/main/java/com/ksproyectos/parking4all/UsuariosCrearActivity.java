package com.ksproyectos.parking4all;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Usuarios;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.PrinterService;
import com.ksproyectos.parking4all.Services.RepositoryService;
import com.ksproyectos.parking4all.core.Business.BusinessServiceProvider;
import com.ksproyectos.parking4all.core.Business.UsuariosBusiness;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

public class UsuariosCrearActivity extends AppCompatActivity {

    private UsuariosBusiness usuaiosBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_crear);
        setTitle("Crear Usuarios");

        usuaiosBusiness = new UsuariosBusiness(new BusinessServiceProvider(
                PrinterService.getInstance(),
                RepositoryService.getInstance(),
                AuthService.getInstance()
        ));

        Button buttonAgregarUsuario = findViewById(R.id.buttonAgregarUsuario);

        EditText editTextNombre = findViewById(R.id.editTextPlaca);

        EditText editTextUsername = findViewById(R.id.editTextUsername);

        EditText editTextPassword = findViewById(R.id.editTextPassword);

        CheckBox checkBoxAdministrador = findViewById(R.id.checkBoxAdministrador);

        Context _this = this;

        buttonAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuarios usuario = new Usuarios();

                usuario.setNombre(editTextNombre.getText().toString());
                usuario.setUsername(editTextUsername.getText().toString());
                usuario.setPassword(editTextPassword.getText().toString());
                usuario.setAdministrador(checkBoxAdministrador.isChecked());
                usuario.setInactivo(false);

                try {
                    usuaiosBusiness.crearUsuario(usuario);
                } catch (Parking4AllException e) {
                    e.printStackTrace();
                }

                ((UsuariosCrearActivity) _this).finish();

            }
        });

    }

}
