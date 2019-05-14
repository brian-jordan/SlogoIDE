package front_end.screens;

import back_end.Parser;
import back_end.command.Command;
import back_end.model_info.Model;
import front_end.RunSlogo;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class VariableScreen {
    public VariableScreen(Map<String, Double> variables, Model model) {
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Variables");
        stage.setWidth(300);
        stage.setHeight(500);

        final Label label = new Label("Your variables");
        label.setFont(RunSlogo.sofiaPro);

        TableColumn variableColumn = new TableColumn("Variable");
        variableColumn.setEditable(false);
        variableColumn.setMinWidth(140);
        variableColumn.setCellValueFactory(
                new PropertyValueFactory<Variable, String>("variable"));

        TableColumn valueColumn = new TableColumn("Value");
        valueColumn.setMinWidth(140);
        valueColumn.setEditable(true);
        valueColumn.setCellValueFactory(new PropertyValueFactory<Variable, String>("value"));
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        valueColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Variable, String>>() {
                    public void handle(TableColumn.CellEditEvent<Variable, String> t) {
                        ((Variable) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setValue(t.getNewValue());
                        model.executeCommands(new Parser().parse(("make " + t.getTableView().getItems().get(t.getTablePosition().getRow()).getVariable() + " " + t.getNewValue()).split("\\s+")));
                    }
                }
        );

        TableView table = new TableView();
        table.getColumns().addAll(variableColumn, valueColumn);
        table.setEditable(true);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        final ObservableList<Variable> data = FXCollections.observableArrayList();
        for(String key : variables.keySet()) {
            Variable var = new Variable(key, variables.get(key).toString());
            data.add(var);
            table.getItems().add(var);
        }
        data.addListener(new ListChangeListener<Variable>(){

            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Variable> pChange) {
                while(pChange.next()) {
                    for (int i = 0; i < pChange.getList().size(); i++) {
                        List<Command> commands = new Parser().parse(new String[] {"make " +
                                pChange.getList().get(i).getVariable() + " " +
                                pChange.getList().get(i).getValue().toString()});
                        model.executeCommands(commands);
                    }
                }
            }
        });

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public static class Variable {

        private final SimpleStringProperty variable;
        private final SimpleStringProperty value;

        private Variable(String fName, String lName) {
            this.variable = new SimpleStringProperty(fName);
            this.value = new SimpleStringProperty(lName);
        }

        public String getVariable() {
            return variable.get();
        }

        public void setVariable(String fName) {
            variable.set(fName);
        }

        public String getValue() {
            return value.get();
        }

        public void setValue(String fName) {
            value.set(fName);
        }
    }
}
