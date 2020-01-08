package facades;

import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import errorhandling.AuthenticationException;
import errorhandling.CityNotFoundException;
import errorhandling.NotUniqueException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /*
    If I want all tables in database i must ensure 
    that fetch-type is eager in entities
     */
    public List<PersonDTO> getPersons() {
        EntityManager em = getEntityManager();
        List<PersonDTO> res = new ArrayList<>();
        List<Person> user;

        TypedQuery tq = em.createQuery("SELECT p FROM Person p", Person.class);
        user = tq.getResultList();
        user.forEach((u) -> res.add(new PersonDTO(u)));

        //System.out.println(res.get(0).getCity());
        return res;
    }

    //Create
    public PersonDTO createPerson(PersonDTO pdto) throws CityNotFoundException, NotUniqueException {
        EntityManager em = getEntityManager();
        List<Hobby> hobbies = new ArrayList<>();
        CityInfo city = null;
        
        // Checks if given zip exists
        try {
            city = CityFacade.getCityFacade(emf).getCityInfo(pdto.getZip());
        } catch (NoResultException e) {
            throw new CityNotFoundException(e.getMessage() + "Given zip is not found");
        }
        
        /*
        Checks if given email is already in the system. If yes, it throws an
        exception due to the fact that an email can only appear once. The
        email is used for logging in.
        */
        Query q = em.createQuery("SELECT COUNT(p.email) FROM Person p WHERE p.email = :email");
        q.setParameter("email", pdto.getEmail());
        if((Long) q.getSingleResult() > 0L) {
            throw new NotUniqueException(pdto.getEmail() + " is in use");
        }

        Address address = new Address(pdto.getStreet(), pdto.getAdditionalinfo(), city);

        /*
        Checking if each hobby exists in db. If it does, the persisted hobby
        will be added to ensure that there won't be duplicates. If not, a new
        hobby will be added
         */
        pdto.getHobbies().forEach((h) -> {
            try {
                Hobby hobby = HobbyFacade.getHobbyFacade(emf).findHobbyByName(h.getName());
                hobbies.add(hobby);
                System.out.println(h.getName());
            } catch (NoResultException e) {
                hobbies.add(new Hobby(h.getName(), h.getDescription()));
            }
        });

        //Create new Person from UserDTO
        Person user = new Person(pdto.getFirstname(), pdto.getLastname(), pdto.getEmail(),
                pdto.getDob(), pdto.getPassword(), pdto.getPhone(), hobbies, address);

        try {
            em.getTransaction().begin();
            em.merge(user); //merge instead of persist to ensure no duplicate hobbies
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new PersonDTO(user);
    }

    //Delete
    public PersonDTO deletePerson(PersonDTO personDto) {
        EntityManager em = getEntityManager();
        try {
            Person p = (Person) em.find(Person.class, personDto.getId());
            em.remove(p);
        } catch (Exception e) {
            // Make a personNotFoundException
        } finally {
            em.close();
        }
        return personDto;
    }

    public PersonDTO updatePerson(PersonDTO udto) {
        EntityManager em = getEntityManager();
        Person changed;
        List<Hobby> hobbies = new ArrayList<>();

        udto.getHobbies().forEach((h) -> {
            try {
                Hobby hobby = HobbyFacade.getHobbyFacade(emf).findHobbyByName(h.getName());
                hobbies.add(hobby);
                System.out.println(h.getName());
            } catch (NoResultException e) {
                hobbies.add(new Hobby(h.getName(), h.getDescription()));
            }
        });

        try {
            changed = em.find(Person.class, udto.getId());

            changed.setFirstName(udto.getFirstname());
            changed.setLastname(udto.getLastname());
            changed.setDateOfBirth(udto.getDob());
            changed.setHobbies(hobbies);
            changed.setEmail(udto.getEmail());
            changed.setPassword(udto.getPassword());
            changed.setPhone(udto.getPhone());

            em.getTransaction().begin();
            em.merge(changed);
            em.getTransaction().commit();

        } finally {
            em.close();
        }

        return new PersonDTO(changed);
    }

    //Read
    public PersonDTO getVerifiedPerson(String email, String password) throws AuthenticationException {
        EntityManager em = getEntityManager();

        Person user;
        try {
            // Use TypedQuery if you get an entity
            TypedQuery<Person> tq
                    = em.createQuery("SELECT p FROM Person p WHERE p.email = :email", Person.class);
            tq.setParameter(":email", email);
            user = tq.getSingleResult();

            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }

        return new PersonDTO(user);
    }

    public static void main(String[] args) throws CityNotFoundException {
        EntityManagerFactory emf2 = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
//        List<PersonDTO> users = getPersonFacade(emf2).getPersons();
//        users.forEach((h) -> System.out.println(h.getCity()));
//        //System.out.println(users.get(0).getFirstname());
//
//        List<Hobby> h = new ArrayList<>();
//        h.add(new Hobby("Ridning", "Somethigbn"));
//        h.add(new Hobby("Dykning", "Hajer er da meget søde"));
//
//        Address address = new Address("Sømoseparken", "80", new CityInfo(2750, "Ballerup"));
//
//        Person u = new Person("Annika", "Annika", "andersinde@mail.dk", new Date(), "password", "phone", h, address);
//        u.setId(4L);
//        PersonDTO u2 = new PersonDTO(u);
//        try {
//            getPersonFacade(emf2).createPerson(u2);
//        } catch (NotUniqueException e) {
//            System.out.println("Its aliveeee!");
//        }
//        
    }
}
