package com.financeapi.finance_api.registration.entity;

import com.financeapi.finance_api.registration.service.command.SaveCheckPeselCommand;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "pesel_searches")
public class PeselSearch {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "caller_id", nullable = false)
	private String callerId;
	@Column(name = "pesel")
	private String pesel;

	public PeselSearch() {}

	public PeselSearch(SaveCheckPeselCommand saveCheckPeselCommand) {
		this.callerId = saveCheckPeselCommand.callerId();
		this.pesel = saveCheckPeselCommand.pesel();
	}
}
