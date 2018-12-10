package com.restapp5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.restapp5.model.Usuario;
import com.restapp5.service.Servicios;

@RestController
public class UsuarioControlador {

	@Autowired
	Servicios servicios;
	
	
	// Listar todos los usuarios
	@RequestMapping(value="/usuario", method = RequestMethod.GET)
	public ResponseEntity<List<Usuario>> listarUsuarios(){
		List<Usuario> usuarios = servicios.getUsuarios();
		if (usuarios.isEmpty()) {
			return new ResponseEntity<List<Usuario>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}
	
	
	// Obtener un usuario
	@RequestMapping(value="/usuario/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> getUsuario(@PathVariable("id") Long id){
		Usuario user = servicios.findById(id);
		if (user == null) {
			return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);
		} 
		
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	
	// Insertar un usuario
	@RequestMapping(value="/usuario", method= RequestMethod.POST)
	public ResponseEntity<Void> insertarUsuario(@RequestBody Usuario user, UriComponentsBuilder uriComp){
		if (servicios.usuarioExiste(user) == true) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		servicios.insertar(user);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uriComp.path("/usuario/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	
	// Modificar un usuario
	@RequestMapping(value="/usuario/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Usuario> modificarUsuario(@PathVariable Long id,@RequestBody Usuario user){
		Usuario userAModificar = servicios.findById(id);
		
		if (userAModificar == null) {
			return new ResponseEntity<Usuario>(user, HttpStatus.NOT_FOUND);
		}
		
		if ( ! userAModificar.getUsername().equals(user.getUsername()) ) {
			if ( servicios.usuarioExiste(user) ) {
				return new ResponseEntity<Usuario>(user, HttpStatus.CONFLICT);
			}
		}		
				
		userAModificar.setUsername(user.getUsername());
		userAModificar.setPassword(user.getPassword());
		userAModificar.setEmail(user.getEmail());
		userAModificar.setFnac(user.getFnac());
		
		servicios.modificar(userAModificar);
		
		return new ResponseEntity<Usuario>(userAModificar, HttpStatus.OK);
	}
	
	
	// Eliminar un usuario
	@RequestMapping(value="/usuario/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id){
		Usuario user = servicios.findById(id);
		if (user == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		
		servicios.eliminar(user);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	//  Eliminar TODOS los usuarios
	@RequestMapping(value="/usuario", method= RequestMethod.DELETE)
	public ResponseEntity<Void> eliminar(){
		
		servicios.eliminarTodos();
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
}
