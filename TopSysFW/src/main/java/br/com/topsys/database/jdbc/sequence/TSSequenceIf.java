package br.com.topsys.database.jdbc.sequence;

public interface TSSequenceIf {
	public Long getNextValue(String nomeSequence);
	public Long getCurrentValue(String nomeSequence);
	
}
