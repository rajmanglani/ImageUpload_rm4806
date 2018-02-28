/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rm4806
 */
@Entity
@Table(name = "IMAGES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Images.findAll", query = "SELECT i FROM Images i")
    , @NamedQuery(name = "Images.findByImgId", query = "SELECT i FROM Images i WHERE i.imgId = :imgId")
    , @NamedQuery(name = "Images.findByCaption", query = "SELECT i FROM Images i WHERE i.caption = :caption")})
public class Images implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMG_ID")
    @Temporal(TemporalType.TIMESTAMP)
    private Date imgId;
    @Lob
    @Column(name = "IMG")
    private Serializable img;
    @Size(max = 1024)
    @Column(name = "CAPTION")
    private String caption;
    @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL")
    @ManyToOne
    private Accounts email;

    public Images() {
    }

    public Images(Date imgId) {
        this.imgId = imgId;
    }

    public Date getImgId() {
        return imgId;
    }

    public void setImgId(Date imgId) {
        this.imgId = imgId;
    }

    public Serializable getImg() {
        return img;
    }

    public void setImg(Serializable img) {
        this.img = img;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Accounts getEmail() {
        return email;
    }

    public void setEmail(Accounts email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imgId != null ? imgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Images)) {
            return false;
        }
        Images other = (Images) object;
        if ((this.imgId == null && other.imgId != null) || (this.imgId != null && !this.imgId.equals(other.imgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Images[ imgId=" + imgId + " ]";
    }
    
}
