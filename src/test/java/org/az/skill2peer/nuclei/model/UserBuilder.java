package org.az.skill2peer.nuclei.model;

import org.az.skill2peer.nuclei.user.model.SocialMediaService;
import org.az.skill2peer.nuclei.user.model.User;
import org.springframework.test.util.ReflectionTestUtils;

public class UserBuilder {

    private Integer id;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private SocialMediaService signInProvider;

    public UserBuilder() {

    }

    public UserBuilder email(final String email) {
        this.email = email;
        return this;
    }

    public UserBuilder firstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder id(final Integer id) {
        this.id = id;
        return this;
    }

    public UserBuilder lastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder password(final String password) {
        this.password = password;
        return this;
    }

    public UserBuilder signInProvider(final SocialMediaService signInProvider) {
        this.signInProvider = signInProvider;
        return this;
    }

    public User build() {
        final User user = User.getBuilder().email(email).firstName(firstName).lastName(lastName).password(password)
                .signInProvider(signInProvider).build();

        ReflectionTestUtils.setField(user, "id", id);

        return user;
    }
}
