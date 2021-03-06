package org.az.skill2peer.user.controller;

import static org.az.skill2peer.nuclei.common.Urls.USER_REGISTER;
import static org.az.skill2peer.nuclei.common.Urls.USER_REGISTRATION_FORM;
import static org.az.skill2peer.nuclei.common.Urls.USER_REGISTRATION_FORM_JSP;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.az.skill2peer.ColumnSensingFlatXMLDataSetLoader;
import org.az.skill2peer.IntegrationTestConstants;
import org.az.skill2peer.common.controller.AbstractControllerIntegrationTest;
import org.az.skill2peer.nuclei.TestUtil;
import org.az.skill2peer.nuclei.WebTestConstants;
import org.az.skill2peer.nuclei.user.dto.RegistrationForm;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;
import org.az.skill2peer.security.CsrfTokenBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.TestProviderSignInAttemptBuilder;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.connect.web.TestProviderSignInAttempt;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

/**
 *
 * @author Artem Zaborskiy
 *
 */

@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = ColumnSensingFlatXMLDataSetLoader.class)
public class ITRegistrationControllerTest extends AbstractControllerIntegrationTest {

    private static final String ACCESS_TOKEN = "accessToken";

    private static final String DISPLAY_NAME = "John Smith";

    private static final String EMAIL = "john.smith@gmail.com";

    private static final Long EXPIRE_TIME = 100000L;

    private static final String IMAGE_URL = "https://www.twitter.com/images/johnsmith.jpg";

    private static final String MALFORMED_EMAIL = "john.smithatgmail.com";

    private static final String FIRST_NAME = "John";

    private static final String LAST_NAME = "Smith";

    private static final String PASSWORD = "password";

    private static final String PASSWORD_VERIFICATION = "passwordVerification";

    private static final String PROFILE_URL = "https://www.twitter.com/johnsmith";

    private static final String PROVIDER_USER_ID = "johnsmith";

    private static final String REFRESH_TOKEN = "refreshToken";

    private static final String SECRET = "secret";

    private static final SocialMediaService SIGN_IN_PROVIDER = SocialMediaService.TWITTER;

