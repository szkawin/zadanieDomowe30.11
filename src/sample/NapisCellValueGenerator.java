package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

/**
 * Created by pwilkin on 09-Nov-17.
 */
public class NapisCellValueGenerator implements Callback<CellDataFeatures<String, String>, ObservableValue<String>> {

    @Override
    public ObservableValue<String> call(CellDataFeatures<String, String> param) {
        return new SimpleStringProperty(param.getValue());
    }
}
