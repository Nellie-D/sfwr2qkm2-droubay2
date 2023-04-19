package droubay.sfwr1qkm2droubay2;

import javafx.application.Application;
import javafx.collections.ObservableList;
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
 * A class for part modification
 * @author Danielle Droubay
 * @version 1.0
 */
public class ModifyPartView extends Application {

    /**
     * A Part object to hold the Part to be modified
     * in the event that object type is changed from an
     * Outsourced to InHouse or vice versa
     */

    public Part localPart;
    /**
     * An InHouse object instantiated for local transaction
     */

    public InHouse localInHouse;
    /**
     * An Outsourced object instantiated for local transaction
     */
    public Outsourced localOutsourced;

    /**
     * Boolean to test whether the part to be modified has changed from inHouse to Outsourced
     * and vice vers
     */

    boolean isInHouse;
    /**
     * Id for each part, consistent across classes
     */


    public static int id;

    /**
     * Indicates if the source is Outsourced or InHouse
     */
    public Label sourceLabel;
    /**
     * Field to show the id
     */
    public TextField idField;
    /**
     * Field to obtain the name
     */
    public TextField nameField;
    /**
     * Field to obtain the inventory
     */
    public TextField invField;
    /**
     * Field to obtain the price
     */
    public TextField priceField;
    /**
     * Field to obtain the maximum
     */
    public TextField maxField;
    /**
     * Field to obtain the minimum
     */
    public TextField minField;
    /**
     * Field to obtain the source, whether machine id or company name
     */
    public TextField sourceField;
    /**
     * Radio button that indicates part is InHouse
     */
    public RadioButton inHouseRadio;
    /**
     * Radio button that indicates part is Outsourced
     */
    public RadioButton outsourceRadio;

    /**
     * Save modified part button
     */
    public Button onSaveBtn;
    /**
     * Cancel button
     */
    public Button onCancelPart;
    /**
     * Displays error resulting from name input
     */
    public Label nameError;
    /**
     * Displays error resulting from price
     */
    public Label priceError;
    /**
     * Displays error resulting from inventory
     */
    public Label invError;
    /**
     * Displays error resulting from minimum
     */
    public Label minError;
    /**
     * Displays error resulting from maximum
     */
    public Label maxError;
    /**
     * Displays error resulting from source
     */
    public Label sourceError;
    /**
     * Displays error message that the part could not be modified
     */
    public Label exceptionMsg;

    /**
     * Build the modifyPartView stage
     * @param stage the stage to build
     * @throws IOException throw IOException
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
     * Call if the Part to be modified is an InHouse object
     */
    public void onInHousePart(){

        // Set the correct sourceLabel text and radio buttons
        sourceLabel.setText("Machine ID");
        outsourceRadio.setSelected(false);
    }

    /**
     * Call if the Part to be modified is an Outsourced object
     *
     */
    public void onOutsourcedPart() {
        // Set the correct sourceLabel and radio buttons
        sourceLabel.setText("Company Name");
        inHouseRadio.setSelected(false);
    }

    /**
     * Call if the JavaFX save button in the modifyPartView application is clicked
     * Save the new data from the user to update the Inventory
     * @throws IOException may throw exception from user input
     */
    public void onSave() throws IOException {
        // Declare variables in case InHouse is changed to Outsourced and vice versa
        String name;
        Double price;
        Integer inventory;
        Integer minimum;
        Integer maximum;
        Integer machineId;
        String companyName;



            try {

                // Test for valid or invalid data

                //Do not allow nameField to be empty
                if (nameField.getText().isBlank()) {
                    nameError.setText("Name cannot be blank");
                    throw new NumberFormatException("Unable to modify part");
                } else {
                   name = nameField.getText();
                    nameError.setText("");
                }

                // Try to set the price
                price = Double.parseDouble(priceField.getText());

                // If there is no decimal point in the given string, throw an exception
                if (!priceField.getText().contains(".")) {
                    throw new NumberFormatException("Unable to modify part");
                }

                // Try to set the inventory
                inventory = Integer.parseInt(invField.getText());
                // Try to set the minimum
                minimum = Integer.parseInt(minField.getText());
                // Try to set the maximum
                maximum = Integer.parseInt(maxField.getText());

                // Ensure that minimum is not greater than maximum and vice versa
                if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText())) {
                    throw new NumberFormatException("Minimum must be less than maximum");
                }
                if (Integer.parseInt(maxField.getText()) < Integer.parseInt(minField.getText())) {
                    throw new NumberFormatException("Maximum must be greater than minimum");
                }

                // Ensure that inventory is a number between minimum and maximum

                if (Integer.parseInt(invField.getText()) > Integer.parseInt(maxField.getText())
                        || Integer.parseInt(invField.getText()) < Integer.parseInt(minField.getText())) {
                    throw new NumberFormatException("Inventory must be a value between maximum and minimum");
                }




