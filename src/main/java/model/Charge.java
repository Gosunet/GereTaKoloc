package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by gosunet on 08/12/15.
 */
@Entity
public class Charge {

    @Id
    private ObjectId id;
    private String nameCharge;
    private String montant;

    public Charge() {
        this.id = new ObjectId();
        this.nameCharge="";
        this.montant="";
    }

    public Charge(String nameCharge, String montant) {
        this.nameCharge = nameCharge;
        this.montant = montant;
        this.id = new ObjectId();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNameCharge() {
        return nameCharge;
    }

    public void setNameCharge(String nameCharge) {
        this.nameCharge = nameCharge;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }
}
