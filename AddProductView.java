package droubay.sfwr1qkm2droubay2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class for creating new Product objects
 * @author Danielle Droubau
 * @version 1.0
 */
public class AddProductView extends Application implements Initializable {

    /**
     * Instantiate a list of Associated Parts per Product for local use and transaction
      */

    private ObservableList<Part> localAssociatedParts = FXCollections.observableArrayList();

    /**
     * Object instantiation of Product Class for local use and transaction
     */


    private Product localProduct;

    /**
     *  Unique id which is static throughout the application
      */


    public static int id;
    /**
     * Displays message to update deletion status
     */

    public Label deleteMsg;

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
    public Label inventoryError;

    /**
     * Displays error resulting from minimum
     */
    public Label minError;

    /**
     * Displays error resulting from maximum
     */
    public Label maxError;

    /**
     * Displays error message that the product could not be made
     */

    public Label exceptionMsg;

    /**
     * Add associated part button
     */
    public Button addBtn;
    /**
     * Remove associated part button
     */
    public Button removeBtn;
    /**
     * Save new product button
     */
    public Button saveBtn;
    /**
     * Cancel button
     */
    public Button cancelBtn;
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
     * Table of associated parts that can be added to a product
     */
    public TableView<Part> addPartTable;
    /**
     * Table of associated parts that can be removed from a product
     */
    public TableView<Part> removePartTable;
    /**
     * A search field for parts to be added
     */
    public TextField searchField;
    /**
     * Column of part ids to be added
     */
    public TableColumn<Object, Object> addPartIDColumn;
    /**
     * Column of part names to be added
     */
    public TableColumn<Object, Object> addPartNameColumn;
    /**
     * Column of part inventory to be added
     */
    public TableColumn<Object, Object> addPartInvLevelColumn;
    /**
     * Column of part prices to be added
     */
    public TableColumn<Object, Object> addPartPriceColumn;
    /**
     * Column of part ids to be removed
     */
    public TableColumn<Object, Object> removePartIdColumn;
    /**
     * Column of part names to be removed
     */
    public TableColumn<Object, Object> removePartNameColumn;
    /**
     * Column of part inventory to be removed
     */
    public TableColumn<Object, Object> removePartInvLevelColumn;
    /**
     * Column of part prices to be removed
     */
    public TableColumn<Object, Object> removePartPriceColumn;

