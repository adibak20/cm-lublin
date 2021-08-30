package example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import example.helper.GsonHelper;
import example.helper.LocalDateSerializer;
import example.model.PatientDTO;
import example.model.VisitDTO;
import example.rest.CoreRestClient;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.logging.Logger;


/**
 * JavaFX App
 */
public class App extends Application {

    Logger logger = Logger.getLogger(App.class.getName());

    private TableView<PatientDTO> patients = new TableView();
    private TableView<VisitDTO> visits = new TableView();

    Font fontHeader = new Font("Arial", 20);
    Label patientLabel = new Label("Pacjenci");
    Label visitsLabel = new Label("Wizyty");

    TextField searchField = new TextField();

    Button addBtn = new Button("Dodaj");
    Button delete = new Button("Usun");

    VBox rootBox = new VBox();
    HBox findBox = new HBox(2);
    HBox buttonsBox = new HBox(2);

    TableColumn idCol = new TableColumn("Id");
    TableColumn firstNameCol = new TableColumn("Imię");
    TableColumn lastNameCol = new TableColumn("Nazwisko");
    TableColumn serialNumberCol = new TableColumn("Pesel");
    TableColumn birthdayCol = new TableColumn("Data urodzin");

    TableColumn doctorNameCol = new TableColumn("Lekarz");
    TableColumn descriptionCol = new TableColumn("Opis wizyty");
    TableColumn dateOfVisitCol = new TableColumn("Termin wizyty");

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        if (connect()) return;

        Scene scene = new Scene(new Group());
        stage.setTitle("CM Lublin");
        stage.setWidth(750);
        stage.setHeight(750);

