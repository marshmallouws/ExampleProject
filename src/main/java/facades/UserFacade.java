package facades;

import dtos.UserDTO;
import entities.Hobby;
import entities.User;
import errorhandling.AuthenticationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class UserFacade {

    private static UserFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<UserDTO> getUsers() {
        EntityManager em = getEntityManager();
        List<UserDTO> res = new ArrayList<>();
        List<User> user;

        TypedQuery tq = em.createQuery("SELECT u FROM User u", User.class);
        user = tq.getResultList();
        user.forEach((u) -> res.add(new UserDTO(u, u.getHobbies(), u.getRole())));
        return res;
    }

    //Create
    public UserDTO createUser(UserDTO udto) {
        EntityManager em = getEntityManager();
        List<Hobby> hobbies = new ArrayList<>();

        /*
        Checking if each hobby exists in db. If it does, the persisted hobby
        will be added to ensure that there won't be duplicates. If not, a new
        hobby will be added
         */
        udto.getHobbies().forEach((h) -> {
            try {
                Hobby hobby = HobbyFacade.getHobbyFacade(emf).findHobbyByName(h.getName());
                hobbies.add(hobby);
                System.out.println(h.getName());
            } catch (NoResultException e) {
                hobbies.add(new Hobby(h.getName(), h.getDescription()));
            }
        });

        //Create new User from UserDTO
        User user = new User(udto.getFirstname(), udto.getLastname(), udto.getEmail(),
                udto.getDob(), udto.getPassword(), udto.getPhone(), hobbies);

        try {
            em.getTransaction().begin();
            em.merge(user); //merge instead of persist to ensure no duplicate hobbies
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new UserDTO(user, hobbies);
    }

    //Delete
    public UserDTO deleteUser(UserDTO personDto) {
        EntityManager em = getEntityManager();
        try {
            User p = (User) em.find(User.class, personDto.getId());
            em.remove(p);
        } catch (Exception e) {
            // Make a personNotFoundException
        } finally {
            em.close();
        }
        return personDto;
    }

    public UserDTO updateUser(UserDTO udto) {
        EntityManager em = getEntityManager();
        User changed;
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
            changed = em.find(User.class, udto.getId());

            
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

        return new UserDTO(changed, hobbies);
    }

    //Read
    public UserDTO getVerifiedUser(String email, String password) throws AuthenticationException {
        EntityManager em = getEntityManager();

        User person;
        try {
            // Use TypedQuery if you get an entity
            TypedQuery<User> tq
                    = em.createQuery("SELECT p FROM Person p WHERE p.email = :email", User.class);
            tq.setParameter(":email", email);
            person = tq.getSingleResult();

            if (person == null || !person.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }

        return new UserDTO(person, person.getHobbies());
    }
    
    /*
    public static void main(String[] args) {
        EntityManagerFactory emf2 = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
        List<Hobby> h = new ArrayList<>();
        h.add(new Hobby("Ridning", "Somethigbn"));
        h.add(new Hobby("Dykning", "Hajer er da meget søde"));

        User u = new User("Andersine", "Andersen", "annika@mail.dk", new Date(), "password", "phone", h);
        u.setId(4L);
        UserDTO u2 = new UserDTO(u, h);
        getUserFacade(emf2).updateUser(u2);
    } */
}
