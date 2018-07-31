/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "GASTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gasto.findAll", query = "SELECT g FROM Gasto g")
    , @NamedQuery(name = "Gasto.findById", query = "SELECT g FROM Gasto g WHERE g.id = :id")
    , @NamedQuery(name = "Gasto.findByCodCuenta", query = "SELECT g FROM Gasto g WHERE g.codCuenta = :codCuenta")
    , @NamedQuery(name = "Gasto.findByNombre", query = "SELECT g FROM Gasto g WHERE g.nombre = :nombre")
	, @NamedQuery(name = "Gasto.findByCtaYFte", query = "SELECT g FROM Gasto g WHERE g.codCuenta = :cuenta AND g.fuenteFCodCentro = :fuente")})
public class Gasto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GASTO_SEQ")
    @SequenceGenerator(sequenceName = "GASTO_ID_SEQ", allocationSize = 1, name = "GASTO_SEQ")
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "COD_CUENTA")
    private BigInteger codCuenta;
    @Size(max = 150)
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "FUENTE_F_COD_CENTRO", referencedColumnName = "COD_CENTRO")
    @ManyToOne(optional = false)
    private FuenteF fuenteFCodCentro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gastoId")
    private List<Gastoexc> gastoexcList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gastoId")
    private List<GastoMes> gastoMesList;

    public Gasto() {
    }

    public Gasto(BigDecimal id) {
        this.id = id;
    }
    
    public Gasto(BigInteger codCuenta, String nombre, FuenteF fuenteFCodCentro) {
            this.codCuenta = codCuenta;
            this.nombre = nombre;
            this.fuenteFCodCentro = fuenteFCodCentro;
        }
        
    public static Gasto findGasto(BigDecimal fuente, BigInteger cuenta){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        FuenteF financiamiento = FuenteF.findById(fuente);
        TypedQuery<Gasto> buscarGasto = em.createNamedQuery("Gasto.findByCtaYFte", Gasto.class);
        buscarGasto.setParameter("cuenta", cuenta);
        buscarGasto.setParameter("fuente", financiamiento);
        List<Gasto> gastos = buscarGasto.getResultList();
        Gasto gasto = null;
        if(gastos.size() > 0){
            gasto = buscarGasto.getSingleResult();
        }
        return gasto;
    }

    public void addGasto(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        em.persist(this);
        trans.commit();
        em.close();
    }
    
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getCodCuenta() {
        return codCuenta;
    }

    public void setCodCuenta(BigInteger codCuenta) {
        this.codCuenta = codCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public FuenteF getFuenteFCodCentro() {
        return fuenteFCodCentro;
    }

    public void setFuenteFCodCentro(FuenteF fuenteFCodCentro) {
        this.fuenteFCodCentro = fuenteFCodCentro;
    }

    @XmlTransient
    public List<Gastoexc> getGastoexcList() {
        return gastoexcList;
    }

    public void setGastoexcList(List<Gastoexc> gastoexcList) {
        this.gastoexcList = gastoexcList;
    }

    @XmlTransient
    public List<GastoMes> getGastoMesList() {
        return gastoMesList;
    }

    public void setGastoMesList(List<GastoMes> gastoMesList) {
        this.gastoMesList = gastoMesList;
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
        if (!(object instanceof Gasto)) {
            return false;
        }
        Gasto other = (Gasto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.inacap.cdn.entities.Gasto[ id=" + id + " ]";
    }
    
}
