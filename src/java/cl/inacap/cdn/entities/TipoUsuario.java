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
@Table(name = "TIPO_USUARIO")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "TipoUsuario.findAll", query = "SELECT t FROM TipoUsuario t")
	, @NamedQuery(name = "TipoUsuario.findById", query = "SELECT t FROM TipoUsuario t WHERE t.id = :id")
	, @NamedQuery(name = "TipoUsuario.findByNombre", query = "SELECT t FROM TipoUsuario t WHERE t.nombre = :nombre")})
public class TipoUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_USU_SEQ")
    @SequenceGenerator(sequenceName = "TIPO_USUARIO_ID_SEQ", allocationSize = 1, name = "TIPO_USU_SEQ")
    @Column(name = "ID")
	private BigDecimal id;
	@Size(max = 250)
    @Column(name = "NOMBRE")
	private String nombre;
	@ManyToMany(mappedBy = "tipoUsuarioList")
	private List<Permiso> permisoList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoUsuarioId")
	private List<Usuario> usuarioList;

	public TipoUsuario() {
	}

	public TipoUsuario(BigDecimal id) {
		this.id = id;
	}
        
        public TipoUsuario(String nombre) {
                this.nombre = nombre;
	}
        
        public TipoUsuario(String nombre, List<Permiso> permisos) {
                this.nombre = nombre;
                this.permisoList = permisos;
	}
        
        public static TipoUsuario findById(BigDecimal id){
            TipoUsuario tUsuario;
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            tUsuario = em.find(TipoUsuario.class, id);
            em.getTransaction().commit();
            em.close();
            emf.close();
            return tUsuario;
        }
        
        public static List<TipoUsuario> findAll(){
            List<TipoUsuario> tipos;
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            TypedQuery<TipoUsuario> result =  em.createNamedQuery("TipoUsuario.findAll", TipoUsuario.class);
            tipos = result.getResultList();
            em.close();
            emf.close();
            if(tipos.isEmpty()){
                return null;
            }else{
                return tipos;
            }
        }
        
        public void addTipoUsuario(List<Permiso> permisos){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            EntityTransaction trans = em.getTransaction();
            this.permisoList = new ArrayList();
            trans.begin();
            for(Permiso permiso:permisos){
                this.getPermisoList().add(permiso);
                Permiso permi = em.merge(permiso);
                permi.getTipoUsuarioList().add(this);
            }
            em.persist(this);
            trans.commit();
            em.close();
        }
        
        public void modificarTipoUsu(TipoUsuario newTipo, List<Permiso> permisos){
            try{
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
                EntityManager em = emf.createEntityManager();
                EntityTransaction trans = em.getTransaction();
                trans.begin();
                TipoUsuario tipo = em.merge(this);
                List<Permiso> oldPermisos = Permiso.findByTipoU(tipo);
                tipo.setNombre(newTipo.getNombre());
                tipo.setPermisoList(permisos);
                tipo.permisoList = new ArrayList();
                boolean validacion = false;
                for(Permiso permiso:permisos){
                    validacion = false;
                    for(TipoUsuario tU:permiso.getTipoUsuarioList()){
                        if(tU.equals(tipo)){
                            validacion = true;
                        }
                    }
                    if(!validacion){
                        Permiso permi = em.merge(permiso);
                        permi.getTipoUsuarioList().add(tipo);
                    }
                    tipo.getPermisoList().add(permiso);
                }
                for(Permiso permi:oldPermisos){
                    validacion = false;
                    for(Permiso permiso:permisos){
                        if(permi.equals(permiso)){
                            validacion = true;
                        }
                    }
                    if(!validacion){
                        Permiso permis = em.merge(permi);
                        permis.getTipoUsuarioList().remove(tipo);
                        tipo.getPermisoList().remove(permis);
                    }
                }
                trans.commit();
                em.close();
            }catch(Exception ex){
                    System.out.print(ex);
            }
	}
        
        public void removeTipoUsu(){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            EntityTransaction trans = em.getTransaction();
            trans.begin();
            TipoUsuario tipo = em.find(TipoUsuario.class, this.getId());
            em.remove(tipo);
            trans.commit();
            em.close();
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

	@XmlTransient
	public List<Permiso> getPermisoList() {
		return permisoList;
	}

	public void setPermisoList(List<Permiso> permisoList) {
		this.permisoList = permisoList;
	}

	@XmlTransient
	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}

	public void setUsuarioList(List<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
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
		if (!(object instanceof TipoUsuario)) {
			return false;
		}
		TipoUsuario other = (TipoUsuario) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "cl.inacap.cdn.entities.TipoUsuario[ id=" + id + " ]";
	}
	
}
