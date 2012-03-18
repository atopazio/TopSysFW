package br.com.topsys.constant;

public final class TSConstant {
	private TSConstant() {
	}

	/*
	 * Constantes usadas no arquivo sql.properties para referenciar o JNDI para
	 * acessar o Banco de Dados
	 */
	public static final String JNDI_CONNECTION = "JNDI.CONNECTION";

	public static final String CLASS_BROKER_IMPL = "CLASS.BROKER.IMPL";

	public static final String CLASS_PERSISTENCE_IMPL = "CLASS.BROKER.IMPL";

	public static final String CLASS_SEQUENCE_IMPL = "CLASS.SEQUENCE.IMPL";

	public final static String DATA_INVALIDA = "DATA.INVALIDA";

	public final static String EMAIL_INVALIDO = "EMAIL.INVALIDO";

	public final static String PERIODO_DATAS_INVALIDAS = "PERIODO.DATAS.INVALIDAS";

	public final static String CAMPO_OBRIGATORIO = "CAMPO.OBRIGATORIO";

	public final static String MENSAGEM_UNIQUE = "MENSAGEM.UNIQUE";

	public final static String MENSAGEM_FOREIGNKEY = "MENSAGEM.FOREIGNKEY";

	public final static String MENSAGEM_SELECIONAR_REGISTRO = "MENSAGEM.SELECIONAR.REGISTRO";

	public final static String CONFIRMACAO_EDITADO = "CONFIRMACAO.EDITADO";

	public final static String CONFIRMACAO_CADASTRO = "CONFIRMACAO.CADASTRO";

	public final static String CONFIRMACAO_EXCLUSAO = "CONFIRMACAO.EXCLUSAO";

	public final static String MENSAGEM_FILE_NOT_FOUND = "MENSAGEM.FILE.NOT.FOUND";

	public final static String MENSAGEM_ERRO_ENTRADA_SAIDA = "MENSAGEM.ERRO.ENTRADA.SAIDA";

	public static final String MENSAGEM_RAISE = "MENSAGEM.RAISE";

	/* */
	public static final String TRATAMENTO_ERRO = "TRATAMENTO.ERRO";

	/* Colocar no struts-config.xml para ser direcionado para tela de erro. */
	public static final String ERRO = "ERRO";

	/*
	 * Constantes usadas no arquivo sql.properties identificando o codigo de
	 * erro gerado pelo banco de dados
	 */
	public final static String CODIGO_UNIQUE = "CODIGO.UNIQUE";

	public final static String CODIGO_FOREIGNKEY = "CODIGO.FOREIGNKEY";

	public static final String CODIGO_RAISE_EXCEPTION = "CODIGO.RAISE.EXCEPTION";

	public static final String CHARSET_ISO = "ISO-8859-1";

	public static final String CHARSET_UTF = "UTF-8";

	public static final String CRIPTOGRAFIA_MD5 = "md5";

	public static final String MIME_TYPE_HTML = "text/html";

	public static final String MIME_TYPE_TXT = "text/plain";

}