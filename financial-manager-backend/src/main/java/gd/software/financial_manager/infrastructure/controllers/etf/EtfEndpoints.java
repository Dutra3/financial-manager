package gd.software.financial_manager.infrastructure.controllers.etf;

import gd.software.financial_manager.domain.model.Etf;
import gd.software.financial_manager.domain.usecase.etf.FetchEtf;
import gd.software.financial_manager.infrastructure.converts.EtfToResponse;
import gd.software.financial_manager.infrastructure.dtos.EtfResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/etfs")
public class EtfEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(EtfEndpoints.class);

    @Autowired
    private FetchEtf fetchEtf;

    @GetMapping("/{id}")
    public ResponseEntity<EtfResponse> fetchEtf(@PathVariable UUID id) throws Exception {
        Etf etf = fetchEtf.by(id);
        logger.info("Get etf {}.", etf.ticker());

        return ResponseEntity.status(HttpStatus.OK).body(EtfToResponse.convert(etf));
    }

    @GetMapping
    public ResponseEntity<List<EtfResponse>> fetchAllEtfs() {
        List<Etf> etfs = fetchEtf.all();
        logger.info("Get all etfs.");

        return ResponseEntity.status(HttpStatus.OK).body(EtfToResponse.convert(etfs));
    }
}
