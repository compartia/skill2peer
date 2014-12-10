package org.az.skill2peer.nuclei.security.dto;

import java.util.Collection;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.az.skill2peer.nuclei.user.model.Role;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;
import org.springframework.security.core.GrantedAuthority;

public class UserDetailsAssert extends AbstractAssert<UserDetailsAssert, SocialUserDetails> {

    public static UserDetailsAssert assertThat(final SocialUserDetails actual) {
        return new UserDetailsAssert(actual);
    }

    private UserDetailsAssert(final SocialUserDetails actual) {
        super(actual, UserDetailsAssert.class);
    }

    public UserDetailsAssert hasFirstName(final String firstName) {
        isNotNull();

        Assertions
                .assertThat(actual.getFirstName())
                .overridingErrorMessage("Expected first name to be <%s> but was <%s>", firstName, actual.getFirstName())
                .isEqualTo(firstName);

        return this;
    }

    public UserDetailsAssert hasId(final Integer id) {
        isNotNull();

        Assertions.assertThat(actual.getId())
                .overridingErrorMessage("Expected id to be <%d> but was <%d>", id, actual.getId()).isEqualTo(id);

        return this;
    }

    public UserDetailsAssert hasLastName(final String lastName) {
        isNotNull();

        Assertions.assertThat(actual.getLastName())
                .overridingErrorMessage("Expected last name to be <%s> but was <%s>", lastName, actual.getLastName())
                .isEqualTo(lastName);

        return this;
    }

    public UserDetailsAssert hasPassword(final String password) {
        isNotNull();

        Assertions.assertThat(actual.getPassword())
                .overridingErrorMessage("Expected password to be <%s> but was <%s>", password, actual.getPassword())
                .isEqualTo(password);

        return this;
    }

    public UserDetailsAssert hasUsername(final String username) {
        isNotNull();

        Assertions.assertThat(actual.getUsername())
                .overridingErrorMessage("Expected username to be <%s> but was <%s>", username, actual.getUsername())
                .isEqualTo(username);

        return this;
    }

    public UserDetailsAssert isActive() {
        isNotNull();

        Assertions.assertThat(actual.isAccountNonExpired())
                .overridingErrorMessage("Expected account to be non expired but it was expired").isTrue();

        Assertions.assertThat(actual.isAccountNonLocked())
                .overridingErrorMessage("Expected account to be non locked but it was locked").isTrue();

        Assertions.assertThat(actual.isCredentialsNonExpired())
                .overridingErrorMessage("Expected credentials to be non expired but they were expired").isTrue();

        Assertions.assertThat(actual.isEnabled())
                .overridingErrorMessage("Expected account to be enabled but it was not").isTrue();

        return this;
    }

    public UserDetailsAssert isRegisteredByUsingFormRegistration() {
        isNotNull();

        Assertions
                .assertThat(actual.getSocialSignInProvider())
                .overridingErrorMessage("Expected socialSignInProvider to be <null> but was <%s>",
                        actual.getSocialSignInProvider()).isNull();

        return this;
    }

    public UserDetailsAssert isRegisteredUser() {
        isNotNull();

        Assertions.assertThat(actual.getRole())
                .overridingErrorMessage("Expected role to be <ROLE_USER> but was <%s>", actual.getRole())
                .isEqualTo(Role.ROLE_USER);

        final Collection<? extends GrantedAuthority> authorities = actual.getAuthorities();

        Assertions.assertThat(authorities.size())
                .overridingErrorMessage("Expected <1> granted authority but found <%d>", authorities.size())
                .isEqualTo(1);

        final GrantedAuthority authority = authorities.iterator().next();

        Assertions.assertThat(authority.getAuthority())
                .overridingErrorMessage("Expected authority to be <ROLE_USER> but was <%s>", authority.getAuthority())
                .isEqualTo(Role.ROLE_USER.name());

        return this;
    }

    public UserDetailsAssert isSignedInByUsingSocialSignInProvider(final SocialMediaService socialSignInProvider) {
        isNotNull();

        Assertions
                .assertThat(actual.getSocialSignInProvider())
                .overridingErrorMessage("Expected socialSignInProvider to be <%s> but was <%s>",
                        socialSignInProvider,
                        actual.getSocialSignInProvider()).isEqualTo(socialSignInProvider);

        return this;
    }
}
