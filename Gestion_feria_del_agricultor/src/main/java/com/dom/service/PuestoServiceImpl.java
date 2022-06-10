package com.dom.service;



import com.dom.dao.IPuestoDAO;
import com.dom.dominio.Puesto;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PuestoServiceImpl implements PuestoService{
    
    @Autowired
    IPuestoDAO puestos;

    @Override
    @Transactional
    public List<Puesto> listarPuestos() {
        return (List<Puesto>)  puestos.findAll();
    }

    @Override
    @Transactional
    public void guardar(Puesto puesto) {
        puestos.save(puesto);
    }

    @Override
    @Transactional
    public void eliminar(Puesto puesto) {
        puestos.delete(puesto);
    }

    @Override
    @Transactional(readOnly = true)
    public Puesto encontrarPuesto(Puesto puesto) {
        return puestos.findById(puesto.getIdPuesto()).orElse(null);
    }
    
}
