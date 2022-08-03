package com.gestion.empleados.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.empleados.entities.Empleado;
import com.gestion.empleados.services.IEmpleadoService;
import com.gestion.empleados.util.pagination.PageRender;
import com.gestion.empleados.util.reportes.EmpleadoExporterExcel;
import com.gestion.empleados.util.reportes.EmpleadoExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class EmpleadoController {

	@Autowired
	private IEmpleadoService empleadoService;

	/**
	 * Muestra los detalles o información de un empleado en específico.
	 * 
	 * @param id     del empleado a consultar sus detalles.
	 * @param modelo
	 * @param flash
	 * @return template "ver.html" con la información (detalles) del empleado.
	 */
	@GetMapping("/ver/{id}")
	public String verDetallesDelEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
			RedirectAttributes flash) {
		Empleado empleado = empleadoService.findOne(id);
		if (empleado == null) {
			flash.addFlashAttribute("error", "El empleado no existe en la base de datos.");
			return "redirect:/listar";
		}
		modelo.put("empleado", empleado);
		modelo.put("titulo", "Detalles del empleado " + empleado.getNombre());
		return "ver";
	}

	/**
	 * Página principal del aplicativo, la cual se encarga de mostrar el listado de
	 * los empleados registrados en el sistema junto con las acciones a realizar.
	 * 
	 * @param page  página a mostrar (esto corresponde a la paginación).
	 * @param model
	 * @return template "listar.html" la cuál muestra el listado de los empleados
	 *         presentes en el sistema.
	 */
	@GetMapping({ "/", "/listar", "" })
	public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Empleado> empleados = empleadoService.findAll(pageRequest);
		PageRender<Empleado> pageRender = new PageRender<>("/listar", empleados);

		model.addAttribute("titulo", "Listado de Empleados");
		model.addAttribute("empleados", empleados);
		model.addAttribute("page", pageRender);

		return "listar";
	}

	/**
	 * Redirecciona a la interfaz para agregar un nuevo empleado al sistema.
	 * 
	 * @param modelo
	 * @return template "form.html" el cual permite ingresar de manera gráfica un
	 *         nuevo empleado al sistema.
	 */
	@GetMapping("/form")
	public String mostrarFormularioDeRegistrarCliente(Map<String, Object> modelo) {
		Empleado empleado = new Empleado();
		modelo.put("empleado", empleado);
		modelo.put("titulo", "Registro de empleados.");
		return "form";
	}

	/**
	 * Se encarga de hacer efectiva la inserción del empleado al sistema; esto se
	 * realiza gracias al envío de datos por el método POST.
	 * 
	 * @param empleado a insertar en el aplicativo.
	 * @param result   objeto que genera resultado de si hubo o no errores al hacer
	 *                 la inserción del empleado.
	 * @param model
	 * @param flash
	 * @param status   estado de la petición HTTP.
	 * @return redirecciona a la página principal del aplicativo "listar.html".
	 */
	@PostMapping("/form")
	public String guardarEmpleado(@Valid Empleado empleado, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Registro de Empleado");
			return "form";
		}

		String mensaje = (empleado.getId() != null) ? "El empleado ha sido editado con éxito"
				: "Empleado registrado con éxito.";
		empleadoService.save(empleado);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/listar";
	}

	/**
	 * Busca un empleado en el aplicativo y redirecciona a la página para ver los
	 * detalles o información de dicho empleado.
	 * 
	 * @param id     por el cuál se buscará el empleado.
	 * @param modelo
	 * @param flash
	 * @return template "form.html" el cual renderiza la información del empleado
	 *         encontrado.
	 */
	@GetMapping("/form/{id}")
	public String editarEmpleado(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
			RedirectAttributes flash) {
		Empleado empleado = null;

		if (id > 0) {
			empleado = empleadoService.findOne(id);
			if (empleado == null) {
				flash.addFlashAttribute("error", "El id del empleado no existe en la base de datos.");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El id del empleado no puede ser 0.");
			return "redirect:/listar";
		}

		modelo.put("empleado", empleado);
		modelo.put("titulo", "Edición de empleados.");
		return "form";
	}

	/**
	 * Busca y elimina un empleado del aplicativo.
	 * 
	 * @param id    por el cual se eliminará el empleado.
	 * @param flash
	 * @return redirección a la pagina principal ("listar.html") en caso de que todo
	 *         haya salido perfecto.
	 */
	@GetMapping("/eliminar/{id}")
	public String eliminarEmpleado(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			empleadoService.delete(id);
			flash.addFlashAttribute("success", "Empleado eliminado con éxito");
		}
		return "redirect:/listar";
	}

	/**
	 * Genera el reporte de los empleado en formato PDF.
	 * 
	 * @param response respuesta del servidor.
	 * @throws DocumentException
	 * @throws IOException
	 */
	@GetMapping("/exportarPDF")
	public void exportarListadoDeEmpleadosPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());

		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".pdf";

		response.setHeader(cabecera, valor);

		List<Empleado> empleados = empleadoService.findAll();

		EmpleadoExporterPDF exporter = new EmpleadoExporterPDF(empleados);
		exporter.exportar(response);
	}

	/**
	 * Genera el reporte de los empleado en formato Excel.
	 * 
	 * @param response respuesta del servidor.
	 * @throws DocumentException
	 * @throws IOException
	 */
	@GetMapping("/exportarExcel")
	public void exportarListadoDeEmpleadosExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());

		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".xlsx";

		response.setHeader(cabecera, valor);

		List<Empleado> empleados = empleadoService.findAll();

		EmpleadoExporterExcel exporter = new EmpleadoExporterExcel(empleados);
		exporter.exportar(response);
	}

}
