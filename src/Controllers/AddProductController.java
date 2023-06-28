package Controllers;

import Model.Inventory;
import static Model.Inventory.lookupPart;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AddProductController implements Initializable {
    
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
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
        partInventory.setAll(Inventory.getAllParts());
        
        partTable1.setItems(partInventory);
        partTable1.refresh();    
    }

    //event handlers
    @FXML
    void SaveAndExit(ActionEvent event) throws Exception {
        
     int id = Inventory.getAllProducts().size() + 1;
     
     String priceString = priceBox.getText();
     double priceDouble = Double.parseDouble(priceString);
     
     String stockString = invBox.getText();
     int stockInt = Integer.parseInt(stockString);
     
     String minString = minBox.getText();
     int minInt = Integer.parseInt(minString);
     
     String maxString = maxBox.getText();
     int maxInt = Integer.parseInt(maxString);
        
    Product newProduct = new Product(id, nameBox.getText(), priceDouble, stockInt, minInt, maxInt);
    
    ObservableList<Part> parts = FXCollections.observableArrayList();
    parts = partTable2.getItems();
    if(parts.isEmpty()) {
     Alert alert = new Alert(AlertType.ERROR);
     alert.setHeaderText("Oops!");
     alert.setContentText("Please include parts with this new product!");

     alert.showAndWait();  
    }
    else {
        for (Part part : parts) {
        newProduct.addAssociatedPart(part);
    }
    Inventory.addProduct(newProduct);
    
    Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/Views/MainScreen.fxml"));
    Scene mainScreenScene = new Scene(mainScreenParent);
    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
    window.setScene(mainScreenScene);
    window.show();
     }
    }

    @FXML
    void AddSelectedPartToTable2(ActionEvent event) {
     Part selectedPart = partTable1.getSelectionModel().getSelectedItem();
     partTable2.getItems().add(selectedPart);
     
    }

    @FXML
    void DeleteSelectedPartFromTable(ActionEvent event) {
    
    Alert alert = new Alert(AlertType.CONFIRMATION);
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
        Alert alert = new Alert(AlertType.CONFIRMATION);
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
}