/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "BANCO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Banco.findAll", query = "SELECT b FROM Banco b")
    , @NamedQuery(name = "Banco.findById", query = "SELECT b FROM Banco b WHERE b.id = :id")
    , @NamedQuery(name = "Banco.findByNombre", query = "SELECT b FROM Banco b WHERE b.nombre = :nombre")
    , @NamedQuery(name = "Banco.findByNumCuenta", query = "SELECT b FROM Banco b WHERE b.numCuenta = :numCuenta")})
public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BANCO_SEQ")
    @SequenceGenerator(sequenceName = "BANCO_ID_SEQ", allocationSize = 1, name = "BANCO_SEQ")
    @Column(name = "ID")
    private BigDecimal id;
    @Size(max = 250)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "NUM_CUENTA")
    private BigInteger numCuenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bancoId")
    private Collection<Proyecto> proyectoCollection;

    public Banco() {
    }

    public Banco(BigDecimal id) {
        this.id = id;
    }
    
    public Banco(String nombre, BigInteger numCuenta) {
        this.nombre = nombre;
        this.numCuenta = numCuenta;
    }
    
    public static List<Banco> findAll(){
        
        System.out.println("");
        System.out.println("----------  Ingreso a Busqueda de Bancos  ----------");
        List<Banco> bancos;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        
        TypedQuery<Banco> result =  em.createNamedQuery("Banco.findAll", Banco.class);

        bancos = result.getResultList();
        
        em.close();
        emf.close();
        System.out.println("----------  Fin de Busqueda de Bancos  ----------");
        System.out.println("");
        
        if(bancos.isEmpty()){
            return null;
        }else{
            return bancos;
        }
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

    public BigInteger getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(BigInteger numCuenta) {
        this.numCuenta = numCuenta;
    }

    @XmlTransient
    public Collection<Proyecto> getProyectoCollection() {
        return proyectoCollection;
    }

    public void setProyectoCollection(Collection<Proyecto> proyectoCollection) {
        this.proyectoCollection = proyectoCollection;
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
        if (!(object instanceof Banco)) {
            return false;
        }
        Banco other = (Banco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.inacap.cdn.entities.Banco[ id=" + id + " ]";
    }
    
}
