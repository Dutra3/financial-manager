package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Reit;

import java.util.List;
import java.util.Optional;

public interface AllReits {

    Reit save(Reit reit);

    Optional<Reit> findReitByTicker(String ticker);

    List<Reit> all();
}
