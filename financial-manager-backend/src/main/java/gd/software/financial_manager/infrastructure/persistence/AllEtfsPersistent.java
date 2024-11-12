package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.domain.usecase.collections.AllEtfs;
import gd.software.financial_manager.infrastructure.converts.RowToEtf;
import gd.software.financial_manager.infrastructure.persistence.repository.EtfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AllEtfsPersistent implements AllEtfs {

    private static final Logger logger = LoggerFactory.getLogger(AllEtfsPersistent.class);

    @Autowired
    EtfRepository repository;

    @Override
    public Optional<Etf> by(UUID id) {
        logger.info("Find ETF with id {}.", id);
        return repository.findById(id).map(RowToEtf::convert);
    }

    @Override
    public List<Etf> all() {
        logger.info("Find all ETFs.");
        return repository.findAll().stream().map(RowToEtf::convert).toList();
    }
}
