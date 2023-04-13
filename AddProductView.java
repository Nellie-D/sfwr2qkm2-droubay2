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
import java.lang.reflect.Type;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class AddProductView extends Application implements Initializable {

    public TextArea errorCheckArea;
    public Label nameError;
    public Label priceError;
    public Label inventoryError;
    public Label minError;
    public Label maxError;
    public Label exceptionMsg;
    private ObservableList<Part> localAssociatedParts = FXCollections.observableArrayList();

    private Product localProduct;

    public static int id;
    public Button addBtn;
    public Button removeBtn;
    public Button saveBtn;
    public Button cancelBtn;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public TableView<Part> addPartTable;
    public TableView<Part> removePartTable;
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

        removePartTable.setItems(localAssociatedParts);
        removePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        removePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        removePartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        removePartInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));


    }



    public static void main(String[] args) {
        launch();
    }

    public void onAddPart(ActionEvent actionEvent) {
        boolean allValid;

        try {
            Part placeHolder = addPartTable.getSelectionModel().getSelectedItem();


            if (placeHolder == null) {
                return;
            }

            if (nameField.getText().isBlank()) {
                allValid = false;

                throw new Exception("blank");

            }
            if (priceField.getText().isBlank()) {
                allValid = false;

                throw new Exception("blank");

            }
            if (invField.getText().isBlank()) {
                allValid = false;
                throw new Exception("blank");

            }
            if (minField.getText().isBlank()) {
                allValid = false;

                throw new Exception("blank");

            }
            if (maxField.getText().isBlank()) {
                allValid = false;

                throw new Exception("blank");

            }


            localAssociatedParts.add(placeHolder);
        } catch (Exception e) {
            exceptionMsg.setText("Unable to add part to product. Please ensure data types are valid and/or not blank before adding parts");

            return;
        }
    }

    public void onRemovePart(ActionEvent actionEvent) {
        Part placeHolder = removePartTable.getSelectionModel().getSelectedItem();

        if(placeHolder == null){
            return;
        }
        localProduct.deleteAssociatedPart(placeHolder);
    }



    public void onSave(ActionEvent actionEvent) throws IOException {
        boolean allValid = false;
        String name;
        Double price;
        Integer inventory;
        Integer minimum;
        Integer maximum;

            try {
                name = nameField.getText();
                if (nameField.getText().isBlank()) {
                    nameError.setText("Name cannot be blank");
                    throw new NumberFormatException("Unable to create new product");
                }
                else {
                    nameError.setText("");
                }
                price = Double.parseDouble(priceField.getText());
                if(!priceField.getText().contains(".")){
                    throw new NumberFormatException("Unable to create new product");
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
            } catch (NumberFormatException e) {

                if (nameField.getText().isBlank()) {
                    nameError.setText("Name cannot be blank");
                }
                else {
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
                            throw new NumberFormatException("Unable to create new product");
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

                exceptionMsg.setText("Unable to create new product");
                return;
            }


            Product newProduct = new Product(id++, name, price, inventory, minimum, maximum);
            Inventory.addProduct(newProduct);

            for (Part localAssociatedPart : localAssociatedParts) {
                newProduct.addAssociatedPart(localAssociatedPart);
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
}



