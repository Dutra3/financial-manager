package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.usecase.collections.AllBonds;
import gd.software.financial_manager.infrastructure.converts.RowToBond;
import gd.software.financial_manager.infrastructure.persistence.repository.BondRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AllBondsPersistent implements AllBonds {

    private static final Logger logger = LoggerFactory.getLogger(AllBondsPersistent.class);

    @Autowired
    BondRepository repository;

    @Override
    public Optional<Bond> by(UUID id) {
        logger.info("Find Bond with id {}.", id);
        return repository.findById(id).map(RowToBond::convert);
    }

    @Override
    public List<Bond> all() {
        logger.info("Find all Bonds.");
        return repository.findAll().stream().map(RowToBond::convert).toList();
    }
}
