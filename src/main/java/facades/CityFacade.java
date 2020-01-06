/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.CityInfo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Annika
 */
public class CityFacade {

    private static CityFacade instance;
    private static EntityManagerFactory emf;

    private CityFacade() {
    }

    public static CityFacade getCityFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CityInfo getCityInfo(int zip) {
        EntityManager em = getEntityManager();
        CityInfo cinfo;
        try {
            TypedQuery<CityInfo> inf
                    = em.createQuery("SELECT c FROM CityInfo c WHERE c.zip = :zip", CityInfo.class);
            inf.setParameter("zip", zip);
            cinfo = inf.getSingleResult();
        } finally {
            em.close();
        }
        return cinfo;
    }
}
