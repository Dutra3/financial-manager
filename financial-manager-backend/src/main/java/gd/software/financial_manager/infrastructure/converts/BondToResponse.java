package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.infrastructure.dtos.BondResponse;

public class BondToResponse {

    public static BondResponse convert(Bond bond) {
        return new BondResponse(bond.id(), bond.name(), bond.description(), bond.type(), bond.industrySegment(), bond.price());
    }
}
