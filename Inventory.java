package droubay.sfwr1qkm2droubay2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author Danielle Droubay
 */

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();


    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart){

        allParts.addAll(newPart);
    }

    public static void addProduct(Product newProduct){

        allProducts.addAll(newProduct);
    }

  /*public static Part lookupPart(int partId){


        // return part that belongs to partId
    }

    public static Product lookupProduct(int productId){
        //return product that belongs to productId
    }

    public static ObservableList<Part> lookupPart(String partName){
        //return a list of the parts that contain such a string
    }

    public static ObservableList<Product> lookupProduct(String productName){
        //return a list of the products that contain such a string
    }

   */

    public static void updatePart(int index, Part selectedPart){
//should replace the item at that index
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product selectedProduct){

        allProducts.set(index, selectedProduct);
    }


    public static boolean deletePart(Part selectedPart){

        allParts.remove(selectedPart);

        return true;
    }



    public static boolean deleteProduct(Product selectedProduct){

        allProducts.remove(selectedProduct);

        return true;
    }
    public static ObservableList<Part> getAllParts(){
        //return a list of all parts
        return allParts;
    }

    public static ObservableList<Product> getAllProducts(){
        //return a list of all products
        return allProducts;
    }


}
