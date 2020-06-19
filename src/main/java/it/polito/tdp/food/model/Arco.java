package it.polito.tdp.food.model;

public class Arco {
	private String porzione1;
	private String porzione2;
	private Integer peso;
	
	public Arco(String porzione1, String porzione2, Integer peso) {
		super();
		this.porzione1 = porzione1;
		this.porzione2 = porzione2;
		this.peso = peso;
	}

	public String getPorzione1() {
		return porzione1;
	}

	public String getPorzione2() {
		return porzione2;
	}

	public Integer getPeso() {
		return peso;
	}

	@Override
	public String toString() {
		return  porzione1 + " " + porzione2 + " " + peso ;
	}
	
	

}
