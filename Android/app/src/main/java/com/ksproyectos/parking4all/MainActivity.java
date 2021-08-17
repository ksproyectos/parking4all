package com.ksproyectos.parking4all;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ksproyectos.parking4all.PrinterAdapters.Q2PrinterAdapter;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.PrinterService;
import com.ksproyectos.parking4all.Services.RepositoryService;
import com.ksproyectos.parking4all.core.Business.BusinessServiceProvider;
import com.ksproyectos.parking4all.core.Business.MainBusiness;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import com.ksproyectos.parking4all.core.Utils.DateUtils;

import org.openalpr.OpenALPR;
import org.openalpr.model.Candidate;
import org.openalpr.model.Result;
import org.openalpr.model.Results;
import org.openalpr.model.ResultsError;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends BaseActivity {

    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.ksproyectos.parking4all.android.datasync.provider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "com.ksproyectos.parking4all";
    // The account name
    public static final String ACCOUNT = "dummyaccount";
    // Instance fields
    Account mAccount;

    // Global variables
    // A content resolver for accessing the provider
    ContentResolver mResolver;

    // Sync interval constants
    public static final long SECONDS_PER_MINUTE = 20L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 1L;
    public static final long SYNC_INTERVAL =
            SYNC_INTERVAL_IN_MINUTES *
                    SECONDS_PER_MINUTE;


    private Button buttonBuscar;
    private EditText editTextBuscar;

    private TextView textViewPlaca;
    private TextView textViewEntrada;
    private TextView textViewTarifa;
    private TextView textViewTiempo;
    private TextView textViewValor;

    private Button buttonEntradaSalida;

    private Button buttonConsultar;

    private Context context;

    private MainBusiness mainBusiness;
    private File destination;
    private String ANDROID_DATA_DIR = "/data/data/com.ksproyectos.parking4all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main, "Parking4All");

        context = this;


        editTextBuscar = findViewById(R.id.editTextPlaca);
        textViewPlaca = findViewById(R.id.textViewPlaca);
        textViewEntrada = findViewById(R.id.textViewEntrada);
        textViewTarifa = findViewById(R.id.textViewTarifa);
        textViewTiempo = findViewById(R.id.textViewTiempo);
        textViewValor = findViewById(R.id.textViewValor);
        buttonEntradaSalida = findViewById(R.id.buttonEntradaSalida);
        buttonConsultar = findViewById(R.id.buttonConsultar);


        mainBusiness = new MainBusiness(new BusinessServiceProvider(
                PrinterService.getInstance(),
                RepositoryService.getInstance(),
                AuthService.getInstance()));

        buttonEntradaSalida.setOnClickListener(onClickEntradaSalida);
        buttonConsultar.setOnClickListener(onClickButtonConsultar);


        editTextBuscar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    consultarPlaca();
                    return true;
                }
                return false;
            }
        });

        editTextBuscar.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        iniciarServicioImpresion();

        mAccount = CreateSyncAccount(this);

        // Get the content resolver for your app
        mResolver = getContentResolver();
        /*
         * Turn on periodic syncing
         */
        ContentResolver.addPeriodicSync(
                mAccount,
                AUTHORITY,
                Bundle.EMPTY,
                SYNC_INTERVAL);

       /* // Pass the settings flags by inserting them in a bundle
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);

        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        /*
         * Request the sync for the default account, authority, and
         * manual sync settings
         */
        //ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);


        findViewById(R.id.buttonFoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });

    }

    private String obtenerPlacaDeFoto(File image){

            final String openAlprConfFile = ANDROID_DATA_DIR + File.separatorChar + "runtime_data" + File.separatorChar + "openalpr.conf";

            return OpenALPR.Factory.create(MainActivity.this, ANDROID_DATA_DIR).recognizeWithCountryRegionNConfig("us", "", image.getAbsolutePath(), openAlprConfFile, 10);
    }

    private void checkPermission() {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissions.isEmpty()) {
            Toast.makeText(this, "Storage access needed to manage the picture.", Toast.LENGTH_LONG).show();
            String[] params = permissions.toArray(new String[permissions.size()]);
            ActivityCompat.requestPermissions(this, params, 1);
        } else { // We already have permissions, so handle as normal
            takePicture();
        }
    }

    public void takePicture() {
        // Use a folder to store all results
        File folder = new File(Environment.getExternalStorageDirectory() + "/OpenALPR/");
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Generate the path for the next photo
        String name = dateToString(new Date(), "yyyy-MM-dd-hh-mm-ss");
        destination = new File(folder, name + ".jpg");

        Uri photoURI = FileProvider
                .getUriForFile(context, context.getApplicationContext()
                        .getPackageName() + ".fileprovider", destination);


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, 100);
    }

    public String dateToString(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());

        return df.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            final ProgressDialog progress = ProgressDialog.show(this, "Procesando", "Escaneando placa ...", true);
            final String openAlprConfFile = ANDROID_DATA_DIR + File.separatorChar + "runtime_data" + File.separatorChar + "openalpr.conf";
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;



            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    /*Bitmap original = BitmapFactory.decodeFile(destination.getAbsolutePath());
                    try (FileOutputStream out = new FileOutputStream(destination.getAbsolutePath())) {
                        original.compress(Bitmap.CompressFormat.JPEG, 20, out);
                        // PNG is a lossless format, the compression factor (100) is ignored
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/



                    String result = OpenALPR.Factory.create(MainActivity.this, ANDROID_DATA_DIR).recognizeWithCountryRegionNConfig("us", "ak", destination.getAbsolutePath(), openAlprConfFile, 10);

                    Log.d("OPEN ALPR", result);

                    try {
                        final Results results = new Gson().fromJson(result, Results.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (results == null || results.getResults() == null || results.getResults().size() == 0) {
                                    Toast.makeText(MainActivity.this, "No fue posible leer la placa de la imagen", Toast.LENGTH_LONG).show();
                                } else {

                                    for(Candidate candidate : results.getResults().get(0).getCandidates()){
                                        if(candidate.getPlate().matches("[A-Z]{3}[1-9]{3}")){
                                            editTextBuscar.setText(candidate.getPlate());
                                            break;
                                        }
                                    }

                                            // Trim confidence to two decimal places
                                            //+ " Confidence: " + String.format("%.2f", results.getResults().get(0).getConfidence()) + "%"
                                            // Convert processing time to seconds and trim to two decimal places
                                           // + " Processing time: " + String.format("%.2f", ((results.getProcessingTimeMs() / 1000.0) % 60)) + " seconds");
                                }
                            }
                        });

                    } catch (JsonSyntaxException exception) {
                        final ResultsError resultsError = new Gson().fromJson(result, ResultsError.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textViewEntrada.setText(resultsError.getMsg());
                            }
                        });
                    }

                    progress.dismiss();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:{
                Map<String, Integer> perms = new HashMap<>();
                // Initial
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for WRITE_EXTERNAL_STORAGE
                Boolean storage = perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
                if (storage) {
                    // permission was granted, yay!
                    takePicture();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Storage permission is needed to analyse the picture.", Toast.LENGTH_LONG).show();
                }
            }
            default:
                break;
        }
    }



    private void iniciarServicioImpresion(){
        PrinterService.getInstance().setPrinterAdapter(new Q2PrinterAdapter(this));
    }

    private void consultarPlaca(){
        /*Convierte el texto a Mayusculas */

        textViewPlaca.setText("");
        textViewEntrada.setText("");
        textViewTarifa.setText("");
        textViewTiempo.setText("");
        textViewValor.setText("");

        Date fechaSalida = new Date();

        if(mainBusiness.consultarPlaca(editTextBuscar.getText().toString())){

            mainBusiness.calcularTotal(fechaSalida);
            /* Actualiza la interfaz */
            textViewPlaca.setText(mainBusiness.getPlaca());
            textViewEntrada.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(mainBusiness.getFechaEntrada()));
            textViewTarifa.setText(mainBusiness.getTipoTarifa());
            textViewTiempo.setText(getTiempoText(
                    DateUtils.getDateDiffDays(mainBusiness.getFechaEntrada(), fechaSalida),
                    DateUtils.getDateDiffHours(mainBusiness.getFechaEntrada(), fechaSalida),
                    DateUtils.getDateDiffMinutes(mainBusiness.getFechaEntrada(), fechaSalida)));

            textViewValor.setText(mainBusiness.getValorTotal() + " Pesos");

        }else{
            textViewPlaca.setText("N/E");
            textViewEntrada.setText("");
            textViewTarifa.setText("");
            textViewTiempo.setText("");
            textViewValor.setText("");
        }


    }

    private View.OnClickListener onClickButtonConsultar = new View.OnClickListener() {
        public void onClick(View v) {
            consultarPlaca();
        }
    };

    private View.OnClickListener onClickEntradaSalida = new View.OnClickListener() {
        public void onClick(View v) {
            if (mainBusiness.consultarPlaca(editTextBuscar.getText().toString())) {

                try {
                    mainBusiness.registrarSalida();
                } catch (Parking4AllException ex) {
                    System.err.println("error ----");
                }

                /* Actualiza la interfaz */
                textViewPlaca.setText("");
                textViewEntrada.setText("");
                textViewTarifa.setText("");
                textViewTiempo.setText("");
                textViewValor.setText("");

                mainBusiness.imprimirTicketSalida();

            } else {


                List<String> options
                        = mainBusiness.getListaTarifasDisponibles();

                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Tarifa");


                CharSequence[] cs = options.toArray(new CharSequence[options.size()]);

                builder.setItems(cs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String tipoTarifa = "";

                        tipoTarifa = options.get(which);

                        mainBusiness.registrarEntrada(editTextBuscar.getText().toString(), tipoTarifa);
                        mainBusiness.imprimirTicketEntrada();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                /* Actualiza la interfaz */
                textViewPlaca.setText("");
                textViewEntrada.setText("");
                textViewTarifa.setText("");
                textViewTiempo.setText("");
                textViewValor.setText("");

            }

        }
    };

    private String getTiempoText(Long dias, Long horas, Long minutos) {
        String tiempo = "";

        if (dias.intValue() > 0) {
            tiempo = dias + " Dias ";
        }
        if (horas.intValue() > 0) {
            tiempo = tiempo + horas + " Horas ";
        }
        if (minutos.intValue() > 0) {
            tiempo = tiempo + minutos + " Minutos";
        }

        return tiempo;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.conectar_impresora:
                iniciarServicioImpresion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }

        return newAccount;
    }

}
