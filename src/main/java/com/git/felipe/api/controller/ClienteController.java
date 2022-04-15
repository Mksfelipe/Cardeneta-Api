package com.git.felipe.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.git.felipe.api.assembler.ClienteCompletoAssembler;
import com.git.felipe.api.assembler.ClienteInputDisassembler;
import com.git.felipe.api.assembler.ClienteModelAssembler;
import com.git.felipe.api.assembler.ItemInputDisassembler;
import com.git.felipe.api.assembler.ItemModelAssembler;
import com.git.felipe.api.model.ClienteCompletoModel;
import com.git.felipe.api.model.ClienteModel;
import com.git.felipe.api.model.ItemModel;
import com.git.felipe.api.model.input.ClienteInput;
import com.git.felipe.api.model.input.ItemInput;
import com.git.felipe.domain.exception.ClienteNaoEncontradoException;
import com.git.felipe.domain.exception.NegocioException;
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
	private ClienteModelAssembler clienteModelAssembler;

	@Autowired
	private ClienteInputDisassembler clienteInputDisassembler;

	@Autowired
	private ItemModelAssembler itemModelAssembler;

	@Autowired
	private ItemInputDisassembler itemInputDisassembler;

	@Autowired
	private ClienteCompletoAssembler clienteCompletoAssembler; 
	
	@GetMapping
	public List<ClienteModel> listar() {
		return clienteModelAssembler.toCollectionModel(clienteService.listar());
	}

	@GetMapping("/nome")
	public List<ClienteModel> buscarPorNome(@RequestParam String nome) {
		return clienteModelAssembler.toCollectionModel(clienteService.buscarPorNome(nome));
	}

	@GetMapping("/{clienteId}")
	public ClienteCompletoModel buscar(@PathVariable Long clienteId) {
		Cliente cliente = clienteService.buscar(clienteId);

		ClienteCompletoModel clienteCompletoModel = clienteCompletoAssembler.toModel(cliente); 
		clienteCompletoModel.somaTotal();
		clienteCompletoModel.quantidadeItens();

		return clienteCompletoModel;
	}

	@GetMapping("/{clienteId}/itens")
	public List<ItemModel> buscarItens(@PathVariable Long clienteId) {
		Cliente cliente = clienteService.buscar(clienteId);
		return itemModelAssembler.toCollectionModel(cliente.getItens());
	}

	@PostMapping("/{clienteId}/itens")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ItemModel adicionarItem(@PathVariable Long clienteId, @RequestBody @Valid ItemInput itemInput) {

		try {
			Cliente cliente = clienteService.buscar(clienteId);
			Item item = itemInputDisassembler.toDomainObject(itemInput);
			item.setCliente(cliente);

			return itemModelAssembler.toModel(itemService.salvar(item));
		} catch (ClienteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ClienteModel adicionar(@RequestBody @Valid Cliente cliente) {
		return clienteModelAssembler.toModel(clienteService.salvar(cliente));
	}

	@PutMapping("/{clienteId}")
	public ClienteModel atualizar(@PathVariable Long clienteId, @RequestBody @Valid ClienteInput clienteInput) {
		Cliente clienteAtual = clienteService.buscar(clienteId);
		clienteInputDisassembler.copyToDomainObject(clienteInput, clienteAtual);
		clienteService.atualizar(clienteId, clienteAtual);

		return clienteModelAssembler.toModel(clienteAtual);
	}

	@PutMapping("/{clienteId}/itens/{itemId}")
	public ItemModel atualizar(@PathVariable Long clienteId, @PathVariable Long itemId, @RequestBody @Valid ItemInput itemInput) {
		Item itemAtual = itemService.buscar(itemId);
		
		itemInputDisassembler.copyToDomainObject(itemInput, itemAtual);
		
		clienteService.atualizarItem(clienteId, itemId, itemAtual);
		return itemModelAssembler.toModel(itemAtual);
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

}
