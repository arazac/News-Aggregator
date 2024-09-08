package org.news.aggregator.repository;

import org.news.aggregator.entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {

    RegistrationToken findByToken(String token);
}
