package gd.software.financial_manager.infrastructure.persistence;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.usecase.CreateBond;
import gd.software.financial_manager.domain.usecase.collections.AllBonds;
import gd.software.financial_manager.infrastructure.converts.BondToRow;
import gd.software.financial_manager.infrastructure.converts.RowToBond;
import gd.software.financial_manager.infrastructure.persistence.relational.BondRow;
import gd.software.financial_manager.infrastructure.persistence.repository.BondRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllBondsPersistent implements AllBonds {

    private static final Logger logger = LoggerFactory.getLogger(CreateBond.class);

    @Autowired
    BondRepository repository;

    @Override
    public Bond save(Bond bond) {
        BondRow bondRow = BondToRow.convert(bond);

        BondRow savedBondRow = repository.save(bondRow);
        logger.info("Saved bond {}.", savedBondRow.getName());

        return RowToBond.convert(savedBondRow);
    }
}
