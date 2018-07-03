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
public class GradeTableAttr {
    public String grade;
    public Integer value;
    
    public GradeTableAttr(String grad, Integer val){
        grade = grad;
        value =val;
    }

    public String getGrade() {
        return grade;
    }

    public Integer getValue() {
        return value;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
