/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointercalculator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Omkar
 */
public class Pointer_Template implements Serializable {
    
    public String templateName;
    public String name;
    public String rollno;
    public Integer totalCredits;
    public Double pointer;
    public ArrayList<String> subjects;
    public ArrayList<String> grades;
    
    public Pointer_Template(String tnm, String nm, String rno, Integer cre, Double ptr, ArrayList<String> sub, ArrayList<String> grad){
        templateName = tnm;
        name = nm;
        rollno = rno;
        totalCredits = cre;
        pointer = ptr;
        subjects =sub;
        grades = grad;
    }
    
    private void setData(String tnm, String nm, String rno, Integer cre, Double ptr, ArrayList<String> sub, ArrayList<String> grad){
        templateName = tnm;
        name = nm;
        rollno = rno;
        totalCredits = cre;
        pointer = ptr;
        subjects =sub;
        grades = grad;
    }
    
}
