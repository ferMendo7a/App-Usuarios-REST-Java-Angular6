package com.restapp5.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restapp5.model.Usuario;


@Service("servicios")
@Transactional
public class ServiciosImpl implements Servicios{

	private static AtomicLong contador = new AtomicLong();  
	private static List<Usuario> usuarios = carga();
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public Usuario findById(long id) {
		Usuario user;
		for(int i=0; i<usuarios.size(); i++) {
			user = usuarios.get(i);
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public void insertar(Usuario user) {
		/*try {
			formato.parse(String.valueOf( user.getFnac() ));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		user.setId(contador.incrementAndGet());
		usuarios.add(user);
	}

	public void modificar(Usuario user) {
		int index = usuarios.indexOf(user);
		usuarios.set(index, user);
	}

	public void eliminar(Usuario user) {
		usuarios.remove(user);
	}	
	
	public void eliminarTodos() {
		usuarios.clear();
	}

	public Usuario findByUsername(String username) {
		Usuario user;
		for(int i=0; i<usuarios.size(); i++) {
			user = usuarios.get(i);
			if(user.getUsername().equalsIgnoreCase(username)) {
				return user;
			}
		}
		return null;
	}	
	
	public boolean usuarioExiste(Usuario user) {
		if ( findByUsername(user.getUsername()) != null ) {
			return true;
		}
		return false;
	}	
	
	
	
	private static List<Usuario> carga(){
		List<Usuario> users = new ArrayList<Usuario>();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		try {
			users.add(new Usuario(contador.incrementAndGet(), "serGio99", "elloko490", "sg9@abc", formato.parse("19/02/1991")));
			users.add(new Usuario(contador.incrementAndGet(), "roge7io", "gT321a", "rogem@abc", formato.parse("30/11/1997")));
			users.add(new Usuario(contador.incrementAndGet(), "ritacaceres1", "a1b2", "rita@abc", formato.parse("01/08/2000")));
			users.add(new Usuario(contador.incrementAndGet(), "walt37cr", "pqee2", "walter95@abc", formato.parse("11/07/1989")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return users;
	}
}
