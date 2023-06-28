package Controllers;

import Model.InHousePart;
import Model.Inventory;
import static Model.Inventory.updatePart;
import Model.OutsourcedPart;
import Model.Part;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ModifyPartController implements Initializable {

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
    private Part selectedPart;
    private int originalPartIndex;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  

    public void InitializePart(Part part) {
        
        selectedPart = part;
        originalPartIndex = Inventory.getAllParts().indexOf(part);
        
        if(selectedPart instanceof InHousePart) {
        InHousePart inHousePart = (InHousePart)selectedPart;
        inHousePartRadioButton.fire();
        
 
        int id = inHousePart.getId();
        String stringId = Integer.toString(id);
        idBox.setText(stringId);
        
        nameBox.setText(inHousePart.getName());
        
        int stock = inHousePart.getStock();
        String stringStock = Integer.toString(stock);
        invBox.setText(stringStock);
        
        double price = inHousePart.getPrice();
        String stringPrice = Double.toString(price);
        priceBox.setText(stringPrice);
        
        int min = inHousePart.getMin();
        String stringMin = Integer.toString(min);
        minBox.setText(stringMin);
        
        int max = inHousePart.getMax();
        String stringMax = Integer.toString(max);
        maxBox.setText(stringMax);
        
        int machineId = inHousePart.getMachineId();
        String stringMachineId = Integer.toString(machineId);
        companyNameBox.setText(stringMachineId);
        }
        
        else if(selectedPart instanceof OutsourcedPart) {
            OutsourcedPart outsourcedPart = (OutsourcedPart)selectedPart;
            outsourcedPartRadioButton.fire();
            
            
        int id = outsourcedPart.getId();
        String stringId = Integer.toString(id);
        idBox.setText(stringId);
        
        nameBox.setText(outsourcedPart.getName());
        
        int stock = outsourcedPart.getStock();
        String stringStock = Integer.toString(stock);
        invBox.setText(stringStock);
        
        double price = outsourcedPart.getPrice();
        String stringPrice = Double.toString(price);
        priceBox.setText(stringPrice);
        
        int min = outsourcedPart.getMin();
        String stringMin = Integer.toString(min);
        minBox.setText(stringMin);
        
        int max = outsourcedPart.getMax();
        String stringMax = Integer.toString(max);
        maxBox.setText(stringMax);
        
        companyNameBox.setText(outsourcedPart.getCompanyName());
        }
    }
    
    //event handlers
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
    private void OnInHouseButton(ActionEvent event) {
        
        switchLabel.setText("Machine ID");
        companyNameBox.setPromptText("Mach ID");
    }

    @FXML
    private void OnOutsourcedButton(ActionEvent event) {
        
        switchLabel.setText("Company Name");
        companyNameBox.setPromptText("Comp Nm");
    }

    @FXML
    private void UpdatePartAndExit(ActionEvent event) throws Exception {
       
            String name =nameBox.getText();
       
            String inventory = invBox.getText();
            int stock = Integer.parseInt(inventory);
           
            String stringPrice = priceBox.getText();
            double price = Double.parseDouble(stringPrice);
           
            String stringMin = minBox.getText();
            int min = Integer.parseInt(stringMin);
        
            String stringMax = maxBox.getText();
            int max = Integer.parseInt(stringMax);
            
            String machineOrCompany = companyNameBox.getText();
           
        if(inHousePartRadioButton.isSelected()) {
            int machineId = Integer.parseInt(machineOrCompany);
            InHousePart inHousePart = new InHousePart(selectedPart.getId(), name, price, stock, min, max, machineId);
            updatePart(originalPartIndex, inHousePart);
        }
        else {
            OutsourcedPart outsourcedPart = new OutsourcedPart(selectedPart.getId(), name, price, stock, min, max, machineOrCompany);
            updatePart(originalPartIndex, outsourcedPart);
        }
        
        
        Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/Views/MainScreen.fxml"));
        Scene mainScreenScene = new Scene(mainScreenParent);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
         
         
    }
}
