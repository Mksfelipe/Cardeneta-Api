package com.git.felipe.api.DTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nome;
	private String sobreNome;
	private LocalDate dataNascimento;
	private int quantiDadeItens;
	private BigDecimal somaTotal;

	List<ItemDto> itens;
}
