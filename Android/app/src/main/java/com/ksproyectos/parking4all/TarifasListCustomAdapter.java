package com.ksproyectos.parking4all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Tarifas;

import java.util.ArrayList;

public class TarifasListCustomAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<Tarifas> tarifasList = new ArrayList<>();

    private View.OnClickListener buttonEliminarListener;

    public View.OnClickListener getButtonEliminarListener() {
        return buttonEliminarListener;
    }

    public void setButtonEliminarListener(View.OnClickListener buttonEliminarListener) {
        this.buttonEliminarListener = buttonEliminarListener;
    }

    public ArrayList<Tarifas> getTarifasList() {
            return tarifasList;
    }

    public TarifasListCustomAdapter(Context context) {

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
        return this.tarifasList.size();
    }

    @Override
    public Object getItem(int position) {
        return tarifasList.get(position);
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
        convertView = inflater.inflate(R.layout.activity_tarifas_list_item, null, true);


        holder.textViewNombre = (TextView) convertView.findViewById(R.id.textViewNombre);
        holder.textViewTiempo = (TextView) convertView.findViewById(R.id.textViewTiempo);
        holder.textViewValor = (TextView) convertView.findViewById(R.id.textViewValor);
        holder.textViewTipoTarifa = (TextView) convertView.findViewById(R.id.textViewTipoTarifa);

        holder.buttonEliminar = (Button) convertView.findViewById(R.id.buttonEliminar);

        convertView.setTag(holder);


        Tarifas tarifa = this.tarifasList.get(position);

        holder.textViewNombre.setText(tarifa.getNombre());
        holder.textViewTipoTarifa.setText(tarifa.getTipoTarifa());
        holder.textViewValor.setText(String.valueOf(tarifa.getTarifa()));

        if(tarifa.getDias() != null){
            holder.textViewTiempo.setText(tarifa.getDias() + " Dias ");
        }
        if(tarifa.getHoras() != null){
            holder.textViewTiempo.setText(holder.textViewTiempo.getText().toString() + tarifa.getHoras() + " Horas " );
        }

        if(tarifa.getMinutos() != null){
            holder.textViewTiempo.setText(holder.textViewTiempo.getText().toString() + tarifa.getMinutos() + " Minutos" );
        }


        holder.buttonEliminar.setTag(tarifa.getIdTarifa());


        holder.buttonEliminar.setOnClickListener(this.buttonEliminarListener);

        return convertView;
    }

    private class ViewHolder {

        protected Button buttonEliminar;
        private TextView textViewNombre, textViewTiempo, textViewValor, textViewTipoTarifa;

    }

}