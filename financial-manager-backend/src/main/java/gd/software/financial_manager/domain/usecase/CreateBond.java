package gd.software.financial_manager.domain.usecase;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.usecase.collections.AllBonds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateBond {

    private static final Logger logger = LoggerFactory.getLogger(CreateBond.class);

    private final AllBonds allBonds;

    public CreateBond(AllBonds allBonds) {
        this.allBonds = allBonds;
    }

    public Bond use(Bond bond) {
        logger.info("Creating bond {}.", bond.name());
        return allBonds.save(bond);
    }
}
