/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.entity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 *
 * @author josselin
 */
@Entity
public class Requete implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLogiciel() {
        return logiciel;
    }
    public void setLogiciel(String logiciel) {
        this.logiciel = logiciel;
    }
    public String getSysteme() {
        return systeme;
    }
    public void setSysteme(String systeme) {
        this.systeme = systeme;
    }
    @Column(name = "PROBLEME", nullable = false, length = 750)
    public String getProbleme() {
        return probleme;
    }
    public void setProbleme(String probleme) {
        this.probleme = probleme;
    }
    @Column(name = "REPONSE", nullable = true, length = 750)
    public String getReponse() {
        return reponse;
    }
    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
    
    private String email, logiciel, systeme, probleme, reponse;
    public void setId(Long id) {
        this.id = id;
    }
    public Requete(){}
    public Requete(String email, String logiciel, String systeme, String probleme) {
        this.email = email;
        this.logiciel = logiciel;
        this.systeme = systeme;
        this.probleme = probleme;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) { // TODO: Warning -this method won't work in the case the id fields are not set
        if (!(object instanceof Requete)) {
            return false;
        }
        Requete other = (Requete) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "metier.Requete[ id=" + id + " ]";
    }

}
