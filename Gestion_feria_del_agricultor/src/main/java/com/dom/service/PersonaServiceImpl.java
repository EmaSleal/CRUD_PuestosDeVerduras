package com.dom.service;

import com.dom.dao.IPersonaDAO;
import com.dom.dominio.Persona;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//para poder inyectar esta clase como un servicio
@Service
public class PersonaServiceImpl implements PersonaService{

    //se inyecta personaDAO
    @Autowired
    private IPersonaDAO pdao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Persona> ListarPersonas() {
        return (List<Persona>) pdao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Persona persona) {
        pdao.save(persona);
    }

    @Override
    @Transactional
    public void eliminar(Persona persona) {
        pdao.delete(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public Persona encontrarPersona(Persona persona) {
       return pdao.findById(persona.getIdPersona()).orElse(null);
    }
    
}
