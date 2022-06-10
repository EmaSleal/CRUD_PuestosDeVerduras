
package com.dom.service;



import com.dom.dominio.Producto;
import java.util.List;


public interface ProductoService {
       public List<Producto> listarProducto();
    
    public void guardar(Producto producto);
    
    public void eliminar(Producto producto);
    
    public Producto encontrarProducto(Producto producto);
}
