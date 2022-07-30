package com.gestion.empleados.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestion.empleados.entities.Empleado;
import com.gestion.empleados.services.IEmpleadoService;
import com.gestion.empleados.util.pagination.PageRender;

@Controller
public class EmpleadoController {

	@Autowired
	private IEmpleadoService empleadoService;
	
	@GetMapping({"/","/listar",""})
	public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Empleado> empleados = empleadoService.findAll(pageRequest);
		PageRender<Empleado> pageRender = new PageRender<>("/listar", empleados);
		
		model.addAttribute("titulo", "Listado de Empleados");
		model.addAttribute("empleados", empleados);
		model.addAttribute("page", pageRender); 
		
		return "listar";
	}
}
