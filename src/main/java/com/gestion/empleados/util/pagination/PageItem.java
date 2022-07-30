package com.gestion.empleados.util.pagination;

import lombok.Data;

@Data
public class PageItem {
	
	private int numero;
	private boolean actual;
	
	public PageItem(int numero, boolean actual) {
		super();
		this.numero = numero;
		this.actual = actual;
	}
	
}
