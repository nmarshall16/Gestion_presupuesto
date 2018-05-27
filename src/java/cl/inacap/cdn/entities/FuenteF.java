/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nicolas
 */
@Entity
@Table(name = "FUENTE_F")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FuenteF.findAll", query = "SELECT f FROM FuenteF f")
    , @NamedQuery(name = "FuenteF.findById", query = "SELECT f FROM FuenteF f WHERE f.id = :id")
    , @NamedQuery(name = "FuenteF.findByNombre", query = "SELECT f FROM FuenteF f WHERE f.nombre = :nombre")
    , @NamedQuery(name = "FuenteF.findByCodigo", query = "SELECT f FROM FuenteF f WHERE f.codigo = :codigo")})
public class FuenteF implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FUENTE_SEQ")
    @SequenceGenerator(sequenceName = "FUENTE_F_ID_SEQ", allocationSize = 1, name = "FUENTE_SEQ")
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 150)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 100)
    @Column(name = "CODIGO")
    private String codigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fuenteFId")
    private Collection<Presupuesto> presupuestoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fuenteFId")
    private Collection<GastoMes> gastoMesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fuenteFId")
    private Collection<Cuenta> cuentaCollection;

    public FuenteF() {
    }

    public FuenteF(BigDecimal id) {
        this.id = id;
    }
    
    public FuenteF(String nombre, String codigo, Collection<Presupuesto> presupuestoCollection, Collection<GastoMes> gastoMesCollection) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.presupuestoCollection = presupuestoCollection;
        this.gastoMesCollection = gastoMesCollection;
    }
    
    public static List<FuenteF> findAll(){
        
        System.out.println("");
        System.out.println("----------  Ingreso a Busqueda de FuenteF  ----------");
        List<FuenteF> ff;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        
        TypedQuery<FuenteF> result =  em.createNamedQuery("FuenteF.findAll", FuenteF.class);
        ff = result.getResultList();
        
        em.close();
        emf.close();
        System.out.println("----------  Fin de Busqueda de FuenteF  ----------");
        System.out.println("");
        return ff;
    }
    
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public Collection<Presupuesto> getPresupuestoCollection() {
        return presupuestoCollection;
    }

    public void setPresupuestoCollection(Collection<Presupuesto> presupuestoCollection) {
        this.presupuestoCollection = presupuestoCollection;
    }

    @XmlTransient
    public Collection<GastoMes> getGastoMesCollection() {
        return gastoMesCollection;
    }

    public void setGastoMesCollection(Collection<GastoMes> gastoMesCollection) {
        this.gastoMesCollection = gastoMesCollection;
    }

    @XmlTransient
    public Collection<Cuenta> getCuentaCollection() {
        return cuentaCollection;
    }

    public void setCuentaCollection(Collection<Cuenta> cuentaCollection) {
        this.cuentaCollection = cuentaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FuenteF)) {
            return false;
        }
        FuenteF other = (FuenteF) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.inacap.cdn.entities.FuenteF[ id=" + id + " ]";
    }
    
}
