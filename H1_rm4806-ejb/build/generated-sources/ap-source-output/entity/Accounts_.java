package entity;

import entity.Images;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-28T17:24:03")
@StaticMetamodel(Accounts.class)
public class Accounts_ { 

    public static volatile SingularAttribute<Accounts, String> fname;
    public static volatile SingularAttribute<Accounts, String> password;
    public static volatile SingularAttribute<Accounts, String> lname;
    public static volatile SingularAttribute<Accounts, String> email;
    public static volatile ListAttribute<Accounts, Images> imagesList;

}