package org.az.skill2peer.nuclei.security.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.az.skill2peer.nuclei.user.model.Role;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

public class SocialUserDetails extends SocialUser {

    public static class Builder {

        private final Set<GrantedAuthority> authorities;

        private String firstName;

        private Integer id;

        private String lastName;

        private String password;

        private Role role;

        private SocialMediaService socialSignInProvider;

        private String username;

        public Builder() {
            this.authorities = new HashSet<>();
        }

        public SocialUserDetails build() {
            final SocialUserDetails user = new SocialUserDetails(username, password, authorities);

            user.id = id;
            user.firstName = firstName;
            user.lastName = lastName;
            user.role = role;
            user.socialSignInProvider = socialSignInProvider;

            return user;
        }

        public Builder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder id(final Integer id) {
            this.id = id;
            return this;
        }

        public Builder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder password(String password) {
            if (password == null) {
                password = "SocialUser";
            }

            this.password = password;
            return this;
        }

        public Builder role(final Role role) {
            this.role = role;

            final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
            this.authorities.add(authority);

            return this;
        }

        public Builder socialSignInProvider(final SocialMediaService socialSignInProvider) {
            this.socialSignInProvider = socialSignInProvider;
            return this;
        }

        public Builder username(final String username) {
            this.username = username;
            return this;
        }
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    private static final long serialVersionUID = 7062690430735778761L;

    private String firstName;

    private Integer id;

    private String lastName;

    private Role role;

    private SocialMediaService socialSignInProvider;

    public SocialUserDetails(final String username,
            final String password,
            final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public String getFirstName() {
        return firstName;
    }

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }

    public SocialMediaService getSocialSignInProvider() {
        return socialSignInProvider;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("username", getUsername())
                .append("firstName", firstName).append("lastName", lastName).append("role", role)
                .append("socialSignInProvider", socialSignInProvider).toString();
    }
}
