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
	private List<TipoUsuario> tipoUsuarioList;

	public Permiso() {
	}

	public Permiso(BigDecimal id) {
		this.id = id;
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
