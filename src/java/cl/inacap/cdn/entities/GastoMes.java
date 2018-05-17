/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nicolas
 */
@Entity
@Table(name = "GASTO_MES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GastoMes.findAll", query = "SELECT g FROM GastoMes g")
    , @NamedQuery(name = "GastoMes.findById", query = "SELECT g FROM GastoMes g WHERE g.id = :id")
    , @NamedQuery(name = "GastoMes.findByFecha", query = "SELECT g FROM GastoMes g WHERE g.fecha = :fecha")
    , @NamedQuery(name = "GastoMes.findByMes", query = "SELECT g FROM GastoMes g WHERE g.mes = :mes")
    , @NamedQuery(name = "GastoMes.findByEstado", query = "SELECT g FROM GastoMes g WHERE g.estado = :estado")
    , @NamedQuery(name = "GastoMes.findByNumFac", query = "SELECT g FROM GastoMes g WHERE g.numFac = :numFac")
    , @NamedQuery(name = "GastoMes.findByIdProyecto", query = "SELECT g FROM GastoMes g WHERE g.idProyecto = :idProyecto")})
public class GastoMes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 100)
    @Column(name = "MES")
    private String mes;
    @Column(name = "ESTADO")
    private Character estado;
    @Column(name = "NUM_FAC")
    private BigInteger numFac;
    @Column(name = "ID_PROYECTO")
    private BigInteger idProyecto;
    @JoinColumn(name = "CUENTA_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cuenta cuentaId;
    @JoinColumn(name = "FUENTE_F_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private FuenteF fuenteFId;
    @JoinColumn(name = "GASTO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Gasto gastoId;
    @JoinColumn(name = "PROVEEDOR_RUT", referencedColumnName = "RUT")
    @ManyToOne(optional = false)
    private Proveedor proveedorRut;

    public GastoMes() {
    }

    public GastoMes(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public BigInteger getNumFac() {
        return numFac;
    }

    public void setNumFac(BigInteger numFac) {
        this.numFac = numFac;
    }

    public BigInteger getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(BigInteger idProyecto) {
        this.idProyecto = idProyecto;
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

    public Gasto getGastoId() {
        return gastoId;
    }

    public void setGastoId(Gasto gastoId) {
        this.gastoId = gastoId;
    }

    public Proveedor getProveedorRut() {
        return proveedorRut;
    }

    public void setProveedorRut(Proveedor proveedorRut) {
        this.proveedorRut = proveedorRut;
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
        if (!(object instanceof GastoMes)) {
            return false;
        }
        GastoMes other = (GastoMes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.inacap.cdn.entities.GastoMes[ id=" + id + " ]";
    }
    
}
