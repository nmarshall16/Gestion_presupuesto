/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nicolas
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
