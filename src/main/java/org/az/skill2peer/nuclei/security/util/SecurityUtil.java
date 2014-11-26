package org.az.skill2peer.nuclei.security.util;

import org.az.skill2peer.nuclei.security.dto.SocialUserDetails;
import org.az.skill2peer.nuclei.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static SocialUserDetails getCurrentUser() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();
        return (SocialUserDetails)authentication.getPrincipal();
    }

    public static void logInUser(final User user) {
        LOGGER.info("Logging in user: {}", user);

        final SocialUserDetails userDetails = SocialUserDetails.getBuilder()
                .firstName(user.getFirstName())
                .id(user.getId())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .role(user.getRole())
                .socialSignInProvider(user.getSignInProvider())
                .username(user.getEmail())
                .build();
        LOGGER.debug("Logging in principal: {}", userDetails);

        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LOGGER.info("User: {} has been logged in.", userDetails);

        //        LocaleContextHolder.setLocale(Locale.);

    }

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SecurityUtil.class);
}
