/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ksproyectos.parking4all.Controllers;

import com.ksproyectos.parking4all.DAO.Mysql.Entities.Usuarios;
import com.ksproyectos.parking4all.DAO.Mysql.Entities.Movimientos;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class MovimientosController extends BaseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private Query movimientosQuery;
    private List movimientosList;
    private Query tiposTarifaQuery;
    private ObservableList tableData;
    
    public Usuarios usuario;
    
    @FXML
    private TableView tableViewMovimientos;
    @FXML
    private DatePicker datePickerFechaDesde;
    @FXML
    private DatePicker datePickerFechaHasta;
    @FXML
    private ComboBox comboBoxFilteTipoFecha;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        movimientosQuery = entityManager.createQuery("SELECT m FROM Movimientos m WHERE m.fechaEntrada BETWEEN :fechaLimite AND CURRENT_DATE");
        Calendar fechaLimite = Calendar.getInstance();
        fechaLimite.add(Calendar.DATE, -30);
        movimientosQuery.setParameter("fechaLimite", fechaLimite.getTime());
        movimientosList = movimientosQuery.getResultList();

        TableColumn idmovimientoCol = new TableColumn("Id");
        TableColumn placaCol = new TableColumn("Placa");
        TableColumn fechaentradaCol = new TableColumn("Fecha entrada");
        TableColumn fechasalidaCol = new TableColumn("Fecha salida");
        TableColumn tipotarifaCol = new TableColumn("Tipo tarifa");
        TableColumn usuariosalidaCol = new TableColumn("Usuario salida");
        TableColumn usuarioentradaCol = new TableColumn("Usuario entrada");
        TableColumn valortotalCol = new TableColumn("Valor total");

        idmovimientoCol.setCellValueFactory(new PropertyValueFactory<Movimientos, String>("idMovimiento")
        );
        placaCol.setCellValueFactory(new PropertyValueFactory<Movimientos, String>("Placa")
        );
        fechaentradaCol.setCellValueFactory(new PropertyValueFactory<Movimientos, String>("FechaEntrada")
        );
        fechasalidaCol.setCellValueFactory(new PropertyValueFactory<Movimientos, String>("FechaSalida")
        );
        tipotarifaCol.setCellValueFactory(new PropertyValueFactory<Movimientos, String>("TipoTarifa")
        );
        usuariosalidaCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimientos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimientos, String> param) {
                if(param.getValue().getFinalizado()){
                    return new SimpleObjectProperty<>(param.getValue().getUsuariosidUsuarioSalida().getNombre());
                }else{
                     return new SimpleObjectProperty<>("");
                }
            }
        });
        usuarioentradaCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimientos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimientos, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getUsuariosidUsuarioEntrada().getNombre());

            }
        });
        valortotalCol.setCellValueFactory(new PropertyValueFactory<Movimientos, String>("ValorTotal")
        );

        tableData = FXCollections.observableArrayList(movimientosList);

        tableViewMovimientos.setItems(tableData);
        tableViewMovimientos.getColumns().addAll(idmovimientoCol, placaCol, fechaentradaCol, usuarioentradaCol, fechasalidaCol, usuariosalidaCol, tipotarifaCol, valortotalCol);
        
        List<String> optionsList = new ArrayList<String>();
        optionsList.add("Entrada");
        optionsList.add("Salida");
        ObservableList<String> options
                = FXCollections.observableArrayList(optionsList);
        comboBoxFilteTipoFecha.setItems(options);

    }
    private void refreshComponents() {
        movimientosList = movimientosQuery.getResultList();
        tableData.removeAll(tableData);
        tableData.addAll(FXCollections.observableArrayList(movimientosList));
    }
    public void onButtonFiltrarActionPerformed() {                                              
        if(comboBoxFilteTipoFecha.getSelectionModel().getSelectedItem().toString().equals("Entrada")){
            movimientosQuery = entityManager.createQuery("SELECT m FROM Movimientos m WHERE m.fechaEntrada BETWEEN :fecha1 AND :fecha2 ");
        }else{
            movimientosQuery = entityManager.createQuery("SELECT m FROM Movimientos m WHERE m.fechaSalida BETWEEN :fecha1 AND :fecha2 ");
        }
        LocalDate localDate1 = datePickerFechaDesde.getValue();
        Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
        Date date1 = Date.from(instant1);
        
        LocalDate localDate2 = datePickerFechaHasta.getValue();
        Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
        Date date2 = Date.from(instant2);
        
        movimientosQuery.setParameter("fecha1", date1);
        movimientosQuery.setParameter("fecha2", date2);
        refreshComponents();
    }                                             

    public void onButtonEliminarMovimientoActionPerformed() {                                                         
        // TODO add your handling code here:
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        if(usuario.getAdministrador()){
           
            alert.setTitle("Confirmacion");
            alert.setHeaderText("");
            alert.setContentText("¿Desea eliminar el registro?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    entityManager.getTransaction().begin();
                    entityManager.remove(movimientosList.get(tableViewMovimientos.getSelectionModel().getSelectedIndex()));
                    entityManager.getTransaction().commit();

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
        else{
            alert2.setTitle("Informacion");
            alert2.setHeaderText(null);
            alert2.setContentText("Solo los administradores pueden eliminar registros");

            alert2.showAndWait();
        }
    }                                                        

}
