package com.gestion.empleados.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.gestion.empleados.entities.Empleado;

public interface IEmpleadoRepository extends PagingAndSortingRepository<Empleado, Long>{

}
