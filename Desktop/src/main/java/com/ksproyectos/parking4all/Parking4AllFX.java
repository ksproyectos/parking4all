package com.ksproyectos.parking4all;

import com.ksproyectos.parking4all.Controllers.LoginController;
import com.ksproyectos.parking4all.Services.LicenciaService;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author kevin
 */

public class Parking4AllFX extends Application {
    
    private final static Logger LOGGER = Logger.getLogger(Parking4AllFX.class);    
    
    ResourceBundle bundle = ResourceBundle.getBundle("com.ksproyectos.parking4all.i18n.messages");
    
    @Override
    public void start(Stage primaryStage) throws IOException {
                
        LOGGER.log(Level.INFO, "Validando licencia...");
        
        boolean isValidLicence =  LicenciaService.comprobarLicencia();
        
        if (isValidLicence) {
            
            LOGGER.log(Level.INFO, "Licencia validada correctamente");
            
            LOGGER.log(Level.DEBUG, "Iniciando pantalla de login...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/parking4allfx/Views/Login.fxml"));
            LoginController controller = new LoginController();
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 370);
            Stage stage = new Stage();
            stage.setTitle(bundle.getString("application.name"));
            stage.setScene(scene);
            stage.show();
            
            LOGGER.log(Level.DEBUG, "Finaliza proceso para iniciar pantalla de login");
            
        }else{
            
            LOGGER.log(Level.DEBUG, "Licencia invalida, inicia proceso para mostrar pantalla de licencia ");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/parking4allfx/Views/Licencia.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 370);
            Stage stage = new Stage();
            stage.setTitle(bundle.getString("application.name"));
            stage.setScene(scene);
            stage.show();
            
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        launch(args);
    }
    
}
