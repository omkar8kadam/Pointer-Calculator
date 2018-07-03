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
public class CreditTableAttr {
    public String subject;
    public Integer credit;
    
    public CreditTableAttr(String sub, Integer cre){
        subject = sub;
        credit = cre;
    }

    public String getSubject() {
        return subject;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }
}
