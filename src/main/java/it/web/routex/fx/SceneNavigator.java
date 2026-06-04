package it.web.routex.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SceneNavigator {

    private static final Logger logger = LoggerFactory.getLogger(SceneNavigator.class);

    private static Stage stage;

    private SceneNavigator(){

    }

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void switchTo(String fxml)
    {
        try {
            FXMLLoader loader =
                    new FXMLLoader(SceneNavigator.class.getResource(fxml));
            stage.setScene(new Scene(loader.load(), 1200, 800));
        } catch (Exception e) {
            logger.error("Errore durante il cambio scena", e);
        }
    }
}
