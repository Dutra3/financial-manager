package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Bond;

import java.util.Optional;

public interface AllBonds {

    Bond save(Bond bond);

    Optional<Bond> findBondByTicker(String ticker);
}
