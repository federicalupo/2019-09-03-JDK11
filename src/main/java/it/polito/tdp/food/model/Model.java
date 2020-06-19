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
}
