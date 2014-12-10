package org.az.skill2peer.nuclei.user.controller;

import static org.az.skill2peer.nuclei.Urls.USER_REGISTER;

import javax.validation.Valid;

import org.az.skill2peer.nuclei.security.util.SecurityUtil;
import org.az.skill2peer.nuclei.user.dto.RegistrationForm;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;
import org.az.skill2peer.nuclei.user.model.User;
import org.az.skill2peer.nuclei.user.service.DuplicateEmailException;
import org.az.skill2peer.nuclei.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;

@Controller
@SessionAttributes("user")
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    protected static final String ERROR_CODE_EMAIL_EXIST = "NotExist.user.email";

    protected static final String MODEL_NAME_REGISTRATION_DTO = "user";

    protected static final String VIEW_NAME_REGISTRATION_PAGE = "user/registrationForm";

    private final UserService service;

    @Autowired
    public RegistrationController(final UserService service) {
        this.service = service;
    }

    /**
     * Processes the form submissions of the registration form.
     */
    @RequestMapping(value = USER_REGISTER, method = RequestMethod.POST)
    public String registerUserAccount(@Valid @ModelAttribute("user") final RegistrationForm userAccountData,
            final BindingResult result,
            final WebRequest request) throws DuplicateEmailException {
        LOGGER.debug("Registering user account with information: {}", userAccountData);
        if (result.hasErrors()) {
            LOGGER.debug("Validation errors found. Rendering form view.");
            return VIEW_NAME_REGISTRATION_PAGE;
        }

        final User registered = createUserAccount(userAccountData, result);

        //If email address was already found from the database, render the form view.
        if (registered == null) {
            LOGGER.debug("An email address was found from the database. Rendering form view.");
            return VIEW_NAME_REGISTRATION_PAGE;
        }

        LOGGER.debug("Registered user account with information: {}", registered);

        //Logs the user in.
        SecurityUtil.logInUser(registered);
        LOGGER.debug("User {} has been signed in", registered);
        //If the user is signing in by using a social provider, this method call stores
        //the connection to the UserConnection table. Otherwise, this method does not
        //do anything.
        ProviderSignInUtils.handlePostSignUp(registered.getEmail(), request);

        return "redirect:/";
    }

    /**
     * Renders the registration page.
     */
    @RequestMapping(value = USER_REGISTER, method = RequestMethod.GET)
    public String showRegistrationForm(final WebRequest request, final Model model) {
        LOGGER.debug("Rendering registration page.");

        final Connection<?> connection = ProviderSignInUtils.getConnection(request);

        final RegistrationForm registration = createRegistrationDTO(connection);
        LOGGER.debug("Rendering registration form with information: {}", registration);

        model.addAttribute(MODEL_NAME_REGISTRATION_DTO, registration);

        return VIEW_NAME_REGISTRATION_PAGE;
    }

    private void addFieldError(final String objectName,
            final String fieldName,
            final String fieldValue,
            final String errorCode,
            final BindingResult result) {
        LOGGER.debug(
                "Adding field error object's: {} field: {} with error code: {}",
                objectName,
                fieldName,
                errorCode
                );
        final FieldError error = new FieldError(
                objectName,
                fieldName,
                fieldValue,
                false,
                new String[] { errorCode },
                new Object[] {},
                errorCode
                );

        result.addError(error);
        LOGGER.debug("Added field error: {} to binding result: {}", error, result);
    }

    /**
     * Creates the form object used in the registration form.
     * @param connection
     * @return  If a user is signing in by using a social provider, this method returns a form
     *          object populated by the values given by the provider. Otherwise this method returns
     *          an empty form object (normal form registration).
     */
    private RegistrationForm createRegistrationDTO(final Connection<?> connection) {
        final RegistrationForm dto = new RegistrationForm();

        if (connection != null) {
            final UserProfile socialMediaProfile = connection.fetchUserProfile();
            dto.setEmail(socialMediaProfile.getEmail());
            dto.setFirstName(socialMediaProfile.getFirstName());
            dto.setLastName(socialMediaProfile.getLastName());

            final ConnectionKey providerKey = connection.getKey();
            dto.setSignInProvider(SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase()));
        }

        return dto;
    }

    /**
     * Creates a new user account by calling the service method. If the email address is found
     * from the database, this method adds a field error to the email field of the form object.
     */
    private User createUserAccount(final RegistrationForm userAccountData, final BindingResult result) {
        //XXX: send confirmation email
        LOGGER.debug("Creating user account with information: {}", userAccountData);
        User registered = null;

        try {
            registered = service.registerNewUserAccount(userAccountData);
        } catch (final DuplicateEmailException ex) {
            LOGGER.debug("An email address: {} exists.", userAccountData.getEmail());
            addFieldError(
                    MODEL_NAME_REGISTRATION_DTO,
                    RegistrationForm.FIELD_NAME_EMAIL,
                    userAccountData.getEmail(),
                    ERROR_CODE_EMAIL_EXIST,
                    result);
        }

        return registered;
    }
}
