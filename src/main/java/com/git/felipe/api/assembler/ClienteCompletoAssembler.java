package com.git.felipe.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.git.felipe.api.model.ClienteCompletoModel;
import com.git.felipe.domain.model.Cliente;

@Component
public class ClienteCompletoAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public ClienteCompletoModel toModel(Cliente cliente) {
		return modelMapper.map(cliente, ClienteCompletoModel.class);
	}
}
