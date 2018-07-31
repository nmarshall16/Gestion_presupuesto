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
import javax.persistence.*;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
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
	@JoinColumn(name = "FUENTE_F_COD_CENTRO", referencedColumnName = "COD_CENTRO")
    @ManyToOne(optional = false)
	private FuenteF fuenteFCodCentro;

	public Presupuesto() {
	}

	public Presupuesto(BigDecimal id) {
		this.id = id;
	}

        public Presupuesto(BigInteger montoDis, BigInteger montoTot, BigInteger totalGasta, AnhoProyect anhoProyectId, Cuenta cuentaId, FuenteF fuenteFCodCentro) {
            this.montoDis = montoDis;
            this.montoTot = montoTot;
            this.totalGasta = totalGasta;
            this.anhoProyectId = anhoProyectId;
            this.cuentaId = cuentaId;
            this.fuenteFCodCentro = fuenteFCodCentro;
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

        public static List<Presupuesto> findAll(){
            List<Presupuesto> presupuestos;
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            TypedQuery<Presupuesto> result =  em.createNamedQuery("Presupuesto.findAll", Presupuesto.class);
                    presupuestos = result.getResultList();
            em.close();
            emf.close();
            if(presupuestos.isEmpty()){
                return null;
            }else{
                return presupuestos;
            }
        }

        public static List<Presupuesto> findAllByAnho(AnhoProyect anho){
            List<Presupuesto> presupuestos;
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            TypedQuery<Presupuesto> result =  em.createNamedQuery("Presupuesto.findAllByAnho", Presupuesto.class);
                    result.setParameter("anho", anho);
                    presupuestos = result.getResultList();
            em.close();
            emf.close();
            if(presupuestos.isEmpty()){
                return null;
            }else{
                return presupuestos;
            }
        }
        
        public static Presupuesto findByFuenteAndCuenta(FuenteF fuente, Cuenta cuenta, AnhoProyect anho){
            Presupuesto presupuestos;
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            try{
                TypedQuery<Presupuesto> result =  em.createQuery("SELECT p FROM"
                + " Presupuesto p WHERE p.fuenteFCodCentro = :fuente AND p.cuentaId = :cuenta "
                + "AND p.anhoProyectId = :anho", Presupuesto.class);
                result.setParameter("fuente", fuente);
                result.setParameter("cuenta", cuenta);
                result.setParameter("anho", anho);
                presupuestos = result.getSingleResult();
            }catch(NoResultException ex){
                presupuestos = null;
            }
            em.close();
            emf.close();
            return presupuestos;
        }
	
        public String restarPresupuesto(long monto){
            long disponible = this.getMontoDis().longValue();
            long gastado = this.getTotalGasta().longValue();
            String error = "";
            if(monto > disponible){
                error = "No queda presupuesto para homologar el gasto";
            }else{
                disponible = disponible - monto;
                gastado = gastado + monto;
                try{
                    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
                    EntityManager em = emf.createEntityManager();
                    EntityTransaction trans = em.getTransaction();
                    trans.begin();
                    Presupuesto presupuesto = em.merge(this);
                    presupuesto.setMontoDis(BigInteger.valueOf(disponible));
                    presupuesto.setTotalGasta(BigInteger.valueOf(gastado));
                    trans.commit();
                    em.close();
                }catch(Exception ex){
                    error = "Error: "+ex.getMessage()+"<br>"+" Por lo cual no se pudo homologar correctamente el gasto";
                }
            }
            return error;
        }
        
        public String aumentarPresupuesto(long monto){
            long disponible = this.getMontoDis().longValue();
            long gastado = this.getTotalGasta().longValue();
            String error = "";
            disponible = disponible + monto;
            gastado = gastado - monto;
            if(disponible > this.getMontoTot().longValue()){
                error = "El monto disponible no puede ser mayor al disponible valide su presupuesto disponible";
            }else{
                try{
                    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
                    EntityManager em = emf.createEntityManager();
                    EntityTransaction trans = em.getTransaction();
                    trans.begin();
                    Presupuesto presupuesto = em.merge(this);
                    presupuesto.setMontoDis(BigInteger.valueOf(disponible));
                    presupuesto.setTotalGasta(BigInteger.valueOf(gastado));
                    trans.commit();
                    em.close();
                }catch(Exception ex){
                    error = "Error: "+ex.getMessage()+"<br>";
                }
            }
            return error;
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

	public FuenteF getFuenteFCodCentro() {
		return fuenteFCodCentro;
	}

	public void setFuenteFCodCentro(FuenteF fuenteFCodCentro) {
		this.fuenteFCodCentro = fuenteFCodCentro;
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
