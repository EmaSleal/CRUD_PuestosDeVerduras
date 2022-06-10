
package com.dom.dao;

import com.dom.dominio.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IProductoDAO extends JpaRepository<Producto, Long>{

}
