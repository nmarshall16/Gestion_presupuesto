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
import javax.persistence.Persistence;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nicolas
 */
@Entity
@Table(name = "C_BANCO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CBanco.findAll", query = "SELECT c FROM CBanco c")
    , @NamedQuery(name = "CBanco.findById", query = "SELECT c FROM CBanco c WHERE c.id = :id")
    , @NamedQuery(name = "CBanco.findByNumCuenta", query = "SELECT c FROM CBanco c WHERE c.numCuenta = :numCuenta")
	, @NamedQuery(name = "Presupuesto.findAllByAnho", query = "SELECT p FROM Presupuesto p WHERE p.anhoProyectId = :anho")})
public class CBanco implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "C_BANCO_SEQ")
    @SequenceGenerator(sequenceName = "C_BANCO_ID_SEQ", allocationSize = 1, name = "C_BANCO_SEQ")
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "NUM_CUENTA")
    private BigInteger numCuenta;
    @JoinColumn(name = "BANCO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Banco bancoId;
    @JoinColumn(name = "FUENTE_F_COD_CENTRO", referencedColumnName = "COD_CENTRO")
    @ManyToOne(optional = false)
    private FuenteF fuenteFCodCentro;
    @JoinColumn(name = "PROYECTO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Proyecto proyectoId;

    public CBanco() {
    }

    public CBanco(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public static CBanco findByNumCta(BigDecimal id){
        CBanco cBanco = null;
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            TypedQuery<CBanco> result =  em.createNamedQuery("CBanco.findByNumCuenta", CBanco.class);
            result.setParameter("numCuenta", id);
            cBanco = result.getSingleResult();
            em.close();
            emf.close();
        }catch(Exception ex){
                cBanco = null;
        }
        return cBanco;
    }
    
    public static CBanco findCuenta(FuenteF fuente, Proyecto proyecto){
        CBanco cBanco = null;
        try{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            TypedQuery<CBanco> result = em.createQuery("SELECT c FROM CBanco c WHERE c.fuenteFCodCentro = :fuente AND c.proyectoId = :proyecto", CBanco.class);
            result.setParameter("fuente", fuente);
            result.setParameter("proyecto", proyecto);
            cBanco = result.getSingleResult();
            em.close();
            emf.close();
        }catch(Exception ex){
                cBanco = null;
        }
        return cBanco;
    }
	
	public static List<CBanco> findAllByBanco(Banco banco){
        
        System.out.println("");
        System.out.println("----------  Ingreso a Busqueda de Bancos  ----------");
        List<CBanco> cBancos;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        TypedQuery<CBanco> result =  em.createNamedQuery("CBanco.findAllByBanco", CBanco.class);
		result.setParameter("banco", banco);
		cBancos = result.getResultList();
        em.close();
        emf.close();
        System.out.println("----------  Fin de Busqueda de Bancos  ----------");
        System.out.println("");
        
        if(cBancos.isEmpty()){
            return null;
        }else{
            return cBancos;
        }
    }
	
	public static CBanco insCBanco(CBanco cBanco){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(cBanco);
		em.getTransaction().commit();
		em.close();
		return cBanco;
	}
    
    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(BigInteger numCuenta) {
        this.numCuenta = numCuenta;
    }

    public Banco getBancoId() {
        return bancoId;
    }

    public void setBancoId(Banco bancoId) {
        this.bancoId = bancoId;
    }

    public FuenteF getFuenteFCodCentro() {
        return fuenteFCodCentro;
    }

    public void setFuenteFCodCentro(FuenteF fuenteFCodCentro) {
        this.fuenteFCodCentro = fuenteFCodCentro;
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
        if (!(object instanceof CBanco)) {
            return false;
        }
        CBanco other = (CBanco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.inacap.cdn.entities.CBanco[ id=" + id + " ]";
    }
    
}
