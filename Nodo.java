import java.util.ArrayList;

/*
 * La classe Nodo rappresenta un'implementazione di un nodo del grafo.
 * Possiede i seguenti variabili:
 * nome: il nome del nodo, deve essere una stringa di caratteri alfanumerici
 * adj: una lista che contiene i nodi adiacenti ad esso
 * colore: assume il valore del colore durante le visite BFS e DFS;
 * predecessore: assume il nome del nodo predecessore durante le visite DFS e BSF
 * distanza: rappresenta la distanza dalla radice dell'albero dei cammini minimi
 * deg: il grado di raggiungibilità del nodo; il numero di nodi che si possono raggiungere
 * effetuando una visita a partire dal nodo    
 */
public class Nodo {
	private String nome="";
	private ArrayList<Nodo> adj = new ArrayList<Nodo>();
	private String colore="";
	private String predecessore="";
	private int distanza;
	private int deg;
	
	/*
	 *Il costruttore di default 
	 */
	public Nodo() {
	}
	    
	/*
	 * Costruttore della classe Nodo 
	 * @param s: il nome del nuovo nodo che viene costruito
	 */
	public Nodo(String s){ 
		this.nome=s;
    }
	    
	
	/*
	 * Metodo che mi restituisce la lista di adiacenza di this    
	 * @return adj: la lista di adiacenza di this
	 */
	public ArrayList<Nodo> get_adj() {
		return this.adj;
	}
	
	
	/*
	 * Metodo che mi restituisce il nome di this
	 * @return nome: il nome di this
	 */
	public String getNome(){
		return this.nome;
	}
	
	/*
	 * Metodo che aggiunge un nuovo nodo alla lista di adiacenza di this
	 * @param j: il nodo che va aggiunto alla lista di adiacenza di this
	 */
	public void aggiungi_nodo(Nodo j) {
		this.adj.add(j);   
	    }
	
	
	/*
	 * Metodo che cancella un nodo dalla lista di adiacenza di this
	 * param i: il nodo che va cancellato da this
	 */
	public void delete_nodo(Nodo i){
		for(int o=0; o<this.adj.size(); o++) {
			if(this.adj.get(o).getNome().equals(i.getNome())) {
				this.adj.remove(o);
				break;
			}
		}
	}
	
	/*
	 * Metodo che restituisce il grado di raggiungibilità di this
	 * @return deg: il grado di this
	 */
	public int get_deg(){
		return this.deg;
	}
	
	/*
	 * Metodo che imposta il grado di raggiungibilità di this
	 * @param n: il grado che va assegnato a this
	 */
	public void set_deg(int n){
		this.deg=n;
	}
	
	/*
	 * Metodo che restituisce il valore che indica la distanza di this dalla radice
	 * @return distanza: la distanza di this dalla radice  
	 */
	public int get_distanza(){
		return	this.distanza;
	}
	
	/*
	 * Metodo che imposta la distanza di this dalla radice
	 * @param n: il valore che va assegnato a this 
	 */
	public void set_distanza(int n){
		this.distanza=n;
	}
	
	/*
	 * Metodo che imposta il colore di this
	 * @param s: il colore che va assegnato a this
	 */
	public void set_colore(String s){
		this.colore=s;
	}
	
	/*
	 * Metodo che restitusce il colore di this
	 * @return colore: il colore di this
	 */
	public String get_colore(){
		return this.colore;
	}
	
	/*
	 * Metodo che imposta il predecessore di this
	 * @param s: il del predecessore che va assegnato a this
	 */
	public void set_predecessore(String s){
		this.predecessore=s;
	}
	
	/*
	 * Metodo che restituisce il predecessore di this
	 * @return predecessore: il predecessore di this 
	 */
	public String get_predecessore(){
		return this.predecessore;
	}
	
	/*
	 * Metodo che imposta la lista di adiacenza a this
	 * @param vl: la lista di adiacenza che va assegnata a this
	 */
	public void set_lista_adj(ArrayList<Nodo> vl) {
		this.adj = vl;
	}
}
