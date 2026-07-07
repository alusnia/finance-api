package com.financeapi.finance_api.user.internal.registration;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "pesel_searches")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeselSearch {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "cif", nullable = false)
	private String cif;
	@Column(name = "pesel")
	private String pesel;
	@Column(name = "searched_at")
	private LocalDateTime searchedAt;
}
