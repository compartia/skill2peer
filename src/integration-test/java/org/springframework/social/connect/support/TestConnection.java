package org.springframework.social.connect.support;

import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;

public class TestConnection extends AbstractConnection {

    private final ConnectionData connectionData;

    private final UserProfile userProfile;

    public TestConnection(final ConnectionData connectionData, final UserProfile userProfile) {
        super(connectionData, null);
        this.connectionData = connectionData;
        this.userProfile = userProfile;
    }

    @Override
    public UserProfile fetchUserProfile() {
        return userProfile;
    }

    @Override
    public Object getApi() {
        return null;
    }

    @Override
    public ConnectionData createData() {
        return connectionData;
    }
}
