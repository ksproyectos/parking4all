package com.ksproyectos.parking4all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ksproyectos.parking4all.core.Entities.IUsuarios;

import java.util.ArrayList;

public class UsuariosListCustomAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<IUsuarios> list = new ArrayList<>();

    private View.OnClickListener buttonEliminarListener;

    public View.OnClickListener getButtonEliminarListener() {
        return buttonEliminarListener;
    }

    public void setButtonEliminarListener(View.OnClickListener buttonEliminarListener) {
        this.buttonEliminarListener = buttonEliminarListener;
    }

    public ArrayList<IUsuarios> getList() {
            return list;
    }

    public UsuariosListCustomAdapter(Context context) {

        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        if(getCount() > 0){
            return getCount();
        }else{
            return super.getViewTypeCount();
        }
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        holder = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.activity_usuarios_list_item, null, true);


        holder.textViewNombre = (TextView) convertView.findViewById(R.id.textViewNombre);
        holder.textViewUsername = (TextView) convertView.findViewById(R.id.textViewUsername);
        holder.textViewPassword = (TextView) convertView.findViewById(R.id.textViewPassword);
        holder.textViewAdministrador = (TextView) convertView.findViewById(R.id.textViewAdministrador);
        holder.textViewInactivo = (TextView) convertView.findViewById(R.id.textViewInactivo);


        holder.buttonEliminar = (Button) convertView.findViewById(R.id.buttonEliminar);

        convertView.setTag(holder);

        IUsuarios usuario = this.list.get(position);

        holder.textViewNombre.setText("Nombre: " + usuario.getNombre());
        holder.textViewUsername.setText("Username: " + usuario.getUsername());
        if(!usuario.getAdministrador()) {
            holder.textViewPassword.setText("Password: " + usuario.getPassword());
        }else{
            holder.textViewPassword.setText("Password: ********");
        }
        holder.textViewAdministrador.setText("Administrador: " + (usuario.getAdministrador()?"Si": "No"));
        holder.textViewInactivo.setText("Inactivo: " + (usuario.getInactivo()?"Si":"No"));

        holder.buttonEliminar.setTag(usuario.getIdUsuario());

        holder.buttonEliminar.setOnClickListener(this.buttonEliminarListener);

        return convertView;
    }

    private class ViewHolder {

        protected Button buttonEliminar;
        private TextView textViewNombre, textViewUsername, textViewPassword, textViewAdministrador, textViewInactivo;

    }

}