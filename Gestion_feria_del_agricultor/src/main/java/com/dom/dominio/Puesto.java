
package com.dom.dominio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "puesto")
public class Puesto implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPuesto;
    
    
    @OneToMany
    @JoinColumn(name="id_puestos")
    private List<Persona> nombre;
    
    @OneToMany
    @JoinColumn(name="id_puesto")
    private List<Producto> productos;
}
    