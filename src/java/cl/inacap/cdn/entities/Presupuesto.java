/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.*;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nicolas
 */
@Entity
@Table(name = "PRESUPUESTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Presupuesto.findAll", query = "SELECT p FROM Presupuesto p")
    , @NamedQuery(name = "Presupuesto.findById", query = "SELECT p FROM Presupuesto p WHERE p.id = :id")
    , @NamedQuery(name = "Presupuesto.findByMontoDis", query = "SELECT p FROM Presupuesto p WHERE p.montoDis = :montoDis")
    , @NamedQuery(name = "Presupuesto.findByMontoTot", query = "SELECT p FROM Presupuesto p WHERE p.montoTot = :montoTot")
    , @NamedQuery(name = "Presupuesto.findByTotalGasta", query = "SELECT p FROM Presupuesto p WHERE p.totalGasta = :totalGasta")})
public class Presupuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRESUPUESTO_SEQ")
    @SequenceGenerator(sequenceName = "PRESUPUESTO_ID_SEQ", allocationSize = 1, name = "PRESUPUESTO_SEQ")
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "MONTO_DIS")
    private BigInteger montoDis;
    @Column(name = "MONTO_TOT")
    private BigInteger montoTot;
    @Column(name = "TOTAL_GASTA")
    private BigInteger totalGasta;
    @JoinColumn(name = "ANHO_PROYECT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private AnhoProyect anhoProyectId;
    @JoinColumn(name = "CUENTA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cuenta cuentaId;
    @JoinColumn(name = "FUENTE_F_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FuenteF fuenteFId;

    public Presupuesto() {
    }

    public Presupuesto(BigDecimal id) {
        this.id = id;
    }
    
    public Presupuesto(BigInteger montoDis, BigInteger montoTot, BigInteger totalGasta, AnhoProyect anhoProyectId, Cuenta cuentaId, FuenteF fuenteFId) {
        this.montoDis = montoDis;
        this.montoTot = montoTot;
        this.totalGasta = totalGasta;
        this.anhoProyectId = anhoProyectId;
        this.cuentaId = cuentaId;
        this.fuenteFId = fuenteFId;
    }
    
    public static Presupuesto insPresupuesto(Presupuesto presupuesto){
        
        System.out.println("");
        System.out.println("--------------- Ingreso a insertar Presupuesto ---------------");
        
        EntityManagerFactory emf    = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em            = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(presupuesto);
            em.getTransaction().commit();
            em.close();
            emf.close();
            
        } catch (ConstraintViolationException e) {
            System.out.println("Error en Insertar Presupuesto");
            System.out.println("Clase de error "+e.getClass());
            System.out.println("Causa de error "+e.getCause());
            System.out.println("No se!"+e.initCause(e.getCause()));           
        }
        System.out.println("--------------- Fin de inserta Presupuesto ---------------");
        System.out.println("");
        
        return presupuesto;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigInteger getMontoDis() {
        return montoDis;
    }

    public void setMontoDis(BigInteger montoDis) {
        this.montoDis = montoDis;
    }

    public BigInteger getMontoTot() {
        return montoTot;
    }

    public void setMontoTot(BigInteger montoTot) {
        this.montoTot = montoTot;
    }

    public BigInteger getTotalGasta() {
        return totalGasta;
    }

    public void setTotalGasta(BigInteger totalGasta) {
        this.totalGasta = totalGasta;
    }

    public AnhoProyect getAnhoProyectId() {
        return anhoProyectId;
    }

    public void setAnhoProyectId(AnhoProyect anhoProyectId) {
        this.anhoProyectId = anhoProyectId;
    }

    public Cuenta getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Cuenta cuentaId) {
        this.cuentaId = cuentaId;
    }

    public FuenteF getFuenteFId() {
        return fuenteFId;
    }

    public void setFuenteFId(FuenteF fuenteFId) {
        this.fuenteFId = fuenteFId;
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
        if (!(object instanceof Presupuesto)) {
            return false;
        }
        Presupuesto other = (Presupuesto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.inacap.cdn.entities.Presupuesto[ id=" + id + " ]";
    }
    
}
