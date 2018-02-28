package entity;

import entity.Accounts;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-28T17:24:03")
@StaticMetamodel(Images.class)
public class Images_ { 

    public static volatile SingularAttribute<Images, Serializable> img;
    public static volatile SingularAttribute<Images, Date> imgId;
    public static volatile SingularAttribute<Images, String> caption;
    public static volatile SingularAttribute<Images, Accounts> email;

}