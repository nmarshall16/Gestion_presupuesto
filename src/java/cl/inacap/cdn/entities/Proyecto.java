/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "PROYECTO")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p")
	, @NamedQuery(name = "Proyecto.findById", query = "SELECT p FROM Proyecto p WHERE p.id = :id")
	, @NamedQuery(name = "Proyecto.findByNombre", query = "SELECT p FROM Proyecto p WHERE p.nombre = :nombre")
	, @NamedQuery(name = "Proyecto.findByCodigo", query = "SELECT p FROM Proyecto p WHERE p.codigo = :codigo")
	, @NamedQuery(name = "Proyecto.findByFechaIni", query = "SELECT p FROM Proyecto p WHERE p.fechaIni = :fechaIni")
	, @NamedQuery(name = "Proyecto.findByFechaFin", query = "SELECT p FROM Proyecto p WHERE p.fechaFin = :fechaFin")
	, @NamedQuery(name = "Proyecto.findByEstado", query = "SELECT p FROM Proyecto p WHERE p.estado = :estado")})
public class Proyecto implements Serializable {

	private static final long serialVersionUID = 1L;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROYECTO_SEQ")
    @SequenceGenerator(sequenceName = "PROYECTO_ID_SEQ", allocationSize = 1, name = "PROYECTO_SEQ")
    @Column(name = "ID")
	private BigDecimal id;
	@Size(max = 300)
    @Column(name = "NOMBRE")
	private String nombre;
	@Size(max = 250)
    @Column(name = "CODIGO")
	private String codigo;
	@Column(name = "FECHA_INI")
    @Temporal(TemporalType.TIMESTAMP)
	private Date fechaIni;
	@Column(name = "FECHA_FIN")
    @Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;
	@Column(name = "ESTADO")
	private Character estado;
	@JoinTable(name = "PROYECT_ASIG", joinColumns = {
    	@JoinColumn(name = "PROYECTO_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
    	@JoinColumn(name = "USUARIO_RUT", referencedColumnName = "RUT")})
    @ManyToMany
	private List<Usuario> usuarioList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectoId")
	private List<CBanco> cBancoList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "proyectoId")
	private List<AnhoProyect> anhoProyectList;

	public Proyecto() {
	}

	public Proyecto(BigDecimal id) {
		this.id = id;
	}
    
    public Proyecto(String nombre, String codigo, Date fechaIni, Date fechaFin, Character estado) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.fechaIni = fechaIni;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }
    
    public static Proyecto findById(BigDecimal id){
        Proyecto pro;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        pro = em.find(Proyecto.class, id);
        
        em.getTransaction().commit();
        em.close();
        emf.close();
        return pro;
    }
    
    public static Proyecto insProyecto(Proyecto proyecto){
        
        System.out.println("");
        System.out.println("--------------- Ingreso a insertarProyecto ---------------");
        
        EntityManagerFactory emf    = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em            = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(proyecto);
            em.getTransaction().commit();
            em.close();
            emf.close();
            
        } catch (ConstraintViolationException e) {
            System.out.println("Error en Insertar Proyecto");
            System.out.println("Clase de error "+e.getClass());
            System.out.println("Causa de error "+e.getCause());
            System.out.println("No se!"+e.initCause(e.getCause()));           
        }
        System.out.println("--------------- Fin de insertarProyecto ---------------");
        System.out.println("");

        return proyecto;
    }
    
    public static List<Proyecto> findByEstado(Character estado){
        
        System.out.println("");
        System.out.println("----------  Ingreso a Busqueda de Proyectos  ----------");
        List<Proyecto> proyectos;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        
        TypedQuery<Proyecto> result =  em.createNamedQuery("Proyecto.findByEstado", Proyecto.class);
        result.setParameter("estado", estado);

        proyectos = result.getResultList();
        
        em.close();
        emf.close();
        System.out.println("----------  Fin de Busqueda de Proyectos  ----------");
        System.out.println("");
        
        if(proyectos.isEmpty()){
            return null;
        }else{
            return proyectos;
        }
    }
	
	public static Proyecto hideProyecto(Proyecto proyecto){
        
        System.out.println("");
        System.out.println("--------------- Ingreso a insertarProyecto ---------------");
        
        EntityManagerFactory emf    = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em            = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            proyecto.setEstado('0');
			em.merge(proyecto);
            em.getTransaction().commit();
            em.close();
            emf.close();
            
        } catch (ConstraintViolationException e) {
            System.out.println("Error en Insertar Proyecto");
            System.out.println("Clase de error "+e.getClass());
            System.out.println("Causa de error "+e.getCause());
            System.out.println("No se!"+e.initCause(e.getCause()));           
        }
        System.out.println("--------------- Fin de insertarProyecto ---------------");
        System.out.println("");

        return proyecto;
    }
	
	public static Proyecto updateProyecto(Proyecto proyecto){
        
        System.out.println("");
        System.out.println("--------------- Ingreso a insertarProyecto ---------------");
        
        EntityManagerFactory emf    = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em            = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
			em.merge(proyecto);
            em.getTransaction().commit();
            em.close();
            emf.close();
            
        } catch (ConstraintViolationException e) {
            System.out.println("Error en Insertar Proyecto");
            System.out.println("Clase de error "+e.getClass());
            System.out.println("Causa de error "+e.getCause());
            System.out.println("No se!"+e.initCause(e.getCause()));           
        }
        System.out.println("--------------- Fin de insertarProyecto ---------------");
        System.out.println("");

        return proyecto;
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

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	@XmlTransient
	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}

	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}

	@XmlTransient
	public List<CBanco> getCBancoList() {
		return cBancoList;
	}

	public void setCBancoList(List<CBanco> cBancoList) {
		this.cBancoList = cBancoList;
	}

	@XmlTransient
	public List<AnhoProyect> getAnhoProyectList() {
		return anhoProyectList;
	}

	public void setAnhoProyectList(List<AnhoProyect> anhoProyectList) {
		this.anhoProyectList = anhoProyectList;
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
		if (!(object instanceof Proyecto)) {
			return false;
		}
		Proyecto other = (Proyecto) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "cl.inacap.cdn.entities.Proyecto[ id=" + id + " ]";
	}
	
}
