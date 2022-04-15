package com.git.felipe.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.git.felipe.api.model.ItemModel;
import com.git.felipe.domain.model.Item;

@Component
public class ItemModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ItemModel toModel(Item Item) {
		return modelMapper.map(Item, ItemModel.class);
	}
	
	public List<ItemModel> toCollectionModel(List<Item> itens) {
		return itens.stream()
				.map(item -> toModel(item))
				.collect(Collectors.toList());
	}
	
}
