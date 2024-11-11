package gd.software.financial_manager.domain.usecase.bond;

import gd.software.financial_manager.domain.model.Bond;
import gd.software.financial_manager.domain.usecase.collections.AllBonds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FetchBond {

    private static final Logger logger = LoggerFactory.getLogger(FetchBond.class);

    private final AllBonds allBonds;

    public FetchBond(AllBonds allBonds) {
        this.allBonds = allBonds;
    }

    public Bond by(UUID id) throws Exception {
        logger.info("Find bond with id {}.", id);
        return allBonds.by(id).orElseThrow(() -> new Exception("cant_find_bond_with_id"));
    }

    public List<Bond> all() {
        logger.info("Fetch all bonds");
        return allBonds.all();
    }
}
