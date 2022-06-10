package com.dom.service;



import com.dom.dao.IProductoDAO;
import com.dom.dominio.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService{
    
    @Autowired
    IProductoDAO pro;

    @Override
    @Transactional
    public List<Producto> listarProducto() {
        List<Producto> lp =  pro.findAll();
        
        return lp;
    }

    @Override
    @Transactional
    public void guardar(Producto producto) {
        pro.save(producto);
    }

    @Override
    @Transactional
    public void eliminar(Producto producto) {
        pro.delete(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto encontrarProducto(Producto producto) {
        return pro.findById(producto.getIdProducto()).orElse(null);
    }
    
}
