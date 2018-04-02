/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Accounts;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.mindrot.jbcrypt.BCrypt;
import session.AccountsFacade;

/**
 * This controller encapsulates the logic for allowing an user to login to the system. It brings the values of email and password 
 * from the UI and check it against the ones in the DB. If the credentials match, it grants access else 
 * tells the user what error had occurred. 
 * @author rm4806
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable{

    @EJB
    private AccountsFacade accFacade;
    
    private String email, password;
    private Accounts temp;
    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
        accFacade = new AccountsFacade();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Accounts getTemp() {
        return temp;
    }

    public void setTemp(Accounts temp) {
        this.temp = temp;
    }
    
    
    /**
     * This method gets the values from the UI. It first tries to find the account object with the email specified by the user.
     * If it can find it, it compares the password provided by the user to the one in the DB. Else it tells the user that the 
     * email specified is not present in the DB. 
     * @throws IOException 
     */
    public void login(){
         temp = accFacade.find(this.getEmail().toLowerCase());
            if(temp == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email error","Cannot find email account.." ));
            }else{
                try{
                    if(BCrypt.checkpw(this.getPassword(), temp.getPassword())){
                    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                    ec.redirect("faces/homePage.xhtml");
                    } else{
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password error","Password does not match.." ));
                    }
                } catch (Exception e){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password error","Password does not match.." ));
                }
                
            }
 
    }
    
    public void logout() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("index.xhtml");
    }
}
