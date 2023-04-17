package droubay.sfwr1qkm2droubay2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A class for creating new Part objects
 * @author Danielle Droubay
 * @version 1.0
 */
public class AddPartView extends Application {


    /**
     *  id of each part, static throughout the application
     */

    public static int id;

    /**
     * Indicates if the source is Outsourced or InHouse
     */
    public Label sourceLabel;
    /**
     * Save new part button
     */
    public Button onSaveBtn;
    /**
     * Radio button that indicates part is InHouse
     */
    public RadioButton inHouseRadio;
    /**
     * Radio button that indicates part is Outsourced
     */
    public RadioButton outsourceRadio;
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
     * Displays error message that the part could not be made
     */
    public Label exceptionMsg;
    /**
     * Displays error resulting from source
     */
    public Label sourceError;

    /**
     * Build the addPartView stage
     * @param stage the stage to build
     * @throws IOException may throw exception
     */
    @Override
    public void start(Stage stage)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-part-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("addpartview");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Call if the Part to be modified is an InHouse object
     */
    public void onInHousePart() {
        // Set the correct sourceLabel text and radio buttons
        sourceLabel.setText("Machine ID");
        outsourceRadio.setSelected(false);
    }

    /**
     * Call if the Part to be modified is an Outsourced object
     */
    public void onOutsourcedPart() {
        // Set the correct sourceLabel and radio buttons
        sourceLabel.setText("Company Name");
        inHouseRadio.setSelected(false);

    }

