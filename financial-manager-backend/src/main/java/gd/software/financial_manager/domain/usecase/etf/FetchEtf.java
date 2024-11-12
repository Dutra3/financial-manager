package gd.software.financial_manager.domain.usecase.etf;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.domain.usecase.collections.AllEtfs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FetchEtf {

    private static final Logger logger = LoggerFactory.getLogger(FetchEtf.class);

    private final AllEtfs allEtfs;

    public FetchEtf(AllEtfs allEtfs) {
        this.allEtfs = allEtfs;
    }

    public Etf by(UUID id) throws Exception {
        logger.info("Find etf with id {}.", id);
        return allEtfs.by(id).orElseThrow(() -> new Exception("cant_find_etf_with_id"));
    }

    public List<Etf> all() {
        logger.info("Fetch all etfs.");
        return allEtfs.all();
    }
}
