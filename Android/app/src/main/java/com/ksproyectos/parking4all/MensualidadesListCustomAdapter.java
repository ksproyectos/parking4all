package com.ksproyectos.parking4all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ksproyectos.parking4all.core.Entities.IMensualidades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MensualidadesListCustomAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<IMensualidades> list = new ArrayList<>();

    private View.OnClickListener buttonEliminarListener;

    public View.OnClickListener getButtonEliminarListener() {
        return buttonEliminarListener;
    }

    public void setButtonEliminarListener(View.OnClickListener buttonEliminarListener) {
        this.buttonEliminarListener = buttonEliminarListener;
    }

    public ArrayList<IMensualidades> getList() {
            return list;
    }

    public MensualidadesListCustomAdapter(Context context) {

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
        convertView = inflater.inflate(R.layout.activity_mensualidades_list_item, null, true);


        holder.textViewPlaca = (TextView) convertView.findViewById(R.id.textViewPlaca);
        holder.textViewFechaPago = (TextView) convertView.findViewById(R.id.textViewFechaPago);
        holder.textViewValorTotal = (TextView) convertView.findViewById(R.id.textViewValorTotal);
        holder.textViewNombres = (TextView) convertView.findViewById(R.id.textViewNombres);
        holder.textViewNumeroDocumento = (TextView) convertView.findViewById(R.id.textViewNumeroDocumento);
        holder.textViewTelefono = (TextView) convertView.findViewById(R.id.textViewTelefono);



        holder.buttonEliminar = (Button) convertView.findViewById(R.id.buttonEliminar);

        convertView.setTag(holder);

        IMensualidades mensualidad = this.list.get(position);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        holder.textViewPlaca.setText("Placa: " + mensualidad.getPlaca());
        holder.textViewFechaPago.setText("Fecha Pago: " + df.format(mensualidad.getFechaPago()));
        holder.textViewValorTotal.setText("Valor Total: " + mensualidad.getValorTotal());
        holder.textViewNombres.setText("Nombres: " + mensualidad.getNombres());
        holder.textViewNumeroDocumento.setText("Numero Documento: " + mensualidad.getNumeroDocumento());
        holder.textViewTelefono.setText("Telefono: " + mensualidad.getTelefono());

        holder.buttonEliminar.setTag(mensualidad.getIdMensualidad());

        holder.buttonEliminar.setOnClickListener(this.buttonEliminarListener);

        return convertView;
    }

    private class ViewHolder {

        protected Button buttonEliminar;
        private TextView textViewPlaca, textViewFechaPago, textViewValorTotal, textViewNombres, textViewNumeroDocumento, textViewTelefono;

    }

}