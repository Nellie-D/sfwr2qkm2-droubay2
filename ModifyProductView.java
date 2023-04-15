package droubay.sfwr1qkm2droubay2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductView extends Application implements Initializable {


    public TextField partAddSearch;
    public Label nameError;
    public Label priceError;
    public Label inventoryError;
    public Label minError;
    public Label maxError;
    public Label exceptionMsg;
    private Product localProduct;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public TableView<Part> addPartTable;
    public TableView<Part> removePartTable;
    public Button addBtn;
    public Button removeBtn;
    public Button saveBtn;
    public Button cancelBtn;
    public TextField searchField;
    public TableColumn<Object, Object> addPartIDColumn;
    public TableColumn<Object, Object> addPartNameColumn;
    public TableColumn<Object, Object> addPartInvLevelColumn;
    public TableColumn<Object, Object> addPartPriceColumn;
    public TableColumn<Object, Object> removePartIdColumn;
    public TableColumn<Object, Object> removePartNameColumn;
    public TableColumn<Object, Object> removePartInvLevelColumn;
    public TableColumn<Object, Object> removePartPriceColumn;

    @Override
    public void start(Stage stage)  throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-product-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 822, 505);
        stage.setTitle("addproductview");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        addPartTable.setItems(Inventory.getAllParts());
        addPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        addPartInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));



    }
    public void onAddPart(ActionEvent actionEvent) {
        Part placeHolder = addPartTable.getSelectionModel().getSelectedItem();


        if (placeHolder == null){
            return;
        }
        localProduct.addAssociatedPart(placeHolder);


    }

    public void onRemovePart(ActionEvent actionEvent) {
        Part placeHolderPart = removePartTable.getSelectionModel().getSelectedItem();

        if(placeHolderPart == null){
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Remove Part");
        alert.setHeaderText("Remove Part?");
        alert.setContentText("Do you want to remove this part");
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonCancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonYes, buttonCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonYes){
            localProduct.deleteAssociatedPart(placeHolderPart);
        } else if (result.get() == buttonCancel){
            return;
        } else {
            return;
        }

    }

    public void onSave(ActionEvent actionEvent) throws IOException {
        ObservableList<Product> localAllProducts = Inventory.getAllProducts();

        try {
            for (int i = 0; i < localAllProducts.size(); i++) {
                if (localAllProducts.get(i) == localProduct) {
                    localProduct.setName(nameField.getText());
                    localProduct.setPrice(Double.parseDouble(priceField.getText()));
                    if(!priceField.getText().contains(".")){
                        throw new NumberFormatException("Unable to create product");
                    }
                    localProduct.setStock(Integer.parseInt(invField.getText()));
                    localProduct.setMin(Integer.parseInt(minField.getText()));
                    localProduct.setMax(Integer.parseInt(maxField.getText()));
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
                    Inventory.updateProduct(i, localProduct);
                }

            }
        }  catch (NumberFormatException e) {

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
                inventoryError.setText("Inventory cannot be blank");

            } else {
                try {
                    int isInt = Integer.parseInt(invField.getText()) * 2;
                    if(Integer.parseInt(invField.getText()) > Integer.parseInt(maxField.getText())
                            || Integer.parseInt(invField.getText()) < Integer.parseInt(minField.getText())){
                        throw new Exception("Inventory must be a value between maximum and minimum");
                    } else {
                        inventoryError.setText("");
                    }
                } catch (NumberFormatException g) {
                    inventoryError.setText("Inventory must be numeric");
                } catch (Exception a){
                    inventoryError.setText(a.getMessage());
                }
            }

            if (minField.getText().isBlank()) {
                minError.setText("Minimum cannot be blank");
            } else {
                try {
                    int isInt = Integer.parseInt(minField.getText()) * 2;
                    if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText())){
                        throw new NumberFormatException("can;t");
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
                        throw new NumberFormatException("Unable to modify product");
                    } else {
                        maxError.setText("");
                    }
                } catch (NumberFormatException i) {
                    maxError.setText("Maximum must be numeric and greater than minimum");
                }
            }
            exceptionMsg.setText("Unable to modify product. Please ensure data types are valid and/or not blank");
            return;
        }
        HelloApplication helloInst = new HelloApplication();
        helloInst.start(new Stage());

        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        HelloApplication helloInst = new HelloApplication();
        helloInst.start(new Stage());
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void modifyProduct(Product selectedProduct) {


        localProduct = selectedProduct;
        nameField.setText(String.valueOf(selectedProduct.getName()));
        priceField.setText(String.valueOf(selectedProduct.getPrice()));
        invField.setText(String.valueOf(selectedProduct.getStock()));
        minField.setText(String.valueOf(selectedProduct.getMin()));
        maxField.setText(String.valueOf(selectedProduct.getMax()));

        removePartTable.setItems(localProduct.getAllAssociatedParts());
        removePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        removePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        removePartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        removePartInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
    public void searchPartsTable(ActionEvent actionEvent){
        String queryParts = searchField.getText();

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
           addPartTable.setItems(partResults);

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
}


