package com.emulator.domain.organisation;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrganisationPool {

    private List<OrganisationData> pool = new ArrayList<>();

    public void add(OrganisationData org) {
        pool.add(org);
    }

    public void update(long id, OrganisationData org) {
        if (id >= (pool.size() - 1)) {
            return;
        }
        pool.set((int) id, org);
    }

    public void remove(long id) {
        pool.remove(id);
    }

    public List<OrganisationData> getAll() {
        return pool;
    }
}
