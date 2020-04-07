package com.emulator.domain.organisation;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    default void update(Organisation org, long id) {
        throw new NotYetImplementedException();
    }
}
