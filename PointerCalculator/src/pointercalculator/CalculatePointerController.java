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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Omkar
 */
public class CalculatePointerController implements Initializable {

    private String mainTemplateName;
    
    private Main_Template mtobj;
    private LinkedList<Main_Template> mtls;
    
    private Pointer_Template ptobj;
    private LinkedList<Pointer_Template> ptls;
    
    @FXML private GridPane gridpane;
    @FXML private Button calc;
    @FXML private Button clear;
    @FXML private Button save;
    @FXML private Button remove;
    @FXML private Button edit;
    @FXML private Button backBtn;
    @FXML private Button view;
    
    @FXML private TextField TFptr;
    @FXML private TextField TFname;
    @FXML private TextField TFrno;
    @FXML private Label TFtemplateName;
    
    @FXML private TableView<ResultTableAttr> table;
    @FXML private TableColumn<ResultTableAttr,Integer> srnoCol;
    @FXML private TableColumn<ResultTableAttr,String> nameCol;
    @FXML private TableColumn<ResultTableAttr,String> rnoCol;
    @FXML private TableColumn<ResultTableAttr,Integer> creCol;
    @FXML private TableColumn<ResultTableAttr,Double> ptrCol;
    
    private int k,srno=0;
    private TextField tf[];
    private Integer tot_cre=0, cre=0;
    private Double ptr;
    private ArrayList<String> grades;
    private ArrayList<String> subjects;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        openMainFile();
        openPointerFile();
        
        grades = new ArrayList<>();
        subjects = new ArrayList<>();
        
