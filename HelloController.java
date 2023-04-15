package droubay.sfwr1qkm2droubay2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    static Part placeHolder;
    public TableColumn<Object, Object> partIDColumn;
    public TableColumn<Object, Object> partNameColumn;
    public TableColumn<Object, Object> invLevelColumn;
    public TableColumn<Object, Object> pricePerUnitColumn;
    public Button onAddClickProdBtn;
    public Button onAddClickBtn;
    public TableView<Part> partsTable;
    public Button onDeletePart;
    public Button onDeleteProduct;
    public Button onModProduct;
    public TableView<Product> productTable;
    public TableColumn<Object, Object> productIDColumn;
    public TableColumn<Object, Object> productNameColumn;
    public TableColumn<Object, Object> invLevelColumnProduct;
    public TableColumn<Object, Object> priceProductColumn;
    public TextField productSearch;
    public TextField partSearch;
    public Label productDelError;
    AddPartView addPartViewInst = new AddPartView();
    AddProductView addProductInst = new AddProductView();

    private static boolean initDataAdd = true;

    private void testDataAdd() {
        if (!initDataAdd) {
            return;
        }
        initDataAdd = false;
        InHouse a = new InHouse(AddPartView.id++, "Steel Plate", 3.33, 3, 4, 5, 6);

        Outsourced b = new Outsourced(AddPartView.id++, "Gear", 9.99, 44, 0, 10, "Mario's");

        InHouse aOne = new InHouse(AddPartView.id++, "Wires", 31.99, 4, 7, 10, 9);

        Outsourced bOne = new Outsourced(AddPartView.id++, "Chip", 17.01, 8, 89, 1, "Waluigi's");

        Inventory.addPart(a);
        Inventory.addPart(b);
        Inventory.addPart(aOne);
        Inventory.addPart(bOne);

        Product c = new Product(AddProductView.id++, "Lemontine", 10.87, 76, 0, 99);

        Product d = new Product(AddProductView.id++, "Orangetine", 19.21, 28, 2, 100);

        Product cOne = new Product(AddProductView.id++, "Limetine", 10.99, 100, 1, 75);

        Product dOne = new Product(AddProductView.id++, "Clementine", 17.77, 20, 54, 109);

        Inventory.addProduct(c);
        Inventory.addProduct(d);
        Inventory.addProduct(cOne);
        Inventory.addProduct(dOne);
    }

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


    public void onAddClickedPart(ActionEvent actionEvent) throws IOException {
        addPartViewInst.start(new Stage());

        Stage stage = (Stage) onAddClickBtn.getScene().getWindow();
        stage.close();
    }

    public void onAddClickedProduct(ActionEvent actionEvent) throws IOException {
        addProductInst.start(new Stage());


        Stage stage = (Stage) onAddClickProdBtn.getScene().getWindow();
        stage.close();
    }

    public void onExitClicked(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void onModClickedPart(ActionEvent actionEvent) throws IOException {
        Part placeHolder = partsTable.getSelectionModel().getSelectedItem();

        if (placeHolder == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-part-view.fxml"));
        Parent root = loader.load();

        ModifyPartView localModPartView = loader.getController();
        localModPartView.modifyPart(placeHolder);

        Stage stage = new Stage();
        stage.setTitle("Modify Part");
        stage.setScene(new Scene(root));
        stage.show();
        Stage currStage = (Stage) onAddClickBtn.getScene().getWindow();
        currStage.close();

    }

    public void onDeletePartBtn(ActionEvent actionEvent) throws IOException {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Delete Part?");
        alert.setContentText("Do you want to delete this part");
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonCancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonYes, buttonCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            Inventory.deletePart(selectedPart);
        } else if (result.get() == buttonCancel){
            return;
        } else {
            return;
        }


    }

    public void onDeleteClickedProduct(ActionEvent actionEvent) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            return;
        }
        if(!selectedProduct.getAllAssociatedParts().isEmpty()){
            productDelError.setText("Cannot delete product that has associated part");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Delete Product?");
            alert.setContentText("Do you want to delete this product");
            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonCancel = new ButtonType("Cancel");
            alert.getButtonTypes().setAll(buttonYes, buttonCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonYes){
                Inventory.deleteProduct(selectedProduct);
            } else if (result.get() == buttonCancel){
                return;
            } else {
                return;
            }
            productDelError.setText("");

        }
    }

    public void onModClickedProduct(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();


        if (selectedProduct == null) {
            return;
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-product-view.fxml"));
        Parent root = loader.load();

        ModifyProductView localModProductView = loader.getController();
        localModProductView.modifyProduct(selectedProduct);

        Stage stage = new Stage();
        stage.setTitle("Modify Product");
        stage.setScene(new Scene(root));
        stage.show();
        Stage currStage = (Stage) onModProduct.getScene().getWindow();
        currStage.close();
    }

    public void searchPartsTable(ActionEvent actionEvent){
        String queryParts = partSearch.getText();

        ObservableList<Part> partResults = searchPartByName(queryParts);

        if(partResults.size() == 0){
            try {
                int partId = Integer.parseInt(queryParts);
                Part part = getPartById(partId);
                if (part != null) {
                    partResults.add(part);
                }
            } catch (NumberFormatException e){
                searchPartByName(queryParts);
            }
        }
        if(partResults.size() == 0){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Not Found");
            alert.setHeaderText("Part Not Found");
            alert.setContentText("Specified part could not be found");
            alert.showAndWait();

        } else {
            partsTable.setItems(partResults);

        }

    }

    private ObservableList<Part> searchPartByName(String partialPart) {
        ObservableList<Part> partNames = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part part : allParts){
            if(part.getName().contains(partialPart)){
                partNames.add(part);
            }
        }

        return partNames;
    }

    private Part getPartById(Integer partId) {
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }
    public void searchProductTable(ActionEvent actionEvent) {
        String queryProducts = productSearch.getText();

        ObservableList<Product> productResults = searchProductByName(queryProducts);

        if(productResults.size() == 0){
            try {
                int productId = Integer.parseInt(queryProducts);
                Product product = getProductById(productId);
                if (product != null) {
                    productResults.add(product);
                }
            } catch (NumberFormatException e){
                searchProductByName(queryProducts);
            }
        }
        if (productResults.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Not Found");
            alert.setHeaderText("Product Not Found");
            alert.setContentText("Specified product could not be found");
            alert.showAndWait();
        } else {
            productTable.setItems(productResults);

        }
    }

    private ObservableList<Product> searchProductByName(String partialProduct) {
        ObservableList<Product> productNames = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product product : allProducts){
            if(product.getName().contains(partialProduct)){
                productNames.add(product);

            }
        }

        return productNames;
    }

    private Product getProductById(Integer productId){
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(int i =0; i < allProducts.size(); i++){
            Product product = allProducts.get(i);
            if(product.getId() == productId){
                return product;
            }
        }
        return null;
    }
}