package com.ipartek.formacion.dgt.api;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.formacion.modelo.daos.CocheDAO;
import com.ipartek.formacion.modelo.pojo.Coche;

@RestController
public class VehiculoController {

	private final static Logger LOG = Logger.getLogger(VehiculoController.class);
	private static CocheDAO cocheDAO;
	
	public VehiculoController() {
		super();
		cocheDAO = CocheDAO.getInstance();
	}
	
	
	@RequestMapping( value= {"/api/vehiculo"}, method = RequestMethod.GET)
	public ArrayList<Coche> listar(){		
		
		ArrayList<Coche> coches = new ArrayList<Coche>();
		coches.add( new Coche( 1L, "BI000CM", "Fiat", 34555 ) );
		coches.add( new Coche( 2L, "ZA000HK", "Flagoneta", 34555 ) );
		
		return coches;
	}
	
	
	
	
}
