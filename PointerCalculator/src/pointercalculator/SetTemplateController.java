/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointercalculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Omkar
 */
public class SetTemplateController implements Initializable {
    
    private Main_Template mtobj;
    private LinkedList<Main_Template>mtls;
    
    private Pointer_Template ptobj;
    private LinkedList<Pointer_Template> ptls;
    
    @FXML private Label Ltmpname;
    @FXML private TextField TFtmpname;
    @FXML private Button backBtn;
    @FXML private Button saveBtn;
    @FXML private Button deleteBtn;
    
    @FXML private TextField TFsubject;
    @FXML private TextField TFcredit;
    @FXML private Button add1Btn;
    @FXML private Button remove1Btn;
    
    @FXML private TextField TFgrade;
    @FXML private TextField TFvalue;
    @FXML private Button add2Btn;
    @FXML private Button remove2Btn;
    
    @FXML private TableView<CreditTableAttr> creditTable;
    @FXML private TableColumn<CreditTableAttr,String> TCsubject;
    @FXML private TableColumn<CreditTableAttr,String> TCcredit;
    
    @FXML private TableView<GradeTableAttr> gradeTable;
    @FXML private TableColumn<GradeTableAttr,String> TCgrade;
    @FXML private TableColumn<GradeTableAttr,Integer> TCvalue;
    
    @FXML private AnchorPane ap;
    
    private int editflg=0, flg=0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        flg=0;
        deleteBtn.setVisible(false);
        saveBtn.setAlignment(Pos.CENTER);
        TCsubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        TCcredit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        TCgrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        TCvalue.setCellValueFactory(new PropertyValueFactory<>("value"));
        
