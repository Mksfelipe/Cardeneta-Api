package com.git.felipe.api.controller;

import java.math.BigDecimal;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.git.felipe.api.DTO.ClienteDto;
import com.git.felipe.api.DTO.ItemDto;
import com.git.felipe.domain.model.Cliente;
import com.git.felipe.domain.model.Item;
import com.git.felipe.domain.service.ClienteService;
import com.git.felipe.domain.service.ItemService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<Cliente> listar() {
		return clienteService.listar();
	}

	@GetMapping("/{clienteId}")
	public ClienteDto buscar(@PathVariable Long clienteId) {
		Cliente cliente = clienteService.buscar(clienteId);
		ClienteDto clienteDto = converterParaDto(cliente);

		clienteDto.setQuantiDadeItens(clienteDto.getItens().size());

		clienteDto
				.setSomaTotal(cliente.getItens().stream().map(Item::getValor).reduce(BigDecimal.ZERO, BigDecimal::add));
		return clienteDto;
	}

	@GetMapping("/{clienteId}/itens")
	public List<ItemDto> buscarItens(@PathVariable Long clienteId) {
		Cliente cliente = clienteService.buscar(clienteId);
		return itensDtoList(cliente.getItens());
	}

	@PostMapping("/{clienteId}/itens")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ItemDto adicionarItem(@PathVariable Long clienteId, @RequestBody @Valid Item item) {
		Cliente cliente = clienteService.buscar(clienteId);
		item.setCliente(cliente);

		return converterParaDto(itemService.salvar(item));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente adicionar(@RequestBody @Valid Cliente cliente) {
		return clienteService.salvar(cliente);
	}

	@PutMapping("/{clienteId}")
	public Cliente atualizar(@PathVariable Long clienteId, @RequestBody @Valid Cliente cliente) {
		cliente = clienteService.atualizar(clienteId, cliente);
		return cliente;
	}

	@PutMapping("/{clienteId}/itens/{itemId}")
	public ItemDto atualizar(@PathVariable Long clienteId, @PathVariable Long itemId, @RequestBody @Valid Item item) {
		clienteService.atualizarItem(clienteId, itemId, item);
		return converterParaDto(item);
	}
	
	@DeleteMapping("/{clienteId}/itens/{itemId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long clienteId, @PathVariable Long itemId) {
		clienteService.deletarItem(clienteId, itemId);
	}

	@DeleteMapping("/{clienteId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long clienteId) {
		clienteService.deletar(clienteId);
	}

	private ClienteDto converterParaDto(Cliente cliente) {
		ClienteDto clienteDto = modelMapper.map(cliente, ClienteDto.class);
		return clienteDto;
	}

	private ItemDto converterParaDto(Item item) {
		ItemDto itemDto = modelMapper.map(item, ItemDto.class);
		return itemDto;
	}

	private List<ItemDto> itensDtoList(List<Item> listItens) {
		List<ItemDto> listDto = listItens.stream().map(Item -> modelMapper.map(Item, ItemDto.class))
				.collect(Collectors.toList());
		return listDto;
	}

}
