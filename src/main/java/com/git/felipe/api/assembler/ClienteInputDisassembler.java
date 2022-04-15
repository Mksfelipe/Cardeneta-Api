package com.git.felipe.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.git.felipe.api.model.input.ClienteInput;
import com.git.felipe.domain.model.Cliente;

@Component
public class ClienteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cliente toDomainObject(ClienteInput cidadeInput) {
		return modelMapper.map(cidadeInput, Cliente.class);
	}

	public void copyToDomainObject(ClienteInput clienteInput, Cliente cliente) {
		modelMapper.map(clienteInput, cliente);
	}
	
}
