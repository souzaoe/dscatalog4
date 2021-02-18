package com.souzaoe.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.souzaoe.dscatalog.services.exceptions.DatabaseException;
import com.souzaoe.dscatalog.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND; 
		StandardError err = new StandardError();
		err.setLinhaTempo(Instant.now()); 
		err.setEstado(status.value());
		err.setError("Recurso não encontrado");
		err.setMensagem(e.getMessage());	
		err.setCaminho(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}	
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST; 
		StandardError err = new StandardError();
		err.setLinhaTempo(Instant.now()); 
		err.setEstado(status.value());
		err.setError("Exceção de base de dados");
		err.setMensagem(e.getMessage());	
		err.setCaminho(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}	
	
}
