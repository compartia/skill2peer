package org.az.skill2peer.nuclei.user.repository;

import org.az.skill2peer.nuclei.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
}
