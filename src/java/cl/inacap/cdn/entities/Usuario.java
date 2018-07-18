/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
	, @NamedQuery(name = "Usuario.findByRut", query = "SELECT u FROM Usuario u WHERE u.rut = :rut")
	, @NamedQuery(name = "Usuario.findByDv", query = "SELECT u FROM Usuario u WHERE u.dv = :dv")
	, @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
	, @NamedQuery(name = "Usuario.findByApellido", query = "SELECT u FROM Usuario u WHERE u.apellido = :apellido")
	, @NamedQuery(name = "Usuario.findByClave", query = "SELECT u FROM Usuario u WHERE u.clave = :clave")})
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RUT")
	private BigDecimal rut;
	@Column(name = "DV")
	private Character dv;
	@Size(max = 250)
    @Column(name = "NOMBRE")
	private String nombre;
	@Size(max = 250)
    @Column(name = "APELLIDO")
	private String apellido;
	@Size(max = 150)
    @Column(name = "CLAVE")
	private String clave;
	@ManyToMany(mappedBy = "usuarioList")
	private List<Proyecto> proyectoList;
	@JoinColumn(name = "TIPO_USUARIO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
	private TipoUsuario tipoUsuarioId;

	public Usuario() {
	}

	public Usuario(BigDecimal rut) {
		this.rut = rut;
	}

    public static Usuario findById(BigDecimal rut){
        Usuario usuario;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        usuario = em.find(Usuario.class, rut);
        
        em.getTransaction().commit();
        em.close();
        emf.close();
        return usuario;
    }
    
	public BigDecimal getRut() {
		return rut;
	}

	public void setRut(BigDecimal rut) {
		this.rut = rut;
	}

	public Character getDv() {
		return dv;
	}

	public void setDv(Character dv) {
		this.dv = dv;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@XmlTransient
	public List<Proyecto> getProyectoList() {
		return proyectoList;
	}

	public void setProyectoList(List<Proyecto> proyectoList) {
		this.proyectoList = proyectoList;
	}

	public TipoUsuario getTipoUsuarioId() {
		return tipoUsuarioId;
	}

	public void setTipoUsuarioId(TipoUsuario tipoUsuarioId) {
		this.tipoUsuarioId = tipoUsuarioId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (rut != null ? rut.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) object;
		if ((this.rut == null && other.rut != null) || (this.rut != null && !this.rut.equals(other.rut))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "cl.inacap.cdn.entities.Usuario[ rut=" + rut + " ]";
	}
	
}
