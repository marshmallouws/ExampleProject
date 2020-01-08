/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

/**
 *
 * @author Annika
 */
public class UserFacadeTest {
    /*
    private static EntityManagerFactory emf;
    private static UserFacade facade;

    public UserFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = UserFacade.getUserFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.getTransaction().commit();

            em.getTransaction().begin();
            em.persist(new Person("Annika", "Ehlers", "mail", new Date(), "Unicorn", "1234", new ArrayList<Hobby>(), null));
            em.persist(new Person("Peter", "Bom", "mail", new Date(), "Peter", "1234", new ArrayList<Hobby>(), null));
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @Test
    public void testCreateUser() {
        //Arrange
        Person user = new Person("Jens", "Jensen", "jens@mail.dk", new Date(), "jens", "1234", null, null);
        List<Hobby> h = new ArrayList<>();
        PersonDTO exp = new PersonDTO(user);

        //Act
        PersonDTO res = facade.createUser(exp);

        //Assert
        assertEquals(exp.getFirstname(), res.getFirstname());
        assertEquals(exp.getDob(), res.getDob());
    }

    @Test
    public void createUser2() {
        Person user = new Person("Jens", "Jensen", "jens@mail.dk", new Date(), "jens", "1234", null, null);
        List<Hobby> h = new ArrayList<>();
        PersonDTO exp = new PersonDTO(user);

        //Act
        PersonDTO res = facade.createUser(exp);
    }

    @Test
    public void negativeTestCreateUser() {
    } */
}
