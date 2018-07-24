/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.cdn.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
@Table(name = "GASTO_MES")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "GastoMes.findAll", query = "SELECT g FROM GastoMes g")
	, @NamedQuery(name = "GastoMes.findById", query = "SELECT g FROM GastoMes g WHERE g.id = :id")
	, @NamedQuery(name = "GastoMes.findByIdCompra", query = "SELECT g FROM GastoMes g WHERE g.idCompra = :idCompra")
	, @NamedQuery(name = "GastoMes.findByImporte", query = "SELECT g FROM GastoMes g WHERE g.importe = :importe")
	, @NamedQuery(name = "GastoMes.findByFecha", query = "SELECT g FROM GastoMes g WHERE g.fecha = :fecha")
	, @NamedQuery(name = "GastoMes.findByMes", query = "SELECT g FROM GastoMes g WHERE g.mes = :mes")
	, @NamedQuery(name = "GastoMes.findByStatus", query = "SELECT g FROM GastoMes g WHERE g.status = :status")
	, @NamedQuery(name = "GastoMes.findByOrdenCompra", query = "SELECT g FROM GastoMes g WHERE g.ordenCompra = :ordenCompra")
	, @NamedQuery(name = "GastoMes.findByNumFac", query = "SELECT g FROM GastoMes g WHERE g.numFac = :numFac")
	, @NamedQuery(name = "GastoMes.findByAtributoPago", query = "SELECT g FROM GastoMes g WHERE g.atributoPago = :atributoPago")
	, @NamedQuery(name = "GastoMes.findByAsiento", query = "SELECT g FROM GastoMes g WHERE g.asiento = :asiento")
	, @NamedQuery(name = "GastoMes.findByTipo", query = "SELECT g FROM GastoMes g WHERE g.tipo = :tipo")
	, @NamedQuery(name = "GastoMes.findByRutProvedor", query = "SELECT g FROM GastoMes g WHERE g.rutProvedor = :rutProvedor")
	, @NamedQuery(name = "GastoMes.findByNombreProvedor", query = "SELECT g FROM GastoMes g WHERE g.nombreProvedor = :nombreProvedor")
	, @NamedQuery(name = "GastoMes.findByUnidadNegocio", query = "SELECT g FROM GastoMes g WHERE g.unidadNegocio = :unidadNegocio")
	, @NamedQuery(name = "GastoMes.findBySede", query = "SELECT g FROM GastoMes g WHERE g.sede = :sede")
	, @NamedQuery(name = "GastoMes.findByProducto", query = "SELECT g FROM GastoMes g WHERE g.producto = :producto")
	, @NamedQuery(name = "GastoMes.findByFilial", query = "SELECT g FROM GastoMes g WHERE g.filial = :filial")
	, @NamedQuery(name = "GastoMes.findByCuentaBanco", query = "SELECT g FROM GastoMes g WHERE g.cuentaBanco = :cuentaBanco")
	, @NamedQuery(name = "GastoMes.findByDescripcion", query = "SELECT g FROM GastoMes g WHERE g.descripcion = :descripcion")
	, @NamedQuery(name = "GastoMes.findByReferencia", query = "SELECT g FROM GastoMes g WHERE g.referencia = :referencia")
	, @NamedQuery(name = "GastoMes.findByEstado", query = "SELECT g FROM GastoMes g WHERE g.estado = :estado")
	, @NamedQuery(name = "GastoMes.findByOrigen", query = "SELECT g FROM GastoMes g WHERE g.origen = :origen")})
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
	@Column(name = "ID_COMPRA")
	private BigInteger idCompra;
	@Column(name = "IMPORTE")
	private BigInteger importe;
	@Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	@Column(name = "MES")
	private BigInteger mes;
	@Column(name = "STATUS")
	private Character status;
	@Size(max = 250)
    @Column(name = "ORDEN_COMPRA")
	private String ordenCompra;
	@Column(name = "NUM_FAC")
	private BigInteger numFac;
	@Size(max = 400)
    @Column(name = "ATRIBUTO_PAGO")
	private String atributoPago;
	@Size(max = 250)
    @Column(name = "ASIENTO")
	private String asiento;
	@Column(name = "TIPO")
	private Character tipo;
	@Size(max = 12)
    @Column(name = "RUT_PROVEDOR")
	private String rutProvedor;
	@Size(max = 250)
    @Column(name = "NOMBRE_PROVEDOR")
	private String nombreProvedor;
	@Size(max = 150)
    @Column(name = "UNIDAD_NEGOCIO")
	private String unidadNegocio;
	@Column(name = "SEDE")
	private BigInteger sede;
	@Size(max = 250)
    @Column(name = "PRODUCTO")
	private String producto;
	@Size(max = 200)
    @Column(name = "FILIAL")
	private String filial;
	@Size(max = 250)
    @Column(name = "CUENTA_BANCO")
	private String cuentaBanco;
	@Size(max = 300)
    @Column(name = "DESCRIPCION")
	private String descripcion;
	@Size(max = 200)
    @Column(name = "REFERENCIA")
	private String referencia;
	@Size(max = 200)
    @Column(name = "ESTADO")
	private String estado;
	@Size(max = 200)
    @Column(name = "ORIGEN")
	private String origen;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gastoMesId")
	private List<Homologar> homologarList;
	@JoinColumn(name = "ANHO_PROYECT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
	private AnhoProyect anhoProyectId;
	@JoinColumn(name = "GASTO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
	private Gasto gastoId;

	public GastoMes() {
	}

	public GastoMes(BigDecimal id) {
		this.id = id;
	}
        
        public static GastoMes findById(BigInteger id){
        GastoMes gasto;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try{
            TypedQuery<GastoMes> result = em.createNamedQuery("GastoMes.findById", GastoMes.class);
            result.setParameter("id", id);
            gasto = result.getSingleResult();
        }catch(NoResultException ex){
            gasto = null;
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        return gasto;
        }
        
        public void addGastoMes(){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            EntityTransaction trans = em.getTransaction();
            trans.begin();
            em.persist(this);
            trans.commit();
            em.close();
        }
        
        public void removeGastosMes(ArrayList<GastoMes> gastos){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            EntityTransaction trans = em.getTransaction();
            for(GastoMes gasto: gastos){
                GastoMes gastoMes = em.find(GastoMes.class, gasto.getId());
                trans.begin();
                em.remove(gastoMes);
                trans.commit();
            }
            em.close();
        }
        
        public static List<GastoMes> findGastos(BigInteger mes, AnhoProyect anho){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            TypedQuery<GastoMes> buscarGasto = em.createQuery("SELECT g FROM GastoMes g WHERE g.mes = :mes AND g.anhoProyectId = :anho", GastoMes.class);
            buscarGasto.setParameter("mes", mes);
            buscarGasto.setParameter("anho", anho);
            List<GastoMes> gastos = buscarGasto.getResultList();
            return gastos;
        }
        
        public void actualizarEstado(char estado){
            try{
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("CDNPU");
                EntityManager em = emf.createEntityManager();
                EntityTransaction trans = em.getTransaction();
                trans.begin();
                GastoMes gasto = em.merge(this);
                gasto.setStatus(estado);
                trans.commit();
                em.close();
            }catch(Exception ex){
                System.out.print(ex);
            }
        }
        
        public static boolean validaEstadoGastos(List<GastoMes> gastos){
            boolean estado = true;
            for (GastoMes gasto : gastos) {
                if(gasto.getStatus().equals('P')){
                    estado = false;
                }
            }
            return estado;
        }
        
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigInteger getMes() {
		return mes;
	}

	public void setMes(BigInteger mes) {
		this.mes = mes;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public BigInteger getNumFac() {
		return numFac;
	}

	public void setNumFac(BigInteger numFac) {
		this.numFac = numFac;
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

	public String getRutProvedor() {
		return rutProvedor;
	}

	public void setRutProvedor(String rutProvedor) {
		this.rutProvedor = rutProvedor;
	}

	public String getNombreProvedor() {
		return nombreProvedor;
	}

	public void setNombreProvedor(String nombreProvedor) {
		this.nombreProvedor = nombreProvedor;
	}

	public String getUnidadNegocio() {
		return unidadNegocio;
	}

	public void setUnidadNegocio(String unidadNegocio) {
		this.unidadNegocio = unidadNegocio;
	}

	public BigInteger getSede() {
		return sede;
	}

	public void setSede(BigInteger sede) {
		this.sede = sede;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getFilial() {
		return filial;
	}

	public void setFilial(String filial) {
		this.filial = filial;
	}

	public String getCuentaBanco() {
		return cuentaBanco;
	}

	public void setCuentaBanco(String cuentaBanco) {
		this.cuentaBanco = cuentaBanco;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	@XmlTransient
	public List<Homologar> getHomologarList() {
		return homologarList;
	}

	public void setHomologarList(List<Homologar> homologarList) {
		this.homologarList = homologarList;
	}

	public AnhoProyect getAnhoProyectId() {
		return anhoProyectId;
	}

	public void setAnhoProyectId(AnhoProyect anhoProyectId) {
		this.anhoProyectId = anhoProyectId;
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
