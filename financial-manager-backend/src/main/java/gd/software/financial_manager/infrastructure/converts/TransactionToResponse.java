package gd.software.financial_manager.infrastructure.converts;

import gd.software.financial_manager.domain.model.Transaction;
import gd.software.financial_manager.infrastructure.dtos.TransactionResponse;

import java.util.List;

public class TransactionToResponse {

    public static List<TransactionResponse> convert(List<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionToResponse::convert)
                .toList();
    }

    private static TransactionResponse convert(Transaction transaction) {
        return new TransactionResponse(transaction.id(), transaction.name(), transaction.description(), transaction.amount(),
                transaction.paymentDate(), "Debit", transaction.category().name());
    }
}
