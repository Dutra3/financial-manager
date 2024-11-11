package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.infrastructure.dtos.BondResponse;

import java.util.List;

public class BondToResponse {

    public static List<BondResponse> convert(List<Bond> bonds) {
        return bonds.stream()
                .map(BondToResponse::convert)
                .toList();
    }

    public static BondResponse convert(Bond bond) {
        return new BondResponse(bond.id(), bond.name(), bond.description(), bond.type(), bond.industrySegment(), bond.price());
    }
}
