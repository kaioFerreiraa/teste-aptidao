package br.com.teste.exception;

public class RegraNegocioException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
