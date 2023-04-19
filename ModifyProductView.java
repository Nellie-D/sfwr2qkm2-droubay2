package droubay.sfwr1qkm2droubay2;

import javafx.application.Application;
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
 * A class for product modification
 * @author Danielle Droubay
 * @version 1.0
 */
public class ModifyProductView extends Application implements Initializable {


    /**
     * Object instantiation of Product Class for local use and transaction
     */

    private Product localProduct;
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
     * Displays message to update deletion status
     */
    public Label deleteMsg;
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
     * Table of associated parts that can be added to a product
     */
    public TableView<Part> addPartTable;
    /**
     * Table of associated parts that can be removed from a product
     */
    public TableView<Part> removePartTable;
    /**
     * Add associated part button
     */
    public Button addBtn;
    /**
     * Remove associated part button
     */
    public Button removeBtn;
    /**
     * Save modified product button
     */
    public Button saveBtn;
    /**
     * Cancel button
     */
    public Button cancelBtn;
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
        // Open modifyProductView window as an addProductView
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

    }

    /**
     * Call if the Add Part button is clicked
     * Adds an associated part to the product
     */
    public void onAddPart() {
        // Create a variable to store the selected Part to be added
        Part placeHolder = addPartTable.getSelectionModel().getSelectedItem();

        // If no parts are selected, return
        if (placeHolder == null){
            return;
        }
        // Add the selected part to the Product's list of associated parts

        localProduct.addAssociatedPart(placeHolder);


    }

    /**
     * Call if the Remove Part button is clicked
     * Removes an associated part from the product
     */
    public void onRemovePart() {
        // Create a variable to store the selected part to be removed
        Part placeHolderPart = removePartTable.getSelectionModel().getSelectedItem();
        // If no parts are selected, return
        if(placeHolderPart == null){
            return;
        }
        /* If the part is successfully removed from the product's list of associated parts, show no message.
        Otherwise, inform the user that the part was not removed */
        if(Product.deleteAssociatedPart(placeHolderPart)){
            deleteMsg.setText("");
        } else {
            deleteMsg.setText("Part not deleted");
        }

    }

    /**
     * Call if the Save Button is clicked
     * Saves the updated data for this product
     * @throws IOException may throw exception from user input
     */
    public void onSave() throws IOException {
        // Create a variable for iterating through the list of all products in store's inventory
        ObservableList<Product> localAllProducts = Inventory.getAllProducts();
        // Start a try, as user input may lead to IOException
        try {
            // Iterate through the list of all products until the current product matches the product to be modified
            for (int i = 0; i < localAllProducts.size(); i++) {
                if (localAllProducts.get(i) == localProduct) {
                    // Try to set the name
                    localProduct.setName(nameField.getText());
                    // Try to set the price, and ensure it contains a decimal
                    localProduct.setPrice(Double.parseDouble(priceField.getText()));
                    if(!priceField.getText().contains(".")){
                        throw new NumberFormatException("Unable to create product");
                    }
                    // Try to set the stock (or inventory)
                    localProduct.setStock(Integer.parseInt(invField.getText()));
                    // Try to set the minimum
                    localProduct.setMin(Integer.parseInt(minField.getText()));
                    // Try to set the maximum
                    localProduct.setMax(Integer.parseInt(maxField.getText()));

                    // Ensure that maximum is greater than minimum, and that inventory is between max and min
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
                    // If no exceptions thrown, update the product at index i to the updated Product localProduct
                    Inventory.updateProduct(i, localProduct);
                }

            }
            // If exceptions are thrown, handle accordingly
        }  catch (NumberFormatException e) {
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
                    // Ensure that priceField contains a decimal point (is a double)
                    if (priceField.getText().contains(".")) {
                        // Throw exception is Double parsing is not possible (is a string)
                        double isDouble = Double.parseDouble(priceField.getText()) + 1.0;
                        // Clear the error message if there are no exceptions raised
                        priceError.setText("");
                        // Inform the user that priceField must be numeric and of the format 'x.x'
                    } else {
                        priceError.setText("Price must be numeric and of the format 'x.xx'");
                        throw new Exception("Unable to modify product");

                    }
                    // Catch the exception thrown in the else statement and handle accordingly
                } catch (Exception f) {
                    priceError.setText("Price must be numeric and of the format 'x.xx'");

                }
            }
            // Do not allow invField to be blank
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
                        throw new NumberFormatException("Unable to modify product");
                    } else {
                        minError.setText("");
                    }
                    // Handle raised exceptions for Integer parsing or invalid numeric value
                } catch (NumberFormatException h) {
                    minError.setText("Minimum must be numeric and less than maximum");
                }
            }

            // Do not allow maximum to be blank
            if (maxField.getText().isBlank()) {
                maxError.setText("Maximum cannot be blank");
            } else {
                try {
                    // Ensure that maximum is numeric and greater than minimum
                    int isInt = Integer.parseInt(maxField.getText()) * 2;
                    if (Integer.parseInt(maxField.getText()) < Integer.parseInt(minField.getText())){
                        throw new NumberFormatException("Unable to modify product");
                    } else {
                        maxError.setText("");
                    }
                    // Handle raised exceptions for Integer parsing or invalid numeric value
                } catch (NumberFormatException i) {
                    maxError.setText("Maximum must be numeric and greater than minimum");
                }
            }
            // If exceptions raised, inform the user that the product has not been modified and return
            exceptionMsg.setText("Unable to modify product. Please ensure data types are valid and/or not blank");
            return;
        }
        // If no exceptions raised, save the data and return to the main application window
        MainApplication helloInst = new MainApplication();
        helloInst.start(new Stage());
        // Close the current window
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Call if the Cancel Button is clicked
     * Closes the current window without saving the modified data for this product
     * @throws IOException may throw exception
     */
    public void onCancel() throws IOException {
        // Open main application window
        MainApplication helloInst = new MainApplication();
        helloInst.start(new Stage());
        // Close the current window without saving modified data
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Called in the main application when Modify Product button is clicked
     * Initializes the fields with the data for the selected product
     * @param selectedProduct the product to be modified
     */

    public void modifyProduct(Product selectedProduct) {

        // Initialize the localProduct object with the selectedProduct passed from the main window
        localProduct = selectedProduct;
        // Initialize the fields
        idField.setText(String.valueOf(selectedProduct.getId()));
        nameField.setText(String.valueOf(selectedProduct.getName()));
        priceField.setText(String.valueOf(selectedProduct.getPrice()));
        invField.setText(String.valueOf(selectedProduct.getStock()));
        minField.setText(String.valueOf(selectedProduct.getMin()));
        maxField.setText(String.valueOf(selectedProduct.getMax()));
        // Initialize the associated part table
        removePartTable.setItems(localProduct.getAllAssociatedParts());
        removePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        removePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        removePartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        removePartInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }

    /**
     * Call if Search Field for Parts is used
     * Searches all parts in the inventory and returns a list of those with corresponding
     * substrings or ids
     */
    public void searchPartsTable(){
        // Create a variable to store the data to be searched for
        String queryParts = searchField.getText();
        // Create a list to store a list of Parts with corresponding strings or ids
        // Call the Inventory lookupPart method with the parameter queryParts

        ObservableList<Part> partResults = Inventory.lookupPart(queryParts);
        /// If the returned list is empty, search for a matching int id
        if(partResults.size() == 0){
            try {
                // May throw IOException from integer parsing
                int partId = Integer.parseInt(queryParts);
                Part part = Inventory.lookupPart(partId);
                // Add the part to the list
                if (part != null) {
                    partResults.add(part);
                }
            } catch (NumberFormatException e){
                // If queryParts is non-numeric, search by string
                Inventory.lookupPart(queryParts);
            }
        }
        // If no parts are found, alert the user
        if(partResults.size() == 0){

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


