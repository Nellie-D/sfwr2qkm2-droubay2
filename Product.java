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
 */
public class Product {

    /**
     * Class members
     */

    // associatedParts stores a list of parts associated with a Part object
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for a Product object
     * Creates a new Product object built of the following parameters
     * @param id the id of the Product object
     * @param name the name of the Product object
     * @param price the price of the Product object
     * @param stock the stock (or inventory) of the Product object
     * @param min the minimum of the Product object
     * @param max the maximum of the Product object
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }


    /**
     * @return the id
     */

    public int getId() { return id; }


    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }
    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }


    /**
     *
     * @param part add an associated part to product
     */


    public void addAssociatedPart(Part part){

        associatedParts.add(part);

    }

    /**
     * @param part the part to be removed from the list of Product objects associated parts
     * @return whether the part has been removed
     */

    public static boolean deleteAssociatedPart(Part part){

        // Dialogue box to confirm whether the user wants to remove pat

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
            associatedParts.remove(part);
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
     * @return a list of parts associated with a Product
     */

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}