package com.git.felipe.api.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDto {

	@NotNull
	private String nome;
	private String sobreNome;
	private LocalDate dataNascimento;
	private int quantiDadeItens;
	private BigDecimal somaTotal;

	List<ItemDto> itens;
}
