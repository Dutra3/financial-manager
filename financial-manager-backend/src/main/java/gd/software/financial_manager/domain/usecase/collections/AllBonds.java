package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Bond;

import java.util.Optional;
import java.util.UUID;

public interface AllBonds {

    Optional<Bond> findBondByTicker(String ticker);

    Optional<Bond> by(UUID id);
}
