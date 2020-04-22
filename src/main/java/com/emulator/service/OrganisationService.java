package com.emulator.service;

import com.emulator.domain.organisation.*;
import com.emulator.repository.OrganisationRepository;
import com.emulator.repository.entity.Organisation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganisationService {

    private static Logger log;
    private OrganisationRepository orgRepo;
    private UserService service;
    private OrganisationTransformer transformer;

    /*
        Constructor for tests
    */
    public OrganisationService(Logger logger, OrganisationRepository orgRepo, UserService service,
                               OrganisationTransformer transformer) {
        this.log = logger;
        this.orgRepo = orgRepo;
        this.service = service;
        this.transformer = transformer;
    }

    @Autowired
    public OrganisationService(OrganisationRepository orgRepo, UserService service,
                               OrganisationTransformer transformer) {
        this.log = LoggerFactory.getLogger(this.getClass());
        this.orgRepo = orgRepo;
        this.service = service;
        this.transformer = transformer;
    }

    public OrganisationResponse add(HttpSession session, OrganisationData orgData) {
        log.debug("OrganisationService: Add organisation data='" + orgData.toString() + "'");
        service.authorizationCheck(session);
        orgData.check();
        Organisation organisation = transformer.transform(orgData);
        orgRepo.save(organisation);
        return getSuccessPostOrganisations();
    }

    public OrganisationResponse getAll(HttpSession session) {
        log.debug("OrganisationService: Get organisations");
        service.authorizationCheck(session);
        List<Organisation> orgs = orgRepo.findAll();
        List<OrganisationData> orgsData = new ArrayList<>(orgs.size());
        for (Organisation org : orgs) {
            orgsData.add(transformer.transform(org));
        }
        return getSuccessGetOrganisations(orgsData);
    }


    public OrganisationResponse update(HttpSession session, String organisationId,
                                       OrganisationData orgData) {
        log.debug("OrganisationService: Update organisation id='" + organisationId + "' data='" + orgData.toString() + "'");
        service.authorizationCheck(session);
        orgData.check();
        long id = Long.parseLong(organisationId);
        Organisation org = transformer.transform(orgData);
        orgRepo.save(org);
        return getSuccessPutOrganisations();
    }

    public OrganisationResponse remove(HttpSession session, String organisationId) {
        log.debug("OrganisationService: Remove organisation id='" + organisationId + "'");
        service.authorizationCheck(session);
        long id = Long.parseLong(organisationId);
        orgRepo.deleteById(id);
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
