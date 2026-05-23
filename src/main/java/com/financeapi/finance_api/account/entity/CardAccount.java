package com.financeapi.finance_api.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "card_accounts")
public class CardAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",  nullable = false)
    private Account account;

    @Setter
    @Column (name = "is_primary_for_currency",  nullable = false)
    private Boolean isPrimaryForCurrency;

    public CardAccount() {}

    public CardAccount(Card card, Account account) {
        this.card = card;
        this.account = account;
        this.isPrimaryForCurrency = false;
    }
}
