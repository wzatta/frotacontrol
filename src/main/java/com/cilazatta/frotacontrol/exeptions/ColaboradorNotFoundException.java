package com.cilazatta.frotacontrol.exeptions;

public class ColaboradorNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ColaboradorNotFoundException(Long id) {
        super("Colaborador não encontrado com o ID: " + id);
    }
}
