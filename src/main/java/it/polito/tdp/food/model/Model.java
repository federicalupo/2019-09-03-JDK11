package it.polito.tdp.food.model;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	private Graph<String, DefaultWeightedEdge > grafo;
	private Integer cont;
	private List<String> migliore;
	private Integer pesoMax;
	
	public Model() {
		dao = new FoodDao();
		
	}
	
	
	public void creaGrafo(Integer calorie) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, dao.listaVertici(calorie));
		
		//archi:
		//potrei trovare tutte coppie e aggiungere solo quelle per cui i tipi sono entrambi presenti oppure 
		//imposto nuovamente il limite delle calorie
		
		for(Arco a:dao.archi(calorie)) {
			Graphs.addEdge(this.grafo, a.getPorzione1(), a.getPorzione2(), a.getPeso());
		}
	}
	
	public List<String> vertici(){
		List<String> vertici = new LinkedList<>(this.grafo.vertexSet());
		return vertici;
	}
	
	public Integer nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Arco> listaCollegati(String porzione){
		List<Arco> collegati = new LinkedList<>();
		
		for(String s: Graphs.neighborListOf(this.grafo, porzione)) {
			
			Arco a = new Arco(porzione, s, (int)this.grafo.getEdgeWeight(this.grafo.getEdge(porzione, s)));
			collegati.add(a);
		}
		
		
		return collegati;
		
	}
	
	/**
	 * parto da sorgente, 
	 * mi faccio dare i vicini,
	 * vado in ognuno e conto i passi
	 * chiamo ricorsiva
	 * 
	 * se conteggio == passi => controllo se è max e salvo
	 * 
	 * backtracking 
	 * 
	 * @param sorgente
	 * @param passi
	 */
	
	public List<String> cercaCammino(String sorgente, Integer passi) {
		List<String> parziale = new LinkedList<>();
		Integer peso=0;
		
		this.cont=0;
		this.pesoMax=-1;
		this.migliore = new LinkedList<>();
		
		parziale.add(sorgente);
		
		ricorsiva(sorgente, passi, parziale, peso, cont);
		
		return migliore; //controllo se è vuota
	}
	private void ricorsiva(String sorgente, Integer passi, List<String> parziale, Integer peso, Integer cont) {
		
		if(cont.compareTo(passi)==0) {
			if(peso > pesoMax) {
				pesoMax = peso;
				this.migliore = new LinkedList<>(parziale);
			}
			return;
		}
		
		for(String s : Graphs.neighborListOf(this.grafo, sorgente)) {
			if(!parziale.contains(s)) { //evitare cicli
				parziale.add(s);
				
				//cont++; //non va bene!!! devo passarlo come parametro
				
				peso += (int)this.grafo.getEdgeWeight(this.grafo.getEdge(sorgente, s));
				
				ricorsiva(s, passi, parziale, peso, cont+1); //cont+1, non ++
				
				parziale.remove(s);
				
			}
		}
		
	}
	
	public Integer pesoMax() {
		return pesoMax;
	}
}
