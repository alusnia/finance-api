package com.financeapi.finance_api.account.entity;

import com.financeapi.finance_api.user.entity.SecurityLock;
import com.financeapi.finance_api.user.entity.LockType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String number;

    @Setter
    @Column(nullable = false)
    private String pinCode;

    @Setter
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardAccount> accounts;

	@Setter
	@Embedded
	private SecurityLock securityLock = new SecurityLock(LockType.OPEN);

    public Card() {}

    public Card(String number, String pinCode,  List<CardAccount> accounts) {
        this.number = number;
        this.pinCode = pinCode;
        this.accounts = accounts;
    }
}
