package com.app.trading.infrastructure.jpa;

import com.app.trading.domain.Currency;
import com.app.trading.domain.Transaction;
import com.app.trading.domain.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="transaction")
public class TransactionEntity {
    @Id
    private String id;
    private String userId;
    private String price;
    private String amount;
    private String currency;
    private String type;

    public static Transaction toDomain(TransactionEntity entity){
        return new Transaction(new BigDecimal(entity.getPrice()), new BigDecimal(entity.getAmount()), Currency.valueOf(entity.getCurrency()), TransactionType.valueOf(entity.getType()));
    }

    public static TransactionEntity toEntity(UUID userId, Transaction transaction){
        return TransactionEntity.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId.toString())
                .price(transaction.getPrice().toString())
                .amount(transaction.getValue().toString())
                .currency(transaction.getCurrency().toString())
                .type(transaction.getTransactionType().toString())
                .build();
    }
}
