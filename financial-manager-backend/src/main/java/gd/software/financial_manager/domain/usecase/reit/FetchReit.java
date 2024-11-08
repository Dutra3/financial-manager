package gd.software.financial_manager.domain.usecase.reit;

import gd.software.financial_manager.domain.model.Reit;
import gd.software.financial_manager.domain.usecase.collections.AllReits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FetchReit {

    private static final Logger logger = LoggerFactory.getLogger(FetchReit.class);

    private final AllReits allReits;

    public FetchReit(AllReits allReits) {
        this.allReits = allReits;
    }

    public List<Reit> all() {
        logger.info("Fetch all reits");
        return allReits.all();
    }
}