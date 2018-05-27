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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
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
    , @NamedQuery(name = "GastoMes.findByIdAnhoProyect", query = "SELECT g FROM GastoMes g WHERE g.idAnhoProyect = :idAnhoProyect")
    , @NamedQuery(name = "GastoMes.findByIdCompra", query = "SELECT g FROM GastoMes g WHERE g.idCompra = :idCompra")
    , @NamedQuery(name = "GastoMes.findByImporte", query = "SELECT g FROM GastoMes g WHERE g.importe = :importe")
    , @NamedQuery(name = "GastoMes.findByOrdenCompra", query = "SELECT g FROM GastoMes g WHERE g.ordenCompra = :ordenCompra")
    , @NamedQuery(name = "GastoMes.findByAtributoPago", query = "SELECT g FROM GastoMes g WHERE g.atributoPago = :atributoPago")
    , @NamedQuery(name = "GastoMes.findByAsiento", query = "SELECT g FROM GastoMes g WHERE g.asiento = :asiento")
    , @NamedQuery(name = "GastoMes.findByTipo", query = "SELECT g FROM GastoMes g WHERE g.tipo = :tipo")})
public class GastoMes implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GASTO_MES_SEQ")
    @SequenceGenerator(sequenceName = "GASTO_MES_ID_SEQ", allocationSize = 1, name = "GASTO_MES_SEQ")
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
    @Column(name = "ID_ANHO_PROYECT")
    private BigInteger idAnhoProyect;
    @Column(name = "ID_COMPRA")
    private BigInteger idCompra;
    @Column(name = "IMPORTE")
    private BigInteger importe;
    @Size(max = 150)
    @Column(name = "ORDEN_COMPRA")
    private String ordenCompra;
    @Size(max = 400)
    @Column(name = "ATRIBUTO_PAGO")
    private String atributoPago;
    @Size(max = 150)
    @Column(name = "ASIENTO")
    private String asiento;
    @Column(name = "TIPO")
    private Character tipo;
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

    public BigInteger getIdAnhoProyect() {
        return idAnhoProyect;
    }

    public void setIdAnhoProyect(BigInteger idAnhoProyect) {
        this.idAnhoProyect = idAnhoProyect;
    }

    public BigInteger getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(BigInteger idCompra) {
        this.idCompra = idCompra;
    }

    public BigInteger getImporte() {
        return importe;
    }

    public void setImporte(BigInteger importe) {
        this.importe = importe;
    }

    public String getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(String ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public String getAtributoPago() {
        return atributoPago;
    }

    public void setAtributoPago(String atributoPago) {
        this.atributoPago = atributoPago;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
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
