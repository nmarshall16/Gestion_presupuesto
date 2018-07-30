/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "HOMOLOGAR")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Homologar.findAll", query = "SELECT h FROM Homologar h")
	, @NamedQuery(name = "Homologar.findById", query = "SELECT h FROM Homologar h WHERE h.id = :id")
	, @NamedQuery(name = "Homologar.findByEstado", query = "SELECT h FROM Homologar h WHERE h.estado = :estado")})
public class Homologar implements Serializable {

	private static final long serialVersionUID = 1L;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOMOLOGAR_SEQ")
    @SequenceGenerator(sequenceName = "HOMOLOGAR_ID_SEQ", allocationSize = 1, name = "HOMOLOGAR_SEQ")
    @Column(name = "ID")
	private BigDecimal id;
	@Column(name = "ESTADO")
	private Character estado;
	@JoinColumn(name = "CUENTA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
	private Cuenta cuentaId;
	@JoinColumn(name = "GASTO_MES_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
	private GastoMes gastoMesId;

	public Homologar() {
	}

	public Homologar(BigDecimal id) {
		this.id = id;
	}
        
        public static Homologar findById(BigDecimal id){
            Homologar homol;
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Homologar> result = em.createNamedQuery("Homologar.findById", Homologar.class);
            result.setParameter("id", id);
            homol = result.getSingleResult();
            em.getTransaction().commit();
            em.close();
            emf.close();
            return homol;
        }
        
	public void addHomologacion(){
		try{
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
			EntityManager em = emf.createEntityManager();
			EntityTransaction trans = em.getTransaction();
			trans.begin();
			em.persist(this);
			trans.commit();
			em.close();
		}catch(Exception ex){
			System.out.print(ex);
		}
	}
        
	public static List<Homologar> findHomologaciones(GastoMes gasto){
		List<Homologar> homologacion = null;
		try{
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Homologar> buscarGasto = em.createQuery("SELECT h FROM Homologar h WHERE h.gastoMesId = :gasto", Homologar.class);
			buscarGasto.setParameter("gasto", gasto);
			homologacion = buscarGasto.getResultList();
		}catch(NoResultException ex){
			homologacion = null;
		}
		return homologacion;
	}

	public static Homologar findHomologacion(GastoMes gasto){
		Homologar homologacion = null;
		try{
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<Homologar> buscarGasto = em.createQuery("SELECT h FROM Homologar h WHERE h.gastoMesId = :gasto", Homologar.class);
			buscarGasto.setParameter("gasto", gasto);
			homologacion = buscarGasto.getSingleResult();
		}catch(NoResultException ex){
			homologacion = null;
		}
		return homologacion;
	}

	public void actualizarHomologacion(Cuenta cuenta){
		try{
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
			EntityManager em = emf.createEntityManager();
			EntityTransaction trans = em.getTransaction();
			trans.begin();
			Homologar homologacion = em.merge(this);
			homologacion.setCuentaId(cuenta);
			trans.commit();
			em.close();
		}catch(Exception ex){
			System.out.print(ex);
		}
	}
        
        public void actualizarEstado(char estado){
            try{
                    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
                    EntityManager em = emf.createEntityManager();
                    EntityTransaction trans = em.getTransaction();
                    trans.begin();
                    Homologar homologacion = em.merge(this);
                    homologacion.setEstado(estado); 
                    trans.commit();
                    em.close();
            }catch(Exception ex){
                    System.out.print(ex);
            }
	}
        
        public static int getGastosP(AnhoProyect anho, String mes){
            int largo = 0;
            List<Homologar> homologacion = null;
            ArrayList<Homologar> pendientes = new ArrayList<>();
            try{
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                TypedQuery<Homologar> buscarGasto = em.createQuery("SELECT h FROM Homologar h WHERE h.estado = :estado", Homologar.class);
                buscarGasto.setParameter("estado", 'P');
                homologacion = buscarGasto.getResultList();
                for(Homologar ho:homologacion){
                    if(ho.getGastoMesId().getAnhoProyectId().equals(anho) && ho.getGastoMesId().getMes().equals(new BigInteger(mes))){
                        pendientes.add(ho);
                    }
                }
            }catch(NoResultException ex){
                homologacion = null;
            }
            if(pendientes.size()>0){
                largo = pendientes.size();
            }
            return largo;
        }
        
        public static ArrayList<Homologar> getGastosPendientes(AnhoProyect anho, String mes){
            List<Homologar> homologacion = null;
            ArrayList<Homologar> pendientes = new ArrayList<>();
            try{
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                TypedQuery<Homologar> buscarGasto = em.createQuery("SELECT h FROM Homologar h WHERE h.estado = :estado", Homologar.class);
                buscarGasto.setParameter("estado", 'P');
                homologacion = buscarGasto.getResultList();
                for(Homologar ho:homologacion){
                    if(ho.getGastoMesId().getAnhoProyectId().equals(anho) && ho.getGastoMesId().getMes().equals(new BigInteger(mes))){
                        pendientes.add(ho);
                    }
                }
            }catch(NoResultException ex){
                homologacion = null;
            }
            return pendientes;
        }
        
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	public Cuenta getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(Cuenta cuentaId) {
		this.cuentaId = cuentaId;
	}

	public GastoMes getGastoMesId() {
		return gastoMesId;
	}

	public void setGastoMesId(GastoMes gastoMesId) {
		this.gastoMesId = gastoMesId;
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
		if (!(object instanceof Homologar)) {
			return false;
		}
		Homologar other = (Homologar) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "cl.inacap.cdn.entities.Homologar[ id=" + id + " ]";
	}
	
}
