package gd.software.financial_manager.domain.usecase.bond;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.usecase.collections.AllBonds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateBond {

    private static final Logger logger = LoggerFactory.getLogger(CreateBond.class);

    private final AllBonds allBonds;

    public CreateBond(AllBonds allBonds) {
        this.allBonds = allBonds;
    }

    public Bond use(Bond bond) {
        logger.info("Creating bond {}.", bond.name());
        Optional<Bond> optionalBond = allBonds.findBondByTicker(bond.ticker());
        if (optionalBond.isPresent()) {
            var bondDB = optionalBond.get();
            bondDB.setQuantity(bondDB.quantity().add(bond.quantity()));
            bondDB.setTransactionDate(bondDB.transactionDate());
            bondDB.calculateAveragePrice(bond.quantity(), bond.price());

            return allBonds.save(bondDB);
        } else {
            return allBonds.save(bond);
        }
    }
}
