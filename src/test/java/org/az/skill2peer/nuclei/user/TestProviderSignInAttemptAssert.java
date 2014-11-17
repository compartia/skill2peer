package org.az.skill2peer.nuclei.user;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.springframework.social.connect.web.TestProviderSignInAttempt;

public class TestProviderSignInAttemptAssert
        extends AbstractAssert<TestProviderSignInAttemptAssert, TestProviderSignInAttempt> {

    private TestProviderSignInAttemptAssert(final TestProviderSignInAttempt actual) {
        super(actual, TestProviderSignInAttemptAssert.class);
    }

    public static TestProviderSignInAttemptAssert assertThatSignIn(final TestProviderSignInAttempt actual) {
        return new TestProviderSignInAttemptAssert(actual);
    }

    public TestProviderSignInAttemptAssert createdNoConnections() {
        isNotNull();

        Assertions.assertThat(actual.getConnections())
                .overridingErrorMessage("Expected that no connections were created but found <%d> connection",
                        actual.getConnections().size()
                )
                .isEmpty();

        return this;
    }

    public TestProviderSignInAttemptAssert createdConnectionForUserId(final String userId) {
        isNotNull();

        Assertions.assertThat(actual.getConnections())
                .overridingErrorMessage("Expected that connection was created for user id <%s> but found none.",
                        userId
                )
                .contains(userId);

        return this;
    }
}
