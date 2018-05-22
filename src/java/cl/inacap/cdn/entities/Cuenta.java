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
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nicolas
 */
@Entity
@Table(name = "CUENTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c")
    , @NamedQuery(name = "Cuenta.findById", query = "SELECT c FROM Cuenta c WHERE c.id = :id")
    , @NamedQuery(name = "Cuenta.findByCodCuenta", query = "SELECT c FROM Cuenta c WHERE c.codCuenta = :codCuenta")
    , @NamedQuery(name = "Cuenta.findByNombre", query = "SELECT c FROM Cuenta c WHERE c.nombre = :nombre")})
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUENTA_SEQ")
    @SequenceGenerator(sequenceName = "CUENTA_ID_SEQ", allocationSize = 1, name = "CUENTA_SEQ")
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 100)
    @Column(name = "COD_CUENTA")
    private String codCuenta;
    @Size(max = 150)
    @Column(name = "NOMBRE")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentaId")
    private Collection<Presupuesto> presupuestoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentaId")
    private Collection<GastoMes> gastoMesCollection;

    public Cuenta() {
    }

    public Cuenta(BigDecimal id) {
        this.id = id;
    }
    
    public static List<Cuenta> findAll(){
        
        System.out.println("");
        System.out.println("----------  Ingreso a Busqueda de Cuentas  ----------");
        List<Cuenta> cuentas;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        
        TypedQuery<Cuenta> result =  em.createNamedQuery("Cuenta.findAll", Cuenta.class);
        cuentas = result.getResultList();
        
        em.close();
        emf.close();
        System.out.println("----------  Fin de Busqueda de Cuentas  ----------");
        System.out.println("");
        return cuentas;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCodCuenta() {
        return codCuenta;
    }

    public void setCodCuenta(String codCuenta) {
        this.codCuenta = codCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.inacap.cdn.entities.Cuenta[ id=" + id + " ]";
    }
    
}
