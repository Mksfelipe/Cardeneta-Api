package com.git.felipe.domain.exception;

public class ItemNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public ItemNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ItemNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de item com código %d", id));
	}

}
