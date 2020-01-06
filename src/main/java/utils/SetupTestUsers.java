//package utils;
//import entities.Role;
//import entities.User;
//import entities.Hobby;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//
//public class SetupTestUsers {
//  public static void main(String[] args) {
//
//    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
//    EntityManager em = emf.createEntityManager();
//    
//    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
//    // CHANGE the three passwords below, before you uncomment and execute the code below
//    // Also, either delete this file, when users are created or rename and add to .gitignore
//    // Whatever you do DO NOT COMMIT and PUSH with the real passwords
//
//    
//    List<Hobby> hobbies = new ArrayList<>();
//    hobbies.add(new Hobby("Ridning", "Det er sjovt"));
//    hobbies.add(new Hobby("Svømning", "Er for tabere"));
//    User user = new User("Annika", "Ehlers", "mail", new Date(), "Password", "Phone", null);
//    User admin = new User("Annika", "Ehlers", "mail1", new Date(), "Password1", "Phone", null);
//
//    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
//      throw new UnsupportedOperationException("You have not changed the passwords");
//
//    em.getTransaction().begin();
//    Role userRole = new Role("user");
//    Role adminRole = new Role("admin");
//    user.addRole(userRole);
//    admin.addRole(adminRole);
//    both.addRole(userRole);
//    both.addRole(adminRole);
//    em.persist(userRole);
//    em.persist(adminRole);
//    em.persist(user);
//    em.persist(admin);
//    em.persist(both);
//    em.getTransaction().commit();
//    System.out.println("PW: " + user.getUserPass());
//    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
//    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
//    System.out.println("Created TEST Users");
//   
//  }
//
//}
