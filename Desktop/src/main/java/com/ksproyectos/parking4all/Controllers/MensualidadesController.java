/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ksproyectos.parking4all.Controllers;

import com.ksproyectos.parking4all.DAO.Mysql.Entities.Usuarios;
import com.ksproyectos.parking4all.DAO.Mysql.Entities.Mensualidades;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.PrinterService;
import com.ksproyectos.parking4all.Services.RepositoryService;
import com.ksproyectos.parking4all.core.Business.BusinessServiceProvider;
import com.ksproyectos.parking4all.core.Business.MensualidadesBusiness;
import com.ksproyectos.parking4all.core.Entities.IMensualidades;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import net.sf.jasperreports.engine.JasperReport;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class MensualidadesController extends BaseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public Usuarios usuario;

    @FXML
    private TableView tableViewMensualidades;
    @FXML
    private TextField textFieldNoDocumento;
    @FXML
    private TextField textFieldNombres;
    @FXML
    private TextField textFieldPlaca;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private ComboBox comboBoxTipoTarifa;

    private List mensualidadesList;

    ObservableList<String> tableData;
    
    MensualidadesBusiness mensualidadesBusiness;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          
        mensualidadesBusiness = new MensualidadesBusiness(
                new BusinessServiceProvider(
                        PrinterService.getInstance()
                        , RepositoryService.getInstance()
                ,AuthService.getInstance()));

        mensualidadesList = mensualidadesBusiness.obtenerMensualidadesActivas();
        
        TableColumn noDocumentoCol = new TableColumn("No Documento");
        TableColumn nombresCol = new TableColumn("Nombre");
        TableColumn telefonoCol = new TableColumn("Telefono");

        TableColumn placaCol = new TableColumn("Placa");
        TableColumn fechaPagoCol = new TableColumn("Fecha de pago");
        

        noDocumentoCol.setCellValueFactory(new PropertyValueFactory<Mensualidades, String>("NumeroDocumento")
        );
        nombresCol.setCellValueFactory(new PropertyValueFactory<Mensualidades, String>("Nombres")
        );
        telefonoCol.setCellValueFactory(new PropertyValueFactory<Mensualidades, String>("Telefono")
        );
        placaCol.setCellValueFactory(new PropertyValueFactory<Mensualidades, String>("Placa")
        );
        fechaPagoCol.setCellValueFactory(new PropertyValueFactory<Mensualidades, String>("FechaPago")
        );

        tableData = FXCollections.observableArrayList(mensualidadesList);

        tableViewMensualidades.setItems(tableData);
        tableViewMensualidades.getColumns().addAll(noDocumentoCol, nombresCol, telefonoCol, placaCol, fechaPagoCol);
        
        List tiposTarifaList = mensualidadesBusiness.getListaTarifasDisponibles();
        
            ObservableList<String> options
                = FXCollections.observableArrayList(tiposTarifaList);
        comboBoxTipoTarifa.setItems(options);
        
        if(options.size() == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Atencion");
            alert.setHeaderText("");
            alert.setContentText("No hay tarifas configuradas en el sistema");
            alert.showAndWait();
            
            return;
        }
        
        comboBoxTipoTarifa.getSelectionModel().select(0);
        
        refreshComponents();
        
    }

    private void refreshComponents() {
        mensualidadesList = mensualidadesBusiness.obtenerMensualidadesActivas();
        tableData.removeAll(tableData);
        tableData.addAll(FXCollections.observableArrayList(mensualidadesList));
        
        textFieldNoDocumento.setText("");
        textFieldNombres.setText("");
        textFieldPlaca.setText("");
        textFieldTelefono.setText("");
    }
    
    @FXML
    public void onKeyReleasedPlaca(KeyEvent evt) {
        int pos = textFieldPlaca.getCaretPosition();
        textFieldPlaca.setText(textFieldPlaca.getText().toUpperCase());
        if(textFieldPlaca.getText().length() > 7){
            textFieldPlaca.setText(textFieldPlaca.getText().substring(0, 7));
        }
        textFieldPlaca.positionCaret(pos);

    }
    public void buttonRegistrarMensualidadActionPerformed() {            
        try{
            mensualidadesBusiness.crearMensualidad(
                textFieldNombres.getText()
                ,textFieldNoDocumento.getText()
                ,textFieldPlaca.getText()
                ,textFieldTelefono.getText()
                ,comboBoxTipoTarifa.getSelectionModel().getSelectedItem().toString()
                );
            
            mensualidadesBusiness.imprimirTicketMensualidad();
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Atencion");
            alert.setHeaderText("");
            alert.setContentText(e.getMessage());
            alert.show();
            return;
        }

        refreshComponents();
        textFieldNoDocumento.setText("");
        textFieldNombres.setText("");
        textFieldPlaca.setText("");

    }

    public void buttonEliminarMensualidadActionPerformed() {
        if (usuario.getAdministrador()) {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmacion");
            alert.setHeaderText("");
            alert.setContentText("¿Desea eliminar el registro?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    mensualidadesBusiness.eliminarMensualidad((IMensualidades) mensualidadesList.get(tableViewMensualidades.getSelectionModel().getSelectedIndex()));
                } catch (Parking4AllException ex) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Atención");
                    errorAlert.setHeaderText("");
                    errorAlert.setContentText(ex.getMessage());
                    errorAlert.showAndWait();

                }
                refreshComponents();
            } else {

            }
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("Solo los administradores pueden eliminar registros");
            
            alert.showAndWait();
        }

    }

}
