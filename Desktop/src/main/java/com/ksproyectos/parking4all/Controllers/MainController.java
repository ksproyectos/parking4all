package com.ksproyectos.parking4all.Controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.ksproyectos.parking4all.DAO.Mysql.Entities.Tarifas;
import com.ksproyectos.parking4all.DAO.Mysql.Entities.Usuarios;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.PrinterService;
import com.ksproyectos.parking4all.Services.RepositoryService;
import com.ksproyectos.parking4all.core.Business.BusinessServiceProvider;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import com.ksproyectos.parking4all.core.Entities.ITarifas;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import com.ksproyectos.parking4all.core.Business.MainBusiness;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import com.ksproyectos.parking4all.core.Utils.DateUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class MainController implements Initializable {

    @FXML
    private Label LabelPlaca;
    @FXML
    private Label LabelEntrada;
    @FXML
    private Label LabelTiempo;
    @FXML
    private Label LabelTarifa;
    @FXML
    private Label LabelValor;
    @FXML
    private TextField TextFieldPlaca;

    public Usuarios usuario;
    
    private MainBusiness mainBusiness;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LabelEntrada.setText("*");
        LabelTiempo.setText("*");
        LabelTarifa.setText("*");
        LabelValor.setText("*");
        
        
        
        mainBusiness = new MainBusiness(
            new BusinessServiceProvider(
                    PrinterService.getInstance()
                    , RepositoryService.getInstance()
                    , AuthService.getInstance())
        );
    }

    public void onButtonEntradaSalidaAction() {
        
        if(TextFieldPlaca.getText().length() < 6){
            return;
        }

        if (mainBusiness.consultarPlaca(TextFieldPlaca.getText())) {
               
            try {
                mainBusiness.registrarSalida();
            } catch (Parking4AllException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

            /* Actualiza la interfaz */
            LabelPlaca.setText("Ingrese una Placa");
            LabelEntrada.setText("");
            LabelTarifa.setText("");
            LabelTiempo.setText("");
            LabelValor.setText("");

            mainBusiness.imprimirTicketSalida();
            
        } else {
                   
            ObservableList<String> options
                    = FXCollections.observableArrayList(mainBusiness.getListaTarifasDisponibles());

            ChoiceDialog<String> dialog = new ChoiceDialog<>("", options);
            dialog.setTitle("Seleccionar Tarifa");
            dialog.setHeaderText(null);
            dialog.setContentText("Seleccione la tarifa:");

            Optional<String> userResponse = dialog.showAndWait();
            /* Ingresar Movimiento con la Tarifa seleccionada */
            if (userResponse.isPresent()) {
                mainBusiness.registrarEntrada(TextFieldPlaca.getText(), userResponse.get());
                mainBusiness.imprimirTicketEntrada();
                TextFieldPlaca.setText("");

            }
        }

    }

    public void onButtonPrepagadaAction(){
        if(TextFieldPlaca.getText().length() < 6){
            return;
        }

        ObservableList<ITarifas> options
                = FXCollections.observableArrayList(mainBusiness.getListaTarifasPrepagadasActivas());

        if(null == options || options.size() == 0 ){
            return;
        }

        ChoiceDialog<ITarifas> dialog = new ChoiceDialog<ITarifas>(options.get(0), options);
        dialog.setTitle("Seleccionar Tarifa");
        dialog.setHeaderText(null);
        dialog.setContentText("Seleccione la tarifa:");

        Optional<ITarifas> userResponse = dialog.showAndWait();

        if (userResponse.isPresent()) {

            try {
                mainBusiness.registrarEntradaPrepagada(TextFieldPlaca.getText(), userResponse.get());
            } catch (Parking4AllException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            /* Actualiza la interfaz */
            LabelPlaca.setText("Ingrese una Placa");
            LabelEntrada.setText("");
            LabelTarifa.setText("");
            LabelTiempo.setText("");
            LabelValor.setText("");

            mainBusiness.imprimirTicketSalida();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Entrada prepagada");
            alert.setHeaderText("La entrada ha sido registrada con exito.");

            NumberFormat formatter = NumberFormat.getCurrencyInstance();


            String s = "El valor de esta entrada fue de " + formatter.format(userResponse.get().getTarifa());
            alert.setContentText(s);
            alert.show();

        }

    }

    @FXML
    public void onKeyReleasedPlaca(KeyEvent evt) {
        /*Convierte el texto a Mayusculas */
        int pos = TextFieldPlaca.getCaretPosition();
        TextFieldPlaca.setText(TextFieldPlaca.getText().toUpperCase());
        if(TextFieldPlaca.getText().length() > 7){
            TextFieldPlaca.setText(TextFieldPlaca.getText().substring(0, 7));
        }
        TextFieldPlaca.positionCaret(pos);        

        LabelPlaca.setText("Presione ENTER");
        LabelEntrada.setText("");
        LabelTarifa.setText("");
        LabelTiempo.setText("");
        LabelValor.setText("");
        /*Si se presiona enter y el texto tiene al menos 4 caracteres, ejecuta la busqueda */
        if (evt.getCode() == ENTER) {
            
            Date fechaSalida = new Date();
            
            if(mainBusiness.consultarPlaca(TextFieldPlaca.getText())){

                mainBusiness.calcularTotal(fechaSalida);
                /* Actualiza la interfaz */
                LabelPlaca.setText(mainBusiness.getPlaca());
                LabelEntrada.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(mainBusiness.getFechaEntrada()));
                LabelTarifa.setText(mainBusiness.getTipoTarifa());
                setLabelTiempoText(DateUtils.getDateDiffDays(mainBusiness.getFechaEntrada(), fechaSalida)
                        , DateUtils.getDateDiffHours(mainBusiness.getFechaEntrada(), fechaSalida)
                        , DateUtils.getDateDiffMinutes(mainBusiness.getFechaEntrada(), fechaSalida)
                );
                LabelValor.setText(mainBusiness.getValorTotal() + " Pesos");
            
            }else{
                LabelPlaca.setText("Placa no encontrada");
                LabelEntrada.setText("");
                LabelTarifa.setText("");
                LabelTiempo.setText("");
                LabelValor.setText("");
            }
            
        }

    }

    private void setLabelTiempoText(Long dias, Long horas, Long minutos) {
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

        LabelTiempo.setText(tiempo);
    }

    @FXML
    public void onButtonMovimientosAction() throws IOException {
        if(usuario.getAdministrador()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/parking4allfx/Views/Movimientos.fxml"));
            MovimientosController controller = new MovimientosController();
            controller.usuario = usuario;
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 1200, 600);
            stage.setTitle("Parking4All 2.0 - Mensualidades");
            stage.setScene(scene);
            stage.show();
        }else{
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Control de usuarios");
            alert.setHeaderText("No tiene permisos para ingresar a esta opcion");
            String s = "Solo los usuarios con permisos de administracion pueden ingresar a esta opcion";
            alert.setContentText(s);
            alert.show();
            
        }

    }

    @FXML
    public void onButtonMensualidadesAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/parking4allfx/Views/Mensualidades.fxml"));
        MensualidadesController controller = new MensualidadesController();
        controller.usuario = usuario;
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root, 640, 512);
        stage.setTitle("Parking4All 2.0 - Mensualidades");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onButtonTarifasAction() throws IOException {
        if(usuario.getAdministrador()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/parking4allfx/Views/Tarifas.fxml"));
            TarifasController controller = new TarifasController();
            controller.usuario = usuario;
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 750, 700);
            stage.setTitle("Parking4All 2.0 - Tarifas");
            stage.setScene(scene);
            stage.show();
        }else{
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Control de usuarios");
            alert.setHeaderText("No tiene permisos para ingresar a esta opcion");
            String s = "Solo los usuarios con permisos de administracion pueden ingresar a esta opcion";
            alert.setContentText(s);
            alert.show();
            
        }
    }

    @FXML
    public void onButtonUsuariosAction() throws IOException {
        if(usuario.getAdministrador()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/parking4allfx/Views/Usuarios.fxml"));
            UsuariosController controller = new UsuariosController();
            loader.setController(controller);
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 800, 468);
            stage.setTitle("Parking4All 2.0 - Usuarios");
            stage.setScene(scene);
            stage.show();
        }else{
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Control de usuarios");
            alert.setHeaderText("No tiene permisos para ingresar al editor de usuarios");
            String s = "Solo los usuarios con permisos de administracion pueden ingresar a esta opcion";
            alert.setContentText(s);
            alert.show();
            
        }
        
    }

    @FXML
    public void onButtonCajaAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/parking4allfx/Views/Caja.fxml"));
        CajaController controller = new CajaController();
        loader.setController(controller);
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root, 640, 430);
        stage.setTitle("Parking4All 2.0 - Caja");
        stage.setScene(scene);
        stage.show();
    }

}
