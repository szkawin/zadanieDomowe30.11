package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Controller {


    public Button wyloguj;
    public Button zaloguj;

    public TextField login;
    public TextField loginWstpce;
    public TextField rolaWStopce;
    public AnchorPane wprdanych;
    public DataController datacontroller;
    public AnchorPane tabStatystyki;
    public Tab tabDane;
    public Tab tabStaty;
    public PasswordField haslo;
    public StatisticsController statisticscontroller;
    public TabPane tabPane;


    public void handleZaloguj(ActionEvent actionEvent) {

        if ( "admin".equals(login.getText()) && login.getText().equals(haslo.getText())) {
            rolaWStopce.setText("administrator");
            loginWstpce.setText(login.getText());
            datacontroller.zalogujAdmina();
        }else if( "user".equals(login.getText()) && login.getText().equals(haslo.getText())) {
            rolaWStopce.setText("szaraczek");
            loginWstpce.setText(login.getText());
            datacontroller.zalogujSzaraka();
        }else{
            return;
        }

        wyloguj.setVisible(true);
        zaloguj.setVisible(false);
        wyloguj.requestFocus();
        tabStaty.setDisable(false);
        tabDane.setDisable(false);
    }

    public void handleWyloguj(ActionEvent actionEvent) {
        loginWstpce.setText("");
        rolaWStopce.setText("");
        zaloguj.setVisible(true);
        wyloguj.setVisible(false);
        tabStaty.setDisable(true);
        tabDane.setDisable(true);
    }

    //@FXML
    public void initialize() throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("data.fxml"));
            Parent pane = loader.load();
            datacontroller = loader.getController();
            datacontroller.setParentController(this);
            wprdanych.getChildren().clear();
            wprdanych.getChildren().add(pane);
            tabStaty.setDisable(true);
            tabDane.setDisable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("statystyki.fxml"));
            Parent pane = loader.load();
            statisticscontroller = loader.getController();
            tabStatystyki.getChildren().clear();
            tabStatystyki.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public void ustawStatystyki(float wiek, float wzrost, int ile){
        statisticscontroller.ustawStatystyki(wiek, wzrost, ile);
        tabPane.getSelectionModel().select(tabStaty);

    }
}
