package com.dom.service;

import com.dom.dao.IUsuarioDAO;
import com.dom.dominio.Puesto;
import com.dom.dominio.Rol;
import com.dom.dominio.Usuario;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//declaro que es un bean de spring tipo servicio
@Service("userDatailsService")
@Slf4j
public class UsuarioService implements UserDetailsService {

    @Autowired
    private IUsuarioDAO udao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = udao.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        var roles = new ArrayList<GrantedAuthority>();
        for (Rol rol : usuario.getRoles()) {
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }

        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return (List<Usuario>) udao.findAll();
    }

    @Transactional
    public void guardar(Usuario usuario, BCryptPasswordEncoder encoder) {

        var pass = usuario.getPassword();
        usuario.setPassword(encoder.encode(pass));

        udao.save(usuario);
    }

    @Transactional
    public void eliminar(Usuario usuario) {
        udao.delete(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario encontrarUsuario(Usuario usuario) {
        return udao.findById(usuario.getIdUsuario()).orElse(null);
    }

}
