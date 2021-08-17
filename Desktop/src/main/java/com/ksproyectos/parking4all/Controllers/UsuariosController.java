/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ksproyectos.parking4all.Controllers;

import com.ksproyectos.parking4all.DAO.Mysql.Entities.Usuarios;
import com.ksproyectos.parking4all.DAO.Mysql.Entities.Movimientos;
import com.ksproyectos.parking4all.Services.AuthService;
import com.ksproyectos.parking4all.Services.PrinterService;
import com.ksproyectos.parking4all.Services.RepositoryService;
import com.ksproyectos.parking4all.core.Business.BusinessServiceProvider;
import com.ksproyectos.parking4all.core.Business.UsuariosBusiness;
import com.ksproyectos.parking4all.core.Entities.IUsuarios;
import com.ksproyectos.parking4all.core.Exception.Parking4AllException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class UsuariosController extends BaseController implements Initializable {

    /**
     * Initializes the controller class.
     */

    private List usuariosList;
    private ObservableList tableData;

    @FXML
    private TableView tableViewUsuarios;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private CheckBox checkboxInactivo;
    @FXML
    private CheckBox checkboxAdministrador;

    @FXML
    private Button buttonNuevo;
    @FXML
    private Button buttonEditar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonGuardar;
    @FXML
    private Button buttonEliminar;
    private UsuariosBusiness usuariosBusiness;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        usuariosBusiness = new UsuariosBusiness(
                new BusinessServiceProvider(
                        PrinterService.getInstance(),
                        RepositoryService.getInstance(),
                        AuthService.getInstance()
                ));
        
        try {
            usuariosList = usuariosBusiness.obtenerUsuarios();
        } catch (Parking4AllException ex) {
           Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Atención");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());

            alert.showAndWait();  
        }

        TableColumn nombreCol = new TableColumn("Nombre");
        TableColumn usernameCol = new TableColumn("Username");
        TableColumn passwordCol = new TableColumn("Password");
        TableColumn administradorCol = new TableColumn("Administrador");
        TableColumn inactivoCol = new TableColumn("Inactivo");

        nombreCol.setCellValueFactory(new PropertyValueFactory<Usuarios, String>("Nombre")
        );
        usernameCol.setCellValueFactory(new PropertyValueFactory<Movimientos, String>("Username")
        );
        passwordCol.setCellValueFactory(new PropertyValueFactory<Movimientos, String>("Password")
        );
        administradorCol.setCellValueFactory(new PropertyValueFactory<Movimientos, Boolean>("Administrador")
        );
        inactivoCol.setCellValueFactory(new PropertyValueFactory<Movimientos, Boolean>("Inactivo")
        );

        tableData = FXCollections.observableArrayList(usuariosList);

        tableViewUsuarios.setItems(tableData);
        tableViewUsuarios.getColumns().addAll(nombreCol, usernameCol, passwordCol, administradorCol, inactivoCol);

        tableViewUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (tableViewUsuarios.getItems().size() > 0) {
                    buttonEliminar.setDisable(false);
                    buttonEditar.setDisable(false);
                }
            }
        });
        tableViewUsuarios.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuarios>() {
            @Override
            public void changed(ObservableValue<? extends Usuarios> observable, Usuarios oldValue, Usuarios newValue) {
                if (oldValue != null) {
                    textFieldNombre.setText(oldValue.getNombre());
                    textFieldUsername.setText(oldValue.getUsername());
                    textFieldPassword.setText(oldValue.getPassword());
                    checkboxAdministrador.setSelected(oldValue.getAdministrador());
                    checkboxInactivo.setSelected(oldValue.getInactivo());
                }
                if (newValue != null) {
                    textFieldNombre.setText(newValue.getNombre());
                    textFieldUsername.setText(newValue.getUsername());
                    textFieldPassword.setText(newValue.getPassword());
                    checkboxAdministrador.setSelected(newValue.getAdministrador());
                    checkboxInactivo.setSelected(newValue.getInactivo());
                }
            }
        });

    }

    private void refreshComponents() {
        try {
            usuariosList = usuariosBusiness.obtenerUsuarios();
        } catch (Parking4AllException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Atención");
            alert.setHeaderText(null);
            alert.setContentText(ex.getMessage());

            alert.showAndWait();  
        }
        tableData.removeAll(tableData);
        tableData.addAll(FXCollections.observableArrayList(usuariosList));
    }

    public void onButtonNuevoActionPerformed() {

        Usuarios usuario = new Usuarios();
        usuario.setAdministrador(false);
        usuario.setInactivo(false);
        usuariosList.add(usuario);

        tableData.removeAll(tableData);
        tableData.addAll(FXCollections.observableArrayList(usuariosList));
        tableViewUsuarios.getSelectionModel().selectLast();

        /*UX*/
        buttonNuevo.setDisable(true);
        buttonGuardar.setDisable(false);
        buttonEliminar.setDisable(true);
        buttonEditar.setDisable(true);
        buttonCancelar.setDisable(false);

        textFieldNombre.setDisable(false);
        textFieldUsername.setDisable(false);
        textFieldPassword.setDisable(false);
        checkboxAdministrador.setDisable(false);
        checkboxInactivo.setDisable(false);

        tableViewUsuarios.setDisable(true);
    }

    public void onButtonEditarActionPerformed() {
        // TODO add your handling code here:
        buttonNuevo.setDisable(true);
        buttonGuardar.setDisable(false);
        buttonEliminar.setDisable(true);
        buttonEditar.setDisable(true);
        buttonCancelar.setDisable(false);

        textFieldNombre.setDisable(false);
        textFieldUsername.setDisable(false);
        textFieldPassword.setDisable(false);
        checkboxAdministrador.setDisable(false);
        checkboxInactivo.setDisable(false);

        tableViewUsuarios.setDisable(true);
    }

    public void onButtonGuardarActionPerformed() {
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
        try {
            IUsuarios usuario = (IUsuarios) usuariosList.get(tableViewUsuarios.getSelectionModel().getSelectedIndex());   
            
            usuario.setNombre(textFieldNombre.getText());
            usuario.setUsername(textFieldUsername.getText());
            usuario.setPassword(textFieldPassword.getText());
            usuario.setAdministrador(checkboxAdministrador.isSelected());
            usuario.setInactivo(checkboxInactivo.isSelected());
            
            if (usuario.getIdUsuario() == null) {
                usuariosBusiness.crearUsuario(usuario);
            }else{
                usuariosBusiness.actualizarUsuario(usuario);
            }

            alert2.setTitle("Informacion");
            alert2.setHeaderText(null);
            alert2.setContentText("Se han guardado los cambios");

            alert2.showAndWait();

            refreshComponents();
            /*UX*/
            buttonNuevo.setDisable(false);
            buttonGuardar.setDisable(true);
            buttonEliminar.setDisable(false);
            buttonEditar.setDisable(false);
            buttonCancelar.setDisable(true);

            textFieldNombre.setDisable(true);
            textFieldUsername.setDisable(true);
            textFieldPassword.setDisable(true);
            checkboxAdministrador.setDisable(true);
            checkboxInactivo.setDisable(true);

            tableViewUsuarios.setDisable(false);

        } catch (Exception e) {
            alert2.setTitle("Informacion");
            alert2.setHeaderText(null);
            alert2.setContentText("No se guardaron los cambios");

        }
    }

    public void onButtonCancelarActionPerformed() {
        // TODO add your handling code here:
        if (((IUsuarios) usuariosList.get(usuariosList.size() - 1)).getIdUsuario() == null) {
            usuariosList.remove(usuariosList.size() - 1);
        }
        refreshComponents();

        buttonNuevo.setDisable(false);
        buttonGuardar.setDisable(true);
        buttonEliminar.setDisable(true);
        buttonEditar.setDisable(true);
        buttonCancelar.setDisable(true);

        textFieldNombre.setDisable(true);
        textFieldUsername.setDisable(true);
        textFieldPassword.setDisable(true);
        checkboxAdministrador.setDisable(true);
        checkboxInactivo.setDisable(true);

        tableViewUsuarios.setDisable(false);
    }

    public void onButtonEliminarActionPerformed() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText("");
        alert.setContentText("¿Desea eliminar el registro?");
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                
                usuariosBusiness.eliminarUsuario((IUsuarios) usuariosList.get(tableViewUsuarios.getSelectionModel().getSelectedIndex()));
                
                alert2.setTitle("Informacion");
                alert2.setHeaderText(null);
                alert2.setContentText("Eliminado correctamente");
                refreshComponents();

                /*UX*/
                buttonNuevo.setDisable(false);
                buttonGuardar.setDisable(true);
                buttonEliminar.setDisable(true);
                buttonEditar.setDisable(true);
                buttonCancelar.setDisable(true);

                textFieldNombre.setDisable(true);
                textFieldUsername.setDisable(true);
                textFieldPassword.setDisable(true);
                checkboxAdministrador.setDisable(true);
                checkboxInactivo.setDisable(true);

                tableViewUsuarios.setDisable(false);
            } catch (Exception e) {
                alert2.setTitle("Informacion");
                alert2.setHeaderText(null);
                alert2.setContentText("No se pudo eliminar el registro");
            }

        }
    }

}
