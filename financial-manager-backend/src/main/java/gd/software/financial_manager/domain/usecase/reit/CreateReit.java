package gd.software.financial_manager.domain.usecase.reit;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.domain.usecase.collections.AllReits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateReit {

    private static final Logger logger = LoggerFactory.getLogger(CreateReit.class);

    private final AllReits allReits;

    public CreateReit(AllReits allReits) {
        this.allReits = allReits;
    }

    public Reit use(Reit reit) {
        logger.info("Creating reit {}.", reit.name());
        Optional<Reit> optionalReit = allReits.findReitByTicker(reit.ticker());

        if (optionalReit.isPresent()) {
            var reitDB = optionalReit.get();
            reitDB.setQuantity(reitDB.quantity().add(reit.quantity()));
            reitDB.setTransactionDate(reit.transactionDate());
            reitDB.calculateAveragePrice(reit.quantity(), reit.price());

            return allReits.save(reitDB);
        } else {
            return allReits.save(reit);
        }
    }
}
