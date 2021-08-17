/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ksproyectos.parking4all.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.Query;
import com.ksproyectos.parking4all.DAO.Mysql.Entities.Usuarios;
import com.ksproyectos.parking4all.Services.AuthService;

/**
 * FXML Controller class
 *
 * @author kevin
 */
public class LoginController extends BaseController implements Initializable {

    /**
     * Initializes the controller class.
     */
        
    @FXML
    private TextField  textFieldUsername;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Button buttonLogin;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    private void loginAction(){
        Query query;
        query = entityManager.createQuery("SELECT u FROM Usuarios u WHERE u.username = :username AND u.password = :password");
        query.setParameter("username", textFieldUsername.getText());
        query.setParameter("password", new String(passwordFieldPassword.getText()));
        try{
            Usuarios usuario = (Usuarios)query.getSingleResult();
            
            if(usuario.getIdUsuario() == null){
        
            }else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/parking4allfx/Views/Main.fxml"));
                
                AuthService.getInstance().setUsuario(usuario);
                MainController main = new MainController();
               main.usuario = usuario;
                loader.setController(main);
                Parent root = loader.load();
                Scene scene = new Scene(root, 640, 370);
                Stage stage = new Stage();
                stage.setTitle("Parking4All 2.0");
                stage.setScene(scene);
                stage.show();
                Stage loginStage = (Stage) buttonLogin.getScene().getWindow();
                // do what you have to do
                loginStage.close();
            }
            
        }catch(Exception e){
           System.out.print(e);
        }
    }
    public void onButtonLoginAction(){
        loginAction();
    }
}
