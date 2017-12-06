package sample;

import javafx.scene.control.TextArea;

public class StatisticsController {


    public TextArea pole;

    public void ustawStatystyki(float wiek, float wzrost, int ile) {
        StringBuffer napisDoWydania = new StringBuffer();
        napisDoWydania.append("Średni wiek: ");
        napisDoWydania.append(wiek);
        napisDoWydania.append("\n");
        napisDoWydania.append("Średni wzrost: ");
        napisDoWydania.append(wzrost);
        napisDoWydania.append("\n");
        napisDoWydania.append("Ilu ludzi: ");
        napisDoWydania.append(ile);
        pole.setText(napisDoWydania.toString());
    }

    public void initialize() {
        pole.setText("Brak statystyk");
        pole.setEditable(false);

    }


}
