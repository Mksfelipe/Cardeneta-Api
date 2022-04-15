package com.git.felipe.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.git.felipe.api.model.input.ItemInput;
import com.git.felipe.domain.model.Item;

@Component
public class ItemInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Item toDomainObject(ItemInput itemInput) {
		return modelMapper.map(itemInput, Item.class);
	}

	public void copyToDomainObject(ItemInput clienteInput, Item item) {
		modelMapper.map(clienteInput, item);
	}
}
