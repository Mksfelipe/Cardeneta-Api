package com.git.felipe.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteCompletoModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nome;
	private LocalDate dataNascimento;
	private int quantiDadeItens;
	private BigDecimal somaTotal;

	private List<ItemModel> itens;

	public BigDecimal somaTotal() {
		setSomaTotal(itens.stream().map(ItemModel::getValor).reduce(BigDecimal.ZERO, BigDecimal::add));
		return getSomaTotal();
	}

	public int quantidadeItens() {
		return this.quantiDadeItens = itens.size();
	}
}
