/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier1;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author josselin
 */
@ManagedBean 
@SessionScoped

public class Hotline {
    @EJB
    private Requete requete;
    private Client client;
    
    private String prenom, nom, email, telephone, logiciel, systeme, probleme; /*** Creates a new instance of Hotline*/
    public Hotline(){
        requete = new Requete();
    } 
    
    public Requete getRequete() {
        return requete;
    }
    public void setRequete(Requete requete) {
        this.requete = requete;
    }
    public String getProbleme() {
        return probleme;
    }
    public void setProbleme(String probleme) {
        this.probleme = probleme;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
    public String isLogged(){
        return email==null ? "FALSE" : "TRUE";
    }
    public String inscrireClient() {
        Client c = new Client();
        c.setId(email);
        c.setPrenom(prenom);
        c.setNom(nom);
        c.setTelephone(telephone);
        c.setSupport(false);        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("finalPU");
        ClientJpaController clientControleur = new ClientJpaController(emf);
        clientControleur.create(c);
        return "ok";
    }
    public String sauvegardeRequete() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("finalPU");
        RequeteJpaController j = new RequeteJpaController(emf);
        Requete r = new Requete();
        r.setEmail(email);
        r.setLogiciel(logiciel);
        r.setProbleme(probleme);
        r.setSysteme(systeme);
        j.create(r);
        ClientJpaController clientControleur = new ClientJpaController(emf);
        Client c = clientControleur.findClient(email);
        if (c != null) return "LIST";
        else return "client";
    }
    
    public List<Requete> getRequetes(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("finalPU");
        RequeteJpaController j = new RequeteJpaController(emf);
        return j.findRequeteEntities();
    }
    
    public List<Requete> getRequetesFromUser(String email){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("finalPU");
        ClientJpaController c = new ClientJpaController(emf);
        if(c.isClientSupport(email)) return getRequetes();
        RequeteJpaController j = new RequeteJpaController(emf);
        return j.findRequeteEntitiesOfUser(email);
    }
    
    
    /**
     * Action handler - user selects a request record from the list
     * (data table) to view/edit
     * @param requete
     * @return
     */
    public String showDetails(Requete requete){
        this.requete = requete;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("finalPU");
        ClientJpaController c = new ClientJpaController(emf);
        if(c.isClientSupport(email)) return "SUPPORT";
        return "DETAILS";
    }
    
    /**
     * Action handler - update the changes in the Request object to the
     * database
     * @return
     */
    public String update(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("finalPU");
        RequeteJpaController j = new RequeteJpaController(emf);
        try {
            j.edit(this.requete);
        } catch (Exception ex) {
            Logger.getLogger(Hotline.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "SAVED";
    }
    
        /**
     * Action handler - remove the request object from the database
     * @return
     */
    public String remove(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("finalPU");
        RequeteJpaController j = new RequeteJpaController(emf);
        try {
            j.destroy(this.requete.getId());
        } catch (Exception ex) {
            Logger.getLogger(Hotline.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "REMOVED";
    }
}