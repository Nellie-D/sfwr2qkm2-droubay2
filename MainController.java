package droubay.sfwr1qkm2droubay2;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Danielle Droubay
 * @version 1.0
 */
public class MainController implements Initializable {

    /**
     * Column of part ids
     */
    public TableColumn<Object, Object> partIDColumn;
    /**
     * Column of part names
     */
    public TableColumn<Object, Object> partNameColumn;
    /**
     * Column of part inventory
     */
    public TableColumn<Object, Object> invLevelColumn;
    /**
     * Column of part prices
     */
    public TableColumn<Object, Object> pricePerUnitColumn;
    /**
     * Add product button
     */
    public Button onAddClickProdBtn;
    /**
     * Add part button
     */
    public Button onAddClickBtn;
    /**
     * The part table
     */
    public TableView<Part> partsTable;
    /**
     * Delete part button
     */
    public Button onDeletePart;
    /**
     * Delete product button
     */
    public Button onDeleteProduct;
    /**
     * Modify product button
     */
    public Button onModProduct;
    /**
     * The product table
     */
    public TableView<Product> productTable;
    /**
     * Column of product ids
     */
    public TableColumn<Object, Object> productIDColumn;
    /**
     * Column of product names
     */
    public TableColumn<Object, Object> productNameColumn;
    /**
     * Column of product inventory
     */
    public TableColumn<Object, Object> invLevelColumnProduct;
    /**
     * Column of product price
     */
    public TableColumn<Object, Object> priceProductColumn;
    /**
     * A search field for products
     */
    public TextField productSearch;
    /**
     * A search field for parts
     */
    public TextField partSearch;
    /**
     * Displays error message if product cannot be deleted
     */
    public Label productDelError;
    /**
     * Displays a message to update deletion status
     */
    public Label deleteMsg;

    /**
     * Instantiation of addPartView class
     */
    AddPartView addPartViewInst = new AddPartView();

    /**
     * Instantiation of addProductView class
     */
    AddProductView addProductInst = new AddProductView();

    /**
     * initDataAdd informs the application whether test data has been added yet
     * This ensures that the test data is not initialized every time the main application is launched per program run
      */
    private static boolean initDataAdd = true;

    /**
     * Call during initialization.
     * Adds test data to the tables to allow for functionality testing and demonstration
     */
    private void testDataAdd() {
        // Return if initial data has been added once this program run
        if (!initDataAdd) {
            return;
        }
        initDataAdd = false;

        // Create test data for InHouse and Outsourced objects

        InHouse a = new InHouse(AddPartView.id++, "Steel Plate", 3.33, 3, 0, 100, 6);

        Outsourced b = new Outsourced(AddPartView.id++, "Gear", 9.99, 44, 0, 100, "Coleman");

        InHouse aOne = new InHouse(AddPartView.id++, "Wires", 31.99, 99, 5, 100, 9);

        Outsourced bOne = new Outsourced(AddPartView.id++, "Chip", 17.01, 8, 5, 100, "Andy's");
        // Add the objects to the Inventory
        Inventory.addPart(a);
        Inventory.addPart(b);
        Inventory.addPart(aOne);
        Inventory.addPart(bOne);

        // Create test data for Product objects
        Product c = new Product(AddProductView.id++, "Regular Bundle", 10.15, 76, 0, 95);

        Product d = new Product(AddProductView.id++, "Deluxe Bundle", 31.21, 28, 2, 100);

        Product cOne = new Product(AddProductView.id++, "Premium Bundle", 76.33, 50, 1, 75);

        Product dOne = new Product(AddProductView.id++, "Supreme Bundle", 150.77, 75, 54, 110);
        // Add the objects to the Inventory
        Inventory.addProduct(c);
        Inventory.addProduct(d);
        Inventory.addProduct(cOne);
        Inventory.addProduct(dOne);
    }

