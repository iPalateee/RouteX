package it.web.routex.fx;
import it.web.routex.bean.InformazioniPercorsoBean;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ResultFXController {

    /* COMPONENTI FXML */

    @FXML private Label startLabel;
    @FXML private Label endLabel;
    @FXML private Label cityLabel;
    @FXML private Label statusLabel;
    @FXML private Label changesLabel;
    @FXML private Label stationsLabel;
    @FXML private Label timeLabel;
    @FXML private Label totalStationsLabel;
    @FXML private Label soilLabel;

    @FXML private ListView<String> routeStepsList;
    @FXML private ListView<String> changesList;
    @FXML private ListView<String> interchangeList;

    /* INIZIALIZZAZIONE */

    @FXML
    public void initialize() {

        InformazioniPercorsoBean dto = ResultContext.getResult();
        String start = ResultContext.getStart();
        String end = ResultContext.getEnd();
        String city = ResultContext.getCity();
        String status = ResultContext.getStatus();

        if (dto == null) {
            SceneNavigator.switchTo("home.fxml");
            return;
        }

        /* DATI SINGOLI */


        startLabel.setText(start);
        endLabel.setText(end);
        cityLabel.setText(city);
        statusLabel.setText(status);
        changesLabel.setText(String.valueOf(dto.getCityLife().getNumeroCambi()));
        stationsLabel.setText(String.valueOf(dto.getNumeroStazioniUsate()));
        timeLabel.setText(dto.getMinutaggio() + " min");
        totalStationsLabel.setText(String.valueOf(dto.getCityLife().getNumeroStazioniTotali()));
        soilLabel.setText(String.format("%.2f %%", dto.getPercentualeStazioniUsate()));

        /* LISTE */

        routeStepsList.setItems(
                FXCollections.observableArrayList(dto.getCityLife().getPercorsiConNomi())
        );

        changesList.setItems(
                FXCollections.observableArrayList(dto.getCityLife().getSequenzeDiCambiamento())
        );

        interchangeList.setItems(
                FXCollections.observableArrayList(dto.getCityLife().getSequenzeNodiCruciali())
        );
    }

    /* NAVIGAZIONE */

    @FXML
    public void onBack() {
        ResultContext.clear();
        SceneNavigator.switchTo("search.fxml");
    }

    @FXML
    public void onHome() {
        ResultContext.clear();
        SceneNavigator.switchTo("home.fxml");
    }
}
