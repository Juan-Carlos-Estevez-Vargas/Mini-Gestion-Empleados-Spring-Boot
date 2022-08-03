package com.gestion.empleados.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gestion.empleados.entities.Empleado;

public interface IEmpleadoService {

	/**
	 * Busca el listado de empleados presentes en la base de datos.
	 * 
	 * @return listado con los empleados o registros encontrados.
	 */
	public List<Empleado> findAll();

	/**
	 * Busca el listado de empleados presentes en la base de datos.
	 * 
	 * @param pageable
	 * @return página con los registros encontrados.
	 */
	public Page<Empleado> findAll(Pageable pageable);

	/**
	 * Inserta un nuevo empleado a la base de datos.
	 * 
	 * @param empleado a insertar en la base de datos.
	 */
	public void save(Empleado empleado);

	/**
	 * Busca un empleado en específico.
	 * 
	 * @param id por el cual se buscará el empleado.
	 * @return empleado encontrado.
	 */
	public Empleado findOne(Long id);

	/**
	 * Elimina un empleado del aplicativo.
	 * 
	 * @param id por el cuál se eliminará el registro (empleado).
	 */
	public void delete(Long id);

}