    /**
     * Initialize the application and call testDataAdd method
     * This also establishes cell factories to fill the partsTable and productTable
     * @param url pass the url to the initializer
     * @param resourceBundle pass the resourceBundle to the initializer
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        testDataAdd();

        partsTable.setItems(Inventory.getAllParts());

        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        invLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));


        productTable.setItems(Inventory.getAllProducts());

        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceProductColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        invLevelColumnProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));


    }

    /**
     * Call if the Add Part button is clicked
     * This calls the addPartView class
     * @throws IOException may throw exception
     */
    public void onAddClickedPart() throws IOException {
        // Open an AddPartView window
        addPartViewInst.start(new Stage());
        // Close the current window
        Stage stage = (Stage) onAddClickBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Call if the Add Product button is clicked
     * This calls the addProductView class
     * @throws IOException may throw exception
     */
    public void onAddClickedProduct() throws IOException {
        // Open an AddProductView window
        addProductInst.start(new Stage());
        // Close the current window
        Stage stage = (Stage) onAddClickProdBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Call if the exit button is clicked
     * Closes the application
     */
    public void onExitClicked() {
        // Close the program
        System.exit(0);
    }

    /**
     * Call if the Modify Part button is clicked
     * This calls the modifyPartView class
     * @throws IOException may throw exception
     */
    public void onModClickedPart() throws IOException {

        // Create a placeHolder part to pass the selected Part to the new modifyPartView class
        Part placeHolder = partsTable.getSelectionModel().getSelectedItem();
        // If no part is selected, return
        if (placeHolder == null) {
            return;
        }
        // Establish the modifyPartView window to open
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-part-view.fxml"));
        Parent root = loader.load();

        // Call the modifyPartView method modifyPart with parameter placeHolder

        ModifyPartView localModPartView = loader.getController();
        localModPartView.modifyPart(placeHolder);

        // Open the Modify Part window and close the current window

        Stage stage = new Stage();
        stage.setTitle("Modify Part");
        stage.setScene(new Scene(root));
        stage.show();
        Stage currStage = (Stage) onAddClickBtn.getScene().getWindow();
        currStage.close();

    }

    /**
     * Call if the Delete Part button is clicked
     * Deletes the selected Part from Inventory
     */
    public void onDeletePartBtn() {

        // Create a variable to carry the selected part
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        // If not part selected, return
        if (selectedPart == null) {
            return;
        }
        // If the part is successfully deleted, show no message or erase previous message
        if(Inventory.deletePart(selectedPart)){
            deleteMsg.setText("");
        } else {
            // If part is not deleted, inform the user
            deleteMsg.setText("Part not deleted");
        }


    }

    /**
     * Call if the Delete Product button is clicked
     * Delete the selected Product from Inventory
     */
    public void onDeleteClickedProduct() {
        // Create a variable to store the selected product
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        // If no product selected, return
        if (selectedProduct == null) {

            return;
        }
        // If the selected product has associated parts, do not delete product
        if(!selectedProduct.getAllAssociatedParts().isEmpty()){
            productDelError.setText("Cannot delete product that has associated part");
        } else {
            // Attempt to delete product
            if(Inventory.deleteProduct(selectedProduct)){
                productDelError.setText("");
            } else {
                // If product not delete, inform user
                productDelError.setText("Product not deleted");
            }
        }
    }

    /**
     * Call if the Modify Product button is clicked
     * This calls the ModifyProductView class
     * @throws IOException may throw exception
     */
    public void onModClickedProduct() throws IOException {
        // Create a variable to store the selected product
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

        // If no product selected, return
        if (selectedProduct == null) {
            return;
        }

        // Establish the window to be opened
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-product-view.fxml"));
        Parent root = loader.load();
        // Call the modifyProductView method modifyProduct with parameter selectedProduct
        ModifyProductView localModProductView = loader.getController();
        localModProductView.modifyProduct(selectedProduct);
        // Open the Modify Product window and close the current window
        Stage stage = new Stage();
        stage.setTitle("Modify Product");
        stage.setScene(new Scene(root));
        stage.show();
        Stage currStage = (Stage) onModProduct.getScene().getWindow();
        currStage.close();
    }

    /**
     * Call if the Search Part Field is used
     * Searches a list of all the parts in the inventory and returns
     * a list of parts with corresponding substrings or ids
     */
    public void searchPartsTable(){
        // Create a variable to store the data to search for
        String queryParts = partSearch.getText();
        // Create a list that calls Inventory method lookupPart with parameter queryParts
        // This will search for a matching string queryParts
        ObservableList<Part> partResults = Inventory.lookupPart(queryParts);

        // If the returned list is empty, search for a matching int id
        if(partResults.size() == 0){
            // May throw IOException from integer parsing
            try {
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
            partsTable.setItems(partResults);
        }

    }

    /**
     * Call if the Search Product Field is used
     * Searches a list of all the products in the inventory and returns
     * a list of products with the corresponding substrings or ids
     */
    public void searchProductTable() {
        // Create a variable to store the data to search for
        String queryProducts = productSearch.getText();
        // Create a list that calls Inventory method lookupProduct with parameter queryProducts
        // This will search for a matching string queryProducts
        ObservableList<Product> productResults = Inventory.lookupProduct(queryProducts);
        // If the returned list is empty, search for a matching int id
        if(productResults.size() == 0){
            // May throw IOException from integer parsing
            try {
                int productId = Integer.parseInt(queryProducts);
                Product product = Inventory.lookupProduct(productId);
                // Add the product to the list
                if (product != null) {
                    productResults.add(product);
                }
                // If queryProducts is non-numeric, search by string
            } catch (NumberFormatException e){
                Inventory.lookupProduct(queryProducts);
            }
        }
        // If no products are found, alert the user
        if (productResults.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Not Found");
            alert.setHeaderText("Product Not Found");
            alert.setContentText("Specified product could not be found");
            alert.showAndWait();
        } else {
            // If products are found, update the table with the corresponding objects
            productTable.setItems(productResults);

        }
    }


}