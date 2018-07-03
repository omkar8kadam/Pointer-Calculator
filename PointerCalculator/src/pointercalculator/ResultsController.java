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
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Omkar
 */
public class ResultsController implements Initializable {

    private String tmpName;
    
    private Pointer_Template ptobj;
    private LinkedList<Pointer_Template> ptls;
    
    private int srno=0;
    
    @FXML private Label templateName;
    @FXML private Button backBtn;
    @FXML private Button removeBtn;
    
    @FXML private TableView<ResultTableViewAttr> table;
    @FXML private TableColumn<ResultTableViewAttr,Integer> srnoCol;
    @FXML private TableColumn<ResultTableViewAttr,String> nameCol;
    @FXML private TableColumn<ResultTableViewAttr,String> rnoCol;
    @FXML private TableColumn<ResultTableViewAttr,Integer> creCol;
    @FXML private TableColumn<ResultTableViewAttr,Double> ptrCol;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        srno=0;
        
        srnoCol.setCellValueFactory(new PropertyValueFactory<>("srno"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        rnoCol.setCellValueFactory(new PropertyValueFactory<>("rollno"));
        creCol.setCellValueFactory(new PropertyValueFactory<>("totalCredits"));
        ptrCol.setCellValueFactory(new PropertyValueFactory<>("pointer"));  
    }    
    
    private ObservableList<ResultTableViewAttr> getData(){
        openPointerFile();
        ObservableList<ResultTableViewAttr> ptrlist = FXCollections.observableArrayList();
        int i=0,n=ptls.size();
        while(i<n)
        {
            ptobj=ptls.get(i);
            if(ptobj.templateName.equals(templateName.getText()))
            {
                srno++;
                ResultTableViewAttr obj = new ResultTableViewAttr(srno,ptobj.rollno,ptobj.name,ptobj.totalCredits,ptobj.pointer,ptobj.grades);
                ptrlist.add(obj);
            }
            i++;
        }
        return ptrlist;
    }
    
    public void setTemplateName(String s)
    {
        templateName.setText(s);
        tmpName=s;
        
        ArrayList<String> sub= new ArrayList<>(0);
        openPointerFile();
        int i=0,n=ptls.size();
        while(i<n)
        {
            ptobj=ptls.get(i);
            if(ptobj.templateName.equals(templateName.getText()))
            {
                sub = ptobj.subjects;
            }
            i++;
        }
        
        n=sub.size();
        for(i=0;i<n;i++)
        {
            TableColumn<ResultTableViewAttr,String> t = new TableColumn(sub.get(i));
            final int z = i;
            t.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getListObject(z)));
            table.getColumns().addAll(t);
        }
        
        table.setItems(getData());
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
    
    private boolean searchPointerFile(ResultTableViewAttr a)
    {
        int i=0,n=ptls.size();
        while(i<n)
        {
            ptobj=ptls.get(i);
            if(ptobj.templateName.equals(tmpName) && ptobj.name.equals(a.name) && ptobj.rollno.equals(a.rollno) && ptobj.pointer.equals(a.pointer) && ptobj.totalCredits.equals(a.totalCredits))
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
    private void backBtnAction() throws IOException
    {
        Stage main = (Stage) table.getScene().getWindow();
        main.close();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CalculatePointer.fxml"));   
        Stage stage = new Stage();
        stage.setScene(new Scene((Pane) loader.load()));
        CalculatePointerController controller = loader.<CalculatePointerController>getController();
        controller.setCreditTemplateObject(tmpName);
        stage.setResizable(false);
        stage.show();
    }
    
    @FXML
    private void removeBtnAction()
    {
        ObservableList<ResultTableViewAttr> attrSelected, allAttr;
        allAttr = table.getItems();
        attrSelected = table.getSelectionModel().getSelectedItems();
        ResultTableViewAttr x = attrSelected.get(0);
        if(x!=null)
        {
            attrSelected.forEach(allAttr::remove);
            openPointerFile();
            searchPointerFile(x);
            ptls.remove(ptobj);
            savePointerFile();
        }
    }
    
}
