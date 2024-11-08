package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.domain.usecase.collections.AllReits;
import gd.software.financial_manager.infrastructure.converts.ReitToRow;
import gd.software.financial_manager.infrastructure.converts.RowToReit;
import gd.software.financial_manager.infrastructure.persistence.relational.ReitRow;
import gd.software.financial_manager.infrastructure.persistence.repository.ReitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AllReitsPersistent implements AllReits {

    private static final Logger logger = LoggerFactory.getLogger(AllReitsPersistent.class);

    @Autowired
    ReitRepository repository;

    @Override
    public Reit save(Reit reit) {
        ReitRow reitRow = ReitToRow.convert(reit);

        ReitRow savedReitRow = repository.save(reitRow);
        logger.info("Saved reit {}.", savedReitRow.getName());

        return RowToReit.convert(savedReitRow);
    }

    @Override
    public Optional<Reit> findReitByTicker(String ticker) {
        logger.info("Find Reit with ticker {}", ticker);

        return repository.findByTicker(ticker).map(RowToReit::convert);
    }

    @Override
    public List<Reit> all() {
        logger.info("Find all Reits");
        return repository.findAll().stream().map(RowToReit::convert).toList();
    }
}
