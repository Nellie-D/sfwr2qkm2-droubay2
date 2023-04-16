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

/**
 * @author Danielle Droubay
 * @version 1.0
 */
public class ModifyPartView extends Application {

    /**
     * Class members
     */

    // An InHouse object instantiated for local transaction
    public InHouse localInHouse;
    //An Outsourced object instantiated for local transaction
    public Outsourced localOutsourced;

    // Id for each part, consistent across classes

    public static int id;

    //JavaFX types
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

    /**
     * Build the modifyPartView stage
     * @param stage stage to build
     * @throws IOException may throw IOException
     */
    @Override
    public void start(Stage stage)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modify-part-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("modifypartview");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Launch the main for the modifyPartView class
     *
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Call if the Part to be modified is an InHouse object
     * @param actionEvent an actionable method onInHousePart
     */
    public void onInHousePart(ActionEvent actionEvent){

        // Set the correct sourceLabel text and radio buttons
        sourceLabel.setText("Machine ID");
        outsourceRadio.setSelected(false);
    }

    /**
     * Call if the Part to be modfied is an Outsourced object
     * @param actionEvent an actionable method onOutsourcedPart
     *
     */
    public void onOutsourcedPart(ActionEvent actionEvent) {
        sourceLabel.setText("Company Name");
        inHouseRadio.setSelected(false);
    }

    /**
     * Call if the JavaFX save button in the modifyPartView application is clicked
     * Save the new data from the user to update the Inventory
     * @param actionEvent actionable method onSave
     * @throws IOException may throw exception from user input
     */
    public void onSave(ActionEvent actionEvent) throws IOException{
        // Obtain all parts for local use and transaction
        ObservableList<Part> localAllParts = Inventory.getAllParts();
        // Start a try, as user input may lead to IOException
        try {
            /* Iterate through the local part list until the current part corresponds
            to the part being operated on throughout the class */
            for (int i = 0; i < localAllParts.size(); i++) {
                // Iterate through if the part is an InHouse object
                if (localAllParts.get(i) == localInHouse) {
                    // Test for valid or invalid data

                    //Do not allow nameField to be empty
                    if (nameField.getText().isBlank()) {
                        nameError.setText("Name cannot be blank");
                        throw new NumberFormatException("Unable to modify part");
                    } else {
                        localInHouse.setName(nameField.getText());
                        nameError.setText("");
                    }

                    // Try to set the price
                    localInHouse.setPrice(Double.parseDouble(priceField.getText()));

                    // If there is no decimal point in the given string, throw an exception
                    if (!priceField.getText().contains(".")) {
                        throw new NumberFormatException("Unable to modify part");
                    }

                    // Try to set the inventory
                    localInHouse.setStock(Integer.parseInt(invField.getText()));
                    // Try to set the minimum
                    localInHouse.setMin(Integer.parseInt(minField.getText()));
                    // Try to set the maximum
                    localInHouse.setMax(Integer.parseInt(maxField.getText()));

                    // Ensure that minimum is not greater than maximum and vice versa
                    if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText())){
                        throw new NumberFormatException("Minimum must be less than maximum");
                    }
                    if (Integer.parseInt(maxField.getText()) < Integer.parseInt(minField.getText())){
                        throw new NumberFormatException("Maximum must be greater than minimum");
                    }

                    // Ensure that inventory is a number between minimum and maximum

                    if(Integer.parseInt(invField.getText()) > Integer.parseInt(maxField.getText())
                            || Integer.parseInt(invField.getText()) < Integer.parseInt(minField.getText())){
                        throw new NumberFormatException("Inventory must be a value between maximum and minimum");
                    }

                    // Try to set the machineId

                    localInHouse.setMachineId(Integer.parseInt(sourceField.getText()));
                    if (sourceField.getText().isBlank()) {
                        sourceError.setText("MachineId/CompanyName cannot be blank");
                        throw new NumberFormatException("Unable to modify part");
                    } else {
                        sourceError.setText("");
                    }

                    // If no exceptions are thrown, update the part at the current index to the modified part
                    Inventory.updatePart(i, localInHouse);

                    // Iterate through if the part is an Outsourced object
                } else if (localAllParts.get(i) == localOutsourced) {
                    // Test for valid or invalid data

                    // Do not allow nameField to be empty
                    if (nameField.getText().isBlank()) {
                        nameError.setText("Name cannot be blank");
                        throw new NumberFormatException("Unable to modify part");
                    } else {
                        localOutsourced.setName(nameField.getText());
                        nameError.setText("");
                    }

                    // Try to set the price
                    localOutsourced.setPrice(Double.parseDouble(priceField.getText()));
                    // If the price does not contain a decimal in the string, throw an exception
                    if (!priceField.getText().contains(".")) {
                        throw new NumberFormatException("Unable to modify part");
                    }
                    // Try to set the inventory
                    localOutsourced.setStock(Integer.parseInt(invField.getText()));
                    // Try to set the minimum
                    localOutsourced.setMin(Integer.parseInt(minField.getText()));
                    // Try to set the maximum
                    localOutsourced.setMax(Integer.parseInt(maxField.getText()));

                    // Ensure that minimum is less than maximum

                    if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText())){
                        throw new NumberFormatException("Minimum must be less than maximum");
                    }
                    if (Integer.parseInt(maxField.getText()) < Integer.parseInt(minField.getText())){
                        throw new NumberFormatException("Maximum must be greater than minimum");
                    }

