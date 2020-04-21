package com.emulator.repository;

import com.emulator.repository.entity.Organisation;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

}
