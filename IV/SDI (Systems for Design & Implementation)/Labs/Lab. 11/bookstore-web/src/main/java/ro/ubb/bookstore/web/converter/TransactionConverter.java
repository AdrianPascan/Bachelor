package ro.ubb.bookstore.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.bookstore.core.model.Transaction;
import ro.ubb.bookstore.web.dto.TransactionDto;

@Component
public class TransactionConverter extends BaseConverter<Transaction, TransactionDto> {
    @Override
    public Transaction convertDtoToModel(TransactionDto dto) {
        Transaction transaction = Transaction.builder()
                .customerId(dto.getCustomerId())
                .bookId(dto.getBookId())
                .build();
        transaction.setId(dto.getId());
        return transaction;
    }

    @Override
    public TransactionDto convertModelToDto(Transaction transaction) {
        TransactionDto dto = TransactionDto.builder()
                .customerId(transaction.getCustomerId())
                .bookId(transaction.getBookId())
                .build();
        dto.setId(transaction.getId());
        return dto;
    }
}
