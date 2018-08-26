/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "PERMISO")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Permiso.findAll", query = "SELECT p FROM Permiso p")
	, @NamedQuery(name = "Permiso.findById", query = "SELECT p FROM Permiso p WHERE p.id = :id")
	, @NamedQuery(name = "Permiso.findByNombre", query = "SELECT p FROM Permiso p WHERE p.nombre = :nombre")})

public class Permiso implements Serializable {

	private static final long serialVersionUID = 1L;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Id
    @Basic(optional = false)
    @NotNull
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISO_SEQ")
    @SequenceGenerator(sequenceName = "PERMISO_ID_SEQ", allocationSize = 1, name = "PERMISO_SEQ")
    @Column(name = "ID")
	private BigDecimal id;
	@Size(max = 250)
    @Column(name = "NOMBRE")
	private String nombre;
	@JoinTable(name = "PERMISOSU", joinColumns = {
    	@JoinColumn(name = "PERMISO_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
    	@JoinColumn(name = "TIPO_USUARIO_ID", referencedColumnName = "ID")})
    @ManyToMany
	private List<TipoUsuario> tipoUsuarioList = new ArrayList<>();
	public Permiso() {
	}

	public Permiso(BigDecimal id) {
		this.id = id;
	}
        
        public static List<Permiso> findAll(){
            List<Permiso> permisos;
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            TypedQuery<Permiso> result =  em.createNamedQuery("Permiso.findAll", Permiso.class);
            permisos = result.getResultList();
            em.close();
            emf.close();
            if(permisos.isEmpty()){
                return null;
            }else{
                return permisos;
            }
        }
        
        public static Permiso findById(BigDecimal id){
            Permiso permiso;
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            permiso = em.find(Permiso.class, id);
            em.getTransaction().commit();
            em.close();
            emf.close();
            return permiso;
        }
        
        public static List<Permiso> findByTipoU(TipoUsuario u){
            List<Permiso> permiso = new ArrayList();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Query result = em.createNativeQuery("SELECT p.id, p.nombre FROM permiso p LEFT JOIN permisosu u ON p.id = u.permiso_id WHERE u.tipo_usuario_id = "+u.getId(), Permiso.class);
            permiso = result.getResultList();
            em.close();
            emf.close();
            return permiso;
        }
        
        public static boolean validarPermiso(TipoUsuario u, String id){
            boolean validacion = false;
            try{
                List<Permiso> permisos = u.getPermisoList();
                System.out.println(permisos);
                Permiso permiso = Permiso.findById(new BigDecimal(id));
                for(Permiso p:permisos){
                    if(p.equals(permiso)){
                        validacion = true;
                    }
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
            return validacion;
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
        
        public List<TipoUsuario> getTiposUsuario() {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Query result = em.createNativeQuery("SELECT t.id, t.nombre FROM tipo_usuario t LEFT JOIN permisosu u ON t.id = u.tipo_usuario_id WHERE u.permiso_id = "+this.id, TipoUsuario.class);
            this.tipoUsuarioList = result.getResultList();
            em.close();
            emf.close();
            return tipoUsuarioList;
	}
        
        @XmlTransient
	public List<TipoUsuario> getTipoUsuarioList() {
            return tipoUsuarioList;
	}

	public void setTipoUsuarioList(List<TipoUsuario> tipoUsuarioList) {
		this.tipoUsuarioList = tipoUsuarioList;
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
		if (!(object instanceof Permiso)) {
			return false;
		}
		Permiso other = (Permiso) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "cl.inacap.cdn.entities.Permiso[ id=" + id + " ]";
	}
	
}
