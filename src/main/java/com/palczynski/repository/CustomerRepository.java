package com.palczynski.repository;


import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Arrays.asList;

@Repository
public class CustomerRepository {

    private static final List<String> COUNTERPARTIES = asList("PLUTO1", "PLUTO2");

    public String findByName(String name) {
        return COUNTERPARTIES.stream().filter(c -> c.equals(name)).findFirst().orElse(null);
    }

}
