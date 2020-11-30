package metier.bean;
import metier.dao.RequeteJpaController;
import metier.dao.ClientJpaController;
import metier.entity.Requete;
import metier.entity.Client;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import metier.exceptions.NonexistentEntityException;

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
    
    private String prenom, nom, email, telephone, logiciel, systeme, probleme; 
    EntityManagerFactory emf;
    public Hotline(){
        this.requete = new Requete();
        this.client = new Client();
        emf = Persistence.createEntityManagerFactory("finalPU");
    } 
    
    public Requete getRequete() {
        return requete;
    }
    public void setRequete(Requete requete) {
        this.requete = requete;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
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
    /**
     * Check if client is logged and return the rigth signal.
     * @return "TRUE" if client is logged, "FALSE" otherwise
     */
    public String isLogged(){
        ClientJpaController c = new ClientJpaController(emf);
        client=c.findClient(email);
        return email==null || client==null? "FALSE" : "TRUE";
    }
    /**
     * Create a new Client entity from the form informations
     * and create the corresponding entry in database.
     * @return "LIST" signal
     */
    public String inscrireClient() {
        client = new Client(email, prenom, nom, telephone, false);
        ClientJpaController clientControleur = new ClientJpaController(emf);
        clientControleur.create(client);
        return "LIST";
    }
    
    /**
     * Create a new Requete entity from the form informations 
     * and create the correspondig entry in database.
     * @return return "TRUE" if client is logged, "FALSE" otherwise
     */
    public String sauvegardeRequete() {
        RequeteJpaController j = new RequeteJpaController(emf);
        Requete r = new Requete(email, logiciel, systeme, probleme);
        j.create(r);
        return isLogged();
    }
    
    /**
     * Return a list of all existing Requete entry in database.
     * @return List of all Requete from database
     */
    public List<Requete> getRequetes(){
        RequeteJpaController j = new RequeteJpaController(emf);
        return j.findRequeteEntities();
    }
    
    /**
     * Return a list of all existing Requete belonging to
     * a given user from the database.
     * @param email - email=primary key of user
     * @return List of all Requete from database belonging to user
     */
    public List<Requete> getRequetesFromUser(String email){
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
        RequeteJpaController j = new RequeteJpaController(emf);
        try {
            j.destroy(this.requete.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Hotline.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "REMOVED";
    }
}