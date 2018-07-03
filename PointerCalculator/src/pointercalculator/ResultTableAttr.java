/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointercalculator;

/**
 *
 * @author Omkar
 */
public class ResultTableAttr {
    public Integer srno;
    public String rollno;
    public String name;
    public Integer totalCredits;
    public Double pointer;
    
    public ResultTableAttr(Integer sno, String rno, String nm, Integer cre, Double ptr){
        srno = sno;
        rollno = rno;
        name = nm;
        totalCredits = cre;
        pointer = ptr;
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
}
