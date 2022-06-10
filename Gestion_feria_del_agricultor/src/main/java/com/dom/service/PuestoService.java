
package com.dom.service;



import com.dom.dominio.Puesto;
import java.util.List;


public interface PuestoService {
        public List<Puesto> listarPuestos();
    
    public void guardar(Puesto puesto);
    
    public void eliminar(Puesto puesto);
    
    public Puesto encontrarPuesto(Puesto puesto);
}
