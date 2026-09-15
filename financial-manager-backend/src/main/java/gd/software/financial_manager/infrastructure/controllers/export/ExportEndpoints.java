package gd.software.financial_manager.infrastructure.controllers.export;

import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.domain.usecase.collections.AllTransactions;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.UUID;

@RestController
@RequestMapping("/export")
public class ExportEndpoints {

    @Autowired
    private AllTransactions allTransactions;

    @GetMapping("/transactions/{userId}")
    public void exportTransactions(@PathVariable UUID userId, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.csv");

        var transactions = allTransactions.allBy(userId);

        PrintWriter writer = response.getWriter();
        writer.println("ID,Name,Description,Amount,PaymentDate,Category,Type");

        for (Transaction t : transactions) {
            writer.printf("%s,%s,%s,%s,%s,%s,%s%n",
                    t.id() != null ? t.id() : "",
                    escape(t.name()),
                    escape(t.description()),
                    t.amount() != null ? t.amount() : "",
                    t.paymentDate() != null ? t.paymentDate() : "",
                    t.category() != null ? escape(t.category().name()) : "",
                    t.category() != null ? t.category().type() : ""
            );
        }
        writer.flush();
    }

    private String escape(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