                if (sourceField.getText().isBlank()) {
                    sourceError.setText("MachineId/CompanyName cannot be blank");
                    throw new NumberFormatException("Unable to modify part");
                } else {
                 /* if an inhouse type and has not changed, use setters
                    and replace the inhouse at index i with modified inhouse.
                    this represents no change in inhouse
                     */
                    if (isInHouse && inHouseRadio.isSelected()) {
                        machineId = Integer.parseInt(sourceField.getText());
                        localInHouse.setMachineId(machineId);

                        localInHouse.setName(name);
                        localInHouse.setPrice(price);
                        localInHouse.setStock(inventory);
                        localInHouse.setMin(minimum);
                        localInHouse.setMax(maximum);
                        localPart = localInHouse;

                    /* if an outsourced type and the inhouse radio is selected,
                    create a new inhouse to replace the outsourced at index i.
                    this represents a change from outsourced to inhouse
                     */
                    } else if (!isInHouse && inHouseRadio.isSelected()) {
                        machineId = Integer.parseInt(sourceField.getText());
                        localPart = new InHouse(localOutsourced.getId(), name, price, inventory, minimum, maximum, machineId);
                    }
                    /* if an inhouse and outsource radio is selected, this represents a change from
                    an inhouse type to an outsourced type. create a new outsourced type to replace the
                    inhouse at index i
                     */
                    else if (isInHouse && outsourceRadio.isSelected()) {
                        companyName = sourceField.getText();
                        localPart = new Outsourced(localInHouse.getId(), name, price, inventory, minimum, maximum, companyName);
                    }
                    /* if an outsourced type, and an outsourced radio is selected, this represents no
                    change from an inhouse type. use setters to update the outsourced at index i
                    */
                    else if (!isInHouse && outsourceRadio.isSelected()) {
                        companyName = sourceField.getText();
                        localOutsourced.setCompanyName(companyName);
                        localOutsourced.setName(name);
                        localOutsourced.setPrice(price);
                        localOutsourced.setStock(inventory);
                        localOutsourced.setMin(minimum);
                        localOutsourced.setMax(maximum);
                        localPart = localOutsourced;

                    }

                    for (int i = 0; i < Inventory.getAllParts().size(); i++) {
                        if (Inventory.getAllParts().get(i).getId() == localPart.getId()) {
                            Inventory.updatePart(i, localPart);
                        }
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
                        if (Integer.parseInt(invField.getText()) > Integer.parseInt(maxField.getText())
                                || Integer.parseInt(invField.getText()) < Integer.parseInt(minField.getText())) {
                            throw new Exception("Inventory must be a value between maximum and minimum");
                        } else {
                            invError.setText("");
                        }
                    } catch (NumberFormatException g) {
                        invError.setText("Inventory must be numeric");
                    } catch (Exception a) {
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
                        if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText())) {
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
                        if (Integer.parseInt(maxField.getText()) < Integer.parseInt(minField.getText())) {
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
                    // If no change, ensure that machineId is numeric
                    if (isInHouse && inHouseRadio.isSelected()) {
                        try {
                            // Ensure that machineId is numeric
                            int isInt = Integer.parseInt(sourceField.getText()) * 2;
                            sourceError.setText("");
                        } catch (NumberFormatException k) {
                            sourceError.setText("Machine Id must be numeric");
                        }
                    }
                    // If change from Outsourced to InHouse, ensure that machineId is numeric
                    else if (!isInHouse && inHouseRadio.isSelected()){
                        try {
                            // Ensure that machineId is numeric
                            int isInt = Integer.parseInt(sourceField.getText()) * 2;
                            sourceError.setText("");
                        } catch (NumberFormatException k) {
                            sourceError.setText("Machine Id must be numeric");
                        }
                    }
                    // If change from InHouse to Outsourced, allow
                    else if (isInHouse && outsourceRadio.isSelected()){
                        sourceError.setText("");
                    }
                    // If no change from outsourced to outsourced,  allow
                    else if (!isInHouse && outsourceRadio.isSelected()){
                        sourceError.setText("");
                    }
                }


                exceptionMsg.setText("Unable to modify part. Please ensure data types are valid and/or not blank");

                // Return to allow the user to re-enter data correctly
                return;
            }

            // Upon exceptions, communicate that the part has not been modified

        // Successful part modification has been saved
        // Exit the modifyPartView window and reenter the main application window

        MainApplication helloInst = new MainApplication();
        helloInst.start(new Stage());

        Stage stage = (Stage) onSaveBtn.getScene().getWindow();
        stage.close();

    }

    /**
     * Call if the cancel button has been clicked
     * Saves the updated data for this part
     * @throws IOException may throw exception from user input
     */
    public void onCancelBtn() throws IOException {

        // Open the main application window
        MainApplication helloInst = new MainApplication();
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
            isInHouse = true;
            // Set each of the fields
            localInHouse = (InHouse) selectedPart;
            sourceLabel.setText("Machine ID");
            inHouseRadio.setSelected(true);
            idField.setText(String.valueOf(selectedPart.getId()));
            nameField.setText(String.valueOf(selectedPart.getName()));
            priceField.setText(String.valueOf(selectedPart.getPrice()));
            invField.setText(String.valueOf(selectedPart.getStock()));
            minField.setText(String.valueOf(selectedPart.getMin()));
            maxField.setText(String.valueOf(selectedPart.getMax()));
            sourceField.setText(String.valueOf(((InHouse)selectedPart).getMachineId()));
        }
        else {

            // Set each of the fields
            isInHouse = false;
            localOutsourced = (Outsourced) selectedPart;
            sourceLabel.setText("Company Name");
            outsourceRadio.setSelected(true);
            idField.setText(String.valueOf(selectedPart.getId()));
            nameField.setText(String.valueOf(selectedPart.getName()));
            priceField.setText(String.valueOf(selectedPart.getPrice()));
            invField.setText(String.valueOf(selectedPart.getStock()));
            minField.setText(String.valueOf(selectedPart.getMin()));
            maxField.setText(String.valueOf(selectedPart.getMax()));
            sourceField.setText(String.valueOf(((Outsourced)selectedPart).getCompanyName()));

        }



    }
}
