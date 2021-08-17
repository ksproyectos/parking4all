/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ksproyectos.parking4all.Controllers;

import com.ksproyectos.parking4all.DAO.Mysql.Entities.Caja;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.PrinterService;
import com.ksproyectos.parking4all.Services.RepositoryService;
import com.ksproyectos.parking4all.core.Business.BusinessServiceProvider;
import com.ksproyectos.parking4all.core.Business.CajaBusiness;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class CajaController extends BaseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private Caja caja;    
    @FXML
    private Label labelFechaApertura;
    @FXML
    private Label labelTotal;
    @FXML
    private Button buttonCerrarCaja;
    
    CajaBusiness cajaBusiness;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cajaBusiness = new CajaBusiness(
                new BusinessServiceProvider(
                        PrinterService.getInstance()
                        , RepositoryService.getInstance()
                , AuthService.getInstance()));
        
        try {
            cajaBusiness.consultarCaja();
        } catch (Parking4AllException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Atención");
            alert.setContentText(ex.getMessage());
        }
        
        labelFechaApertura.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(cajaBusiness.getFechaApertura()));
        labelTotal.setText(cajaBusiness.getTotal().toString());
    }
    
    public void onButtonCerrarCajaActionPerformed() {                                                 
        try{
            
            buttonCerrarCaja.setDisable(true);
            
            cajaBusiness.setOnCerrarCajaListener(new CajaBusiness.OnCerrarCajaListener() {
                @Override
                public void onCerrarCajaFailedPrint() {

                }

                @Override
                public void onCerrarCajaFailed(String s) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmar accion");
                    alert.setHeaderText("Hubo un error y no podemos imprimir el ticket de cierre de caja");
                    alert.setContentText("¿Desea continuar sin imprimir el ticket?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        cajaBusiness.cerrarCaja(false);
                    }
                }

                @Override
                public void onCerrarCajaSuccess() {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Atencion");
                    alert.setHeaderText("Resultado del cierre de caja");
                    alert.setContentText("Se ha realizado el cierre de caja exitosamente");

                    alert.show();
                }
            });
                       
            cajaBusiness.cerrarCaja(true);

        }catch(Exception e){
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Atencion");
            alert.setHeaderText("Resultado del cierre de caja");
            alert.setContentText("Upps!! No se pudo hacer el cierre de caja debido a un error desconocido.");
            
            alert.showAndWait();

        }
            
    }
}
