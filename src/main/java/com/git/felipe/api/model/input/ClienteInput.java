package com.git.felipe.api.model.input;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteInput {

	@NotBlank
	@Size(max = 50)
	private String nome;
	
	@Past
	@NotNull
	private LocalDate dataNascimento;
	
}
