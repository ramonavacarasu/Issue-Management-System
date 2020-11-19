package ims.users.boundary;

import ims.users.entity.Credential;
import ims.users.entity.User;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class MockData {

    @Inject
    private UsersService service;

    @PostConstruct
    public void init() {
        //Dummy accounts
        User adminUser = new User();
        adminUser.setCredential(new Credential("admin", "admin"));
        adminUser.setEmail("admin@ims.com");
        adminUser.setName("Admin");
        service.add(adminUser);

        User guestUser = new User();
        guestUser.setCredential(new Credential("guest", "guest"));
        guestUser.setEmail("guest@ims.com");
        guestUser.setName("Guest");
        service.add(guestUser);
    }
}
