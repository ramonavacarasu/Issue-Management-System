package ims.issues.boundary;

import ims.issues.entity.Issue;
import ims.issues.entity.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Singleton
@Startup
public class MockData {

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        //Dummy data to begin with
        User adminUser = new User(1L, "Admin");
        em.persist(adminUser);

        Issue issue1 = new Issue("Fix A", adminUser);
        issue1.setDescription("fix is required for A");
        issue1.setCreated(LocalDateTime.now());
        em.persist(issue1);

        User guestUser = new User(2L, "Guest");
        em.persist(guestUser);
        Issue issue2 = new Issue("Fix B", guestUser);
        issue2.setDescription("fix is required for B");
        issue2.setCreated(LocalDateTime.now());
        em.persist(issue2);
    }
}
