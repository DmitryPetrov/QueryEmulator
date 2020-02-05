package com.emulator.service;

import com.emulator.domain.organisation.OrganisationData;
import com.emulator.domain.organisation.OrganisationPool;
import com.emulator.domain.organisation.OrganisationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class OrganisationService {

    private static Logger log;
    private OrganisationPool pool;
    private UserService service;

    /*
        Constructor for tests
    */
    public OrganisationService(Logger logger, OrganisationPool pool, UserService service) {
        this.log = logger;
        this.pool = pool;
        this.service = service;
    }

    @Autowired
    public OrganisationService(OrganisationPool pool, UserService service) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.pool = pool;
        this.service = service;
    }

    public OrganisationResponse add(HttpSession session, OrganisationData org) {
        log.debug("OrganisationService: Add organisation data='" + org.toString() + "'");
        service.authorizationCheck(session);
        org.check();
        pool.add(org);
        return getSuccessPostOrganisations();
    }

    public OrganisationResponse getAll(HttpSession session) {
        log.debug("OrganisationService: Get organisations");
        service.authorizationCheck(session);
        return getSuccessGetOrganisations(pool.getAll());
    }


    public OrganisationResponse update(HttpSession session, String organisationId, OrganisationData org) {
        log.debug("OrganisationService: Update organisation id='" + organisationId + "' data='" + org.toString() + "'");
        service.authorizationCheck(session);
        org.check();
        long id = Long.parseLong(organisationId);
        pool.update(id, org);
        return getSuccessPutOrganisations();
    }

    public OrganisationResponse remove(HttpSession session, String organisationId) {
        log.debug("OrganisationService: Remove organisation id='" + organisationId + "'");
        service.authorizationCheck(session);
        long id = Long.parseLong(organisationId);
        pool.remove(id);
        return getSuccessDeleteOrganisations();
    }


    private OrganisationResponse getSuccessPostOrganisations() {
        OrganisationResponse result = new OrganisationResponse();
        result.setStatus("OK");
        result.setMessage("Add organisation succeed");
        log.debug("OrganisationService: Add organisation succeed");
        return result;
    }

    private OrganisationResponse getSuccessGetOrganisations(List<OrganisationData> orgs) {
        OrganisationResponse result = new OrganisationResponse();
        result.setStatus("OK");
        result.setMessage("Get organisations succeed");
        result.setOrganisations(orgs);
        log.debug("OrganisationService: Get organisation succeed");
        return result;
    }

    private OrganisationResponse getSuccessPutOrganisations() {
        OrganisationResponse result = new OrganisationResponse();
        result.setStatus("OK");
        result.setMessage("Update organisation succeed");
        log.debug("OrganisationService: Update organisation succeed");
        return result;
    }

    private OrganisationResponse getSuccessDeleteOrganisations() {
        OrganisationResponse result = new OrganisationResponse();
        result.setStatus("OK");
        result.setMessage("Remove organisation succeed");
        log.debug("OrganisationService: Remove organisation succeed");
        return result;
    }
}
