package app.security;

import app.exceptions.EntityNotFoundException;
import app.exceptions.ValidationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


public class SecurityDAO implements ISecurityDAO {

    private final EntityManagerFactory emf;

    public SecurityDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public User getVerifiedUser(String username, String password) throws ValidationException {
        try (EntityManager em = emf.createEntityManager()) {
            User foundUser = em.find(User.class, username);
            if (foundUser.checkPassword(password)) {
                return foundUser;
            } else {
                throw new ValidationException("Invalid password");
            }
        }
    }

    @Override
    public User createUser(String username, String password) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = new User(username, password);
            em.persist(user);
            em.getTransaction().commit();
            return user;
        }
    }


    @Override
    public Role createRole(String role) {
        try(EntityManager em = emf.createEntityManager()) {
            Role role1 = em.find(Role.class, role);
            em.getTransaction().begin();
            em.persist(role1);
            em.getTransaction().commit();
            return role1;
        }
    }

    @Override
    public User addUserRole(String username, String role) throws EntityNotFoundException {
        try(EntityManager em = emf.createEntityManager()) {
            User foundUser = em.find(User.class, username);
            Role foundRole = em.find(Role.class, role);
            if (foundUser == null || foundRole == null) {
                throw new EntityNotFoundException("User not found");
            }
            em.getTransaction().begin();
            foundUser.addRole(foundRole);
            em.getTransaction().commit();
            return foundUser;
        }
    }
}
