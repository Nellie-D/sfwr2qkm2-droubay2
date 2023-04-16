package droubay.sfwr1qkm2droubay2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;


/**
 *
 * @author Danielle Droubay
 * @version 1.0
 *
 */

public class Inventory {

    /**
     * Class members
     */

    // allParts stores a list of objects, Parts, both InHouse and Outsourced
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    // allProducts stores a list of objects, Products
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     *
     * @param newPart the part to be added
     */
    public static void addPart(Part newPart){

        allParts.addAll(newPart);
    }

    /**
     *
     * @param newProduct the product to be added
     */

    public static void addProduct(Product newProduct){

        allProducts.addAll(newProduct);
    }

    /**
     *
     * @param partId the part id that corresponds to the part to be looked up
     * @return the part that corresponds to the parameter partId
     */
    public static Part lookupPart(int partId){

        // Iterate through the all the parts in the store's inventory
        // Compare the current id to the given partId

        for (int i = 0; i < allParts.size(); i++) {
            Part part = allParts.get(i);

            // Return the part if a match is found

            if (part.getId() == partId) {
                return part;
            }
        }
        // If a match is not found, return null

        return null;

    }

    /**
     *
     * @param productId the productId that corresponds to the product to be looked up
     * @return the Product that corresponds to the given parameter productId
     */
    public static Product lookupProduct(int productId){

        // Iterate through all the products the store has in inventory
        // Compare the current id to the given productId

        for(int i =0; i < allProducts.size(); i++){
            Product product = allProducts.get(i);

            // If a match is found, return the current product

            if(product.getId() == productId){
                return product;
            }
        }

        // If a match is not found, return null

        return null;


    }

    /**
     *
     * @param partName the partName that corresponds to the part to be looked up
     * @return the parts that have corresponding character strings and substrings
     */
    public static ObservableList<Part> lookupPart(String partName){
        // Create a list to hold the parts with matching strings and substrings

        ObservableList<Part> partNameList = FXCollections.observableArrayList();

        // Iterate through the parts in the inventory

        for(Part part : allParts){

            // If the current part contains string or susbtring partName, add it to the list

            if(part.getName().contains(partName)){
                partNameList.add(part);
            }
        }

        // Return a list of the parts that contain the specified string or substring

        return partNameList;
    }

    /**
     *
     * @param productName the productName that corresponds to the product to be looked up
     * @return the parts that have corresponding character strings and substrings
     */
    public static ObservableList<Product> lookupProduct(String productName){

        // Create a list to hold the products with matching strings and substrings

        ObservableList<Product> productNames = FXCollections.observableArrayList();

        // Iterate through the products in the inventory

        for(Product product : allProducts){

            // If the current part contains string or susbtring partName, add it to the list

            if(product.getName().contains(productName)){
                productNames.add(product);

            }
        }

        // Return a list of the parts that contain the specified string or substring

        return productNames;

    }

    /**
     *
     * @param index the index of the specified part to be updated
     * @param selectedPart the updated part to be placed at the given index
     */
    public static void updatePart(int index, Part selectedPart){

        /* Set the item at the specified index to be the part specified in
        the ModifyPartView Class, in the onSave method*/

        allParts.set(index, selectedPart);
    }

    /**
     *
     * @param index the index of the specified product to be updated
     * @param selectedProduct the updated product to be placed at the given index
     */
    public static void updateProduct(int index, Product selectedProduct){

        /* Set the item at the specified index to be the part specified in
        the ModifyProductView Class, in the onSave method*/

        allProducts.set(index, selectedProduct);
    }

    /**
     *
     * @param selectedPart the part to be removed from inventory
     * @return whether the part has been removed
     */
    public static boolean deletePart(Part selectedPart){

        // Dialogue box to confirm whether user wants to delete part

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Delete Part?");
        alert.setContentText("Do you want to delete this part");
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonCancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonYes, buttonCancel);
        Optional<ButtonType> result = alert.showAndWait();


        if (result.get() == buttonYes){
            // If yes, remove part and return true
            allParts.remove(selectedPart);
            return true;
        } else if (result.get() == buttonCancel){
            // Return false if user cancels dialogue box
            return false;
        } else {
            // Return false if user closes dialogue box
            return false;
        }

    }

    /**
     *
     * @param selectedProduct the product to be removed from inventory
     * @return whether the part has been removed
     */

    public static boolean deleteProduct(Product selectedProduct){

        // Dialogue box to confirm whether the user wants to delete product

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete Product");
        alert.setHeaderText("Delete Product?");
        alert.setContentText("Do you want to delete this product");
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonCancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonYes, buttonCancel);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == buttonYes){

            // If yes, return true and remove product from inventory

            allProducts.remove(selectedProduct);

            return true;
        } else if (result.get() == buttonCancel){
            // Return false if user cancels dialogue box
            return false;
        } else {
            //Return false if user closes dialogue box
            return false;
        }

    }

    /**
     *
     * @return a list of all parts in the inventory
     */
    public static ObservableList<Part> getAllParts(){
        //return a list of all parts
        return allParts;
    }

    /**
     *
     * @return a list of al products in the inventory
     */
    public static ObservableList<Product> getAllProducts(){
        //return a list of all products
        return allProducts;
    }


}
