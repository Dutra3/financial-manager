package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.infrastructure.dtos.EtfResponse;

import java.util.List;

public class EtfToResponse {

    public static List<EtfResponse> convert(List<Etf> etfs) {
        return etfs.stream()
                .map(EtfToResponse::convert)
                .toList();
    }

    public static EtfResponse convert(Etf etf) {
        return new EtfResponse(etf.id(), etf.name(), etf.ticker(), etf.description(), etf.type(), etf.industrySegment(), etf.price());
    }
}
