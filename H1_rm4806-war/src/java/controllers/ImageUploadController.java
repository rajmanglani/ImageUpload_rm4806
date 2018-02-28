/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Accounts;
import entity.Images;
import java.io.IOException;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import session.AccountsFacade;
import session.ImagesFacade;

/**
 *This controller encapsulates the logic for uploading images to the db. It uploads images associated with the user who is 
 * uploading the images. 
 * @author rm4806
 */
@Named(value = "imageUploadController")
@RequestScoped
public class ImageUploadController {

    @EJB
    private ImagesFacade imgFacade;
    
    @EJB
    private AccountsFacade accFacade;
    
    private String caption;
    private String email;
    
    /**
     * Creates a new instance of ImageUploadController
     */
    public ImageUploadController() {
        imgFacade = new ImagesFacade();
        accFacade = new AccountsFacade();
    }
    
    @PostConstruct
    public void init(){
        //Bring email from the session map which was put after the user was authenticated. 
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("email");
    }
    
    public void upload(FileUploadEvent event) throws IOException{
        UploadedFile uf = event.getFile();
        byte[] img = IOUtils.toByteArray(uf.getInputstream());     // Use IOUtils to convert image file to byte array since it is serializable
        Images image = new Images();
        image.setImgId(new Date());             // Timestamp which is also the PK in the images table
        image.setImg(img);
        Accounts a = accFacade.find(email);    // Set the user as the foreign key 
        image.setEmail(a);
        //Bring the caption from the form since uploading is an ajax event.
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        image.setCaption(req.getParameter("form1:caption"));
        imgFacade.create(image);       // Insert image in the db
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Size: ", uf.getFileName() + " " + uf.getSize()));
        //Refresh page
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    
    public void goToViewImg() throws IOException{
        // Redirect to the view images page
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("ViewImages.xhtml");
    }
    
}