    /**
     * Call if the JavaFx save button in the addPartView application is clicked
     * <p>
     * Save the new data to build a  new Part object and add to the Inventory
     * </p>
     * @throws Exception may throw exception from user input
     */
    public void onSave() throws Exception {
        // Declare variables
        String name;
        Double price;
        Integer inventory;
        Integer minimum;
        Integer maximum;
        Integer machineId;
        String companyName;
        // Initialize the variables with user input
        // If the inHouse radio is active, build an InHouse object
        if (inHouseRadio.isSelected()) {
            exceptionMsg.setText("");
            // Start a try, as user input may lead to IOException
            try {

                // Do not allow nameField to be empty
                name = nameField.getText();
                if (nameField.getText().isBlank()) {
                    nameError.setText("Name cannot be blank");
                    throw new NumberFormatException("Unable to create inHouse");
                } else {
                    nameError.setText("");
                }
                // Ensure that price is numeric and contains a decimal
                price = Double.parseDouble(priceField.getText());
                if (!priceField.getText().contains(".")) {
                    throw new NumberFormatException("Unable to create inHouse");
                }
                // Try to set the inventory
                inventory = Integer.parseInt(invField.getText());
                // Try to set the minimum
                minimum = Integer.parseInt(minField.getText());
                // Try to set the maximum
                maximum = Integer.parseInt(maxField.getText());
                // Ensure that minimum is not greater than maximum
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
                machineId = Integer.parseInt(sourceField.getText());
                // If an exception has been raised, handle accordingly
            } catch (NumberFormatException e) {
                // Do not allow nameField to be blank
                if (nameField.getText().isBlank()) {
                    nameError.setText("Name cannot be blank");
                } else {
                    nameError.setText("");
                }

                // Do not allow priceField to be blank
                if (priceField.getText().isBlank()) {
                    priceError.setText("Price cannot be blank");
                } else {
                    try {
                        // Ensure that priceField is numeric and contains a decimal point
                        if (priceField.getText().contains(".")) {
                            double isDouble = Double.parseDouble(priceField.getText()) + 1.0;
                            priceError.setText("");
                        } else {
                            priceError.setText("Price must be numeric and of the format 'x.xx'");
                            throw new Exception("Unable to create inHouse object");

                        }
                    } catch (Exception f) {
                        priceError.setText("Price must be numeric and of the format 'x.xx'");
                    }
                }
                // Do not allow inventory to be blank
                if (invField.getText().isBlank()) {
                    invError.setText("Inventory cannot be blank");

                } else {
                    try {
                        // Ensure that inventory is numeric
                        // Ensure that inventory is between minimum and maximum
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
                // Do not allow minimum to be blank
                if (minField.getText().isBlank()) {
                    minError.setText("Minimum cannot be blank");
                } else {
                    try {
                        // Ensure that minimum is numeric and less than maximum
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

                // Do not allow  maximum to be blank
                if (maxField.getText().isBlank()) {
                    maxError.setText("Maximum cannot be blank");
                } else {
                    try {
                        // Ensure that maximum is numeric
                        if (Integer.parseInt(maxField.getText()) < Integer.parseInt(minField.getText())){
                            throw new NumberFormatException("Unable to modify part");
                        } else {
                            maxError.setText("");
                        }
                    } catch (NumberFormatException i) {
                        maxError.setText("Maximum must be numeric and greater than minimm");
                    }
                }
                // Do not allow sourceField to be blank

                if (sourceField.getText().isBlank()) {
                    sourceError.setText("Source cannot be blank");
                } else {
                    try {
                        // Ensure that machineId is numeric
                        int isInt = Integer.parseInt(sourceField.getText()) * 2;
                        sourceError.setText("");
                    } catch (NumberFormatException j) {

                        sourceError.setText("Machine Id must be numeric");
                    }
                }


                // Communicate to the user that exceptions have been raised
                // A part is unable to be created
                exceptionMsg.setText("Unable to create new part. Please ensure data types are valid and/or not blank");
                return;
            }
            // If no exceptions raised, create and initialize the new InHouse object
            InHouse newInHouse = new InHouse(id++, name, price, inventory, minimum, maximum, machineId);
            // Add the inHouse part to the Inventory
            Inventory.addPart(newInHouse);

            // If the Outsourced radio button is active, create an Outsourced object
        } else if (outsourceRadio.isSelected()){
            exceptionMsg.setText("");
            // Start a try, as user input may raise an exception
            try {
                // Do not allow nameField to be blank
                name = nameField.getText();
                if (nameField.getText().isBlank()) {
                    nameError.setText("Name cannot be blank");
                    throw new NumberFormatException("Unable to create new part");
                } else {
                    nameError.setText("");
                }
                // Try to set the price, and ensure it contains a decimal point
                price = Double.parseDouble(priceField.getText());
                if (!priceField.getText().contains(".")) {
                    throw new NumberFormatException("Unable to create new part");
                }
                // Try to set the inventory
                inventory = Integer.parseInt(invField.getText());
                // Try to set the minimum
                minimum = Integer.parseInt(minField.getText());
                // Try to set the maximum
                maximum = Integer.parseInt(maxField.getText());

                // Ensure that the maximum is not less than the minimum
                if (maximum < minimum){
                    throw new NumberFormatException("Unable to create new product");
                }
                // Ensure that inventory is between maximum and minimum
                if(inventory > maximum || inventory < minimum){
                    throw new NumberFormatException("Unable to create new product");
                }
                // Do not allow the sourceField to be blank
                companyName = sourceField.getText();
                if (sourceField.getText().isBlank()) {
                    sourceError.setText("Source cannot be blank");
                    throw new NumberFormatException("Unable to create new part");
                } else {
                    sourceError.setText("");
                }
                // Catch any exception raised
            } catch (NumberFormatException e) {
                // Do not allow nameField to be blank
                if (nameField.getText().isBlank()) {
                    nameError.setText("Name cannot be blank");
                } else {
                    nameError.setText("");
                }
                // Do not allow priceField to be blank
                if (priceField.getText().isBlank()) {
                    priceError.setText("Price cannot be blank");
                } else {
                    try {
                        // Ensure that price is numeric and contains a decimal
                        if (priceField.getText().contains(".")) {
                            double isDouble = Double.parseDouble(priceField.getText()) + 1.0;
                            priceError.setText("");
                        } else {
                            priceError.setText("Price must be numeric and of the format 'x.xx'");
                            throw new Exception("Unable to create part");

                        }
                    } catch (Exception f) {
                        priceError.setText("Price must be numeric and of the format 'x.xx'");

                    }
                }
                // Do not allow inventory to be blank
                if (invField.getText().isBlank()) {
                    invError.setText("Inventory cannot be blank");

                } else {
                    try {
                        // Ensure that inventory is numeric
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
                // Do not allow minimum to be blank
                if (minField.getText().isBlank()) {
                    minError.setText("Minimum cannot be blank");
                } else {
                    try {
                        // Ensure that minimum is numeric
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

                // Do not allow maximum to be blank
                if (maxField.getText().isBlank()) {
                    maxError.setText("Maximum cannot be blank");
                } else {
                    try {
                        // Ensure that maximum is numeric
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
                // Do not allow sourceField to be blank
                if (outsourceRadio.isSelected()) {
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
                // Communicate to the user that a part could not be created

                exceptionMsg.setText("Unable to create new part. Please ensure data types are valid and/or not blank");
                return;
            }
            // If no exceptions raised, create Outsourced part object
            Outsourced newOutsource = new Outsourced(id++, name, price, inventory, minimum, maximum, companyName);
            // Add the Outsourced part to the store's Inventory
            Inventory.addPart(newOutsource);
        } else {
            // Ensure the concrete type of object has been established before saving
            exceptionMsg.setText("Please select inHouse or Outsource");
            return;
        }
        // Upon successful creation of object and save to Inventory, exit current window
        // Reenter the main view
        MainApplication helloInst = new MainApplication();
        helloInst.start(new Stage());

        Stage stage = (Stage) onSaveBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Call if the cancel button has been clicked
     * @throws IOException may throw exception from user input
     */
    public void onCancelBtn() throws IOException {
        // Open the main application window
        MainApplication helloInst = new MainApplication();
        helloInst.start(new Stage());

        // Close the current addPartView window without saving data
        Stage stage = (Stage) onCancelPart.getScene().getWindow();
        stage.close();
    }

}

