
package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class InventoryProgram extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        addTestData();
        
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();   
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    void addTestData() {
        //Inhouse parts
        Part Ha = new InHousePart(1, "Part Ha", 2.0, 5, 1, 10, 56);
        Part Hb = new InHousePart(2, "Part Hb", 3.0, 10, 1, 25, 57); 
        Part Hc = new InHousePart(3, "Part Hc", 5.0, 25, 5, 100, 58);
        Inventory.addPart(Ha);
        Inventory.addPart(Hb);
        Inventory.addPart(Hc);
        
        //outsourced parts
        Part Oa = new OutsourcedPart(4, "Part Oa", 3.0, 7, 3, 45, "Amazon");
        Part Ob = new OutsourcedPart(5, "Part Ob", 6.0, 23, 5, 50, "Sears");
        Part Oc = new OutsourcedPart(6, "Part Oc", 8.0, 45, 4, 76, "Macy's");
        Inventory.addPart(Oa);
        Inventory.addPart(Ob);
        Inventory.addPart(Oc);
        
        //Products
        Product Pa = new Product(1, "Product a", 50.0, 25, 20, 70);
        Product Pb = new Product(2, "Product b", 75.0, 50, 25, 100);
        Product Pc = new Product(3, "Product c", 60.0, 45, 20, 120);
        Pa.addAssociatedPart(Oa);
        Pa.addAssociatedPart(Hb);
        Pb.addAssociatedPart(Oc);
        Pb.addAssociatedPart(Ha);
        Pc.addAssociatedPart(Ob);
        Pc.addAssociatedPart(Hc);
        Inventory.addProduct(Pa);
        Inventory.addProduct(Pb);
        Inventory.addProduct(Pc);
    }
}
