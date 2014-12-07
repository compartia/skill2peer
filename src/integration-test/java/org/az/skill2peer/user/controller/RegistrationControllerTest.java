package org.az.skill2peer.user.controller;

import static org.az.skill2peer.nuclei.Urls.USER_REGISTER;
import static org.az.skill2peer.nuclei.security.SecurityContextAssert.assertThat;
import static org.az.skill2peer.nuclei.user.RegistrationFormAssert.assertThatRegistrationForm;
import static org.az.skill2peer.nuclei.user.TestProviderSignInAttemptAssert.assertThatSignIn;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.az.skill2peer.config.UnitTestContext;
import org.az.skill2peer.nuclei.TestUtil;
import org.az.skill2peer.nuclei.Urls;
import org.az.skill2peer.nuclei.WebTestConstants;
import org.az.skill2peer.nuclei.config.WebAppContext;
import org.az.skill2peer.nuclei.model.UserBuilder;
import org.az.skill2peer.nuclei.user.dto.RegistrationForm;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;
import org.az.skill2peer.nuclei.user.model.User;
import org.az.skill2peer.nuclei.user.service.DuplicateEmailException;
import org.az.skill2peer.nuclei.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.support.TestProviderSignInAttemptBuilder;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.connect.web.TestProviderSignInAttempt;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Petri Kainulainen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { UnitTestContext.class, WebAppContext.class })
@WebAppConfiguration
@IntegrationTest
public class RegistrationControllerTest {

    private static final String EMAIL = "john.smith@gmail.com";

    private static final String MALFORMED_EMAIL = "john.smithatgmail.com";

    private static final String FIRST_NAME = "John";

    private static final String LAST_NAME = "Donn";

    private static final String PASSWORD = "password";

    private static final String PASSWORD_VERIFICATION = "passwordVerification";

    private static final SocialMediaService SIGN_IN_PROVIDER = SocialMediaService.TWITTER;

    private static final String SOCIAL_MEDIA_SERVICE = "twitter";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private UserService userServiceMock;

    @Test
    public void registerUserAccount_NormalRegistration_ShouldCreateNewUserAccountAndRenderHomePage() throws Exception {
        final User registered = new UserBuilder().id(1).email(EMAIL).firstName(FIRST_NAME).lastName(LAST_NAME)
                .password(PASSWORD).build();

        when(userServiceMock.registerNewUserAccount(isA(RegistrationForm.class))).thenReturn(registered);

        mockMvc.perform(post(USER_REGISTER).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm()))
                .andExpect(status().isMovedTemporarily()).andExpect(redirectedUrl("/"));

        assertThat(SecurityContextHolder.getContext()).loggedInUserIs(registered)
                .loggedInUserHasPassword(registered.getPassword()).loggedInUserIsRegisteredByUsingNormalRegistration();

        final ArgumentCaptor<RegistrationForm> registrationFormArgument = ArgumentCaptor
                .forClass(RegistrationForm.class);
        verify(userServiceMock, times(1)).registerNewUserAccount(registrationFormArgument.capture());
        verifyNoMoreInteractions(userServiceMock);

