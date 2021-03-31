package jm.security.example.service;

import jm.security.example.dao.UserDao;
import jm.security.example.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;
    private final UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    // «Пользователь» – это просто Object. В большинстве случаев он может быть
    //  приведен к классу UserDetails.
    // Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.getUserByName(s);
    }


    public List<User> allUser() {
        List<User> users = entityManager.createQuery("SELECT u from User u").getResultList();
        return users;
    }

    @Transactional
    public void add(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Transactional
    public void edit(int id, User editedUser) {
        User updatedUser = getById(id);
        entityManager.detach(updatedUser);
        updatedUser.setName(editedUser.getName());
        updatedUser.setPassword(editedUser.getPassword());
        updatedUser.setRoles(editedUser.getRoles());
        entityManager.merge(updatedUser);
    }

    public User getById(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.detach(user);
        return user;
    }
}
