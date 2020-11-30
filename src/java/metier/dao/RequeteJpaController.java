package metier.dao;
import metier.entity.Requete;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import metier.exceptions.NonexistentEntityException;

/**
 *
 * @author josselin
 */
public class RequeteJpaController implements Serializable {
    public RequeteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public void create(Requete requete) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(requete);
            em.getTransaction().commit();
            
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void edit(Requete requete) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            requete = em.merge(requete);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = requete.getId();
                if (findRequete(id) == null) {
                    throw new NonexistentEntityException("The requete with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Requete requete;
            try {
                requete = em.getReference(Requete.class, id);
                requete.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The requete with id " + id + " no longer exists.", enfe);
            }
            em.remove(requete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public List < Requete > findRequeteEntities() {
        return findRequeteEntities(true, -1, -1);
    }
    public List < Requete > findRequeteEntities(int maxResults, int firstResult) {
        return findRequeteEntities(false, maxResults, firstResult);
    }
    private List < Requete > findRequeteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Requete.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
     
    /**
     * Return requests belonging to the user with id==email.
     * @param email - primary key of user
     * @return list of Requete belonging to user
     */
    public List < Requete > findRequeteEntitiesOfUser(String email) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();            
            CriteriaQuery cq = cb.createQuery();
            Root<Requete> rootItem = cq.from(Requete.class);
            Predicate predicate = cb.equal(rootItem.get("email"), email);
            cq.where(predicate);
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    public Requete findRequete(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Requete.class, id);
        } finally {
            em.close();
        }
    }
    public int getRequeteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root < Requete > rt = cq.from(Requete.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}