package Controllers;

import Model.InHousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddPartController {
    

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
    private TextField companyNameBox;
    @FXML
    private RadioButton inHousePartRadioButton;
    @FXML
    private ToggleGroup Group;
    @FXML
    private RadioButton outsourcedPartRadioButton;
    @FXML
    private Button savePartButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label switchLabel;
    
    
    //event handlers
    @FXML
    void SaveAndExit(ActionEvent event) throws Exception {
        
     if (outsourcedPartRadioButton.isSelected()) {
        
     int id = Inventory.getAllParts().size() + 1;
     
     String priceString = priceBox.getText();
     double priceDouble = Double.parseDouble(priceString);
     
     String stockString = invBox.getText();
     int stockInt = Integer.parseInt(stockString);
     
     String minString = minBox.getText();
     int minInt = Integer.parseInt(minString);
     
     String maxString = maxBox.getText();
     int maxInt = Integer.parseInt(maxString);
        
    Part newPart = new OutsourcedPart(id, nameBox.getText(), priceDouble, stockInt, minInt, maxInt, companyNameBox.getText());
    Inventory.addPart(newPart);
    
    //exit to main page
    Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/Views/MainScreen.fxml"));
        Scene mainScreenScene = new Scene(mainScreenParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
     }
     
     else if (inHousePartRadioButton.isSelected()) {
     
     int id = Inventory.getAllParts().size() + 1;
     
     String priceString = priceBox.getText();
     double priceDouble = Double.parseDouble(priceString);
     
     String stockString = invBox.getText();
     int stockInt = Integer.parseInt(stockString);
     
     String minString = minBox.getText();
     int minInt = Integer.parseInt(minString);
     
     String maxString = maxBox.getText();
     int maxInt = Integer.parseInt(maxString);    
     
     String machineIdString = companyNameBox.getText();
     int machineIdInt = Integer.parseInt(machineIdString);
     
    
     Part newPart = new InHousePart(id, nameBox.getText(), priceDouble, stockInt, minInt, maxInt, machineIdInt);
     Inventory.addPart(newPart);
     
     //exit to main page
     Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/Views/MainScreen.fxml"));
     Scene mainScreenScene = new Scene(mainScreenParent);
        
     Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
     window.setScene(mainScreenScene);
     window.show();
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
    private void OnInHouseButton(ActionEvent event) {
        
        switchLabel.setText("Machine ID");
        companyNameBox.setPromptText("Mach ID");
    }

    @FXML
    private void OnOutsourcedButton(ActionEvent event) {
        
        switchLabel.setText("Company Name");
        companyNameBox.setPromptText("Comp Nm");
    } 
}
