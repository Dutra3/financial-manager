package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Reit;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AllReits {

    Optional<Reit> findReitByTicker(String ticker);

    Optional<Reit> by(UUID id);

    List<Reit> all();
}
