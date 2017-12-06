package sample;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.YEARS;

public class DataController {


    public TableView<Czlowieczek> table;
    public TextField textfieldWzrost;
    public TextField textfieldPESEL;
    public TextField textfieldSurname;
    public TextField textfieldName;
    public SplitPane dodawanieLudzika;
    public GridPane naprawdeDodawanieLudzika;
    public Button buttonDodaj;
    public Button wyliczStaty;

    public void handleDodaj(ActionEvent actionEvent) {

        try {

            Czlowieczek człowieczek = new Czlowieczek(
                    textfieldName.getText(),
                    textfieldSurname.getText(),
                    textfieldPESEL.getText(),
                    Integer.parseInt(textfieldWzrost.getText()));

            table.getItems().add(człowieczek);
        }
        catch(Exception e)
        {
            System.out.println("Oops!");
        }
    }

    public void wyliczacz(ActionEvent actionEvent) {
        float wiek = 0;
        float wzrost = 0;
        int ileLudkow = table.getItems().size();
        for (Czlowieczek cz : table.getItems()) {
            wiek += cz.wiek;
            wzrost += cz.wzrost;
        }

        if (ileLudkow != 0) {
            wiek = wiek / ileLudkow;
            wzrost = wzrost / ileLudkow;
        }


        parentController.ustawStatystyki(wiek, wzrost, ileLudkow);
    }

    public static class Czlowieczek {

        protected String name;
        protected Integer wiek;
        protected String nazwisko;
        protected String pesel;
        protected Integer wzrost;


        public Czlowieczek(String name, String nazwisko, String pesel, Integer wzrost) {
            this.name = name;
            this.nazwisko = nazwisko;
            this.wiek = wyliczWiek(pesel);
            this.pesel = pesel;
            this.wzrost = wzrost;

        }

        private static Integer wyliczWiek(String pesel) {
            String napispesel = pesel;
            int rok = Integer.parseInt(napispesel.substring(0,2));
            int miesiąc = Integer.parseInt(napispesel.substring(2,4));
            int dzień = Integer.parseInt(napispesel.substring(4,6));
            if (miesiąc > 80) {
                miesiąc = miesiąc-80;
                rok = rok+1800; // :)
            }else if(miesiąc > 60){
                miesiąc = miesiąc-60;
                rok = rok+2200;
            }else if(miesiąc > 40){
                miesiąc = miesiąc-40;
                rok = rok+2100;
            }else if(miesiąc > 20){
                miesiąc = miesiąc-20;
                rok = rok+2000;
            }else{
                miesiąc = miesiąc; //co jest, to jest; niemożliwe, aby coś było i nie było
                rok = rok+1900;
            }

            LocalDate dataUrodzenia = LocalDate.of(rok, miesiąc, dzień);
            LocalDate teraz = LocalDate.now();

            return (int) dataUrodzenia.until(teraz, YEARS);


        }


        public String getName() {
            return name;
        }

        public String getNazwisko() {return nazwisko;}

        public Integer getWiek() {
            return wiek;
        }

        public String getPesel() {return pesel;}

        public Integer getWzrost() {return wzrost;}




    }


    public void initialize() {
        for (TableColumn<Czlowieczek, ?> column : table.getColumns()) {
            if ("name".equals(column.getId())) {
                TableColumn<Czlowieczek, String> textColumn = (TableColumn<Czlowieczek, String>) column;
                textColumn.setCellValueFactory(new PropertyValueFactory<Czlowieczek, String>("name"));
            } else if ("pesel".equals(column.getId())) {
                TableColumn<Czlowieczek, String> lengthColumn = (TableColumn<Czlowieczek, String>) column;
                lengthColumn.setCellValueFactory(new PropertyValueFactory<Czlowieczek, String>("pesel"));
            } else if ("surname".equals(column.getId())) {
                TableColumn<Czlowieczek, String> textColumn = (TableColumn<Czlowieczek, String>) column;
                textColumn.setCellValueFactory(new PropertyValueFactory<Czlowieczek, String>("nazwisko"));
            } else if ("wzrost".equals(column.getId())) {
                TableColumn<Czlowieczek, Integer> textColumn = (TableColumn<Czlowieczek, Integer>) column;
                textColumn.setCellValueFactory(new PropertyValueFactory<Czlowieczek, Integer>("wzrost"));
            } else if ("wiek".equals(column.getId())) {
                TableColumn<Czlowieczek, Integer> textColumn = (TableColumn<Czlowieczek, Integer>) column;
                textColumn.setCellValueFactory(new PropertyValueFactory<Czlowieczek, Integer>("wiek"));
            } else if ("kasuj".equals(column.getId())) {
                TableColumn<Czlowieczek, Czlowieczek> kasujColumn = (TableColumn<Czlowieczek, Czlowieczek>) column;

                kasujColumn.setMinWidth(40);
                kasujColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                kasujColumn.setCellFactory(param -> new TableCell<Czlowieczek, Czlowieczek>() {
                    private final Button deleteButton = new Button("Kasuj");

                    @Override
                    protected void updateItem(Czlowieczek czlowieczek, boolean empty) {
                        super.updateItem(czlowieczek, empty);

                        if (czlowieczek == null) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                            deleteButton.setOnAction(event ->  table.getItems().remove(czlowieczek));
                        }
                    }
                });
            }
        }
        /*for (TableColumn<String, ?> column : table.getColumns()) {
            if ("napis".equals(column.getId())) {
                TableColumn<String, String> textColumn = (TableColumn<String, String>) column;
                textColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()));
            } else if ("dlugosc".equals(column.getId())) {
                TableColumn<String, String> lengthColumn = (TableColumn<String, String>) column;
                lengthColumn.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().length())));
            }
        }*/
    }

    private Controller parentController;

    public void setParentController(Controller parentController) {this.parentController = parentController;}

    public Controller getParentController() {return parentController;}



    public void zalogujSzaraka() {
        naprawdeDodawanieLudzika.setDisable(true);
        buttonDodaj.setDisable(true);
    }
    public void zalogujAdmina() {
        naprawdeDodawanieLudzika.setDisable(false);
        buttonDodaj.setDisable(false);
    }

}