    /**
     *
     * @param stage the stage to be built
     * @throws IOException may throw exception
     */
    @Override
    public void start(Stage stage)  throws IOException {
        // Open addProductView window
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-product-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 822, 505);
        stage.setTitle("addproductview");
        stage.setScene(scene);
        stage.show();

    }
    /**
     * Initialize the window
     * This also establishes cell factories to fill the addPartTable
     * @param url pass the url to the initializer
     * @param resourceBundle pass the resourceBundle to the initializer
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        // Establish cell factories with all Parts
        addPartTable.setItems(Inventory.getAllParts());
        addPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        addPartInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        removePartTable.setItems(localAssociatedParts);
        removePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        removePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        removePartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        removePartInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));


    }

    /**
     * Call if the Add Part button is clicked
     * Adds an associated part to the product
     */
    public void onAddPart() {

        /* Start a try, as an exception may be raised if
         an associated part is added to a product that does not exist */

        try {
            // Create a variable to store the selected part to be added
            Part placeHolder = addPartTable.getSelectionModel().getSelectedItem();

            // If not part selected, return
            if (placeHolder == null) {
                return;
            }

            /* If any of the fields are blank, a product cannot be created.
             As a result, a part cannot be added */

            if (nameField.getText().isBlank()) {


                throw new Exception("blank");

            }
            if (priceField.getText().isBlank()) {


                throw new Exception("blank");

            }
            if (invField.getText().isBlank()) {

                throw new Exception("blank");

            }
            if (minField.getText().isBlank()) {


                throw new Exception("blank");

            }
            if (maxField.getText().isBlank()) {


                throw new Exception("blank");

            }

            // If no exceptions are raised, add the part to the product's list of associated parts
            localAssociatedParts.add(placeHolder);
            exceptionMsg.setText("");

            // Handle any raised exceptions
        } catch (Exception e) {
            // Communicate to the user that the part could not be added and return
            exceptionMsg.setText("Unable to add part to product. Please ensure data types are valid and/or not blank before adding parts");
            return;
        }
    }

    /**
     * Call if the Remove Part button is clicked
     * Removes an associated part from the product
     */
    public void onRemovePart() {
        // Create a variable to store the selected part to be removed
        Part placeHolder = removePartTable.getSelectionModel().getSelectedItem();
        // If no parts are selected, return
        if(placeHolder == null){
            return;
        }
        /* If the part is successfully removed from the product's list of associated parts, show no message.
        Otherwise, inform the user that the part was not removed */
        if(Product.deleteAssociatedPart(placeHolder)){
            deleteMsg.setText("");
        } else {
            deleteMsg.setText("Part not deleted");
        }
    }
    /**
     * RUNTIME ERROR
     * <p> While creating the onSave method, I frequently encountered
     *     runtime errors while trying to save the data to the new product.
     *     For example, entering non-numeric data into the inventory field
     *     would cause the system to crash, as parsing a string with character values
     *     into an Integer raised a NumberFormatException.
     *     I corrected the error by adding the try / catch handlers into
     *     the Save method. With the try / catch blocks, whenever a non-numeric value is
     *     parsed into an Integer or Double data type, the system does not crash, but rather
     *     redirects to the handler in the catch block.
     * </p>
     * Call if the Save Button is clicked
     * Saves the data for this product
     * @throws IOException may throw exception from user input
     */
    public void onSave() throws IOException {
        // Declare variables
        String name;
        Double price;
        Integer inventory;
        Integer minimum;
        Integer maximum;
        // Initialize the variables with user input
        // Start a try, as user input may lead to IOException
            try {
                // Do not allow nameField to be empty
                name = nameField.getText();
                if (nameField.getText().isBlank()) {
                    nameError.setText("Name cannot be blank");
                    throw new NumberFormatException("Unable to create new product");
                }
                else {
                    nameError.setText("");
                }
                // Ensure that price is numeric and contains a decimal
                price = Double.parseDouble(priceField.getText());
                if(!priceField.getText().contains(".")){
                    throw new NumberFormatException("Unable to create new product");
                }
                // Try to set the inventory
                inventory = Integer.parseInt(invField.getText());
                // Try to set the minimum
                minimum = Integer.parseInt(minField.getText());
                // Try to set the maximum
                maximum = Integer.parseInt(maxField.getText());
                // Ensure that minimum is not greater than maximum
                if (maximum < minimum){
                    throw new NumberFormatException("Unable to create new product");
                }
                // Ensure that inventory is a number between minimum and maximum
                if(inventory > maximum || inventory < minimum){
                    throw new NumberFormatException("Unable to create new product");
                }
                // If an exception has been raised, handle accordingly
            } catch (NumberFormatException e) {
                // Do not allow nameField to be blank
                if (nameField.getText().isBlank()) {
                    nameError.setText("Name cannot be blank");
                }
                else {
                    nameError.setText("");
                }
                // Do not allow priceField to be blank
                if (priceField.getText().isBlank()) {
                    priceError.setText("Price cannot be blank");
                } else {
                    try {
                        // Ensure that priceField contains a decimal point (is a double)
                        if (priceField.getText().contains(".")) {
                            // Throw exception is Double parsing is not possible (is a string)
                            double isDouble = Double.parseDouble(priceField.getText()) + 1.0;
                            // Clear the error message if there are no exceptions raised
                            priceError.setText("");
                        } else {
                            // Inform the user that priceField must be numeric and of the format 'x.x'
                            priceError.setText("Price must be numeric and of the format 'x.xx'");
                            throw new Exception("can't");

                        }
                        // Catch the exception thrown in the else statement and handle accordingly
                    } catch (Exception f) {
                        priceError.setText("Price must be numeric and of the format 'x.xx'");

                    }
                }
                // Do not allow inventory to be blank
                if (invField.getText().isBlank()) {
                    inventoryError.setText("Inventory cannot be blank");

                } else {
                    try {
                        // Ensure that inventory is numeric
                        int isInt = Integer.parseInt(invField.getText()) * 2;
                        // Ensure that inventory is between maximum and minimum
                        if(Integer.parseInt(invField.getText()) > Integer.parseInt(maxField.getText())
                        || Integer.parseInt(invField.getText()) < Integer.parseInt(minField.getText())){
                            throw new Exception("Inventory must be a value between maximum and minimum");
                        } else {
                            inventoryError.setText("");
                        }
                        // Handle raised exceptions for Integer parsing or invalid numeric value
                    } catch (NumberFormatException g) {
                        inventoryError.setText("Inventory must be numeric");
                    } catch (Exception a){
                        inventoryError.setText(a.getMessage());
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
                            throw new NumberFormatException("Unable to create new product");
                        } else {
                            minError.setText("");
                        }
                        // Handle raised exceptions for Integer parsing or invalid numeric value
                    } catch (NumberFormatException h) {
                        minError.setText("Minimum must be numeric and less than maximum");
                    }
                }

                // Do not allow  maximum to be blank
                if (maxField.getText().isBlank()) {
                    maxError.setText("Maximum cannot be blank");
                } else {
                    try {
                        // Ensure that maximum is numeric and greater than minimum
                        int isInt = Integer.parseInt(maxField.getText()) * 2;
                        if (Integer.parseInt(maxField.getText()) < Integer.parseInt(minField.getText())){
                            throw new NumberFormatException("Unable to create new product");
                        } else {
                            maxError.setText("");
                        }
                        // Handle raised exceptions for Integer parsing or invalid numeric value
                    } catch (NumberFormatException i) {
                        maxError.setText("Maximum must be numeric and greater than minimum");
                    }
                }
                // If exceptions raised, inform the user that the product has not been created and return
                exceptionMsg.setText("Unable to create new product");
                return;
            }

            // If no exceptions raised, save the data and create new Product object
            Product newProduct = new Product(id++, name, price, inventory, minimum, maximum);
            // Add the new Product to the store's Inventory
            Inventory.addProduct(newProduct);

            // Add any associated parts to the product
            for (Part localAssociatedPart : localAssociatedParts) {
                newProduct.addAssociatedPart(localAssociatedPart);
            }

            // Close the current window and open the main application window
            MainApplication helloInst = new MainApplication();
            helloInst.start(new Stage());
            Stage stage = (Stage) saveBtn.getScene().getWindow();
            stage.close();

    }

    /**
     *
     * Call if the Cancel Button is clicked
     * Closes the current window without saving the data for this product
     * @throws IOException may throw exception
     */
    public void onCancel() throws IOException {
        // Open main application window
        MainApplication helloInst = new MainApplication();
        helloInst.start(new Stage());
        // Close the current window without saving data
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Call if Search Field for Parts is used
     * Searches all parts in the inventory and returns a list of those with corresponding
     * substrings or ids
     */
    public void searchPartsTable() {
        // Create a variable to store the data to be searched for
        String queryParts = searchField.getText();
        // Create a list to store a list of Parts with corresponding strings or ids
        // Call the Inventory lookupPart method with the parameter queryParts

        ObservableList<Part> partResults = Inventory.lookupPart(queryParts);
        /// If the returned list is empty, search for a matching int id
        if (partResults.size() == 0) {
            try {
                // May throw IOException from integer parsing
                int partId = Integer.parseInt(queryParts);
                Part part = Inventory.lookupPart(partId);
                // Add the part to the list
                if (part != null) {
                    partResults.add(part);
                }
            } catch (NumberFormatException e) {
                // If queryParts is non-numeric, search by string
                Inventory.lookupPart(queryParts);
            }
        }
        // If no parts are found, alert the user
        if (partResults.size() == 0) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Not Found");
            alert.setHeaderText("Part Not Found");
            alert.setContentText("Specified part could not be found");
            alert.showAndWait();

        } else {
            // If parts are found, update the table with the corresponding objects
            addPartTable.setItems(partResults);

        }


    }
}