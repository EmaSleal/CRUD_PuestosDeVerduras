package com.dom.dao;

import com.dom.dominio.Persona;
import org.springframework.data.repository.CrudRepository;

//Long por la llave primaria que es de tipo Long
public interface IPersonaDAO extends CrudRepository<Persona, Long>{
    
}
