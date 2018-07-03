/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointercalculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Omkar
 */
public class Main_Template implements Serializable {
    
    public String templateName;
    public ArrayList<String> subjects;
    public ArrayList<Integer> credits;
    public HashMap<String,Integer> grades;
    
    public Main_Template(String tnm, ArrayList<String> sub, ArrayList<Integer> cre, HashMap<String,Integer> map){
        templateName = tnm;
        subjects = sub;
        credits = cre;
        grades = map;
    }
    
    public void setData(String tnm, ArrayList<String> sub, ArrayList<Integer> cre, HashMap<String,Integer> map){
        templateName = tnm;
        subjects = sub;
        credits = cre;
        grades = map;
    }
    
}
