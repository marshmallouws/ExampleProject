/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Hobby;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Annika
 */
public class HobbyFacade {
    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private HobbyFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public Hobby findHobbyByName(String hobbyName) {
        EntityManager em = getEntityManager();
        Hobby h = null;
        try {
            TypedQuery tq = em.createQuery("SELECT h FROM Hobby h WHERE h.name = :name", Hobby.class);
            tq.setParameter("name", hobbyName);
            h = (Hobby) tq.getSingleResult();
        } finally {
            em.close();
        }
        return h;
    }
}
