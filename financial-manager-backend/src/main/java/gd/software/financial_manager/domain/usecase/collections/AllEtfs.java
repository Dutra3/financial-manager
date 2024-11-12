package gd.software.financial_manager.domain.usecase.collections;

import gd.software.financial_manager.domain.model.Etf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AllEtfs {

    Optional<Etf> by(UUID id);

    List<Etf> all();
}
