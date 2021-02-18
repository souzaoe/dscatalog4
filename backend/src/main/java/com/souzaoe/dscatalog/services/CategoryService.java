package com.souzaoe.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.souzaoe.dscatalog.dto.CategoryDTO;
import com.souzaoe.dscatalog.entities.Category;
import com.souzaoe.dscatalog.repositories.CategoryRepository;
import com.souzaoe.dscatalog.services.exceptions.DatabaseException;
import com.souzaoe.dscatalog.services.exceptions.ResourceNotFoundException;


// springr fazendo a gerencia, dependencia, service pois é camada de servico
@Service
public class CategoryService {
	// dependencia pela variavel repository
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
		Page<Category> list = repository.findAll(pageRequest);
		// usando a espreção lambda para fazer o for
		return list.map(x -> new CategoryDTO(x));
		// convertendo a listcatgory, para lista categoryDTO, percorre toda lista
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
		return new CategoryDTO(entity); 
	}
	
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category(); 
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity); 
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id); 
			entity.setName(dto.getName());
			entity = repository.save(entity); 
			return new CategoryDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrada! " + id);
		}		
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id não encontrada " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integridade violada"); 
		}
	}	
}
