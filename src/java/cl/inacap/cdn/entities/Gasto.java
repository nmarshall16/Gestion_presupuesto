/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nicolas
 */
@Entity
@Table(name = "GASTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gasto.findAll", query = "SELECT g FROM Gasto g")
    , @NamedQuery(name = "Gasto.findByCodCuenta", query = "SELECT g FROM Gasto g WHERE g.codCuenta = :codCuenta")
    , @NamedQuery(name = "Gasto.findByNombre", query = "SELECT g FROM Gasto g WHERE g.nombre = :nombre")})
public class Gasto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_CUENTA")
    private BigDecimal codCuenta;
    @Size(max = 150)
    @Column(name = "NOMBRE")
    private String nombre;
    @JoinColumn(name = "FUENTE_F_COD_CENTRO", referencedColumnName = "COD_CENTRO")
    @ManyToOne(optional = false)
    private FuenteF fuenteFCodCentro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gastoCodCuenta")
    private Collection<GastoMes> gastoMesCollection;

    public Gasto() {
    }

    public Gasto(BigDecimal codCuenta) {
        this.codCuenta = codCuenta;
    }

    public BigDecimal getCodCuenta() {
        return codCuenta;
    }

    public void setCodCuenta(BigDecimal codCuenta) {
        this.codCuenta = codCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public FuenteF getFuenteFCodCentro() {
        return fuenteFCodCentro;
    }

    public void setFuenteFCodCentro(FuenteF fuenteFCodCentro) {
        this.fuenteFCodCentro = fuenteFCodCentro;
    }

    @XmlTransient
    public Collection<GastoMes> getGastoMesCollection() {
        return gastoMesCollection;
    }

    public void setGastoMesCollection(Collection<GastoMes> gastoMesCollection) {
        this.gastoMesCollection = gastoMesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCuenta != null ? codCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gasto)) {
            return false;
        }
        Gasto other = (Gasto) object;
        if ((this.codCuenta == null && other.codCuenta != null) || (this.codCuenta != null && !this.codCuenta.equals(other.codCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.inacap.cdn.entities.Gasto[ codCuenta=" + codCuenta + " ]";
    }
    
}
