/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Accounts;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import session.AccountsFacade;

/**
 * This controller encapsulates the logic for allowing an user to login to the system. It brings the values of email and password 
 * from the UI and check it against the ones in the DB. If the credentials match, it grants access else 
 * tells the user what error had occurred. 
 * @author rm4806
 */
@Named(value = "loginController")
@RequestScoped
public class LoginController {

    @EJB
    private AccountsFacade accFacade;
    
    private String email, password;
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
    
    /**
     * This method gets the values from the UI. It first tries to find the account object with the email specified by the user.
     * If it can find it, it compares the password provided by the user to the one in the DB. Else it tells the user that the 
     * email specified is not present in the DB. 
     * @throws IOException 
     */
    public void login() throws IOException{
        Accounts temp = accFacade.find(this.getEmail().toLowerCase());
            if(temp == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email error","Cannot find email account.." ));
            }else{
                if(temp.getPassword().equals(this.getPassword())){
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("email", this.getEmail());  // This makes sure that the email of the user is passed on to the next page so that we know who the user is
                    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                    ec.redirect("faces/homePage.xhtml");
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password error","Password does not match.." ));

                }
            }
 
    }
}
