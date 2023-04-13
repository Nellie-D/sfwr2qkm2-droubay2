package droubay.sfwr1qkm2droubay2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPartView extends Application {

    public static int id;
    public Label sourceLabel;

    public Button onSaveBtn;
    public RadioButton inHouseRadio;
    public RadioButton outsourceRadio;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public TextField sourceField;
    public Button onCancelPart;
    public Label nameError;
    public Label priceError;
    public Label invError;
    public Label minError;
    public Label maxError;
    public Label exceptionMsg;
    public Label sourceError;

    @Override
    public void start(Stage stage)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-part-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("addpartview");
        stage.setScene(scene);
        stage.show();



    }


    public static void main(String[] args) {
        launch();
    }

    public void onInHousePart(ActionEvent actionEvent) {

        sourceLabel.setText("Machine ID");
        outsourceRadio.setSelected(false);

    }

    public void onOutsourcedPart(ActionEvent actionEvent) {

        sourceLabel.setText("Company Name");
        inHouseRadio.setSelected(false);

    }

    public void onSave(ActionEvent actionEvent) throws Exception {
        boolean allValid = true;
        String name;
        Double price;
        Integer inventory;
        Integer minimum;
        Integer maximum;
        Integer machineId;
        String companyName;
        if (inHouseRadio.isArmed()) {
            try {


                name = nameField.getText();
                if (nameField.getText().isBlank()) {
                    nameError.setText("Name cannot be blank");
                    throw new NumberFormatException("cannot");
                } else {
                    nameError.setText("");
                }
                price = Double.parseDouble(priceField.getText());
                if (!priceField.getText().contains(".")) {
                    throw new NumberFormatException("can't");
                }
                inventory = Integer.parseInt(invField.getText());
                minimum = Integer.parseInt(minField.getText());
                maximum = Integer.parseInt(maxField.getText());
                machineId = Integer.parseInt(sourceField.getText());
            } catch (NumberFormatException e) {
                System.out.print("Innumformat");
                if (nameField.getText().isBlank()) {
                    nameError.setText("Name cannot be blank");
                } else {
                    nameError.setText("");
                }
                if (priceField.getText().isBlank()) {
                    priceError.setText("Price cannot be blank");
                } else {
                    try {

                        if (priceField.getText().contains(".")) {
                            double isDouble = Double.parseDouble(priceField.getText()) + 1.0;
                            priceError.setText("");
                        } else {
                            priceError.setText("Price must be numeric and of the format 'x.xx'");
                            throw new Exception("can't");

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
                        invError.setText("");
                    } catch (NumberFormatException g) {
                        invError.setText("Inventory must be numeric");
                    }
                }

                if (minField.getText().isBlank()) {
                    minError.setText("Minimum cannot be blank");
                } else {
                    try {
                        int isInt = Integer.parseInt(minField.getText()) * 2;
                        minError.setText("");
                    } catch (NumberFormatException h) {
                        minError.setText("Minimum must be numeric");
                    }
                }


                if (maxField.getText().isBlank()) {
                    maxError.setText("Maximum cannot be blank");
                } else {
                    try {
                        int isInt = Integer.parseInt(maxField.getText()) * 2;
                        maxError.setText("");
                    } catch (NumberFormatException i) {
                        maxError.setText("Maximum must be numeric");
                    }
                }
                if (inHouseRadio.isArmed()) {
                    if (sourceField.getText().isBlank()) {
                        sourceError.setText("Source cannot be blank");
                    } else {
                        try {
                            int isInt = Integer.parseInt(sourceField.getText()) * 2;
                            sourceError.setText("");
                        } catch (NumberFormatException j) {
                            sourceError.setText("Machine Id must be numeric");
                        }
                    }
                }
                exceptionMsg.setText("Unable to create new product. Please ensure data types are valid and/or not blank");
                return;
            }
            InHouse newInHouse = new InHouse(id++, name, price, inventory, minimum, maximum, machineId);
            Inventory.addPart(newInHouse);
        } else {
            try {
                name = nameField.getText();
                if (nameField.getText().isBlank()) {
                    nameError.setText("Name cannot be blank");
                    throw new NumberFormatException("Unable to create new part");
                } else {
                    nameError.setText("");
                }
                price = Double.parseDouble(priceField.getText());
                if (!priceField.getText().contains(".")) {
                    throw new NumberFormatException("Unable to create new part");
                }
                inventory = Integer.parseInt(invField.getText());
                minimum = Integer.parseInt(minField.getText());
                maximum = Integer.parseInt(maxField.getText());
                if (maximum < minimum){
                    throw new NumberFormatException("Unable to create new product");
                }
                if(inventory > maximum || inventory < minimum){
                    throw new NumberFormatException("Unable to create new product");
                }
                companyName = sourceField.getText();
                if (sourceField.getText().isBlank()) {
                    sourceError.setText("Source cannot be blank");
                    throw new NumberFormatException("Unable to create new part");
                } else {
                    sourceError.setText("");
                }
            } catch (NumberFormatException e) {

                if (nameField.getText().isBlank()) {
                    nameError.setText("Name cannot be blank");
                } else {
                    nameError.setText("");
                }
                if (priceField.getText().isBlank()) {
                    priceError.setText("Price cannot be blank");
                } else {
                    try {

                        if (priceField.getText().contains(".")) {
                            double isDouble = Double.parseDouble(priceField.getText()) + 1.0;
                            priceError.setText("");
                        } else {
                            priceError.setText("Price must be numeric and of the format 'x.xx'");
                            throw new Exception("can't");

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
                            throw new NumberFormatException("Unable to create new part");
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
                            throw new NumberFormatException("Unable to create new product");
                        } else {
                            maxError.setText("");
                        }
                    } catch (NumberFormatException i) {
                        maxError.setText("Maximum must be numeric and greater than minimum");
                    }
                }
                if (outsourceRadio.isArmed()) {
                    if (sourceField.getText().isBlank()) {
                        sourceError.setText("Company Name cannot be blank");
                    } else {
                        sourceError.setText("");
                    }
                } else {
                    if (sourceField.getText().isBlank()) {
                        sourceError.setText("Source cannot be blank");
                    } else {
                        sourceError.setText("");
                    }
                }
                exceptionMsg.setText("Unable to create new product. Please ensure data types are valid and/or not blank");
                return;
            }
            Outsourced newOutsource = new Outsourced(id++, name, price, inventory, minimum, maximum, companyName);
            Inventory.addPart(newOutsource);
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


}