                    // Ensure that inventory is a number between minimum and maximum

                    if(Integer.parseInt(invField.getText()) > Integer.parseInt(maxField.getText())
                            || Integer.parseInt(invField.getText()) < Integer.parseInt(minField.getText())){
                        throw new NumberFormatException("Inventory must be a value between maximum and minimum");
                    }
                    // Try to set the company Name
                    localOutsourced.setCompanyName(sourceField.getText());
                    if (sourceField.getText().isBlank()) {
                        sourceError.setText("MachineId/CompanyName cannot be blank");
                        throw new NumberFormatException("Unable to modify part");
                    } else {
                        sourceError.setText("");
                    }
                    // If no exceptions raised, update the part at the specified index to the updated Outsourced object
                    Inventory.updatePart(i, localOutsourced);

                }
            }
            // Handle any raised exceptions
        } catch (NumberFormatException e) {

                // Test for blank price field
                if (priceField.getText().isBlank()) {
                    priceError.setText("Price cannot be blank");
                } else {
                    try {
                        // Test for lack of decimal point in double
                        // Test for numeric values
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
                // Test for blank inventory field
                if (invField.getText().isBlank()) {
                    invError.setText("Inventory cannot be blank");

                } else {
                    try {
                        // Test for numeric value
                        // Test for valid numeric value between minimum and maximum
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
                // Test for blank minimum field
                if (minField.getText().isBlank()) {
                    minError.setText("Minimum cannot be blank");
                } else {
                    try {
                        // Test for numeric value

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

                // Test for blank maximum field
                if (maxField.getText().isBlank()) {
                    maxError.setText("Maximum cannot be blank");
                } else {
                    try {
                        // Test for numeric value
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
            // Test for blank source field
            if (sourceField.getText().isBlank()) {
                sourceError.setText("MachineId/CompanyName cannot be blank");

            } else {
                sourceError.setText("");
            }

            // Upon exceptions, communicate that the part has not been modified
            exceptionMsg.setText("Unable to modify part. Please ensure data types are valid and/or not blank");

            // Return to allow the user to re-enter data correctly
            return;

         }
        // Successful part modification has been saved
        // Exit the modifyPartView window and reenter the main application window

        HelloApplication helloInst = new HelloApplication();
        helloInst.start(new Stage());

        Stage stage = (Stage) onSaveBtn.getScene().getWindow();
        stage.close();

    }

    /**
     * Call if the cancel button has been clicked
     * @param actionEvent actionable method onCancelBtn
     * @throws IOException may throw exception from user input
     */
    public void onCancelBtn(ActionEvent actionEvent) throws IOException {

        // Open the main application window
        HelloApplication helloInst = new HelloApplication();
        helloInst.start(new Stage());

        // Close the current modifyPartView window without saving data
        Stage stage = (Stage) onCancelPart.getScene().getWindow();
        stage.close();
    }

    /**
     * Called in the main application when the Modify Part button is clicked
     * Instantiates the modifyPartView window with the data from the part
     * to be modified
     * @param selectedPart the part to be modified
     */
    public void modifyPart(Part selectedPart) {

        // Instantiate the fields with the data of the InHouse or Outsourced Part object

        if(selectedPart instanceof InHouse){

            // Set each of the fields
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

            // Set each of the fields
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
