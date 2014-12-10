package org.az.skill2peer.nuclei.security.service;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.az.skill2peer.nuclei.security.dto.UserDetailsAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.az.skill2peer.nuclei.model.UserBuilder;
import org.az.skill2peer.nuclei.security.dto.SocialUserDetails;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;
import org.az.skill2peer.nuclei.user.model.User;
import org.az.skill2peer.nuclei.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryUserDetailsServiceTest {

    private static final String EMAIL = "foo@bar.com";

    private static final String FIRST_NAME = "Foo";

    private static final Integer ID = 1;

    private static final String LAST_NAME = "Bar";

    private static final String PASSWORD = "password";

    private RepositoryUserDetailsService service;

    @Mock
    private UserRepository repositoryMock;

    @Before
    public void setUp() {
        service = new RepositoryUserDetailsService(repositoryMock);
    }

    @Test
    public void loadByUsername_UserNotFound_ShouldThrowException() {
        when(repositoryMock.findByEmail(EMAIL)).thenReturn(null);

        catchException(service).loadUserByUsername(EMAIL);
        Assertions.assertThat(caughtException()).isExactlyInstanceOf(UsernameNotFoundException.class)
                .hasMessage("No user found with username: " + EMAIL).hasNoCause();

        verify(repositoryMock, times(1)).findByEmail(EMAIL);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void loadByUsername_UserRegisteredByUsingFormRegistration_ShouldReturnCorrectUserDetails() {
        final User found = new UserBuilder().email(EMAIL).firstName(FIRST_NAME).id(ID).lastName(LAST_NAME)
                .password(PASSWORD).build();

        when(repositoryMock.findByEmail(EMAIL)).thenReturn(found);

        final UserDetails user = service.loadUserByUsername(EMAIL);
        final SocialUserDetails actual = (SocialUserDetails)user;

        assertThat(actual).hasFirstName(FIRST_NAME).hasId(ID).hasLastName(LAST_NAME).hasPassword(PASSWORD)
                .hasUsername(EMAIL).isActive().isRegisteredUser().isRegisteredByUsingFormRegistration();

        verify(repositoryMock, times(1)).findByEmail(EMAIL);
        verifyNoMoreInteractions(repositoryMock);
    }

    @Test
    public void loadByUsername_UserSignedInByUsingSocialSignInProvider_ShouldReturnCorrectUserDetails() {
        final User found = new UserBuilder().email(EMAIL).firstName(FIRST_NAME).id(ID).lastName(LAST_NAME)
                .signInProvider(SocialMediaService.TWITTER).build();

        when(repositoryMock.findByEmail(EMAIL)).thenReturn(found);

        final UserDetails user = service.loadUserByUsername(EMAIL);
        final SocialUserDetails actual = (SocialUserDetails)user;

        assertThat(actual).hasFirstName(FIRST_NAME).hasId(ID).hasLastName(LAST_NAME).hasUsername(EMAIL).isActive()
                .isRegisteredUser().isSignedInByUsingSocialSignInProvider(SocialMediaService.TWITTER);

        verify(repositoryMock, times(1)).findByEmail(EMAIL);
        verifyNoMoreInteractions(repositoryMock);
    }
}
