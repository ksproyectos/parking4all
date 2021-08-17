/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ksproyectos.parking4all.Controllers;

import com.ksproyectos.parking4all.DAO.Mysql.Entities.Usuarios;
import com.ksproyectos.parking4all.DAO.Mysql.Entities.Mensualidades;
import com.ksproyectos.parking4all.DAO.Mysql.Entities.Tarifas;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.PrinterService;
import com.ksproyectos.parking4all.Services.RepositoryService;
import com.ksproyectos.parking4all.core.Business.BusinessServiceProvider;
import com.ksproyectos.parking4all.core.Business.TarifasBusiness;
import com.ksproyectos.parking4all.core.Entities.ITarifas;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class TarifasController extends BaseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView tableViewTarifas;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private ComboBox<String> comboBoxTipoTarifa;
    @FXML
    private TextField textFieldTarifa;    
    @FXML
    private TextField textFieldTiempo; 
    @FXML
    private ChoiceBox<String> choiceBoxTipoTiempo;
    @FXML
    private ChoiceBox<String> choiceBoxIsPrepagada;

    public Usuarios usuario;

    private List tarifasList;

    ObservableList<String> tableData;
    
    ObservableList<String> observableTipoTarifa;

    private List tipoTarifaList;
    
    TarifasBusiness tarifasBusiness;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tarifasBusiness = new TarifasBusiness(
                new BusinessServiceProvider(
                        PrinterService.getInstance(),
                        RepositoryService.getInstance()
                , AuthService.getInstance()));
        
        
        try {
            tarifasList = tarifasBusiness.obtenerTarifas();
        } catch (Parking4AllException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Atención");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }

        TableColumn idTarifaCol = new TableColumn("Id Tarifa");
        TableColumn nombreCol = new TableColumn("Nombre");
        TableColumn diasCol = new TableColumn("Dias");
        TableColumn horasCol = new TableColumn("Horas");
        TableColumn minutosCol = new TableColumn("Minutos");
        TableColumn tipotarifaCol = new TableColumn("Tipo Tarifa");
        TableColumn tarifaCol = new TableColumn("Tarifa");

        TableColumn activaCol = new TableColumn("Activa");

        TableColumn prepagadaCol = new TableColumn("Prepagada");


        idTarifaCol.setCellValueFactory(new PropertyValueFactory<Tarifas, String>("idTarifa")
        );
        nombreCol.setCellValueFactory(new PropertyValueFactory<Tarifas, String>("Nombre")
        );
        diasCol.setCellValueFactory(new PropertyValueFactory<Tarifas, String>("Dias")
        );
        horasCol.setCellValueFactory(new PropertyValueFactory<Tarifas, String>("Horas")
        );
        minutosCol.setCellValueFactory(new PropertyValueFactory<Tarifas, String>("Minutos")
        );
        tipotarifaCol.setCellValueFactory(new PropertyValueFactory<Tarifas, String>("TipoTarifa")
        );
        tarifaCol.setCellValueFactory(new PropertyValueFactory<Tarifas, String>("Tarifa")
        );
        activaCol.setCellValueFactory(new PropertyValueFactory<Tarifas, String>("Activa")
        );
        prepagadaCol.setCellValueFactory(new PropertyValueFactory<Tarifas, String>("Prepagada")
        );

        tableData = FXCollections.observableArrayList(tarifasList);

        tableViewTarifas.setItems(tableData);
        tableViewTarifas.getColumns().addAll(idTarifaCol, nombreCol, diasCol, horasCol, minutosCol, tipotarifaCol, tarifaCol, activaCol, prepagadaCol);
        
        
        textFieldTarifa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                
            }
        });
        
        class NumberFilter implements ChangeListener<String>{
            
            TextField textfield;
            NumberFilter(TextField textfield){
                this.textfield = textfield;
            }
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    newValue = newValue.replaceAll("[^\\d]", "");
                }
                if("".equals(newValue)){
                    newValue = "0";
                }
                if("0".equals(newValue.substring(0,1)) && newValue.length() > 1){
                    newValue = newValue.substring(1);
                }
                textfield.setText(newValue);
            }
         }
        
        textFieldTiempo.textProperty().addListener(new NumberFilter(textFieldTiempo));
        choiceBoxTipoTiempo.setItems(FXCollections.observableArrayList(
            "Dias", "Horas", "Minutos")
        );

        choiceBoxIsPrepagada.setItems(FXCollections.observableArrayList(
                "Si", "No")
        );


        tipoTarifaList = tarifasBusiness.getListaTarifasDisponibles();
        observableTipoTarifa = FXCollections.observableArrayList(tipoTarifaList);
        comboBoxTipoTarifa.setItems(observableTipoTarifa);

    }
    
    private void refreshComponents() {
        try {
            tarifasList = tarifasBusiness.obtenerTarifas();
        } catch (Parking4AllException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Atención");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());

            alert.showAndWait();
        }
        tableData.removeAll(tableData);
        tableData.addAll(FXCollections.observableArrayList(tarifasList));
        
        tipoTarifaList = tarifasBusiness.getListaTarifasDisponibles();
        observableTipoTarifa.removeAll(observableTipoTarifa);
        observableTipoTarifa.addAll(FXCollections.observableArrayList(tipoTarifaList));
        
        
    }

    public void onButtonAgregarTarifaActionPerformed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        try {
            Tarifas tarifa = new Tarifas();
            tarifa.setNombre(textFieldNombre.getText());
            tarifa.setTarifa(Integer.valueOf(textFieldTarifa.getText().replaceAll("[^\\d]", "")));
            tarifa.setTipoTarifa(comboBoxTipoTarifa.getEditor().getText());
            
            switch(choiceBoxTipoTiempo.getSelectionModel().getSelectedItem()){
                case "Dias":
                    tarifa.setDias(Integer.valueOf(textFieldTiempo.getText()));
                    tarifa.setHoras(0);
                    tarifa.setMinutos(0);
                    break;
                case "Horas":
                    tarifa.setDias(0);
                    tarifa.setHoras(Integer.valueOf(textFieldTiempo.getText()));
                    tarifa.setMinutos(0);
                    break;
                case "Minutos":
                    tarifa.setDias(0);
                    tarifa.setHoras(0);
                    tarifa.setMinutos(Integer.valueOf(textFieldTiempo.getText()));
                    break;
            
            }

            tarifa.setPrepagada(choiceBoxIsPrepagada.getSelectionModel().getSelectedItem()=="Si"?true:false);

            tarifa.setActiva(true);
            
            tarifasBusiness.crearTarifa(tarifa);
            
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("Se han guardado los cambios");

            alert.showAndWait();

            refreshComponents();

        } catch (Exception e) {
            alert.setTitle("Informacion");
            alert.setHeaderText(null);
            alert.setContentText("No se pudieron guardar los cambios");

            alert.showAndWait();
        }
    }

    public void onButtonEliminarTarifaActionPerformed() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Confirmacion");
        alert.setHeaderText("");
        alert.setContentText("¿Desea eliminar el registro?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
               
                tarifasBusiness.eliminarTarifa(
                       (ITarifas) tarifasList.get(
                                tableViewTarifas.getSelectionModel().getSelectedIndex()
                        ));


                refreshComponents();

                alert2.setTitle("Informacion");
                alert2.setHeaderText(null);
                alert2.setContentText("Eliminado correctamente");

                alert2.showAndWait();
            } catch (Exception e) {
                alert2.setTitle("Informacion");
                alert2.setHeaderText(null);
                alert2.setContentText("No se pudo eliminar el registro");

                alert2.showAndWait();
            }

        }
    }

    public void onButtonActivarDesactivarTarifaActionPerformed(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Confirmacion");
        alert.setHeaderText("");
        alert.setContentText("¿Desea Activar/Desactivar la tarifa?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {

                ITarifas tarifa = (ITarifas) tarifasList.get(
                        tableViewTarifas.getSelectionModel().getSelectedIndex());

                tarifasBusiness.eliminarTarifa(tarifa);

                tarifa.setActiva(!tarifa.isActiva());

                tarifasBusiness.crearTarifa(tarifa);

                refreshComponents();

                alert2.setTitle("Informacion");
                alert2.setHeaderText(null);
                alert2.setContentText("Activado/Desactivado correctamente");

                alert2.showAndWait();
            } catch (Exception e) {
                alert2.setTitle("Informacion");
                alert2.setHeaderText(null);
                alert2.setContentText("No se pudo Activar/Desactivar la tarifa");

                alert2.showAndWait();
            }

        }
    }

}
