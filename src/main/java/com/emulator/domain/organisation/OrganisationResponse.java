package com.emulator.domain.organisation;

import com.emulator.domain.frontend.response.Response;

import java.util.List;

public class OrganisationResponse extends Response {

    private List<OrganisationData> organisations;

    public List<OrganisationData> getOrganisations() {
        return organisations;
    }

    public void setOrganisations(List<OrganisationData> organisations) {
        this.organisations = organisations;
    }
}
