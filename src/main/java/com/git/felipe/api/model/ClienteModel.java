package com.git.felipe.api.model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nome;
	private String cpf;
	private LocalDate dataNascimento;
	
}
