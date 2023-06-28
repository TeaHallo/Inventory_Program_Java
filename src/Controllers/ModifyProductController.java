package Controllers;

import Model.Inventory;
import static Model.Inventory.lookupPart;
import static Model.Inventory.updateProduct;
import Model.Part;
import Model.Product;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ModifyProductController implements Initializable {
       
    @FXML
    private TextField idBox;
    @FXML
    private TextField nameBox;
    @FXML
    private TextField invBox;
    @FXML
    private TextField priceBox;
    @FXML
    private TextField maxBox;
    @FXML
    private TextField minBox;
    @FXML
    private TableView<Part> partTable1;
    @FXML
    private TableColumn<Part, Integer> partIdCol1;
    @FXML
    private TableColumn<Part, String> partNameCol1;
    @FXML
    private TableColumn<Part, Integer> partInvCol1;
    @FXML
    private TableColumn<Part, Double> partPriceCol1;
    @FXML
    private TableView<Part> partTable2;
    @FXML
    private TableColumn<Part, Integer> partIdCol2;
    @FXML
    private TableColumn<Part, String> partNameCol2;
    @FXML
    private TableColumn<Part, Integer> partInvCol2;
    @FXML
    private TableColumn<Part, Double> partPriceCol2;
    @FXML
    private Button searchPartButton;
    @FXML
    private TextField searchPartBox;
    @FXML
    private Button addPartButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private Button saveProductButton;
    @FXML
    private Button cancelButton;
    
    private Product selectedProduct;
    private int originalProductIndex;
    
    ObservableList<Part> associatedPartInventory = FXCollections.observableArrayList();
    ObservableList<Part> allPartInventory = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        partIdCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        generatePartsTable();
       
    } 
    private void generatePartsTable() {
        allPartInventory.setAll(Inventory.getAllParts());
        
        partTable1.setItems(allPartInventory);
        partTable1.refresh();    
    }
    
    void InitializeProduct(Product product) {
       
        selectedProduct = product;
        originalProductIndex = Inventory.getAllProducts().indexOf(product);
       
        int id = selectedProduct.getId();
        String stringId = Integer.toString(id);
        idBox.setText(stringId);
        
        nameBox.setText(selectedProduct.getName());
        
        int stock = selectedProduct.getStock();
        String stringStock = Integer.toString(stock);
        invBox.setText(stringStock);
        
        double price = selectedProduct.getPrice();
        String stringPrice = Double.toString(price);
        priceBox.setText(stringPrice);
        
        int min = selectedProduct.getMin();
        String stringMin = Integer.toString(min);
        minBox.setText(stringMin);
        
        int max = selectedProduct.getMax();
        String stringMax = Integer.toString(max);
        maxBox.setText(stringMax);
        
        
        associatedPartInventory.setAll(selectedProduct.getAllAssociatedParts());
        
        partTable2.setItems(associatedPartInventory);
        partTable2.refresh();    
    
}

    
    //event handlers
    @FXML
    void AddSelectedPartToTable2(ActionEvent event) {
     Part selectedPart = partTable1.getSelectionModel().getSelectedItem();
     partTable2.getItems().add(selectedPart);
    }

    @FXML
    void DeleteSelectedPartFromTable(ActionEvent event) {
    
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setHeaderText("Delete selected part?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
    Part selectedPart = partTable2.getSelectionModel().getSelectedItem();
    partTable2.getItems().remove(selectedPart);
    } 
    else {
    // do not remove
    }
    }

    @FXML
    void GoToMainScreen(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you would like to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        //if user wants to exit
        if (result.get() == ButtonType.OK){
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/Views/MainScreen.fxml"));
        Scene mainScreenScene = new Scene(mainScreenParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
        }
        else {
            //stay on current screen
        }
       } 

    @FXML
    void SearchPartButtonHandler(ActionEvent event) {
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
        partTable1.setItems(parts);
        searchPartBox.setText("");
    }

    
    @FXML
    private void UpdateProductAndExit(ActionEvent event) throws Exception {
       
        if(!nameBox.getText().equals(selectedProduct.getName())) {
            selectedProduct.setName(nameBox.getText());
        }
        
        int stock = selectedProduct.getStock();
        String stringStock = Integer.toString(stock);
        if(!invBox.getText().equals(stringStock)) {
            String s = invBox.getText();
            int intStock = Integer.parseInt(s);
           selectedProduct.setStock(intStock);
        }
        
        double price = selectedProduct.getPrice();
        String stringPrice = Double.toString(price);
        if(!priceBox.getText().equals(stringPrice)) {
            String s = priceBox.getText();
            double doublePrice = Double.parseDouble(s);
           selectedProduct.setPrice(doublePrice);
        }
        
        
        int min = selectedProduct.getMin();
        String stringMin = Integer.toString(min);
        if(!minBox.getText().equals(stringMin)) {
            String s = minBox.getText();
            int minInt = Integer.parseInt(s);
           selectedProduct.setMin(minInt);
        }
        
        int max = selectedProduct.getMax();
        String stringMax = Integer.toString(max);
        if(!maxBox.getText().equals(stringMax)) {
            String s = maxBox.getText();
            int intMax = Integer.parseInt(s);
           selectedProduct.setMax(intMax);
        }
        
        ObservableList<Part> newParts = FXCollections.observableArrayList();
        newParts = partTable2.getItems();
        
        if(newParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Oops!");
            alert.setContentText("Please include parts with this product!");

            alert.showAndWait(); 
        }
        else {
        ObservableList<Part> oldParts = FXCollections.observableArrayList();
        oldParts = selectedProduct.getAllAssociatedParts();
        
        
        if(newParts != oldParts) {
           for(Part part : newParts) {
               if(!oldParts.contains(part)) {
                selectedProduct.addAssociatedPart(part);
               }
           }
           for(Part part : oldParts) {
               if(!newParts.contains(part)) {
               selectedProduct.deleteAssociatedPart(part);
               }
           }
        }
        updateProduct(originalProductIndex, selectedProduct);
        
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/Views/MainScreen.fxml"));
        Scene mainScreenScene = new Scene(mainScreenParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }
    }
}
    

