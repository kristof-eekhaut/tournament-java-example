package be.eekhaut.kristof.tournament.tournament.domain;

import be.eekhaut.kristof.tournament.common.domain.ddd.Identifier;
import be.eekhaut.kristof.tournament.common.domain.ddd.SingleValueObject;

public class OrganizerId extends SingleValueObject<Long> implements Identifier {

    public static OrganizerId organizerId(Long value) {
        return new OrganizerId(value);
    }

    public static OrganizerId organizerId(String value) {
        return new OrganizerId(Long.parseLong(value));
    }

    private OrganizerId(Long value) {
        super(value);
    }
}