        final RegistrationForm formObject = registrationFormArgument.getValue();
        assertThatRegistrationForm(formObject).isNormalRegistration().hasEmail(EMAIL).hasFirstName(FIRST_NAME)
                .hasLastName(LAST_NAME).hasPassword(PASSWORD).hasPasswordVerification(PASSWORD);
    }

    @Test
    public void registerUserAccount_NormalRegistrationAndEmailExists_ShouldRenderRegistrationFormWithFieldError()
            throws Exception {
        when(userServiceMock.registerNewUserAccount(isA(RegistrationForm.class)))
                .thenThrow(new DuplicateEmailException(""));

        mockMvc.perform(post(USER_REGISTER).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm()))
                .andExpect(status().isOk())
                .andExpect(view().name(Urls.USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(Urls.USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(EMAIL)),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString()))))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL));

        assertThat(SecurityContextHolder.getContext()).userIsAnonymous();

        final ArgumentCaptor<RegistrationForm> registrationFormArgument = ArgumentCaptor
                .forClass(RegistrationForm.class);
        verify(userServiceMock, times(1)).registerNewUserAccount(registrationFormArgument.capture());
        verifyNoMoreInteractions(userServiceMock);

        final RegistrationForm formObject = registrationFormArgument.getValue();
        assertThatRegistrationForm(formObject).isNormalRegistration().hasEmail(EMAIL).hasFirstName(FIRST_NAME)
                .hasLastName(LAST_NAME).hasPassword(PASSWORD).hasPasswordVerification(PASSWORD);
    }

    @Test
    public void registerUserAccount_NormalRegistrationAndEmptyForm_ShouldRenderRegistrationFormWithValidationErrors()
            throws Exception {
        mockMvc.perform(post(USER_REGISTER).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm()))
                .andExpect(status().isOk())
                .andExpect(view().name(Urls.USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(Urls.USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(hasProperty(WebTestConstants.FORM_FIELD_EMAIL, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString()))))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL,
                        WebTestConstants.FORM_FIELD_FIRST_NAME,
                        WebTestConstants.FORM_FIELD_LAST_NAME,
                        WebTestConstants.FORM_FIELD_PASSWORD,
                        WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION));

        assertThat(SecurityContextHolder.getContext()).userIsAnonymous();
        verifyZeroInteractions(userServiceMock);
    }

    @Test
    public void registerUserAccount_NormalRegistrationAndMalformedEmail_ShouldRenderRegistrationFormWithValidationError()
            throws Exception {
        mockMvc.perform(post(USER_REGISTER).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, MALFORMED_EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm()))
                .andExpect(status().isOk())
                .andExpect(view().name(Urls.USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(Urls.USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(MALFORMED_EMAIL)),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString()))))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL));

        assertThat(SecurityContextHolder.getContext()).userIsAnonymous();

        verifyZeroInteractions(userServiceMock);
    }

    @Test
    public void registerUserAccount_NormalRegistrationAndPasswordMismatch_ShouldRenderRegistrationFormWithValidationErrors()
            throws Exception {
        mockMvc.perform(post(USER_REGISTER).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD_VERIFICATION)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm()))
                .andExpect(status().isOk())
                .andExpect(view().name(Urls.USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(Urls.USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(EMAIL)),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION,
                                        is(PASSWORD_VERIFICATION)),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString()))))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_PASSWORD,
                        WebTestConstants.FORM_FIELD_PASSWORD));

        assertThat(SecurityContextHolder.getContext()).userIsAnonymous();
        verifyZeroInteractions(userServiceMock);
    }

    @Test
    public void registerUserAccount_NormalRegistrationAndTooLongValues_ShouldRenderRegistrationFormWithValidationErrors()
            throws Exception {

        final String email = TestUtil.makeStringOfLen(101);
        final String firstName = TestUtil.makeStringOfLen(101);
        final String lastName = TestUtil.makeStringOfLen(101);

        mockMvc.perform(post(USER_REGISTER).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, email)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, firstName)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, lastName)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm()))
                .andExpect(status().isOk())
                .andExpect(view().name(Urls.USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(Urls.USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(email)),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(firstName)),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(lastName)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, is(PASSWORD)),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString()))))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL,
                        WebTestConstants.FORM_FIELD_FIRST_NAME,
                        WebTestConstants.FORM_FIELD_LAST_NAME));

        assertThat(SecurityContextHolder.getContext()).userIsAnonymous();
        verifyZeroInteractions(userServiceMock);
    }

    @Test
    public void registerUserAccount_SocialSignIn_ShouldCreateNewUserAccountAndRenderHomePage() throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder().connectionData()
                .providerId(SOCIAL_MEDIA_SERVICE).userProfile().email(EMAIL).firstName(FIRST_NAME).lastName(LAST_NAME)
                .build();

        final User registered = new UserBuilder().id(1).email(EMAIL).firstName(FIRST_NAME).lastName(LAST_NAME)
                .signInProvider(SIGN_IN_PROVIDER).build();

        when(userServiceMock.registerNewUserAccount(isA(RegistrationForm.class))).thenReturn(registered);

        mockMvc.perform(post(USER_REGISTER).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, SIGN_IN_PROVIDER.name())
                .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn)
                .sessionAttr(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, new RegistrationForm()))
                .andExpect(status().isMovedTemporarily()).andExpect(redirectedUrl("/"));

        assertThat(SecurityContextHolder.getContext()).loggedInUserIs(registered)
                .loggedInUserIsSignedInByUsingSocialProvider(SIGN_IN_PROVIDER);
        assertThatSignIn(socialSignIn).createdConnectionForUserId(EMAIL);

        final ArgumentCaptor<RegistrationForm> registrationFormArgument = ArgumentCaptor
                .forClass(RegistrationForm.class);
        verify(userServiceMock, times(1)).registerNewUserAccount(registrationFormArgument.capture());
        verifyNoMoreInteractions(userServiceMock);

        final RegistrationForm formObject = registrationFormArgument.getValue();
        assertThatRegistrationForm(formObject).isSocialSignInWithSignInProvider(SIGN_IN_PROVIDER).hasEmail(EMAIL)
                .hasFirstName(FIRST_NAME).hasLastName(LAST_NAME).hasNoPassword().hasNoPasswordVerification();
    }

    @Test
    public void registerUserAccount_SocialSignInAndEmailExist_ShouldRenderRegistrationFormWithFieldError()
            throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder().connectionData()
                .providerId(SOCIAL_MEDIA_SERVICE).userProfile().email(EMAIL).firstName(FIRST_NAME).lastName(LAST_NAME)
                .build();

        when(userServiceMock.registerNewUserAccount(isA(RegistrationForm.class)))
                .thenThrow(new DuplicateEmailException(""));

        mockMvc.perform(post(USER_REGISTER).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, SIGN_IN_PROVIDER.name())
                .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm()))
                .andExpect(status().isOk())
                .andExpect(view().name(Urls.USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(Urls.USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(EMAIL)),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, is(SIGN_IN_PROVIDER)))))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL));

        assertThat(SecurityContextHolder.getContext()).userIsAnonymous();
        assertThatSignIn(socialSignIn).createdNoConnections();

        final ArgumentCaptor<RegistrationForm> registrationFormArgument = ArgumentCaptor
                .forClass(RegistrationForm.class);
        verify(userServiceMock, times(1)).registerNewUserAccount(registrationFormArgument.capture());
        verifyNoMoreInteractions(userServiceMock);

        final RegistrationForm formObject = registrationFormArgument.getValue();
        assertThatRegistrationForm(formObject).isSocialSignInWithSignInProvider(SIGN_IN_PROVIDER).hasEmail(EMAIL)
                .hasFirstName(FIRST_NAME).hasLastName(LAST_NAME).hasNoPassword().hasNoPasswordVerification();
    }

    @Test
    public void registerUserAccount_SocialSignInAndEmptyForm_ShouldRenderRegistrationFormWithValidationErrors()
            throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder().connectionData()
                .providerId(SOCIAL_MEDIA_SERVICE).userProfile().email(EMAIL).firstName(FIRST_NAME).lastName(LAST_NAME)
                .build();

        mockMvc.perform(post(USER_REGISTER).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, SIGN_IN_PROVIDER.name())
                .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm()))
                .andExpect(status().isOk())
                .andExpect(view().name(Urls.USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(Urls.USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(hasProperty(WebTestConstants.FORM_FIELD_EMAIL, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, is(SIGN_IN_PROVIDER)))))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL,
                        WebTestConstants.FORM_FIELD_FIRST_NAME,
                        WebTestConstants.FORM_FIELD_LAST_NAME));

        assertThat(SecurityContextHolder.getContext()).userIsAnonymous();
        assertThatSignIn(socialSignIn).createdNoConnections();
        verifyZeroInteractions(userServiceMock);
    }

    @Test
    public void registerUserAccount_SocialSignInAndMalformedEmail_ShouldRenderRegistrationFormWithValidationError()
            throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder().connectionData()
                .providerId(SOCIAL_MEDIA_SERVICE).userProfile().email(EMAIL).firstName(FIRST_NAME).lastName(LAST_NAME)
                .build();

        mockMvc.perform(post(USER_REGISTER).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, MALFORMED_EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, SIGN_IN_PROVIDER.name())
                .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm()))
                .andExpect(status().isOk())
                .andExpect(view().name(Urls.USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(Urls.USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(MALFORMED_EMAIL)),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, is(SIGN_IN_PROVIDER)))))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL));

        assertThat(SecurityContextHolder.getContext()).userIsAnonymous();
        assertThatSignIn(socialSignIn).createdNoConnections();
        verifyZeroInteractions(userServiceMock);
    }

    @Test
    public void registerUserAccount_SocialSignInAndTooLongValues_ShouldRenderRegistrationFormWithValidationErrors()
            throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder().connectionData()
                .providerId(SOCIAL_MEDIA_SERVICE).userProfile().email(EMAIL).firstName(FIRST_NAME).lastName(LAST_NAME)
                .build();

        final String email = TestUtil.makeStringOfLen(101);
        final String firstName = TestUtil.makeStringOfLen(101);
        final String lastName = TestUtil.makeStringOfLen(101);

        mockMvc.perform(post(USER_REGISTER).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, email)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, firstName)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, lastName)
                .param(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, SIGN_IN_PROVIDER.name())
                .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm()))
                .andExpect(status().isOk())
                .andExpect(view().name(Urls.USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(Urls.USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(email)),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(firstName)),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(lastName)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, is(SIGN_IN_PROVIDER)))))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL,
                        WebTestConstants.FORM_FIELD_FIRST_NAME,
                        WebTestConstants.FORM_FIELD_LAST_NAME));

        assertThat(SecurityContextHolder.getContext()).userIsAnonymous();
        assertThatSignIn(socialSignIn).createdNoConnections();
        verifyZeroInteractions(userServiceMock);
    }

    @Before
    public void setUp() {
        Mockito.reset(userServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();

        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    public void showRegistrationForm_NormalRegistration_ShouldRenderRegistrationPageWithEmptyForm() throws Exception {
        mockMvc.perform(get(USER_REGISTER))
                .andExpect(status().isOk())
                .andExpect(view().name(Urls.USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(Urls.USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(hasProperty(WebTestConstants.FORM_FIELD_EMAIL, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString()))));

        verifyZeroInteractions(userServiceMock);
    }

    @Test
    public void showRegistrationForm_SocialSignInWithAllValues_ShouldRenderRegistrationPageWithAllValuesSet()
            throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder().connectionData()
                .providerId(SOCIAL_MEDIA_SERVICE).userProfile().email(EMAIL).firstName(FIRST_NAME).lastName(LAST_NAME)
                .build();

        mockMvc.perform(get(USER_REGISTER).sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn))
                .andExpect(status().isOk())
                .andExpect(view().name(Urls.USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(Urls.USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(EMAIL)),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, is(SIGN_IN_PROVIDER)))));

        verifyZeroInteractions(userServiceMock);
    }

    @Test
    public void showRegistrationForm_SocialSignInWithNoValues_ShouldRenderRegistrationPageWithProviderDetails()
            throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder().connectionData()
                .providerId(SOCIAL_MEDIA_SERVICE).userProfile().build();

        mockMvc.perform(get(USER_REGISTER).sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn))
                .andExpect(status().isOk())
                .andExpect(view().name(Urls.USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(Urls.USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(hasProperty(WebTestConstants.FORM_FIELD_EMAIL, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, is(SIGN_IN_PROVIDER)))));

        verifyZeroInteractions(userServiceMock);
    }
}
