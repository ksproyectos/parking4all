package com.ksproyectos.parking4all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ksproyectos.parking4all.core.Entities.IMovimientos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MovimientosListCustomAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<IMovimientos> list = new ArrayList<>();

    private View.OnClickListener buttonEliminarListener;

    public View.OnClickListener getButtonEliminarListener() {
        return buttonEliminarListener;
    }

    public void setButtonEliminarListener(View.OnClickListener buttonEliminarListener) {
        this.buttonEliminarListener = buttonEliminarListener;
    }

    public ArrayList<IMovimientos> getList() {
            return list;
    }

    public MovimientosListCustomAdapter(Context context) {

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
        convertView = inflater.inflate(R.layout.activity_movimientos_list_item, null, true);


        holder.textViewPlaca = (TextView) convertView.findViewById(R.id.textViewPlaca);
        holder.textViewFechaEntrada = (TextView) convertView.findViewById(R.id.textViewFechaEntrada);
        holder.textViewFechaSalida = (TextView) convertView.findViewById(R.id.textViewFechaSalida);
        holder.textViewTipoTarifa = (TextView) convertView.findViewById(R.id.textViewTipoTarifa);
        holder.textViewValorPagado = (TextView) convertView.findViewById(R.id.textViewValorPagado);

        holder.buttonEliminar = (Button) convertView.findViewById(R.id.buttonEliminar);

        convertView.setTag(holder);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        IMovimientos movimiento = this.list.get(position);

        holder.textViewPlaca.setText("Placa: " + movimiento.getPlaca());
        holder.textViewFechaEntrada.setText("Fecha entrada: " + df.format(movimiento.getFechaEntrada()));
        holder.textViewFechaSalida.setText("Fecha salida: " + (null == movimiento.getFechaSalida()?"":df.format(movimiento.getFechaSalida())));
        holder.textViewTipoTarifa.setText("Tipo tarifa: " + movimiento.getTipoTarifa());
        holder.textViewValorPagado.setText("Valor pagado: " + (null == movimiento.getValorTotal()?"0":movimiento.getValorTotal()) );

        holder.buttonEliminar.setTag(movimiento.getIdMovimiento());

        holder.buttonEliminar.setOnClickListener(this.buttonEliminarListener);

        return convertView;
    }

    private class ViewHolder {

        protected Button buttonEliminar;
        private TextView textViewPlaca, textViewFechaEntrada, textViewFechaSalida, textViewTipoTarifa, textViewValorPagado;

    }

}