package droubay.sfwr1qkm2droubay2;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class ModifyPartView extends Application {

    public InHouse localInHouse;

    public Outsourced localOutsourced;

    public static int id;
    public Label sourceLabel;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField sourceField;
    public RadioButton inHouseRadio;
    public RadioButton outsourceRadio;
    public Button onSaveBtn;
    public Button onCancelPart;
    public TextField minField;
    public Label nameError;
    public Label priceError;
    public Label invError;
    public Label minError;
    public Label maxError;
    public Label sourceError;
    public Label exceptionMsg;


    @Override
    public void start(Stage stage)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modify-part-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("modifypartview");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
    public void onInHousePart(ActionEvent actionEvent){
        sourceLabel.setText("Machine ID");
        outsourceRadio.setSelected(false);
    }

    public void onOutsourcedPart(ActionEvent actionEvent) throws IOException {
        sourceLabel.setText("Company Name");
        inHouseRadio.setSelected(false);
    }

    public void onSave(ActionEvent actionEvent) throws IOException{

        ObservableList<Part> localAllParts = Inventory.getAllParts();

        boolean allValid = true;
        try {

            for (int i = 0; i < localAllParts.size(); i++) {
                //localAllParts.get(i).equals(localInHouse);


                if (localAllParts.get(i) == localInHouse) {

                    if (nameField.getText().isBlank()) {
                        nameError.setText("Name cannot be blank");
                        throw new NumberFormatException("Unable to modify part");
                    } else {
                        localInHouse.setName(nameField.getText());
                        nameError.setText("");
                    }
                    localInHouse.setPrice(Double.parseDouble(priceField.getText()));
                    if (!priceField.getText().contains(".")) {
                        throw new NumberFormatException("Unable to modify part");
                    }
                    localInHouse.setStock(Integer.parseInt(invField.getText()));
                    localInHouse.setMin(Integer.parseInt(minField.getText()));
                    localInHouse.setMax(Integer.parseInt(maxField.getText()));
                    if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText())){
                        throw new NumberFormatException("Minimum must be less than maximum");
                    }
                    if (Integer.parseInt(maxField.getText()) < Integer.parseInt(minField.getText())){
                        throw new NumberFormatException("Maximum must be greater than minimum");
                    }
                    if(Integer.parseInt(invField.getText()) > Integer.parseInt(maxField.getText())
                            || Integer.parseInt(invField.getText()) < Integer.parseInt(minField.getText())){
                        throw new NumberFormatException("Inventory must be a value between maximum and minimum");
                    }
                    localInHouse.setMachineId(Integer.parseInt(sourceField.getText()));
                    if (sourceField.getText().isBlank()) {
                        sourceError.setText("MachineId/CompanyName cannot be blank");
                        throw new NumberFormatException("Unable to modify part");
                    } else {
                        sourceError.setText("");
                    }
                    Inventory.updatePart(i, localInHouse);
                } else if (localAllParts.get(i) == localOutsourced) {



                    if (nameField.getText().isBlank()) {
                        nameError.setText("Name cannot be blank");
                        throw new NumberFormatException("Unable to modify part");
                    } else {
                        localOutsourced.setName(nameField.getText());
                        nameError.setText("");
                    }
                    localOutsourced.setPrice(Double.parseDouble(priceField.getText()));
                    if (!priceField.getText().contains(".")) {
                        throw new NumberFormatException("Unable to modify part");
                    }
                    localOutsourced.setStock(Integer.parseInt(invField.getText()));
                    localOutsourced.setMin(Integer.parseInt(minField.getText()));
                    localOutsourced.setMax(Integer.parseInt(maxField.getText()));
                    if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText())){
                        throw new NumberFormatException("Minimum must be less than maximum");
                    }
                    if (Integer.parseInt(maxField.getText()) < Integer.parseInt(minField.getText())){
                        throw new NumberFormatException("Maximum must be greater than minimum");
                    }
                    if(Integer.parseInt(invField.getText()) > Integer.parseInt(maxField.getText())
                            || Integer.parseInt(invField.getText()) < Integer.parseInt(minField.getText())){
                        throw new NumberFormatException("Inventory must be a value between maximum and minimum");
                    }
                    localOutsourced.setCompanyName(sourceField.getText());
                    if (sourceField.getText().isBlank()) {
                        sourceError.setText("MachineId/CompanyName cannot be blank");
                        throw new NumberFormatException("Unable to modify part");
                    } else {
                        sourceError.setText("");
                    }

                    Inventory.updatePart(i, localOutsourced);

                }
            }
        } catch (NumberFormatException e) {


                if (priceField.getText().isBlank()) {
                    priceError.setText("Price cannot be blank");
                } else {
                    try {

                        if (priceField.getText().contains(".")) {
                            double isDouble = Double.parseDouble(priceField.getText()) + 1.0;
                            priceError.setText("");
                        } else {
                            priceError.setText("Price must be numeric and of the format 'x.xx'");
                            throw new Exception("Unable to create new part");

                        }
                    } catch (Exception f) {
                        priceError.setText("Price must be numeric and of the format 'x.xx'");

                    }
                }

                if (invField.getText().isBlank()) {
                    invError.setText("Inventory cannot be blank");

                } else {
                    try {
                        int isInt = Integer.parseInt(invField.getText()) * 2;
                        if(Integer.parseInt(invField.getText()) > Integer.parseInt(maxField.getText())
                                || Integer.parseInt(invField.getText()) < Integer.parseInt(minField.getText())){
                            throw new Exception("Inventory must be a value between maximum and minimum");
                        } else {
                            invError.setText("");
                        }
                    } catch (NumberFormatException g) {
                        invError.setText("Inventory must be numeric");
                    } catch (Exception a){
                        invError.setText(a.getMessage());
                    }
                }

                if (minField.getText().isBlank()) {
                    minError.setText("Minimum cannot be blank");
                } else {
                    try {
                        int isInt = Integer.parseInt(minField.getText()) * 2;
                        if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText())){
                            throw new NumberFormatException("Unable to modify part");
                        } else {
                            minError.setText("");
                        }
                    } catch (NumberFormatException h) {
                        minError.setText("Minimum must be numeric and less than maximum");
                    }
                }


                if (maxField.getText().isBlank()) {
                    maxError.setText("Maximum cannot be blank");
                } else {
                    try {
                        int isInt = Integer.parseInt(maxField.getText()) * 2;
                        if (Integer.parseInt(maxField.getText()) < Integer.parseInt(minField.getText())){
                            throw new NumberFormatException("Unable to modify part");
                        } else {
                            maxError.setText("");
                        }
                    } catch (NumberFormatException i) {
                        maxError.setText("Maximum must be numeric and greater than minimum");
                    }
                }
            if (sourceField.getText().isBlank()) {
                sourceError.setText("MachineId/CompanyName cannot be blank");

            } else {
                sourceError.setText("");
            }

                exceptionMsg.setText("Unable to modify part. Please ensure data types are valid and/or not blank");
                return;

         }





        HelloApplication helloInst = new HelloApplication();
        helloInst.start(new Stage());

        Stage stage = (Stage) onSaveBtn.getScene().getWindow();
        stage.close();

    }

    public void onCancelBtn(ActionEvent actionEvent) throws IOException {
        HelloApplication helloInst = new HelloApplication();
        helloInst.start(new Stage());
        Stage stage = (Stage) onCancelPart.getScene().getWindow();
        stage.close();
    }

    public void modifyPart(Part selectedPart) {


        if(selectedPart instanceof InHouse){
            localInHouse = (InHouse) selectedPart;
            sourceLabel.setText("Machine ID");
            inHouseRadio.setSelected(true);
            nameField.setText(String.valueOf(selectedPart.getName()));
            priceField.setText(String.valueOf(selectedPart.getPrice()));
            invField.setText(String.valueOf(selectedPart.getStock()));
            minField.setText(String.valueOf(selectedPart.getMin()));
            maxField.setText(String.valueOf(selectedPart.getMax()));
            sourceField.setText(String.valueOf(((InHouse)selectedPart).getMachineId()));
        }
        else {
            localOutsourced = (Outsourced) selectedPart;
            sourceLabel.setText("Company Name");
            outsourceRadio.setSelected(true);
            nameField.setText(String.valueOf(selectedPart.getName()));
            priceField.setText(String.valueOf(selectedPart.getPrice()));
            invField.setText(String.valueOf(selectedPart.getStock()));
            minField.setText(String.valueOf(selectedPart.getMin()));
            maxField.setText(String.valueOf(selectedPart.getMax()));
            sourceField.setText(String.valueOf(((Outsourced)selectedPart).getCompanyName()));

        }



    }
}
