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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nicolas
 */
@Entity
@Table(name = "C_BANCO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CBanco.findAll", query = "SELECT c FROM CBanco c")
    , @NamedQuery(name = "CBanco.findByNumCuenta", query = "SELECT c FROM CBanco c WHERE c.numCuenta = :numCuenta")})
public class CBanco implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUM_CUENTA")
    private BigDecimal numCuenta;
    @JoinColumn(name = "BANCO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Banco bancoId;
    @JoinColumn(name = "FUENTE_F_COD_CENTRO", referencedColumnName = "COD_CENTRO")
    @ManyToOne(optional = false)
    private FuenteF fuenteFCodCentro;
    @JoinColumn(name = "PROYECTO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Proyecto proyectoId;

    public CBanco() {
    }

    public CBanco(BigDecimal numCuenta) {
        this.numCuenta = numCuenta;
    }

    public BigDecimal getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(BigDecimal numCuenta) {
        this.numCuenta = numCuenta;
    }

    public Banco getBancoId() {
        return bancoId;
    }

    public void setBancoId(Banco bancoId) {
        this.bancoId = bancoId;
    }

    public FuenteF getFuenteFCodCentro() {
        return fuenteFCodCentro;
    }

    public void setFuenteFCodCentro(FuenteF fuenteFCodCentro) {
        this.fuenteFCodCentro = fuenteFCodCentro;
    }

    public Proyecto getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Proyecto proyectoId) {
        this.proyectoId = proyectoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numCuenta != null ? numCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CBanco)) {
            return false;
        }
        CBanco other = (CBanco) object;
        if ((this.numCuenta == null && other.numCuenta != null) || (this.numCuenta != null && !this.numCuenta.equals(other.numCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.inacap.cdn.entities.CBanco[ numCuenta=" + numCuenta + " ]";
    }
    
}
