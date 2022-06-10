package com.dom.service;

import com.dom.dao.IRolDAO;
import com.dom.dominio.Rol;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    IRolDAO rdao;



    @Override
    @Transactional
    public void guardar(Rol rol, Long id) {

        rol.setNombre("ROLE_USER");
        rol.setId_usuario(id);
        rdao.save(rol);

    }

}
