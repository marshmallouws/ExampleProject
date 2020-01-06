package facades;

import dtos.UserDTO;
import entities.Hobby;
import entities.User;
import errorhandling.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class UserFacade {
    
    private static UserFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private UserFacade() {}
    
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
    public UserDTO createUser(UserDTO pdto) {
        EntityManager em = getEntityManager();
        List<Hobby> hobbies = new ArrayList<>();
        
        pdto.getHobbies().forEach((h) -> 
                hobbies.add(new Hobby(h.getName(), h.getDescription())));
        
        
        User person = new User(pdto.getFirstname(), pdto.getLastname(), pdto.getEmail(), 
                pdto.getDob(), pdto.getPassword(), pdto.getPhone(), hobbies);
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        
        return new UserDTO(person, hobbies);
    }
    
    //Delete
    public UserDTO deleteUser(UserDTO personDto) {
        EntityManager em = getEntityManager();
        try {
            User p = (User) em.find(User.class, personDto.getId());
            em.remove(p);
        } catch(Exception e) {
            // Make a personNotFoundException
        } finally {
            em.close();
        }
        return personDto;
    }
    
    public UserDTO updateUser(UserDTO pdto) {
        EntityManager em = getEntityManager();
        User changed;
        List<Hobby> hobbies = new ArrayList<>();
        
        try {
            changed = em.find(User.class, pdto.getId());
            pdto.getHobbies().forEach((h) -> hobbies.add(new Hobby(h.getName(), h.getDescription())));
            
            em.getTransaction().begin();
            changed.setFirstName(pdto.getFirstname());
            changed.setLastname(pdto.getLastname());
            changed.setDateOfBirth(pdto.getDob());
            changed.setHobbies(hobbies);
            changed.setEmail(pdto.getEmail());
            changed.setPassword(pdto.getPassword());
            changed.setPhone(pdto.getPhone());
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
            TypedQuery<User> tq = 
                    em.createQuery("SELECT p FROM Person p WHERE p.email = :email", User.class);
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
}
