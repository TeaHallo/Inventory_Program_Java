
package Controllers;

import Model.Inventory;
import static Model.Inventory.lookupPart;
import static Model.Inventory.lookupProduct;
import Model.Part;
import Model.Product;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class MainScreenController implements Initializable {
    
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList(); 
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> PartIDColumn;
    @FXML
    private TableColumn<Part, String> PartNameColumn;
    @FXML
    private TableColumn<Part, Integer> PartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> PartPriceColumn;
    @FXML
    private Button SearchPartButton;
    @FXML
    private Button AddPartButton;
    @FXML
    private Button ModifyPartButton;
    @FXML
    private Button DeletePartButton;
    @FXML
    private TextField searchPartBox;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> ProductIDColumn;
    @FXML
    private TableColumn<Product, String> ProductNameColumn;
    @FXML
    private TableColumn<Product, Integer> ProductInventoryColumn;
    @FXML
    private TableColumn<Product, Double> ProductPriceColumn;
    @FXML
    private Button SearchProductButton;
    @FXML
    private Button AddProductButton;
    @FXML
    private Button ModifyProductButton;
    @FXML
    private Button DeleteProductButton;
    @FXML
    private TextField searchProductBox;
    @FXML
    private Button ExitButton;
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        PartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        ProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
               
        generatePartsTable();
        generateProductsTable();
    }    
    
    private void generatePartsTable() {
        partInventory.setAll(Inventory.getAllParts());
        
        partsTable.setItems(partInventory);
        partsTable.refresh();    
    }
    
    private void generateProductsTable() {
        productInventory.setAll(Inventory.getAllProducts());
        
        productsTable.setItems(productInventory);
        productsTable.refresh();
    }

    //search parts by name or id
    @FXML
    void SearchPartButtonHandler(ActionEvent actionevent) {
        String q = searchPartBox.getText();
        ObservableList<Part> parts = lookupPart(q);
        
        if(parts.isEmpty()){
        try {
            int id = Integer.parseInt(q);
            Part getPart = lookupPart(id);
            if (getPart != null) {
                parts.add(getPart);
            }
            }
        catch (NumberFormatException e) {
            }
        }
        partsTable.setItems(parts);
        searchPartBox.setText("");
    }
    
    //search products by name or id
    @FXML
     void SearchProductButtonHandler (ActionEvent actionevent) {
        String q = searchProductBox.getText();
        ObservableList<Product> products = lookupProduct(q);
        
        if(products.isEmpty()){
        try {
            int id = Integer.parseInt(q);
            Product getProduct = lookupProduct(id);
            if (getProduct != null) {
                products.add(getProduct);
            }
            }
        catch (NumberFormatException e) {
            }
        }
        productsTable.setItems(products);
        searchProductBox.setText("");
    }
     
     //go to add product screen
      @FXML
    void AddProductButtonHandler(ActionEvent event) throws Exception {
        Parent addProductPageParent = FXMLLoader.load(getClass().getResource("/Views/AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductPageParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();
    }
    
    //go to add part screen
     @FXML
    void AddPartButtonHandler(ActionEvent event) throws Exception {
        Parent addPartPageParent = FXMLLoader.load(getClass().getResource("/Views/AddPart.fxml"));
        Scene addPartScene = new Scene(addPartPageParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }
    //delete selected part
    @FXML
    void DeletePartButtonHandler(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setHeaderText("Delete selected part?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){    
    Part part = partsTable.getSelectionModel().getSelectedItem();
    Inventory.deletePart(part);
    partsTable.getItems().remove(part);
    }
   else {
    //do not delete
   } 
    }
    
//delete selected product
    @FXML
    void DeleteProductButtonHandler(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setHeaderText("Delete selected product?");
    
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
    Product product = productsTable.getSelectionModel().getSelectedItem();
    Inventory.deleteProduct(product);
    productsTable.getItems().remove(product);
    }
    else {
        //do not delete
    }
    }
    
//exit program
    @FXML
    void ExitButtonHandler(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setHeaderText("Are you sure you would like to exit?");


    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){    
    Platform.exit();
    }
    else {
        //stay on page
    }
    }
    
//go to modify part screen
    @FXML
    void ModifyPartButtonHandler(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/ModifyPart.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        
        ModifyPartController controller = loader.getController();
        controller.InitializePart(partsTable.getSelectionModel().getSelectedItem());
        
      Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
      window.setScene(tableViewScene);
      window.show();
    }
    
//go to modify product screen
    @FXML
    void ModifyProductButtonHandler(ActionEvent event) throws Exception {
      FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/ModifyProduct.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        
        ModifyProductController controller = loader.getController();
        controller.InitializeProduct(productsTable.getSelectionModel().getSelectedItem());
        
      Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
      window.setScene(tableViewScene);
      window.show();
    }
    
}
