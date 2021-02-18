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
import com.souzaoe.dscatalog.dto.ProductDTO;
import com.souzaoe.dscatalog.entities.Category;
import com.souzaoe.dscatalog.entities.Product;
import com.souzaoe.dscatalog.repositories.CategoryRepository;
import com.souzaoe.dscatalog.repositories.ProductRepository;
import com.souzaoe.dscatalog.services.exceptions.DatabaseException;
import com.souzaoe.dscatalog.services.exceptions.ResourceNotFoundException;


// springr fazendo a gerencia, dependencia, service pois é camada de servico
@Service
public class ProductService {
	// dependencia pela variavel repository
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository; 
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		Page<Product> list = repository.findAll(pageRequest);
		// usando a espreção lambda para fazer o for
		return list.map(x -> new ProductDTO(x));
		// convertendo a listcatgory, para lista categoryDTO, percorre toda lista
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
		return new ProductDTO(entity, entity.getCategories()); 
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product(); 
		copyDtoToEntity(dto, entity); 		
		entity = repository.save(entity);
		return new ProductDTO(entity); 
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getOne(id); 
			copyDtoToEntity(dto, entity); 	
			entity = repository.save(entity); 
			return new ProductDTO(entity);
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
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
			entity.setName(dto.getName());	
			entity.setDescription(dto.getDescription());
			entity.setDate(dto.getDate());
			entity.setImgUrl(dto.getImgUrl());
			entity.setPrice(dto.getPrice());
			
			entity.getCategories().clear();
			for (CategoryDTO catDto : dto.getCategories()) {
				Category category = categoryRepository.getOne(catDto.getId());
				entity.getCategories().add(category); 
			}
	}
}
