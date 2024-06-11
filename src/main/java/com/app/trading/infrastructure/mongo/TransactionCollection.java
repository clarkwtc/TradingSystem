package com.app.trading.infrastructure.mongo;

import com.app.trading.domain.Currency;
import com.app.trading.domain.Transaction;
import com.app.trading.domain.TransactionType;
import lombok.*;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection="transaction")
public class TransactionCollection {
    @Id
    private String id;
    private String userId;
    private String price;
    private String amount;
    private String currency;
    private String type;

    public static Transaction toDomain(TransactionCollection collection){
        return new Transaction(new BigDecimal(collection.getPrice()), new BigDecimal(collection.getAmount()), Currency.valueOf(collection.getCurrency()), TransactionType.valueOf(collection.getType()));
    }

    public static TransactionCollection toEntity(UUID userId, Transaction transaction){
        return TransactionCollection.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId.toString())
                .price(transaction.getPrice().toString())
                .amount(transaction.getValue().toString())
                .currency(transaction.getCurrency().toString())
                .type(transaction.getTransactionType().toString())
                .build();
    }
}
