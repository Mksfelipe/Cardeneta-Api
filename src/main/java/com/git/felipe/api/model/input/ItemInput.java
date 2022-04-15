package com.git.felipe.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.git.felipe.api.model.ClienteModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemInput {
	
	@NotNull
	@Digits(integer = 3, fraction = 2)
	private BigDecimal valor;

	private ClienteModel cliente;

}
