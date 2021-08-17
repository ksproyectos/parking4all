package com.ksproyectos.parking4all;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Usuarios;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.PrinterService;
import com.ksproyectos.parking4all.Services.RepositoryService;
import com.ksproyectos.parking4all.core.Business.BusinessServiceProvider;
import com.ksproyectos.parking4all.core.Business.UsuariosBusiness;
import com.ksproyectos.parking4all.core.Entities.IUsuarios;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

import java.util.ArrayList;
import java.util.List;

public class UsuariosListActivity extends AppCompatActivity {


    ListView listView;

    UsuariosListCustomAdapter listCustomAdapter = new UsuariosListCustomAdapter(this);

    UsuariosBusiness usuariosBusiness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_list);
        setTitle("Usuarios");

        usuariosBusiness = new UsuariosBusiness(new BusinessServiceProvider(
                PrinterService.getInstance(),
                RepositoryService.getInstance(),
                AuthService.getInstance()
        ));


        FloatingActionButton fabCrear = findViewById(R.id.fabCrear);

        Context _this = this;

        fabCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(_this, UsuariosCrearActivity.class);
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.listView);

        listCustomAdapter.setButtonEliminarListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuarios usuario = new Usuarios();
                usuario.setIdUsuario((Integer) v.getTag());

                try {
                    usuariosBusiness.eliminarUsuario(usuario);
                } catch (Parking4AllException e) {
                    e.printStackTrace();
                }

                refreshListViewDataSet();
            }
        });


        listView.setAdapter(listCustomAdapter);

    }

    @Override
    protected void onStart(){
        super.onStart();

        refreshListViewDataSet();

    }



    private void refreshListViewDataSet(){
        List<IUsuarios> list = null;
        try {
            list = usuariosBusiness.obtenerUsuarios();
        } catch (Parking4AllException e) {
            e.printStackTrace();
        }
        if(list == null){
            list = new ArrayList<>();
        }
        listCustomAdapter.getList().clear();
        listCustomAdapter.getList().addAll(list);

        listCustomAdapter.notifyDataSetChanged();
    }


}
