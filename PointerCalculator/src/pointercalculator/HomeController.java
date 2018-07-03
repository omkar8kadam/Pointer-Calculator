/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointercalculator;
import javafx.scene.text.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Omkar
 */
public class HomeController implements Initializable {

    private Main_Template mtobj;
    private LinkedList<Main_Template>mtls;
    
    @FXML private Button addBtn;
    @FXML private GridPane gridpane;
    
    @FXML
    private void addBtnAction() throws IOException{
        
        Stage main = (Stage) addBtn.getScene().getWindow();
        main.close();
        
        Parent root = FXMLLoader.load(getClass().getResource("SetTemplate.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        stage.setOnCloseRequest((WindowEvent event) -> {
            Stage stage2 = new Stage();
            Parent root2=null;
            try 
            {
                root2 = FXMLLoader.load(getClass().getResource("Home.fxml"));
            } catch (IOException ex) {}
            Scene scene2 = new Scene(root2);
            stage2.setScene(scene2);
            stage2.setResizable(false);
            stage2.show();
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        openMainFile();
        if(mtls!=null)
        {
            int i=0,n=mtls.size();
            while(i<n)
            {
                mtobj=mtls.get(i);
                if(mtobj!=null)
                {
                    Label l = new Label(""+(i+1));
                    gridpane.add(l,0,i+1);
                    gridpane.setHalignment(l,HPos.CENTER);
                    l.setFont(Font.font("System",15));

                    Button b = new Button(mtobj.templateName);
                    gridpane.setHalignment(b,HPos.CENTER);
                    b.setAlignment(Pos.CENTER);
                    b.setPrefWidth(200);
                    b.setPrefHeight(72);
                    b.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {                       
                            try 
                            {
                                open_CalculatePointer(b.getText());
                            } 
                            catch (Exception ex){System.out.println(ex);}
                    }});
                    gridpane.add(b,1,i+1);
                }
                else
                    break;
                i++;
            }
        }
    }    
    
    private void openMainFile()
    {
        FileInputStream fis;
        ObjectInputStream ois;
        File f=new File("main_template.dat");
        try
        {
                fis=new FileInputStream(f);
                ois=new ObjectInputStream(fis);
                mtls=(LinkedList<Main_Template>)ois.readObject();
                fis.close();
                ois.close();
        }
        catch(Exception e){
            mtls = new LinkedList<>();
        }
    }
	
    private void saveMainFile()
    {
        FileOutputStream fos;
        ObjectOutputStream oos;
        try
        {
                fos=new FileOutputStream("main_template.dat");
                oos=new ObjectOutputStream(fos);
                oos.writeObject(mtls);
                fos.close();
                oos.close();
        }
        catch(Exception e){}
    }
    
    public void open_CalculatePointer(String str) throws Exception 
    {
        Stage main = (Stage) addBtn.getScene().getWindow();
        main.close();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CalculatePointer.fxml"));   
        Stage stage = new Stage();
        stage.setScene(new Scene((Pane) loader.load()));
        CalculatePointerController controller = loader.<CalculatePointerController>getController();
        controller.setCreditTemplateObject(str);
        stage.setResizable(false);
        stage.show();
        
        stage.setOnCloseRequest((WindowEvent event) -> {
            Stage child2 = new Stage();
            Parent root=null;
            try 
            {
                root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            } catch (IOException ex) {}
            Scene scene = new Scene(root);
            child2.setScene(scene);
            child2.setResizable(false);
            child2.show();
        });    
    } 
}
