package org.az.skill2peer.nuclei.user.service;

import org.az.skill2peer.nuclei.user.dto.RegistrationForm;
import org.az.skill2peer.nuclei.user.model.User;

public interface UserService {

    public User registerNewUserAccount(RegistrationForm userAccountData) throws DuplicateEmailException;
}
