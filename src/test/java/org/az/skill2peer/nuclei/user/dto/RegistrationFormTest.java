package org.az.skill2peer.nuclei.user.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.az.skill2peer.nuclei.user.dto.RegistrationForm;
import org.az.skill2peer.nuclei.user.model.SocialMediaService;
import org.junit.Test;

public class RegistrationFormTest {

    private static final SocialMediaService SIGN_IN_PROVIDER = SocialMediaService.TWITTER;

    @Test
    public void isNormalRegistration_SocialProviderNotSet_ShouldReturnTrue() {
        final RegistrationForm dto = new RegistrationFormBuilder().build();

        final boolean isNormalRegistration = dto.isNormalRegistration();

        assertThat(isNormalRegistration).isTrue();
    }

    @Test
    public void isNormalRegistration_SocialProviderSet_ShouldReturnFalse() {
        final RegistrationForm dto = new RegistrationFormBuilder()
                .isSocialSignInViaSignInProvider(SIGN_IN_PROVIDER)
                .build();

        final boolean isNormalRegistration = dto.isNormalRegistration();

        assertThat(isNormalRegistration).isFalse();
    }

    @Test
    public void isSocialSignIn_SocialProviderNotSet_ShouldReturnFalse() {
        final RegistrationForm dto = new RegistrationFormBuilder().build();

        final boolean isSocialSignIn = dto.isSocialSignIn();

        assertThat(isSocialSignIn).isFalse();
    }

    @Test
    public void isSocialSignIn_SocialProviderSet_ShouldReturnTrue() {
        final RegistrationForm dto = new RegistrationFormBuilder()
                .isSocialSignInViaSignInProvider(SIGN_IN_PROVIDER)
                .build();

        final boolean isSocialSignIn = dto.isSocialSignIn();

        assertThat(isSocialSignIn).isTrue();
    }
}
