package com.git.felipe.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.git.felipe.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	List<Cliente> findByNomeContaining(String nome);

	@Query(value = "SELECT c FROM Cliente c where c.cpf = ?1 and c.password = ?2 ")
	Optional<Cliente> login(String cpf, String password);

}