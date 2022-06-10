package com.dom.web;

import com.dom.dominio.Persona;
import com.dom.dominio.Producto;
import com.dom.dominio.Puesto;
import com.dom.dominio.Rol;
import com.dom.dominio.Usuario;
import com.dom.service.PersonaServiceImpl;
import com.dom.service.ProductoServiceImpl;
import com.dom.service.PuestoServiceImpl;
import com.dom.service.RolServiceImpl;
import com.dom.service.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ControlInicio {

    //se inyecta personaServiceImplementation
    @Autowired
    private PersonaServiceImpl pservice;

    @Autowired
    private UsuarioService us;

    @Autowired(required = true)
    private RolServiceImpl rolservice;

    @Autowired
    private PuestoServiceImpl puestoservice;

    @Autowired
    private ProductoServiceImpl productoservice;

    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user) {
        List personas = pservice.ListarPersonas();
        List puestos = puestoservice.listarPuestos();
        List productos = productoservice.listarProducto();


        productos = vaciaRepetidos(puestos, productos);

        for (int i = 0; i < puestos.size(); i++) {
            Puesto pues = (Puesto) puestos.get(i);
            for (int j = 0; j < personas.size(); j++) {
                Persona personal = (Persona) personas.get(j);
                for (Persona p : pues.getNombre()) {
                    if (p.equals(personal)) {
                        personas.remove(j);
                    }
                }
            }
        }

        //mensajes en consola
        log.info("usuario que hizo login: " + user);
        log.info("ejecutando el controlador spring MVC");

        model.addAttribute("totalClientes", personas.size());
        model.addAttribute("totalPuestos", puestos.size());

        model.addAttribute("personas", personas);
        model.addAttribute("puestos", puestos);
        model.addAttribute("productos", productos);
        return "index";
    }

    @GetMapping("/agregar")
    public String agregar(Persona persona) {
        //inyectamos un objeto de tipo persona
        return "modificar";
    }

    @PostMapping("/guardar")
    public String guardarInfo(@Valid Persona persona, Errors error) {
        if (error.hasErrors()) {
            return "modificar";
        }
        pservice.guardar(persona);
        return "redirect:/";
    }

    @PostMapping("/guardarUS")
    public String guardarInfoUsuario(@Valid Usuario persona, Errors error, Rol rol, BCryptPasswordEncoder encoder) {
        if (error.hasErrors()) {
            return "modificar";
        }
        us.guardar(persona, encoder);
        rolservice.guardar(rol, persona.getIdUsuario());
        return "redirect:/";
    }

    @PostMapping("/guardarPro")
    public String guardarInfoProducto(@Valid Producto producto, Errors error) {
        if (error.hasErrors()) {
            return "modificar";
        }
        productoservice.guardar(producto);
        return "redirect:/";
    }

    @PostMapping("/guardarPues")
    public String guardarInfoPuesto(Persona persona, Producto producto, Puesto puesto, Errors error) {
        if (error.hasErrors()) {
            return "modificar";
        }

        List puestos = puestoservice.listarPuestos();
        if (puestos.size() == 14) {
            return "redirect:/";
        }

        persona = pservice.encontrarPersona(persona);
        List<Persona> pers = new ArrayList();
        pers.add(persona);
        puesto.setNombre(pers);
        producto = productoservice.encontrarProducto(producto);

        var pr = puesto.getProductos();
        if (pr == null) {
            pr = new ArrayList();
        }
        if (pr.size() == 5) {
            return "redirect:/";
        }

        var nuevop = new Producto();
        nuevop.setNombre(producto.getNombre());

        productoservice.guardar(nuevop);

        pr.add(producto);

        puesto.setProductos(pr);
        puestoservice.guardar(puesto);
        return "redirect:/";
    }

    @PostMapping("/editarPuesto")
    public String editarInfoPuesto(Persona persona, Producto producto, Puesto puesto, Errors error) {
        if (error.hasErrors()) {
            return "modificar";
        }

        List puestos = puestoservice.listarPuestos();
        if (puestos.size() == 14) {
            return "redirect:/";
        }

        if (null != puestoservice.encontrarPuesto(puesto)) {
            puesto = puestoservice.encontrarPuesto(puesto);

        }

        persona = pservice.encontrarPersona(persona);
        List<Persona> pers = new ArrayList();
        pers.add(persona);
        puesto.setNombre(pers);
        producto = productoservice.encontrarProducto(producto);

        var pr = puesto.getProductos();
        if (pr == null) {
            pr = new ArrayList();
        }
        if (pr.size() == 5) {
            return "redirect:/";
        }

        var nuevop = new Producto();
        nuevop.setNombre(producto.getNombre());

        productoservice.guardar(nuevop);

        pr.add(producto);

        puesto.setProductos(pr);
        puestoservice.guardar(puesto);
        return "redirect:/";
    }

    @GetMapping("/editar/{idPersona}")
    public String editar(Persona persona, Model model) {
        //inyectamos un objeto de tipo persona

        persona = pservice.encontrarPersona(persona);

        model.addAttribute("persona", persona);
        return "modificar";
    }

    @GetMapping("/editarPues/{idPuesto}")
    public String editarPuesto(Puesto puesto, Model model) {
        //inyectamos un objeto de tipo persona

        List personas = pservice.ListarPersonas();

        var puestos = puestoservice.listarPuestos();
        var productos = productoservice.listarProducto();
        puesto = puestoservice.encontrarPuesto(puesto);

        productos = vaciaRepetidos(puestos, productos);

        model.addAttribute("puesto", puesto);
        model.addAttribute("personas", personas);
        model.addAttribute("productos", productos);
        return "modificarPuesto";
    }

    @GetMapping("/eliminar")
    public String eliminar(Persona persona) {
        //inyectamos un objeto de tipo persona

        pservice.eliminar(persona);
        return "redirect:/";
    }

    @GetMapping("/eliminarPues")
    public String eliminarPuesto(Puesto puesto) {
        //inyectamos un objeto de tipo persona

        puestoservice.eliminar(puesto);
        return "redirect:/";
    }

    public List<Producto> vaciaRepetidos(List<Puesto> puestos, List<Producto> productos) {

        var cont = 0;
        List<Producto> lp2 = new ArrayList();
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            for (int j = i+1; j < productos.size(); j++) {
                if(producto.getNombre().equals(productos.get(j).getNombre())){
                    productos.remove(j);
                    cont++;
                }
                
            }
            cont = 0;
        }
        for (int i = productos.size()-1; i >= 0; i--) {
            Producto producto = productos.get(i);
            for (int j = i-1; j >=0 ; j--) {
                if(producto.getNombre().equals(productos.get(j).getNombre())){
                    productos.remove(j);
                    
                }
                
            }
            
        }
        return productos;
    }

}