package br.com.topsys.exception;

public class TSMessageBusinessException extends TSBusinessException{

	public TSMessageBusinessException(Exception e) {
		super(e);
		// TODO Auto-generated constructor stub
	}
	
	
	public TSMessageBusinessException(String mensagem) {
		super(mensagem);
		
		
	}
	
	public TSMessageBusinessException(String mensagem,Exception e) {
		super(mensagem,e);
	}
	

}
