/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointercalculator;

import java.util.ArrayList;

/**
 *
 * @author Omkar
 */
public class ResultTableViewAttr {
    
    public Integer srno;
    public String rollno;
    public String name;
    public Integer totalCredits;
    public Double pointer;
    public ArrayList<String> grades;
       
    public ResultTableViewAttr(Integer sno, String rno, String nm, Integer cre, Double ptr, ArrayList<String> al){
        srno = sno;
        rollno = rno;
        name = nm;
        totalCredits = cre;
        pointer = ptr;
        grades = al;
    }

    public Integer getSrno() {
        return srno;
    }

    public String getRollno() {
        return rollno;
    }

    public String getName() {
        return name;
    }

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public Double getPointer() {
        return pointer;
    }

    public ArrayList<String> getGrades() {
        return grades;
    }

    public void setSrno(Integer srno) {
        this.srno = srno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }

    public void setPointer(Double pointer) {
        this.pointer = pointer;
    }

    public void setGrades(ArrayList<String> grades) {
        this.grades = grades;
    }

    String getListObject(int i) {
        return grades.get(i);
    }
    
    
}
