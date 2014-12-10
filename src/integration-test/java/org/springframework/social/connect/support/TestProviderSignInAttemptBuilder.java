package org.springframework.social.connect.support;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.TestProviderSignInAttempt;

public class TestProviderSignInAttemptBuilder {

    private String accessToken;

    private String displayName;

    private String email;

    private Long expireTime;

    private String firstName;

    private String imageUrl;

    private String lastName;

    private String profileUrl;

    private String providerId;

    private String providerUserId;

    private String refreshToken;

    private String secret;

    private UsersConnectionRepository usersConnectionRepository;

    public TestProviderSignInAttemptBuilder() {

    }

    public TestProviderSignInAttemptBuilder accessToken(final String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public TestProviderSignInAttemptBuilder connectionData() {
        return this;
    }

    public TestProviderSignInAttemptBuilder displayName(final String displayName) {
        this.displayName = displayName;
        return this;
    }

    public TestProviderSignInAttemptBuilder email(final String email) {
        this.email = email;
        return this;
    }

    public TestProviderSignInAttemptBuilder expireTime(final Long expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public TestProviderSignInAttemptBuilder firstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public TestProviderSignInAttemptBuilder imageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public TestProviderSignInAttemptBuilder lastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public TestProviderSignInAttemptBuilder profileUrl(final String profileUrl) {
        this.profileUrl = profileUrl;
        return this;
    }

    public TestProviderSignInAttemptBuilder providerId(final String providerId) {
        this.providerId = providerId;
        return this;
    }

    public TestProviderSignInAttemptBuilder providerUserId(final String providerUserId) {
        this.providerUserId = providerUserId;
        return this;
    }

    public TestProviderSignInAttemptBuilder refreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public TestProviderSignInAttemptBuilder secret(final String secret) {
        this.secret = secret;
        return this;
    }

    public TestProviderSignInAttemptBuilder usersConnectionRepository(final UsersConnectionRepository usersConnectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
        return this;
    }

    public TestProviderSignInAttemptBuilder userProfile() {
        return this;
    }

    public TestProviderSignInAttempt build() {
        final ConnectionData connectionData = new ConnectionData(providerId,
                providerUserId,
                displayName,
                profileUrl,
                imageUrl,
                accessToken,
                secret,
                refreshToken,
                expireTime);

        final UserProfile userProfile = new UserProfileBuilder()
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName)
                .build();

        final Connection connection = new TestConnection(connectionData, userProfile);

        return new TestProviderSignInAttempt(connection, usersConnectionRepository);
    }
}
