package com.dom.dao;

import com.dom.dominio.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolDAO extends JpaRepository<Rol, Long>{
    
}
