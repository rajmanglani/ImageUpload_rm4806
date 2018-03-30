/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Accounts;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;
import session.AccountsFacade;

/**
 *This controller takes care of the event where a user tries to sign up with the system. It stores the user details in the database. 
 * It also validates the information to some extent. Password comparison and feedback is handled in the xhtml page createAccount. 
 * @author rm4806
 */
@Named(value = "createAccountController")
@RequestScoped
public class CreateAccountController {
    
    @EJB
    private AccountsFacade accFacade;
    
    private String email, fname, lname, password;      // These are the input fields from where values would be brought in to the bean

    /**
     * Creates a new instance of CreateAccountController
     */
    public CreateAccountController() {
        accFacade = new AccountsFacade();     // Initialize facade
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * This method gets the values in the input text areas of the UI and creates a java object of Accounts which maps to the table in the DB.
     * It then calls the create method and if it is able to create an entry in the DB, it informs the user and redirects to the login page.
     * Else it catches the exception and displays the error to the user in a formated manner. 
     */
    public void signupUser(){
        Accounts a = new Accounts();
        a.setEmail(email.toLowerCase());
        a.setFname(fname);
        a.setLname(lname);
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        a.setPassword(hashedPassword);
        try{
            accFacade.create(a);        // create record in DB
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Account created", "Thank you! Go ahead and login"));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect("index.xhtml");
            
        }catch(Exception e){   // SQL identical key exception
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Username already exists in records", "Enter a different email"));
        }
    }
}
