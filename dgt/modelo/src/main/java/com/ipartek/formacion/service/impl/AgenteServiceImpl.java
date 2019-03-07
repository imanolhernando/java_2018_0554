package com.ipartek.formacion.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ipartek.formacion.modelo.daos.CocheDAO;
import com.ipartek.formacion.modelo.daos.LoginDAO;
import com.ipartek.formacion.modelo.daos.MultaDAO;
import com.ipartek.formacion.modelo.pojo.Agente;
import com.ipartek.formacion.modelo.pojo.Coche;
import com.ipartek.formacion.modelo.pojo.Multa;
import com.ipartek.formacion.modelo.pojo.MultaCreada;
import com.ipartek.formacion.service.AgenteService;

public class AgenteServiceImpl implements AgenteService {

	private final static Logger LOG = Logger.getLogger(MultaDAO.class);
	private static MultaDAO multaDAO;
	private static CocheDAO cocheDAO;
	private static LoginDAO loginDAO;
	private static AgenteServiceImpl INSTANCE = null;

	private AgenteServiceImpl() {
		super();
		multaDAO = MultaDAO.getInstance();
		cocheDAO = CocheDAO.getInstance();
		loginDAO = LoginDAO.getInstance();

	}

	public static synchronized AgenteServiceImpl getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AgenteServiceImpl();
		}
		return INSTANCE;
	}

//LOGIN AGENTE GET
	@Override
	public Agente existe(String placa, String password) {

		return loginDAO.login(placa, password);
	}

// GET ALL MULTAS BY ID AGENTE
	@Override
	public List<Multa> listarMultas(int id) {
		return multaDAO.getMultasByIdAgente(id);
	}

// GET ALL MULTAS anuladas BY ID AGENTE
	@Override
	public List<Multa> listarMultasAnuladas(int id) {
		return multaDAO.getMultasAnuladasByIdAgente(id);
	}

//BUSCAR POR MATRICULA
	@Override
	public Coche buscarMatricula(String matricula) {
		return cocheDAO.getByMatricula(matricula);
	}

	@Override
	public Coche conseguirId(String matricula) {
		// TODO Auto-generated method stub
		return null;
	}

// CREAR MULTA POST
	@Override
	public boolean multar(MultaCreada multa) {
		boolean isCreado = false;
		try {
			isCreado = multaDAO.insert(multa);

			// multa.setTipo(tipoDAO.getByIdTipoAnimal(animal.getId()));
			// animal.setDieta(dietaDAO.getByIdDietaAnimal(animal.getId()));
		} catch (SQLException e) {
			LOG.error(e);
		}
		return isCreado;
	}

// ANULAR MULTA PATCH
	@Override
	public boolean anular(int id, int op) {
		boolean isAnulado = false;
		try {
			isAnulado = multaDAO.anular(id, op);

			// multa.setTipo(tipoDAO.getByIdTipoAnimal(animal.getId()));
			// animal.setDieta(dietaDAO.getByIdDietaAnimal(animal.getId()));
		} catch (SQLException e) {
			LOG.error(e);
		}
		return isAnulado;
	}

}