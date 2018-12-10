package com.restapp5.service;

import java.util.List;

import com.restapp5.model.Usuario;

public interface Servicios {
	
	public List<Usuario> getUsuarios();
	
	public Usuario findById(long id);
	
	public void insertar(Usuario user);
	
	public void modificar(Usuario user);
	
	public void eliminar(Usuario user);

	public void eliminarTodos();
	
	public Usuario findByUsername(String username);
	
	public boolean usuarioExiste(Usuario user);
}
