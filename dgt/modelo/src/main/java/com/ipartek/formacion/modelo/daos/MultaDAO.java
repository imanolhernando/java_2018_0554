package com.ipartek.formacion.modelo.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ipartek.formacion.modelo.cm.ConnectionManager;
import com.ipartek.formacion.modelo.pojo.Agente;
import com.ipartek.formacion.modelo.pojo.Coche;
import com.ipartek.formacion.modelo.pojo.Multa;
import com.ipartek.formacion.modelo.pojo.MultaCreada;

public class MultaDAO {
	
	private final static Logger LOG = Logger.getLogger(MultaDAO.class);
	private static MultaDAO INSTANCE = null;
	private boolean isGetById = false;
	private boolean isBaja = false;

	private static final String MULTAS_ANULADAS = "baja";
// private static final String MULTAS_ACTIVAS = "activas";

	private static final String SQL_GETBYID = "{call pa_multa_getById(?)}";
	private static final String SQL_GET_MULTAS_BY_ID_AGENTE = "{call pa_multa_getByAgenteId(?)}";
	private static final String SQL_GET_MULTAS_ANULADAS_BY_ID_AGENTE = "{call pa_multa_anulada_getByAgenteId(?)}";
	private static final String SQL_INSERT = "{call pa_multa_insert(?,?,?,?,?)}";
	private static final String SQL_ANULAR = "{call pa_multa_anular(?)}";
	private static final String SQL_ACTIVAR = "{call pa_multa_activar(?)}";

// constructor privado, solo acceso por getInstance()
	private MultaDAO() {
		super();
	}

// GET INSTANCE	
	public synchronized static MultaDAO getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new MultaDAO();
		}
		return INSTANCE;
	}

// OBTENER MULTA POR ID (sin usar)
	public Multa getById(long id, String opm) {

		Multa m = null;

		isGetById = true;

		try (Connection conn = ConnectionManager.getConnection();
				CallableStatement cs = conn.prepareCall(SQL_GETBYID);) {
			cs.setLong(1, id);

			if ("baja".equals(opm)) {
				isBaja = true;
			} else {
				isBaja = false;
			}

			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					m = rowMapper(rs);
				}
				LOG.debug("Multa encontrada");
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return m;
	}

// LISTAR MULTAS POR ID AGENTE
	public ArrayList<Multa> getMultasByIdAgente(long id) {

		ArrayList<Multa> multas = new ArrayList<Multa>();
		isGetById = false;
		try (Connection conn = ConnectionManager.getConnection();
				CallableStatement cs = conn.prepareCall(SQL_GET_MULTAS_BY_ID_AGENTE);) {

			cs.setLong(1, id);

			try (ResultSet rs = cs.executeQuery()) {
				isGetById = true;
				while (rs.next()) {
					try {
						multas.add(rowMapper(rs));
					} catch (Exception e) {
						LOG.error(e);
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}

		return multas;
	}

// LISTAR MULTAS ANULADAS  POR ID AGENTE
	public ArrayList<Multa> getMultasAnuladasByIdAgente(long id) {

		ArrayList<Multa> multas = new ArrayList<Multa>();
		isGetById = false;
		try (Connection conn = ConnectionManager.getConnection();
				CallableStatement cs = conn.prepareCall(SQL_GET_MULTAS_ANULADAS_BY_ID_AGENTE);) {

			cs.setLong(1, id);

			try (ResultSet rs = cs.executeQuery()) {
				isGetById = true;
				while (rs.next()) {
					try {
						multas.add(rowMapper(rs));
					} catch (Exception e) {
						LOG.error(e);
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}

		return multas;
	}

	// CREAR MULTA
	public boolean insert(MultaCreada m) throws SQLException {

		boolean resul = false;
		isGetById = false;
		try (Connection conn = ConnectionManager.getConnection();
				CallableStatement cs = conn.prepareCall(SQL_INSERT);) {

			cs.setFloat(1, m.getImporte());
			cs.setString(2, m.getConcepto());
			cs.setLong(3, m.getIdAgente());
			cs.setLong(4, m.getIdCoche());

			cs.registerOutParameter(5, Types.INTEGER);

			int affectedRows = cs.executeUpdate();
			if (affectedRows == 1) {
				m.setId(cs.getLong(5));
				resul = true;
			}

		} catch (Exception e) {
			LOG.error(e);
		}
		return resul;

	}

	//ANULAR MULTA UPDATE..  ANULAR Y ACTIVAR SEGUN OPERACION
	public boolean anular(int id, int op) throws SQLException {

		boolean resul = false;
		isBaja = true;
		isGetById = false;
		
		if (op==1) {
			try (Connection conn = ConnectionManager.getConnection();
					CallableStatement cs = conn.prepareCall(SQL_ANULAR);) {

				cs.setLong(1, id);
			
				int affectedRows = cs.executeUpdate();
				if (affectedRows == 1) {
					resul = true;
				}
			}
		}else {
			try (Connection conn = ConnectionManager.getConnection();
					CallableStatement cs = conn.prepareCall(SQL_ACTIVAR);) {

				cs.setLong(1, id);
			
				int affectedRows = cs.executeUpdate();
				if (affectedRows == 1) {
					resul = true;
				}
			}
		}
		
		return resul;

	}

// ROWMAPPER PARA PARAMETROS DE LA BBDD
	private Multa rowMapper(ResultSet rs) throws SQLException {
		Multa m = new Multa();
		Coche c = new Coche();
		Agente a = new Agente();
		Timestamp timestampalta = rs.getTimestamp("fecha_alta");
		m.setFechaAlta(new java.util.Date(timestampalta.getTime()));
		if (isBaja) {
			Timestamp timestampbaja = rs.getTimestamp("fecha_baja");
			if (timestampbaja == null) {
				m.setFechaBaja(null);
			} else {
				m.setFechaBaja(new java.util.Date(timestampbaja.getTime()));
			}

		}

		if (isGetById) {
			try {
				m.setImporte(rs.getDouble("importe"));
				m.setConcepto(rs.getString("concepto"));
				c.setId(rs.getLong("id_coche"));
				c.setModelo(rs.getString("modelo"));
				c.setKm(rs.getInt("km"));
				m.setId(rs.getLong("id"));
				c.setMatricula(rs.getString("matricula"));
				a.setPlaca(rs.getString("placa"));
				a.setNombre(rs.getString("nombre"));
				a.setPassword(rs.getString("password"));
				a.setImagen(rs.getString("imagen"));
			} catch (Exception e) {
				LOG.error(e);

			}
		}
		m.setCoche(c);
		m.setAgente(a);
		return m;
	}

}
