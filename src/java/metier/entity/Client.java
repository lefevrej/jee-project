/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.entity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 *
 * @author josselin
 */
@Entity
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = GenerationType.AUTO) 
    private String id;
    public String getId() {
        return id;
    }
    public String getPrenom() {
        return Prenom;
    }
    public void setPrenom(String Prenom) {
        this.Prenom = Prenom;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public Boolean isSupport(){
        return support;
    }
    public void setSupport(boolean support){
        this.support=support;
    }
    String Prenom, nom, telephone;
    Boolean support;
    public void setId(String id) {
        this.id = id;
    }
    
    public Client(){}
    public Client(String id, String Prenom, String nom, String telephone, Boolean support) {
        this.id = id;
        this.Prenom = Prenom;
        this.nom = nom;
        this.telephone = telephone;
        this.support = support;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) { // TODO: Warning -this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "metier.Client[ id=" + id + " ]";
    }
}