        srnoCol.setCellValueFactory(new PropertyValueFactory<>("srno"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        rnoCol.setCellValueFactory(new PropertyValueFactory<>("rollno"));
        creCol.setCellValueFactory(new PropertyValueFactory<>("totalCredits"));
        ptrCol.setCellValueFactory(new PropertyValueFactory<>("pointer"));
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
      
    public void setCreditTemplateObject(String str)
    {
        mainTemplateName = str;
        TFtemplateName.setText(mainTemplateName);
        search(str);
        Iterator<String> i=mtobj.subjects.iterator();
        Iterator<Integer> j=mtobj.credits.iterator();
        k=0;
        while(i.hasNext())
        {
            String subj = i.next();
            Label sub = new Label(subj);
            subjects.add(subj);
            GridPane.setHalignment(sub, HPos.CENTER);
            sub.setFont(Font.font("System",15));

            Label cre = new Label(j.next().toString());
            GridPane.setHalignment(cre, HPos.CENTER);
            cre.setFont(Font.font("System",15));

            gridpane.add(sub,0,k+1);
            gridpane.add(cre,1,k+1);
            k++;
        }
        setTextFields();
        
        table.setItems(getData());
    }
    
    private ObservableList<ResultTableAttr> getData(){
        openPointerFile();
        ObservableList<ResultTableAttr> ptrlist = FXCollections.observableArrayList();
        int i=0,n=ptls.size();
        while(i<n)
        {
            ptobj=ptls.get(i); 
            if(ptobj.templateName.equals(mainTemplateName))
            {
                srno++;
                ResultTableAttr obj = new ResultTableAttr(srno,ptobj.rollno,ptobj.name,ptobj.totalCredits,ptobj.pointer);
                ptrlist.add(obj);  
            }
            i++;
        }
        return ptrlist;
    }
    
    private void setTextFields()
    {
        tf = new TextField[k];
        
        for(int i=0;i<k;i++)
        {
            tf[i]=new TextField();
            GridPane.setHalignment(tf[i], HPos.CENTER);
            tf[i].setMaxWidth(40);
            TextField t = tf[i];
            t.focusedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(!newValue)
                    {
                        String text = t.getText();
                        
                        if(!gradeStringCheck(text))
                            t.setStyle("-fx-text-inner-color: red;");
                        else
                            t.setStyle("-fx-text-inner-color: black;");
                    }
                }
            });
            gridpane.add(tf[i],2,i+1);
        }
    }
    
    private boolean gradeStringCheck(String s)
    {
        HashMap<String,Integer> map = mtobj.grades;
        for(HashMap.Entry<String, Integer> entry : map.entrySet()) 
        {
            String key = entry.getKey();
            if(s.equals(key))
                return true;
        }
        return false;
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
    
    @FXML private void calcBtnAction()
    {
        grades.clear();
        int i;
        for(i=0;i<k;i++)
        {
            if( tf[i].getText().equals("") || !gradeStringCheck(tf[i].getText()))
            {
                break;
            }
        }
        if(i>=k)
        {
            HashMap<String,Integer> hm = mtobj.grades;
            tot_cre=0; cre=0;
            for(i=0;i<k;i++)
            {
                tot_cre += hm.get(tf[i].getText()) * mtobj.credits.get(i);
                cre += mtobj.credits.get(i);
                grades.add(tf[i].getText());
            }
            ptr = tot_cre.doubleValue() / cre;
            DecimalFormat df = new DecimalFormat("#.00");
            ptr = Double.valueOf(df.format(ptr));
            TFptr.setText(ptr.toString());
        }
    }
    
    @FXML private void clearBtnAction()
    {
        TFname.clear();
        TFrno.clear();
        TFptr.clear();
        for(int i=0;i<k;i++)
        {
            tf[i].clear();
        }
    }
    
    @FXML private void saveBtnAction()
    {
        String nm = TFname.getText();
        String rno = TFrno.getText();
        String p = TFptr.getText();
        if(!nm.equals("") && !rno.equals("") && !p.equals("") )
        {
            srno++;
        
            Pointer_Template a = new Pointer_Template(mainTemplateName,nm,rno,tot_cre,ptr,subjects,grades); 
            ptls.add(a);

            ResultTableAttr obj = new ResultTableAttr(srno,rno,nm,tot_cre,ptr);
            table.getItems().add(obj);

            savePointerFile();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Complete name, rollno and pointer fields!");
            alert.showAndWait();
        }           
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
    
    private boolean searchPointerFile(ResultTableAttr a)
    {
        int i=0,n=ptls.size();
        while(i<n)
        {
            ptobj=ptls.get(i);
            if(ptobj.templateName.equals(mainTemplateName) && ptobj.name.equals(a.name) && ptobj.rollno.equals(a.rollno) && ptobj.pointer.equals(a.pointer) && ptobj.totalCredits.equals(a.totalCredits))
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
    
    @FXML
    private void removeBtnAction()
    {
        ObservableList<ResultTableAttr> attrSelected, allAttr;
        allAttr = table.getItems();
        attrSelected = table.getSelectionModel().getSelectedItems();
        ResultTableAttr x = attrSelected.get(0);
        if(x!=null)
        {
            attrSelected.forEach(allAttr::remove);
            openPointerFile();
            searchPointerFile(x);
            ptls.remove(ptobj);
            savePointerFile();
        }
    }
    
    @FXML
    private void editBtnAction() throws IOException
    {
        Stage main = (Stage) gridpane.getScene().getWindow();
        main.close();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SetTemplate.fxml"));   
        Stage stage = new Stage();
        stage.setScene(new Scene((Pane) loader.load()));
        SetTemplateController controller = loader.<SetTemplateController>getController();
        controller.showBtnAction(mainTemplateName);
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
    
    @FXML
    private void backBtnAction() throws IOException
    {
        Stage main = (Stage)gridpane.getScene().getWindow();
        main.close();
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void viewBtnAction() throws IOException
    {
        Stage main = (Stage) gridpane.getScene().getWindow();
        main.hide();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Results.fxml"));   
        Stage stage = new Stage();
        stage.setScene(new Scene((Pane) loader.load()));
        ResultsController controller = loader.<ResultsController>getController();
        controller.setTemplateName(mainTemplateName);
        stage.setResizable(false);
        stage.show();
        
        stage.setOnCloseRequest((WindowEvent event) -> {
            main.show();
        });
    }
}
