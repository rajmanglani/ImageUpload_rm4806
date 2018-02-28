/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Images;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import session.AccountsFacade;
import session.ImagesFacade;

/**
 *This controller encapsulates the logic for viewing images specific/related to the user who is logged in. 
 * @author rm4806
 */
@Named(value = "imageServiceController")
@RequestScoped
public class ImageServiceController {

    @EJB
    private ImagesFacade imgFacade;
    
    @EJB
    private AccountsFacade accFacade;
    
    private List<Images> allImgs;
    private String email;
    
    /**
     * Creates a new instance of ImageServiceController
     */
    public ImageServiceController() {
        imgFacade = new ImagesFacade();
        accFacade = new AccountsFacade();
        allImgs = new ArrayList<>();
    }
    
    /**
     * This method is called every time the page is initiated.  
     */
    @PostConstruct
    public void init(){
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("email"); // Get email of user who is logged in
        List<Images> images = imgFacade.findAll();    // get all images from the db
        for(Images i : images){
            if(i.getEmail().getEmail().equals(email)){  // email of the images matches with the user logged in, add to the list to display
                allImgs.add(i);
            }
        }
        Collections.sort(allImgs, new Comparator<Images>(){
            @Override
            public int compare(Images o1, Images o2) {
                return o2.getImgId().compareTo(o1.getImgId());    // Newest to oldest sorting
            }
            
        });
    }
    
    /**
     * This method returns the streamed content object to the jsf page to display the image. It reads the byte array from the db and 
     * converts into the streamed content of type image. 
     * @return
     * @throws IOException
     * @throws ParseException 
     */
    public StreamedContent getImage() throws IOException, ParseException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we are rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Get ID value from actual request param.
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            for(Images i : allImgs){
                if(i.getImgId().toString().equals(id)){
                    ByteArrayInputStream bis = new ByteArrayInputStream((byte[]) i.getImg());
                    return new DefaultStreamedContent(bis);
                }
            }
            
            
        }
        return null;
    }
    
    public List<Images> getAllImgs() {
        return allImgs;
    }

    public void setAllImgs(List<Images> allImgs) {
        this.allImgs = allImgs;
    }
    
    /**
     * This method gets the id of the image the user wants to delete. It removes the image from the 
     * DB and refreshes the page. 
     * @param d
     * @throws IOException 
     */
    public void removeImg(Date d) throws IOException{
        Images temp = imgFacade.find(d);
        imgFacade.remove(temp);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
    
    /**
     * This method captures the edited values of the caption and updates it in the db. 
     * @param event 
     */
    public void onRowEdit(RowEditEvent event){
        Date id = ((Images) event.getObject()).getImgId();
        Images temp = imgFacade.find(id);
        temp.setCaption(((Images) event.getObject()).getCaption());
        imgFacade.edit(temp);
        FacesMessage msg = new FacesMessage("Image caption Edited");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowCancel(RowEditEvent event){
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
    
    public void goToUpload() throws IOException{
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("homePage.xhtml");
    }    
}
