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
@Table(name = "GASTOEXC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gastoexc.findAll", query = "SELECT g FROM Gastoexc g")
    , @NamedQuery(name = "Gastoexc.findById", query = "SELECT g FROM Gastoexc g WHERE g.id = :id")})
public class Gastoexc implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GASTOEXC_SEQ")
    @SequenceGenerator(sequenceName = "GASTOEXC_ID_SEQ", allocationSize = 1, name = "GASTOEXC_SEQ")
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "GASTO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Gasto gastoId;

    public Gastoexc() {
    }

    public Gastoexc(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Gasto getGastoId() {
        return gastoId;
    }

    public void setGastoId(Gasto gastoId) {
        this.gastoId = gastoId;
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
        if (!(object instanceof Gastoexc)) {
            return false;
        }
        Gastoexc other = (Gastoexc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.inacap.cdn.entities.Gastoexc[ id=" + id + " ]";
    }
    
}
