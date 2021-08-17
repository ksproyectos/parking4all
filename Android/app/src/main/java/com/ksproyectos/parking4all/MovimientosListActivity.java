package com.ksproyectos.parking4all;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ksproyectos.parking4all.DAO.SQL.Entities.Movimientos;
import com.ksproyectos.parking4all.DAO.SQL.Entities.Usuarios;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.PrinterService;
import com.ksproyectos.parking4all.Services.RepositoryService;
import com.ksproyectos.parking4all.core.Business.BusinessServiceProvider;
import com.ksproyectos.parking4all.core.Business.MainBusiness;
import com.ksproyectos.parking4all.core.Business.MovimientosBusiness;
import com.ksproyectos.parking4all.core.Entities.IMovimientos;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovimientosListActivity extends AppCompatActivity {


    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();
    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    ListView listView;
    MovimientosListCustomAdapter listCustomAdapter = new MovimientosListCustomAdapter(this);
    MovimientosBusiness movimientosBusiness;
    private View popupInputDialogView;
    private EditText editTextFechaInicial;
    private EditText editTextFechaFinal;
    private Button buttonFiltrarPopup;
    private RadioGroup radioGroupTipoMovimiento;
    private List<IMovimientos> list;
    private EditText editTextHoraInicial;
    private EditText editTextHoraFinal;
    private MainBusiness mainBusiness;
    private ArrayList<CheckBox> checkBoxesTiposTarifa;
    private LinearLayout resumenCantidadLayout;
    private LinearLayout resumenTotalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos_list);
        setTitle("Movimientos");

        movimientosBusiness = new MovimientosBusiness(new BusinessServiceProvider(
                PrinterService.getInstance(),
                RepositoryService.getInstance(),
                AuthService.getInstance()
        ));

        mainBusiness = new MainBusiness(new BusinessServiceProvider(
                PrinterService.getInstance(),
                RepositoryService.getInstance(),
                AuthService.getInstance()));


        FloatingActionButton fabCrear = findViewById(R.id.fabCrear);

        Context _this = this;


        listView = findViewById(R.id.listView);

        listCustomAdapter.setButtonEliminarListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Movimientos movimiento = new Movimientos();
                movimiento.setIdMovimiento((Integer) v.getTag());

                try {
                    movimientosBusiness.eliminarMovimiento(movimiento);
                } catch (Parking4AllException e) {
                    e.printStackTrace();
                }

                refreshListViewDataSet();
            }
        });

        listView.setAdapter(listCustomAdapter);

        resumenCantidadLayout = findViewById(R.id.resumenCantidadLayout);

        resumenTotalLayout = findViewById(R.id.resumenTotalLayout);


        Button buttonFiltrar = findViewById(R.id.buttonFiltrar);

        buttonFiltrar.setOnClickListener(v -> {


            // Create a AlertDialog Builder.
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MovimientosListActivity.this);
            // Set title, icon, can not cancel properties.
            alertDialogBuilder.setTitle("Filtro movimientos");
            alertDialogBuilder.setIcon(R.drawable.ic_launcher_background);
            alertDialogBuilder.setCancelable(false);

            // Init popup dialog view and it's ui controls.
            initPopupViewControls();

            // Set the inflated layout view object to the AlertDialog builder.
            alertDialogBuilder.setView(popupInputDialogView);

            // Create AlertDialog and show.
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            buttonFiltrarPopup.setOnClickListener(v1 -> {

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                Date fechaInicial = new Date();
                Date fechaFinal = new Date();
                MovimientosBusiness.TipoMovimiento tipoMovimiento = MovimientosBusiness.TipoMovimiento.ENTRADA;
                try {
                    fechaInicial = df.parse(editTextFechaInicial.getText().toString().trim() + " " + editTextHoraInicial.getText().toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                    alertDialog.cancel();

                    AlertDialog.Builder alert = new AlertDialog.Builder(MovimientosListActivity.this);
                    // Set title, icon, can not cancel properties.
                    alert.setTitle("Atencion");
                    alert.setMessage("La fecha inicial debe estar en formato dd/MM/yyyy HH:mm:ss");
                    alert.show();

                    return;
                }

                try {
                    fechaFinal = df.parse(editTextFechaFinal.getText().toString().trim() + " " + editTextHoraFinal.getText().toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                    alertDialog.cancel();

                    AlertDialog.Builder alert = new AlertDialog.Builder(MovimientosListActivity.this);
                    // Set title, icon, can not cancel properties.
                    alert.setTitle("Atencion");
                    alert.setMessage("La fecha inicial debe estar en formato dd/MM/yyyy HH:mm:ss");
                    alert.show();
                    return;
                }

                switch (radioGroupTipoMovimiento.getCheckedRadioButtonId()) {
                    case R.id.radioButtonEntrada:
                        tipoMovimiento = MovimientosBusiness.TipoMovimiento.ENTRADA;
                        break;
                    case R.id.radioButtonSalida:
                        tipoMovimiento = MovimientosBusiness.TipoMovimiento.SALIDA;
                        break;

                    default:

                        alertDialog.cancel();

                        AlertDialog.Builder alert = new AlertDialog.Builder(MovimientosListActivity.this);
                        // Set title, icon, can not cancel properties.
                        alert.setTitle("Atencion");
                        alert.setMessage("Debe seleccionar Entrada o Salida");
                        alert.show();

                        return;
                }

                List<String> selectedOptionsTiposTarifa = new ArrayList<>();

                for(CheckBox cb : checkBoxesTiposTarifa){
                    if(cb.isSelected()){
                        selectedOptionsTiposTarifa.add(cb.getText().toString());
                    }
                }

                try {
                    list = movimientosBusiness.obtenerMovimientosPorRangoFecha(fechaInicial, fechaFinal, selectedOptionsTiposTarifa, tipoMovimiento);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        Map<String, List<IMovimientos>> groupedList = null;

                        groupedList = list.stream()
                                .collect(
                                        Collectors.groupingBy(
                                                IMovimientos::getTipoTarifa));


                        groupedList.forEach((tipoTarifa, movimientos) -> {
                            TextView tvCantidad = new TextView(_this);
                            tvCantidad.setText(tipoTarifa + ": " + movimientos.size());
                            resumenCantidadLayout.addView(tvCantidad);

                            TextView tvTotal = new TextView(_this);
                            tvCantidad.setText(tipoTarifa + ": " + movimientos.stream()
                                    .map(x -> x.getValorTotal())
                                    .reduce(0, Integer::sum));
                            resumenTotalLayout.addView(tvCantidad);

                        });
                    }else{

                        Map<String, Integer>  cantidadPorTipoTarifa = new HashMap<>();
                        Map<String, Integer>  valorTotalPorTipoTarifa = new HashMap<>();


                        for(IMovimientos movimiento : list){

                            cantidadPorTipoTarifa.put(movimiento.getTipoTarifa(),
                                    cantidadPorTipoTarifa.containsKey(movimiento.getTipoTarifa())?
                                            cantidadPorTipoTarifa.get(movimiento.getTipoTarifa()) + 1: 1
                                     );


                            valorTotalPorTipoTarifa.put(movimiento.getTipoTarifa(),
                                    valorTotalPorTipoTarifa.containsKey(movimiento.getTipoTarifa())?
                                    valorTotalPorTipoTarifa.get(movimiento.getTipoTarifa()) + (null != movimiento.getValorTotal()?movimiento.getValorTotal():0):(null != movimiento.getValorTotal()?movimiento.getValorTotal():0));

                        }


                        resumenTotalLayout.removeAllViews();
                        resumenCantidadLayout.removeAllViews();

                        for (Map.Entry<String, Integer> entry : cantidadPorTipoTarifa.entrySet()) {

                            TextView tvCantidad = new TextView(_this);
                            tvCantidad.setText(entry.getKey() + ": " + entry.getValue());
                            resumenCantidadLayout.addView(tvCantidad);

                            TextView tvTotal = new TextView(_this);
                            tvTotal.setText(entry.getKey()  + ": " + valorTotalPorTipoTarifa.get(entry.getKey()));
                            resumenTotalLayout.addView(tvTotal);

                        }

                    }

                } catch (Parking4AllException e) {
                    e.printStackTrace();
                }
                refreshListViewDataSet();




                alertDialog.cancel();

            });


        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        refreshListViewDataSet();

    }

    private void refreshListViewDataSet() {
        if (list == null) {
            list = new ArrayList<>();
        }
        listCustomAdapter.getList().clear();
        listCustomAdapter.getList().addAll(list);

        listCustomAdapter.notifyDataSetChanged();
    }

    private void initPopupViewControls() {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(MovimientosListActivity.this);

        // Inflate the popup dialog from a layout xml file.
        popupInputDialogView = layoutInflater.inflate(R.layout.popup_movimientos_filters, null);

        // Get user input edittext and button ui controls in the popup dialog.
        editTextFechaInicial = (EditText) popupInputDialogView.findViewById(R.id.editTextFechaInicial);
        editTextFechaFinal = (EditText) popupInputDialogView.findViewById(R.id.editTextFechaFinal);

        editTextHoraInicial = (EditText) popupInputDialogView.findViewById(R.id.editTextHoraInicial);
        editTextHoraFinal = (EditText) popupInputDialogView.findViewById(R.id.editTextHoraFinal);

        radioGroupTipoMovimiento = (RadioGroup) popupInputDialogView.findViewById(R.id.radioGroupTipoMovimiento);

        buttonFiltrarPopup = (Button) popupInputDialogView.findViewById(R.id.buttonFiltrar);

        LinearLayout linearLayout = (LinearLayout) popupInputDialogView.findViewById(R.id.linearLayout);

        List<String> options
                = mainBusiness.getListaTarifasDisponibles();

        checkBoxesTiposTarifa = new ArrayList<>();


        for(String option : options){
            CheckBox cb = new CheckBox(layoutInflater.getContext());
            cb.setText(option);
            checkBoxesTiposTarifa.add(cb);
            linearLayout.addView(cb);
        }


        editTextFechaInicial.setOnClickListener(v -> {
            obtenerFecha(editTextFechaInicial);
        });

        editTextFechaFinal.setOnClickListener(v -> {
            obtenerFecha(editTextFechaFinal);
        });

        editTextHoraInicial.setOnClickListener(v -> {
            obtenerHora(editTextHoraInicial);
        });

        editTextHoraFinal.setOnClickListener(v -> {
            obtenerHora(editTextHoraFinal);
        });
    }

    private void obtenerFecha(EditText editText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                editText.setText(df.format(calendar.getTime()));

            }
        }, anio, mes, dia);

        datePickerDialog.show();

    }

    private void obtenerHora(EditText editText) {
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                editText.setText(hourOfDay + ":" + minute + ":" + "00");
            }
        }, hora, minuto, false);

        recogerHora.show();
    }


}
