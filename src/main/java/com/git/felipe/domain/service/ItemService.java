package com.git.felipe.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.git.felipe.domain.exception.ItemNaoEncontradoException;
import com.git.felipe.domain.model.Item;
import com.git.felipe.domain.repository.ItemRepository;

@Service
public class ItemService {

	private static final String MSG_ITEM_NAO_ENCONTRADO = "Não existe um cadastro de Item com código %d";

	@Autowired
	private ItemRepository itemRepository;


	@Cacheable("itens")
	public List<Item> listarPorClienteId(Long clienteId) {
		return itemRepository.findBycliente_id(clienteId);
	}

	@Cacheable(cacheNames = "clientes", key = "#itemId")
	public Item buscar(Long itemId) {
		return buscarOuFalhar(itemId);
	}

	public Item atualizar(Long itemId, Item item) {
		Item itemAtual = buscarOuFalhar(itemId);
		BeanUtils.copyProperties(item, itemAtual, "id", "dataItem", "dataItemAtualizado");
		itemRepository.save(itemAtual);
		return item;
	}

	@CacheEvict(value = "clientes", allEntries = true)
	public Item salvar(Item item) {
		return itemRepository.save(item);
	}
	
	public void deletar(Long itemId) {
		itemRepository.deleteById(itemId);
	}

	private Item buscarOuFalhar(Long itemId) {
		return itemRepository.findById(itemId)
				.orElseThrow(() -> new ItemNaoEncontradoException(String.format(MSG_ITEM_NAO_ENCONTRADO, itemId)));
	}

}
