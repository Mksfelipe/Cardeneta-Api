package com.git.felipe.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.git.felipe.domain.exception.ClienteNaoEncontradoException;
import com.git.felipe.domain.exception.EntidadeEmUsoException;
import com.git.felipe.domain.exception.ItemNaoEncontradoException;
import com.git.felipe.domain.model.Cliente;
import com.git.felipe.domain.model.Item;
import com.git.felipe.domain.repository.ClienteRepository;

@Service
public class ClienteService {

	private static final String MSG_NAO_EXISTE_ESTE_ITEM_NESTA_CONTA = "Não existe um cadastro item %d para este cliente %d";
	private static final String MSG_CLIENTE_NAO_ENCONTRADO = "Não existe um cadastro de conta com código %d";
	private static final String MSG_CLIENTE_EM_USO = "Cliente de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ItemService itemService;
	
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}

	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	public Cliente buscar(Long clienteId) {
		return buscarOuFalhar(clienteId);
	}

	public Cliente atualizar(Long clienteId, Cliente cliente) {
		Cliente clienteAtual = buscarOuFalhar(clienteId);
		BeanUtils.copyProperties(cliente, clienteAtual, "id");
		return clienteRepository.save(clienteAtual);
	}
	
	public Item atualizarItem(Long clienteId, Long itemId, Item item) {
		List<Item> itens = itemService.listarPorClienteId(clienteId);
		Cliente cliente = buscarOuFalhar(clienteId);
		
		item.setCliente(cliente);
		
		boolean result = itens.stream()
				.anyMatch(Item -> Item.getId() == itemId);
		if (result == false) {
			throw new ItemNaoEncontradoException(String.format(MSG_NAO_EXISTE_ESTE_ITEM_NESTA_CONTA, itemId, clienteId));
		}

		itemService.atualizar(itemId, item);
		return item;
	}
	
	public void deletarItem(Long clienteId, Long itemId) {
		buscarOuFalhar(clienteId);
		List<Item> itens = itemService.listarPorClienteId(clienteId);
		boolean result = itens.stream()
				.anyMatch(Item -> Item.getId() == itemId);
		if (result == false) {
			throw new ItemNaoEncontradoException(String.format(MSG_NAO_EXISTE_ESTE_ITEM_NESTA_CONTA, itemId, clienteId));
		}
		
		itemService.deletar(itemId);
	}
	
	
	public void deletar(Long clienteId) {
		try {
			clienteRepository.deleteById(clienteId);
		} catch (EmptyResultDataAccessException e) {
			throw new ClienteNaoEncontradoException(String.format(MSG_CLIENTE_NAO_ENCONTRADO, clienteId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CLIENTE_EM_USO, clienteId));
		}
	}

	private Cliente buscarOuFalhar(Long clienteId) {
		return clienteRepository.findById(clienteId).orElseThrow(
				() -> new ClienteNaoEncontradoException(String.format(MSG_CLIENTE_NAO_ENCONTRADO, clienteId)));
	}
	
}