        patientLabel.setFont(fontHeader);
        visitsLabel.setFont(fontHeader);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchPatient(newValue);
        });

        buttonsBox.setPadding(new Insets(10));
        buttonsBox.setAlignment(Pos.BASELINE_RIGHT);
        buttonsBox.getChildren().addAll(addBtn, delete);

        findBox.setPadding(new Insets(10));
        findBox.setAlignment(Pos.BASELINE_RIGHT);
        findBox.getChildren().addAll(searchField);

        initPatientTable();
        initVisitsTable();

        patients.setEditable(true);
        patients.getColumns().addAll(idCol, firstNameCol, lastNameCol, serialNumberCol, birthdayCol);
        patients.setMaxHeight(200.00);
        patients.setPrefWidth(stage.getWidth() - 75);
        patients.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> getVisitsByPatientFromCore(newValue)
        );

        visits.getColumns().addAll(doctorNameCol, descriptionCol, dateOfVisitCol);
        visits.setMaxHeight(200.00);

        addBtn.setOnAction(getAddPatientEventEventHandler());
        delete.setOnAction(deletePatientEventHandler());

        rootBox.setPadding(new Insets(10, 0, 0, 25));
        rootBox.getChildren().addAll(patientLabel, findBox, patients, buttonsBox, visitsLabel, visits);

        ((Group) scene.getRoot()).getChildren().addAll(rootBox);

        stage.setScene(scene);
        stage.show();
    }

    private boolean connect() {
        try {
            getAllPatientFromCore();
        } catch (Exception e) {
            logger.severe("Błąd polaczenia z serwerem");
            return true;
        }
        return false;
    }


    private EventHandler<ActionEvent> getAddPatientEventEventHandler() {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Dialog dialog = new Dialog<>();
                dialog.setTitle("Dodaj pacjenta");
                dialog.setResizable(true);

                Label firstNameLabel = new Label("Imię: ");
                Label lastNameLabel = new Label("Nazwisko: ");
                Label serialNumberLabel = new Label("Pesel: ");
                Label birthdayLabel = new Label("Data urodzenia: ");
                TextField firstNameTextField = new TextField();
                TextField lastNameField = new TextField();
                TextField serialNumberField = new TextField();
                DatePicker birthdayField = new DatePicker();

                GridPane grid = new GridPane();
                grid.add(firstNameLabel, 1, 1);
                grid.add(firstNameTextField, 2, 1);
                grid.add(lastNameLabel, 1, 2);
                grid.add(lastNameField, 2, 2);

                grid.add(serialNumberLabel, 1, 3);
                grid.add(serialNumberField, 2, 3);
                grid.add(birthdayLabel, 1, 4);
                grid.add(birthdayField, 2, 4);
                dialog.getDialogPane().setContent(grid);

                ButtonType buttonTypeOk = new ButtonType("Utwórz", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

                dialog.show();

                dialog.setResultConverter(new Callback<ButtonType, PatientDTO>() {
                    @Override
                    public PatientDTO call(ButtonType b) {
                        if (b == buttonTypeOk) {
                            PatientDTO newPatientDTO = new PatientDTO(
                                    firstNameTextField.getText(),
                                    lastNameField.getText(),
                                    serialNumberField.getText(),
                                    birthdayField.getValue());
                            addPatientToCore(newPatientDTO);
                        }
                        return null;
                    }
                });

            }
        };
    }

    private EventHandler<ActionEvent> deletePatientEventHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PatientDTO patientDTO = patients.getSelectionModel().getSelectedItem();
                if (patientDTO != null) {
                    System.out.println(patientDTO.getId());
                    try {
                        CoreRestClient.deletePatient(patientDTO.getId());
                    } catch (IOException e) {
                        logger.severe("nie można usunąć rekodru " + e.getMessage());
                    }
                    patients.getItems().remove(patientDTO);
                    visits.getItems().clear();
                }
            }
        };
    }

    private void searchPatient(String keywords) {
        patients.getItems().clear();
        visits.getItems().clear();
        if (keywords.length() > 0) {
            String response = CoreRestClient.findPatients(keywords);
            Gson gson = GsonHelper.getGsonWithParserLocalDate();
            PatientDTO[] patients = gson.fromJson(response, PatientDTO[].class);
            Arrays.stream(patients).sorted((o1, o2) -> o1.getId().compareTo(o2.getId())).forEach(patientDTO -> {
                addPatient(patientDTO);
            });
        } else {
            getAllPatientFromCore();
        }
    }


    private void getAllPatientFromCore() {
        String response = CoreRestClient.getPatients();
        Gson gson = GsonHelper.getGsonWithParserLocalDate();
        PatientDTO[] patients = gson.fromJson(response, PatientDTO[].class);
        Arrays.stream(patients).sorted((o1, o2) -> o1.getId().compareTo(o2.getId())).forEach(patientDTO -> {
            addPatient(patientDTO);
        });
    }

    private void getVisitsByPatientFromCore(PatientDTO patientDTO) {
        visits.getItems().clear();
        if (patientDTO == null) {
            return;
        }
        try {
            String response = CoreRestClient.getVisitsByPatient(patientDTO.getId());
            Gson gson = GsonHelper.getGsonWithParserLocalDateTime();
            VisitDTO[] patients = gson.fromJson(response, VisitDTO[].class);
            Arrays.stream(patients).forEach(visitDTO -> {
                addVisit(visitDTO);
            });
        } catch (IOException e) {
            logger.severe("nie można  pobrac wizyt  " + e.getMessage());
        }
    }




    private void initPatientTable() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
    }

    private void initVisitsTable() {
        doctorNameCol.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateOfVisitCol.setCellValueFactory(new PropertyValueFactory<>("dateOfVisit"));
    }

    private void addPatient(PatientDTO patientDTO) {
        patients.getItems().add(patientDTO);
    }

    private void addPatientToCore(PatientDTO patientDTO) {
        GsonBuilder gsonBuilder  = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        String request = gson.toJson(patientDTO);
        try {
            String response = CoreRestClient.addPatient(request);
            patientDTO.setId(Long.valueOf(response));

        } catch (IOException e) {
            logger.severe("nie można dodać pacjenta " + e.getMessage());
        }
        addPatient(patientDTO);

    }

    private void addVisit(VisitDTO visitDTO) {
        visits.getItems().add(visitDTO);
    }

}
