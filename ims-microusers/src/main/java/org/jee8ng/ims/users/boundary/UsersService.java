package org.jee8ng.ims.users.boundary;

import org.jee8ng.ims.users.entity.User;

import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.List;

@Stateless
public class UsersService {

    private static final List<User> list = Arrays.asList(
            new User(1L, "Ramona"),
            new User(2L, "Bogdan"),
            new User(3L, "Maria"));

    public List<User> getAll() {
        return list;
    }

    public User getUser(Long id) {
        return list.get(0);
    }

    public Long add(User newUser) {
        return 4L;
    }

    public void updateIfExists(Long id,User existingUser) {
    }

    public void delete(Long id) {
    }
}

