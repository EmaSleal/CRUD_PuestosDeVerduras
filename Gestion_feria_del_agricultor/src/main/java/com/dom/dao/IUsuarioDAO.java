package com.dom.dao;

import com.dom.dominio.Persona;
import com.dom.dominio.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IUsuarioDAO extends JpaRepository<Usuario, Long>{
    Usuario findByUsername(String username);
}
