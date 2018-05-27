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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nicolas
 */
@Entity
@Table(name = "ANHO_PROYECT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnhoProyect.findAll", query = "SELECT a FROM AnhoProyect a")
    , @NamedQuery(name = "AnhoProyect.findById", query = "SELECT a FROM AnhoProyect a WHERE a.id = :id")
    , @NamedQuery(name = "AnhoProyect.findByNum", query = "SELECT a FROM AnhoProyect a WHERE a.num = :num")
    , @NamedQuery(name = "AnhoProyect.findByInicio", query = "SELECT a FROM AnhoProyect a WHERE a.inicio = :inicio")
    , @NamedQuery(name = "AnhoProyect.findByTermino", query = "SELECT a FROM AnhoProyect a WHERE a.termino = :termino")
    , @NamedQuery(name = "AnhoProyect.findByTotalDis", query = "SELECT a FROM AnhoProyect a WHERE a.totalDis = :totalDis")
    , @NamedQuery(name = "AnhoProyect.findByTotal", query = "SELECT a FROM AnhoProyect a WHERE a.total = :total")})
public class AnhoProyect implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANHO_SEQ")
    @SequenceGenerator(sequenceName = "ANHO_PROYECT_ID_SEQ", allocationSize = 1, name = "ANHO_SEQ")
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "NUM")
    private BigInteger num;
    @Column(name = "INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;
    @Column(name = "TERMINO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date termino;
    @Column(name = "TOTAL_DIS")
    private BigInteger totalDis;
    @Column(name = "TOTAL")
    private BigInteger total;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anhoProyectId")
    private Collection<Presupuesto> presupuestoCollection;
    @JoinColumn(name = "PROYECTO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Proyecto proyectoId;

    public AnhoProyect() {
    }

    public AnhoProyect(BigDecimal id) {
        this.id = id;
    }
    
    public AnhoProyect(BigInteger num, Date fIni, Date fTer, BigInteger tDisp, BigInteger total, Proyecto proId) {
        this.num = num;
        this.inicio = fIni;
        this.termino = fTer;
        this.totalDis = tDisp;
        this.total = total;
        this.proyectoId = proId;
    }
    
    public static AnhoProyect findById(int id){
        AnhoProyect anho;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        TypedQuery<AnhoProyect> result = em.createNamedQuery("AnhoProyect.findById", AnhoProyect.class);
        result.setParameter("id", id);
        
        anho = result.getSingleResult();
        
        em.getTransaction().commit();
        em.close();
        emf.close();
        return anho;
    }
    
    public static AnhoProyect insAnho(AnhoProyect anho){
        
        System.out.println("");
        System.out.println("--------------- Ingreso a insertarAnho ---------------");
        
        EntityManagerFactory emf    = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em            = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(anho);
            em.getTransaction().commit();
            em.close();
            emf.close();
            
        } catch (ConstraintViolationException e) {
            System.out.println("Error en Insertar Anho");
            System.out.println("Clase de error "+e.getClass());
            System.out.println("Causa de error "+e.getCause());
            System.out.println("No se!"+e.initCause(e.getCause()));           
        }
        System.out.println("--------------- Fin de insertarAnho ---------------");
        System.out.println("");

        return anho;
    }
    
    public static int countYearsOfProyect(Proyecto pro){
        int cant = 0;
        System.out.println("");
        System.out.println("--------------- Ingreso a countYearsOfProyect ---------------");
        
        EntityManagerFactory emf    = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em            = emf.createEntityManager();
        
        try {
            TypedQuery<AnhoProyect> results = em.createNamedQuery("AnhoProyect.findAllOfProyect", AnhoProyect.class);
            results.setParameter("proyectoId", pro);
            cant = results.getResultList().size();
            em.close();
            emf.close();
            
        } catch (Exception e) {
            System.out.println("Clase de error "+e.getClass());
            System.out.println("Causa de error "+e.getCause());
            System.out.println("No se!"+e.initCause(e.getCause()));
        }
        System.out.println("--------------- Fin de countYearsOfProyect ---------------");
        System.out.println("");
        
        return cant+1;
    }
    
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getNum() {
        return num;
    }

    public void setNum(BigInteger num) {
        this.num = num;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getTermino() {
        return termino;
    }

    public void setTermino(Date termino) {
        this.termino = termino;
    }

    public BigInteger getTotalDis() {
        return totalDis;
    }

    public void setTotalDis(BigInteger totalDis) {
        this.totalDis = totalDis;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }

    @XmlTransient
    public Collection<Presupuesto> getPresupuestoCollection() {
        return presupuestoCollection;
    }

    public void setPresupuestoCollection(Collection<Presupuesto> presupuestoCollection) {
        this.presupuestoCollection = presupuestoCollection;
    }

    public Proyecto getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Proyecto proyectoId) {
        this.proyectoId = proyectoId;
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
        if (!(object instanceof AnhoProyect)) {
            return false;
        }
        AnhoProyect other = (AnhoProyect) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.inacap.cdn.entities.AnhoProyect[ id=" + id + " ]";
    }
    
}