        TFcredit.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue)
                {
                    try
                    {
                        Integer.parseInt(TFcredit.getText());
                        TFcredit.setStyle("-fx-text-inner-color: black;");
                    }
                    catch(Exception e)
                    {
                        if(!TFcredit.getText().equals(""))
                            TFcredit.setStyle("-fx-text-inner-color: red;");
                    }
                }
            }
        });
        
        
        TFvalue.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue)
                {
                    try
                    {
                        Integer.parseInt(TFvalue.getText());
                        TFvalue.setStyle("-fx-text-inner-color: black;");
                    }
                    catch(Exception e)
                    {
                        if(!TFvalue.getText().equals(""))
                            TFvalue.setStyle("-fx-text-inner-color: red;");
                    }
                }
            }
        });
        
        TFtmpname.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue)
                {
                    String text = TFtmpname.getText();
                    openMainFile();
                    if(search(text))
                    {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setContentText("This template already exists! Try another name.");
                        alert.showAndWait();
                        TFtmpname.setStyle("-fx-text-inner-color: red;");
                    }
                    else
                        TFtmpname.setStyle("-fx-text-inner-color: black;");
                }
            }
        });
    }    
    
    @FXML 
    private void addBtn1Action()
    { 
        try
        {
            Integer.parseInt(TFcredit.getText());
            if( !TFsubject.getText().equals("") )
            {
                creditTable.getItems().add(new CreditTableAttr(TFsubject.getText(),Integer.parseInt(TFcredit.getText())));
                TFsubject.clear();
                TFcredit.clear();
                flg=1;
            }
        }
        catch(Exception e)
        {
            if(!TFcredit.getText().equals(""))
                TFcredit.setStyle("-fx-text-inner-color: red;");
        }
    }
    
    @FXML
    private void delBtn1Action()
    {
        ObservableList<CreditTableAttr> attrSelected, allAttr;
        allAttr = creditTable.getItems();
        attrSelected = creditTable.getSelectionModel().getSelectedItems();
        CreditTableAttr x = attrSelected.get(0);
        if(x!=null)
        {
            attrSelected.forEach(allAttr::remove);
            flg=1;
        }
    }
    
    @FXML 
    private void addBtn2Action()
    { 
        try
        {
            Integer.parseInt(TFvalue.getText());
            if( !TFgrade.getText().equals("") )
            {
                gradeTable.getItems().add(new GradeTableAttr(TFgrade.getText(),Integer.parseInt(TFvalue.getText())));
                TFgrade.clear();
                TFvalue.clear();
                flg=1;
            }
        }
        catch(Exception e)
        {
            if(!TFvalue.getText().equals(""))
                TFvalue.setStyle("-fx-text-inner-color: red;");
        }
    }
    
    @FXML
    private void delBtn2Action()
    {
        ObservableList<GradeTableAttr> attrSelected, allAttr;
        allAttr = gradeTable.getItems();
        attrSelected = gradeTable.getSelectionModel().getSelectedItems();
        GradeTableAttr x = attrSelected.get(0);
        if(x!=null)
        {
            attrSelected.forEach(allAttr::remove);
            flg=1;
        }
    }
    
    @FXML
    private void saveBtnAction()
    {
        openMainFile();
        if(!TFtmpname.getText().equals("") && (!search(TFtmpname.getText()) || editflg==1))
        {
            String nm=TFtmpname.getText();

            ArrayList<String> a=new ArrayList<>();
            ArrayList<Integer> b=new ArrayList<>();

            ObservableList<CreditTableAttr> allAttr;
            allAttr = creditTable.getItems();
            Iterator<CreditTableAttr> i=allAttr.iterator();
            while(i.hasNext())
            {
                CreditTableAttr tmp = i.next();
                a.add(tmp.subject);
                b.add(tmp.credit);
            }

            HashMap<String,Integer> map = new HashMap<>();
            ObservableList<GradeTableAttr> allAttr2;
            allAttr2 = gradeTable.getItems(); 
            Iterator<GradeTableAttr> i2=allAttr2.iterator();
            while(i2.hasNext())
            {
                GradeTableAttr tmp = i2.next();
                map.put(tmp.grade,tmp.value); 
            }
            if(editflg==0)
            {
                mtobj=new Main_Template(nm,a,b,map);
                mtls.add(mtobj);
            }
            else
            {
                mtobj.setData(nm,a,b,map);
                if(flg==1)
                {
                    openPointerFile();
                    while(true)
                    {
                        if(searchPointerFile(nm))
                        {
                            ptls.remove(ptobj);
                        }
                        else
                            break;
                    }
                    savePointerFile();
                }
            }

            saveMainFile();
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
    
    public void showBtnAction(String s)
    {
        editflg=1;
        openMainFile();
        String tmp_name=s;
        TFtmpname.setText(s);
        TFtmpname.setVisible(false);
        Ltmpname.setText(s);
        Ltmpname.setVisible(true);
        deleteBtn.setVisible(true);
        creditTable.getItems().clear();
        gradeTable.getItems().clear();
        if(search(tmp_name))
        {
            Iterator<String> i=mtobj.subjects.iterator();
            Iterator<Integer> j=mtobj.credits.iterator();
            while(i.hasNext())
            {
                creditTable.getItems().add(new CreditTableAttr(i.next(),j.next()));
            }
            HashMap<String,Integer> map = mtobj.grades;
            for(HashMap.Entry<String, Integer> entry : map.entrySet()) 
            {
                String key = entry.getKey();
                Integer value = entry.getValue();
                gradeTable.getItems().add(new GradeTableAttr(key,value));
            }
        }
        saveMainFile();
    }
    
    private boolean search(String nm)
    {
        int i=0,n=mtls.size();
        while(i<n)
        {
            mtobj=mtls.get(i);
            if(mtobj.templateName.equals(nm))
                    break;
            i++;
        }
        if(i==n)
        {
            mtobj=null;
            return false;
        }
        else
            return true;
    }
    
    @FXML private void deleteBtnAction() throws IOException
    {  
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("Are you sure you want to delete the template?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            openMainFile();
            if(search(TFtmpname.getText()))
            {
                mtls.remove(mtobj);
                System.out.println(mtobj.templateName);
                System.out.println(search(mtobj.templateName));
                saveMainFile();

                Stage main = (Stage) creditTable.getScene().getWindow();
                main.hide();
                Stage stage = new Stage();
                Parent root2 = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Scene scene2 = new Scene(root2); 
                stage.setScene(scene2);
                stage.setResizable(false);
                stage.show();
            }
            else
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Template not found!");
                alert.showAndWait();
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        } 
    }
    
    @FXML
    private void backBtnAction() throws IOException
    {
        Stage main = (Stage)TFtmpname.getScene().getWindow();
        main.close();
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    private void openPointerFile()
    {
        FileInputStream fis;
        ObjectInputStream ois;
        File f=new File("pointer_template.dat");
        try
        {
                fis=new FileInputStream(f);
                ois=new ObjectInputStream(fis);
                ptls=(LinkedList<Pointer_Template>)ois.readObject();
                fis.close();
                ois.close();
        }
        catch(Exception e){ptls=new LinkedList<>();}
    }
    
    private void savePointerFile()
    {
            FileOutputStream fos;
            ObjectOutputStream oos;
            try
            {
                    fos=new FileOutputStream("pointer_template.dat");
                    oos=new ObjectOutputStream(fos);
                    oos.writeObject(ptls);
                    fos.close();
                    oos.close();
            }
            catch(Exception e){}
    }
    
    private boolean searchPointerFile(String tnm)
    {
        int i=0,n=ptls.size();
        while(i<n)
        {
            ptobj=ptls.get(i);
            if(ptobj.templateName.equals(tnm))
                    break;
            i++;
        }
        if(i==n)
        {
            ptobj=null;
            return false;
        }
        else
            return true;
    }
}
