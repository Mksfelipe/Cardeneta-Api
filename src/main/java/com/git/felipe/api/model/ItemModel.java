package com.git.felipe.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ItemModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;

	private BigDecimal valor;
	private LocalDateTime dataItem;
	private LocalDateTime dataItemAtualizado;

}
