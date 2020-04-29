package com.emulator.controller;

import com.emulator.domain.frontend.response.Response;
import com.emulator.domain.organisation.OrganisationData;
import com.emulator.service.OrganisationService;
import com.emulator.exception.UserIsNotAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/organisations")
public class OrganisationController {

    private ServiceController service;
    private OrganisationService orgService;

    @Autowired
    public OrganisationController(ServiceController serviceController, OrganisationService orgService) {
        this.service = serviceController;
        this.orgService = orgService;
    }

    @PostMapping
    @ResponseBody
    public Response addOrg(HttpSession session, @RequestBody OrganisationData org) {
        try {
            return orgService.add(session, org);
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }

    @GetMapping
    public Response getOrgs(HttpSession session) {
        try {
            return orgService.getAll(session);
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Response updateOrg(HttpSession session, @PathVariable String id, @RequestBody OrganisationData org) {
        try {
            return orgService.update(session, id, org);
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Response removeOrg(HttpSession session, @PathVariable String id) {
        try {
            return orgService.remove(session, id);
        } catch (UserIsNotAuthorizedException e) {
            return service.getUserIsNotAuthorizedResponse();
        } catch (Exception e) {
            return service.getServerFailResponse(e);
        }
    }
}
