package com.git.felipe.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.git.felipe.domain.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findBycliente_id(Long clienteId);
	
}
