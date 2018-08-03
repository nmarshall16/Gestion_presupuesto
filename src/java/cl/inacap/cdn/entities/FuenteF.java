/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
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
    , @NamedQuery(name = "FuenteF.findByCodCentro", query = "SELECT f FROM FuenteF f WHERE f.codCentro = :codCentro")
    , @NamedQuery(name = "FuenteF.findByNombre", query = "SELECT f FROM FuenteF f WHERE f.nombre = :nombre")
    , @NamedQuery(name = "FuenteF.findByTipo", query = "SELECT f FROM FuenteF f WHERE f.tipo = :tipo")})
public class FuenteF implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_CENTRO")
    private BigDecimal codCentro;
    @Size(max = 250)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "TIPO")
    private Character tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fuenteFCodCentro")
    private List<CBanco> cBancoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fuenteFCodCentro")
    private List<Presupuesto> presupuestoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fuenteFCodCentro")
    private List<Gasto> gastoList;

    public FuenteF() {
    }

    public FuenteF(BigDecimal codCentro) {
        this.codCentro = codCentro;
    }
    
    public FuenteF(String nombre, String codigo, List<Presupuesto> presupuestoList, List<Gasto> gastosList) {
        this.nombre = nombre;
        this.presupuestoList = presupuestoList;
        this.gastoList = gastosList;
    }
	
    public static FuenteF findById(BigDecimal codigo){
        FuenteF fuenteF;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<FuenteF> result = em.createNamedQuery("FuenteF.findByCodCentro", FuenteF.class);
        result.setParameter("codCentro", codigo);
        fuenteF = result.getSingleResult();
        em.getTransaction().commit();
        em.close();
        emf.close();
        return fuenteF;
    }
    
    public static FuenteF findNoPecuniario(){
        FuenteF fuenteF;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<FuenteF> result = em.createNamedQuery("FuenteF.findByTipo", FuenteF.class);
        result.setParameter("tipo", 'A');
        fuenteF = result.getSingleResult();
        em.getTransaction().commit();
        em.close();
        emf.close();
        return fuenteF;
    }
    
    public static FuenteF findPecuniario(){
        FuenteF fuenteF;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<FuenteF> result = em.createNamedQuery("FuenteF.findByTipo", FuenteF.class);
        result.setParameter("tipo", 'G');
        fuenteF = result.getSingleResult();
        em.getTransaction().commit();
        em.close();
        emf.close();
        return fuenteF;
    }
    
    public static List<FuenteF> findAll(){
        List<FuenteF> ff;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<FuenteF> result =  em.createNamedQuery("FuenteF.findAll", FuenteF.class);
        ff = result.getResultList();
        em.close();
        emf.close();
        return ff;
    }
    
    public BigDecimal getCodCentro() {
        return codCentro;
    }

    public void setCodCentro(BigDecimal codCentro) {
        this.codCentro = codCentro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<CBanco> getCBancoList() {
        return cBancoList;
    }

    public void setCBancoList(List<CBanco> cBancoList) {
        this.cBancoList = cBancoList;
    }

    @XmlTransient
    public List<Presupuesto> getPresupuestoList() {
        return presupuestoList;
    }

    public void setPresupuestoList(List<Presupuesto> presupuestoList) {
        this.presupuestoList = presupuestoList;
    }

    @XmlTransient
    public List<Gasto> getGastoList() {
        return gastoList;
    }

    public void setGastoList(List<Gasto> gastoList) {
        this.gastoList = gastoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCentro != null ? codCentro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FuenteF)) {
            return false;
        }
        FuenteF other = (FuenteF) object;
        if ((this.codCentro == null && other.codCentro != null) || (this.codCentro != null && !this.codCentro.equals(other.codCentro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.inacap.cdn.entities.FuenteF[ codCentro=" + codCentro + " ]";
    }
    
}