    private static final String SOCIAL_MEDIA_SERVICE = "twitter";

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Test
    @DatabaseSetup("no-users.xml")
    @ExpectedDatabase(value = "register-normal-user-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void registerUserAccount_NormalRegistration_ShouldCreateNewUserAccountAndRenderHomePage() throws Exception {
        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post(USER_REGISTER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD)
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
                )
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DatabaseSetup("/org/az/skill2peer/nuclei/user/users.xml")
    @ExpectedDatabase(value = "/org/az/skill2peer/nuclei/user/users.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void registerUserAccount_NormalRegistrationAndEmailExists_ShouldRenderRegistrationFormWithFieldError()
            throws Exception {
        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post(USER_REGISTER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, IntegrationTestConstants.User.REGISTERED_USER.getUsername())
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD)
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
                )
                .andExpect(status().isOk())
                .andExpect(view().name(USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(
                                hasProperty(WebTestConstants.FORM_FIELD_EMAIL,
                                        is(IntegrationTestConstants.User.REGISTERED_USER.getUsername())),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, is(PASSWORD)),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString())
                        )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL
                        ));
    }

    @Test
    @DatabaseSetup("no-users.xml")
    @ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void registerUserAccount_NormalRegistrationAndEmptyForm_ShouldRenderRegistrationFormWithValidationErrors()
            throws Exception {
        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post(USER_REGISTER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
                )
                .andExpect(status().isOk())
                .andExpect(view().name(USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString())
                        )))
                .andExpect(model().attributeHasFieldErrors(
                        WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL,
                        WebTestConstants.FORM_FIELD_FIRST_NAME,
                        WebTestConstants.FORM_FIELD_LAST_NAME,
                        WebTestConstants.FORM_FIELD_PASSWORD,
                        WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION
                        ));
    }

    @Test
    @DatabaseSetup("no-users.xml")
    @ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void registerUserAccount_NormalRegistrationAndMalformedEmail_ShouldRenderRegistrationFormWithValidationError()
            throws Exception {
        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post(USER_REGISTER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, MALFORMED_EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD)
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
                )
                .andExpect(status().isOk())
                .andExpect(view().name(USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(MALFORMED_EMAIL)),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString())
                        )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL
                        ));
    }

    @Test
    @DatabaseSetup("no-users.xml")
    @ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void registerUserAccount_NormalRegistrationAndPasswordMismatch_ShouldRenderRegistrationFormWithValidationErrors()
            throws Exception {
        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post(USER_REGISTER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD_VERIFICATION)
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
                )
                .andExpect(status().isOk())
                .andExpect(view().name(USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(EMAIL)),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, is(PASSWORD_VERIFICATION)),
                        hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString())
                        )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_PASSWORD,
                        WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION
                        ));
    }

    @Test
    @DatabaseSetup("no-users.xml")
    @ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void registerUserAccount_NormalRegistrationAndTooLongValues_ShouldRenderRegistrationFormWithValidationErrors()
            throws Exception {
        final String email = TestUtil.makeStringOfLen(101);
        final String firstName = TestUtil.makeStringOfLen(101);
        final String lastName = TestUtil.makeStringOfLen(101);

        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post(USER_REGISTER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, email)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, firstName)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, lastName)
                .param(WebTestConstants.FORM_FIELD_PASSWORD, PASSWORD)
                .param(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, PASSWORD)
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
                )
                .andExpect(status().isOk())
                .andExpect(view().name(USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(email)),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(firstName)),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(lastName)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, is(PASSWORD)),
                        hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, isEmptyOrNullString())
                        )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL,
                        WebTestConstants.FORM_FIELD_FIRST_NAME,
                        WebTestConstants.FORM_FIELD_LAST_NAME
                        ));
    }

    @Test
    @DatabaseSetup("no-users.xml")
    @ExpectedDatabase(value = "register-social-user-expected.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void registerUserAccount_SocialSignIn_ShouldCreateNewUserAccountAndRenderHomePage() throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder()
                .connectionData()
                .accessToken(ACCESS_TOKEN)
                .displayName(DISPLAY_NAME)
                .expireTime(EXPIRE_TIME)
                .imageUrl(IMAGE_URL)
                .profileUrl(PROFILE_URL)
                .providerId(SOCIAL_MEDIA_SERVICE)
                .providerUserId(PROVIDER_USER_ID)
                .refreshToken(REFRESH_TOKEN)
                .secret(SECRET)
                .usersConnectionRepository(usersConnectionRepository)
                .userProfile()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post(USER_REGISTER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, SIGN_IN_PROVIDER.name())
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
                )
                .andExpect(status().isMovedTemporarily())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DatabaseSetup("/org/az/skill2peer/nuclei/user/users.xml")
    @ExpectedDatabase(value = "/org/az/skill2peer/nuclei/user/users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void registerUserAccount_SocialSignInAndEmailExist_ShouldRenderRegistrationFormWithFieldError()
            throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder()
                .connectionData()
                .accessToken(ACCESS_TOKEN)
                .displayName(DISPLAY_NAME)
                .expireTime(EXPIRE_TIME)
                .imageUrl(IMAGE_URL)
                .profileUrl(PROFILE_URL)
                .providerId(SOCIAL_MEDIA_SERVICE)
                .providerUserId(PROVIDER_USER_ID)
                .refreshToken(REFRESH_TOKEN)
                .secret(SECRET)
                .usersConnectionRepository(usersConnectionRepository)
                .userProfile()
                .email(IntegrationTestConstants.User.REGISTERED_USER.getUsername())
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post(USER_REGISTER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, IntegrationTestConstants.User.REGISTERED_USER.getUsername())
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, SIGN_IN_PROVIDER.name())
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
                )
                .andExpect(status().isOk())
                .andExpect(view().name(USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        allOf(
                                hasProperty(WebTestConstants.FORM_FIELD_EMAIL,
                                        is(IntegrationTestConstants.User.REGISTERED_USER.getUsername())),
                                hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, isEmptyOrNullString()),
                                hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, is(SIGN_IN_PROVIDER))
                        )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL
                        ));
    }

    @Test
    @DatabaseSetup("no-users.xml")
    @ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void registerUserAccount_SocialSignInAndEmptyForm_ShouldRenderRegistrationFormWithValidationErrors()
            throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder()
                .connectionData()
                .accessToken(ACCESS_TOKEN)
                .displayName(DISPLAY_NAME)
                .expireTime(EXPIRE_TIME)
                .imageUrl(IMAGE_URL)
                .profileUrl(PROFILE_URL)
                .providerId(SOCIAL_MEDIA_SERVICE)
                .providerUserId(PROVIDER_USER_ID)
                .refreshToken(REFRESH_TOKEN)
                .secret(SECRET)
                .usersConnectionRepository(usersConnectionRepository)
                .userProfile()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post(USER_REGISTER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, SIGN_IN_PROVIDER.name())
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
                )
                .andExpect(status().isOk())
                .andExpect(view().name(USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, is(SIGN_IN_PROVIDER))
                        )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL,
                        WebTestConstants.FORM_FIELD_FIRST_NAME,
                        WebTestConstants.FORM_FIELD_LAST_NAME
                        ));
    }

    @Test
    @DatabaseSetup("no-users.xml")
    @ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void registerUserAccount_SocialSignInAndMalformedEmail_ShouldRenderRegistrationFormWithValidationError()
            throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder()
                .connectionData()
                .accessToken(ACCESS_TOKEN)
                .displayName(DISPLAY_NAME)
                .expireTime(EXPIRE_TIME)
                .imageUrl(IMAGE_URL)
                .profileUrl(PROFILE_URL)
                .providerId(SOCIAL_MEDIA_SERVICE)
                .providerUserId(PROVIDER_USER_ID)
                .refreshToken(REFRESH_TOKEN)
                .secret(SECRET)
                .usersConnectionRepository(usersConnectionRepository)
                .userProfile()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post(USER_REGISTER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, MALFORMED_EMAIL)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, FIRST_NAME)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, LAST_NAME)
                .param(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, SIGN_IN_PROVIDER.name())
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
                )
                .andExpect(status().isOk())
                .andExpect(view().name(USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(MALFORMED_EMAIL)),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(FIRST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(LAST_NAME)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, is(SIGN_IN_PROVIDER))
                        )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL
                        ));
    }

    @Test
    @DatabaseSetup("no-users.xml")
    @ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void registerUserAccount_SocialSignInAndTooLongValues_ShouldRenderRegistrationFormWithValidationErrors()
            throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder()
                .connectionData()
                .accessToken(ACCESS_TOKEN)
                .displayName(DISPLAY_NAME)
                .expireTime(EXPIRE_TIME)
                .imageUrl(IMAGE_URL)
                .profileUrl(PROFILE_URL)
                .providerId(SOCIAL_MEDIA_SERVICE)
                .providerUserId(PROVIDER_USER_ID)
                .refreshToken(REFRESH_TOKEN)
                .secret(SECRET)
                .usersConnectionRepository(usersConnectionRepository)
                .userProfile()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        final String email = TestUtil.makeStringOfLen(101);
        final String firstName = TestUtil.makeStringOfLen(101);
        final String lastName = TestUtil.makeStringOfLen(101);

        final CsrfToken csrfToken = new CsrfTokenBuilder()
                .headerName(IntegrationTestConstants.CSRF_TOKEN_HEADER_NAME)
                .requestParameterName(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME)
                .tokenValue(IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .build();

        mockMvc.perform(post(USER_REGISTER)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(WebTestConstants.FORM_FIELD_EMAIL, email)
                .param(WebTestConstants.FORM_FIELD_FIRST_NAME, firstName)
                .param(WebTestConstants.FORM_FIELD_LAST_NAME, lastName)
                .param(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, SIGN_IN_PROVIDER.name())
                .param(IntegrationTestConstants.CSRF_TOKEN_REQUEST_PARAM_NAME,
                        IntegrationTestConstants.CSRF_TOKEN_VALUE)
                .sessionAttr(IntegrationTestConstants.CSRF_TOKEN_SESSION_ATTRIBUTE_NAME, csrfToken)
                .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn)
                .sessionAttr(WebTestConstants.SESSION_ATTRIBUTE_USER_FORM, new RegistrationForm())
                )
                .andExpect(status().isOk())
                .andExpect(view().name(USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM, allOf(
                        hasProperty(WebTestConstants.FORM_FIELD_EMAIL, is(email)),
                        hasProperty(WebTestConstants.FORM_FIELD_FIRST_NAME, is(firstName)),
                        hasProperty(WebTestConstants.FORM_FIELD_LAST_NAME, is(lastName)),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_PASSWORD_VERIFICATION, isEmptyOrNullString()),
                        hasProperty(WebTestConstants.FORM_FIELD_SIGN_IN_PROVIDER, is(SIGN_IN_PROVIDER))
                        )))
                .andExpect(model().attributeHasFieldErrors(WebTestConstants.MODEL_ATTRIBUTE_USER_FORM,
                        WebTestConstants.FORM_FIELD_EMAIL,
                        WebTestConstants.FORM_FIELD_FIRST_NAME,
                        WebTestConstants.FORM_FIELD_LAST_NAME
                        ));
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    @DatabaseSetup("no-users.xml")
    @ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void showRegistrationForm_NormalRegistration_ShouldRenderRegistrationPageWithEmptyForm() throws Exception {
        mockMvc.perform(get(USER_REGISTER))
                .andExpect(status().isOk())
                .andExpect(view().name(USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute("user", allOf(
                        hasProperty("email", isEmptyOrNullString()),
                        hasProperty("firstName", isEmptyOrNullString()),
                        hasProperty("lastName", isEmptyOrNullString()),
                        hasProperty("password", isEmptyOrNullString()),
                        hasProperty("passwordVerification", isEmptyOrNullString()),
                        hasProperty("signInProvider", isEmptyOrNullString())
                        )));
    }

    @Test
    @DatabaseSetup("no-users.xml")
    @ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void showRegistrationForm_SocialSignInWithAllValues_ShouldRenderRegistrationPageWithAllValuesSet()
            throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder()
                .connectionData()
                .providerId(SOCIAL_MEDIA_SERVICE)
                .userProfile()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();

        mockMvc.perform(get(USER_REGISTER)
                .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn)
                )
                .andExpect(status().isOk())
                .andExpect(view().name(USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute("user", allOf(
                        hasProperty("email", is(EMAIL)),
                        hasProperty("firstName", is(FIRST_NAME)),
                        hasProperty("lastName", is(LAST_NAME)),
                        hasProperty("password", isEmptyOrNullString()),
                        hasProperty("passwordVerification", isEmptyOrNullString()),
                        hasProperty("signInProvider", is(SIGN_IN_PROVIDER))
                        )));
    }

    @Test
    @DatabaseSetup("no-users.xml")
    @ExpectedDatabase(value = "no-users.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void showRegistrationForm_SocialSignInWithNoValues_ShouldRenderRegistrationPageWithProviderDetails()
            throws Exception {
        final TestProviderSignInAttempt socialSignIn = new TestProviderSignInAttemptBuilder()
                .connectionData()
                .providerId(SOCIAL_MEDIA_SERVICE)
                .userProfile()
                .build();

        mockMvc.perform(get(USER_REGISTER)
                .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, socialSignIn)
                )
                .andExpect(status().isOk())
                .andExpect(view().name(USER_REGISTRATION_FORM))
                .andExpect(forwardedUrl(USER_REGISTRATION_FORM_JSP))
                .andExpect(model().attribute("user", allOf(
                        hasProperty("email", isEmptyOrNullString()),
                        hasProperty("firstName", isEmptyOrNullString()),
                        hasProperty("lastName", isEmptyOrNullString()),
                        hasProperty("password", isEmptyOrNullString()),
                        hasProperty("passwordVerification", isEmptyOrNullString()),
                        hasProperty("signInProvider", is(SIGN_IN_PROVIDER))
                        )));
    }
}
