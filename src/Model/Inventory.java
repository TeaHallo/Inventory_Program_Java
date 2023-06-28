
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
     
    //add methods
    public static void addPart(Part newPart) {
         allParts.add(newPart);
         
    }
    
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    //search by id methods
   public static Part lookupPart(int partId) {
        ObservableList<Part> allPartsArray = getAllParts();
        for(int i= 0; i < allPartsArray.size(); i++) {
            Part pt = allPartsArray.get(i);
            
            if(pt.getId() == partId) {
                return pt;
            }
        }
        return null;
    }
    
    public static Product lookupProduct(int productId){
        ObservableList<Product> allProductsArray = getAllProducts();
        for(int i= 0; i < allProductsArray.size(); i++) {
            Product prod = allProductsArray.get(i);
            
            if(prod.getId() == productId) {
                return prod;
            }
        }
        return null;
    }
    
    //search by name methods
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        ObservableList<Part> allPartsArray = getAllParts();
        
        for (Part pt : allPartsArray) {
            if(pt.getName().contains(partName)) {
                matchingParts.add(pt);
            }
        }
       return matchingParts;
    }
  
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProductsArray = getAllProducts();
        
        for (Product prod : allProductsArray) {
            if(prod.getName().contains(productName)) {
                matchingProducts.add(prod);
            }
        }
       return matchingProducts;
    }
    
    //update methods
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
        
    }
    
    //delete methods
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }
    
    public static boolean deleteProduct(Product selectedProduct) {
       return allProducts.remove(selectedProduct);
    }
    
    //get all methods
     public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
}
