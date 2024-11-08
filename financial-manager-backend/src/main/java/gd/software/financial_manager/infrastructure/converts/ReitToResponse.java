package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.infrastructure.dtos.ReitResponse;

import java.util.List;

public class ReitToResponse {

    public static List<ReitResponse> convert(List<Reit> reits) {
        return reits.stream()
                .map(ReitToResponse::convert)
                .toList();
    }

    public static ReitResponse convert(Reit reit) {
        return new ReitResponse(reit.id(), reit.name(), reit.ticker(), reit.description(), reit.type(),
                reit.industrySegment(), reit.price());
    }
}
