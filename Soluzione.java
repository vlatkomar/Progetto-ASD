
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.regex.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Soluzione {
	InputStreamReader istream = new InputStreamReader(System.in) ;
	BufferedReader bufRead = new BufferedReader(istream) ;
        static Scanner sc;
        static String nome;                  //contiene il nome del grafo 
	static ArrayList<Nodo> sd = new ArrayList<Nodo>();              //la struttura dati principale 
	static Nodo max;
	static	ArrayList<Nodo> Nuovi = new ArrayList<Nodo>();

	/*
	 * il metodo main legge un file dallo standard input, 
	 * risolve il problema 
         * e scrive sullo standard output
	 */
	public static void main (String[] args){
		String[] s = new String[10000000];
		int i1=0;
		sc = new Scanner(System.in);
		while(sc.hasNext()){
			s[i1] = sc.nextLine();
			readTokens(comprimiStringa(s[i1]));
			i1++;
		}
		solve();             // metodo principale che risolve il problema
		stampa();            // metodo per scrivere sullo standard output
	}	
	

	/*
	 * readTokens è un metodo che legge la stringa passata in input, riconosce i nodi usando le espressini regolari,
	 * li salva sulla struttura dati principale sd e il nome del grafo nella variabile nome
	 * @param line; una linea del file passato in input
	 */
	public static void readTokens(String line){
		String pattern = "(([a-zA-Z0-9](\\s*)+->(\\s*)[a-zA-Z0-9])|(([a-zA-Z0-9]*)));";	
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(line);
		
		String pattern2 = "(digraph(\\s)*[a-zA-Z0-9]*)";
		Pattern p2 = Pattern.compile(pattern2);
		Matcher m2 = p2.matcher(line);
		if(m2.find()){
			nome = line.substring(7);
		}
		if(m.find()){
			String pom[] = line.split("->");
			String pom1 = pom[0];
			pom1 = pom1.replace(";", "");	
			String pom2="";
			if(pom.length==2) {
				pom2 = pom[1];
				pom2 = pom2.replace(";", "");	
				Nodo a = new Nodo(pom1);
				if(!(contiene(a))){
					sd.add(a);
				} 
				a = getNodo(pom1);	
				Nodo b = new Nodo(pom2);
				if(!(contiene(b))){
					sd.add(b);
				}
				b = getNodo(pom2);
				a.aggiungi_nodo(b);				
			} else {
				Nodo a = new Nodo(pom1);
				sd.add(a);
			}
		}		
	}

	/*
	 * getNodo prende in input un nome di un nodo e restituisce il nodo che ha quello nome 
	 * @param s: il nome del nodo passato in input
	 * @return Nodo: il nodo che ha lo stesso nome come s  
	 */
	public static Nodo getNodo(String s){
		String a;
		int i=0;
		while( i<sd.size()){
			a = sd.get(i).getNome();
			if(s.equals(a)){
				break;
			}
			i++;
		}
		return sd.get(i);
	}

	/*
	 * contiene controlla se esiste un nodo nella struttura dati sd 
	 * @param a: il nodo ricercato nella struttura sd 
	 * @return boolean: restituisce vero se lo trova, altrimenti falso
	 */
	public static boolean contiene(Nodo a){
		for(int i=0; i<sd.size(); i++){
			if(a.getNome().equals(sd.get(i).getNome())){
				return true;
			}
		}
		return false;
	}

	/*
	 * comprimiStringa prende in input una stringa, leva i caratteri blank e restituisce una nuova stringa compressa   
	 * @param s: la stringa in input
	 * @return r: la stringa dopo le modifiche
	 */
	public static String comprimiStringa(String s){
		String r = "";
		for(int i=0; i<s.length(); i++){
			char k = s.charAt(i);
			if((k != ' ') && (k != '\n') && (k != '\t') && (k != '\r')){
				r = r + k;
			}
		}
		return r;
	}

		
	/*
	 * La visita DFS modificata rispetto alla versione degli appunti delle lezioni;
	 * va eseguita solo a partire a un nodo;
	 * @param s: il nodo da quale parte la visita
	 * @param sd: la struttura dati su quale vanno salvate le modifiche
	 */
	public static void DFS(ArrayList<Nodo> sd, Nodo s){
		for(int i=0; i<sd.size(); i++){
			sd.get(i).set_colore("Bianco");
			sd.get(i).set_predecessore("NIL");
			for(int k=0; k<sd.get(i).get_adj().size(); k++){
				sd.get(i).get_adj().get(k).set_colore("Bianco");
				sd.get(i).get_adj().get(k).set_predecessore("NIL");
			}
		}
		s.set_colore("Bianco");
		s.set_predecessore("NIL");
		DFS_visit(sd, s);
	}


	
	/*
	 * DFS_visit: versione cambiata rispetto alla versione vista a lezione:
	 * se trova un ciclo (un arco da un nodo grigio verso un altro nodo grigio) lo rimuove
	 * @param sd: la struttura dati che contiene i nodi
	 * @param j: il nodo da cui parte la visita
	 */
	public static void DFS_visit(ArrayList<Nodo> sd, Nodo j){
		j.set_colore("Grigio");
			for(int n=0; n<j.get_adj().size(); n++){
				Nodo p = j.get_adj().get(n);
				if(p.get_colore().equals("Grigio") && (!p.get_predecessore().equals(j.get_predecessore()))){
					j.delete_nodo(p);
					n--;
					
				}
				else if(p.get_colore().equals("Bianco")){
					p.set_predecessore("nodo "+j.getNome());
					DFS_visit(sd, p);
				}
			}
		j.set_colore("Nero");	
		return ;
	}

	/*
	 * La visita BFS vista a lezione 
	 * @param sd: la struttura dati su cui vanno salvati i dati della visita
	 * @param s: il nodo da cui parte la visita
	 * 
	 */
	public static void BFS(ArrayList<Nodo> sd, Nodo s){
		for(int i=0; i<sd.size(); i++){
			sd.get(i).set_colore("Bianco");
			sd.get(i).set_predecessore("NIL");
			sd.get(i).set_distanza(99999999);
		}	
		Queue<Nodo> Q = new LinkedList<Nodo>();
	        s.set_colore("Grigio");
		s.set_distanza(0);
		s.set_predecessore("NIL");
		Q.add(s);
		while(!(Q.isEmpty())){
			Nodo u =Q.poll() ;
			for(int i=0; i<u.get_adj().size(); i++){
				Nodo v = u.get_adj().get(i);
				if(v.get_colore().equals("Bianco")){
					v.set_colore("Grigio");
					v.set_predecessore(u.getNome());
					v.set_distanza(u.get_distanza()+1);
					Q.add(v);
				}
			}
			u.set_colore("Nero");
			Q.remove(u);
		}
		
	}
	
	/*
	 * BFS_mod esegue la visita BFS sul grafo da un dato nodo e dopo 
	 * calcola il numero di nodi che si possono raggiungere a partire da quel nodo in input 
	 * e alla fine lo salva nel parametro degree
	 * @param s: il nodo da cui parte la BFS
	 * @sd: la struttura dati su quale vanno eseguite le modifiche 
	 */
	public static void BFS_mod(ArrayList<Nodo> sd, Nodo s){
		for(int i=0; i<sd.size(); i++){
			sd.get(i).set_colore("Bianco");
			sd.get(i).set_predecessore("NIL");
			sd.get(i).set_distanza(99999999);
		}	
		Queue<Nodo> Q = new LinkedList<Nodo>();
	        s.set_colore("Grigio");
		s.set_distanza(0);
		s.set_predecessore("NIL");
		Q.add(s);
		while(!(Q.isEmpty())){
			Nodo u =Q.poll() ;
			for(int i=0; i<u.get_adj().size(); i++){
				Nodo v = u.get_adj().get(i);
				if(v.get_colore().equals("Bianco")){
					v.set_colore("Grigio");
					v.set_predecessore(u.getNome());
					v.set_distanza(u.get_distanza()+1);
					Q.add(v);
				}
			}
			u.set_colore("Nero");
			Q.remove(u);
		}
		int degree=0;
		for(int i=0; i<sd.size(); i++){
			if(sd.get(i).get_colore().equals("Nero")){
				degree++;
			}
		}
		degree--;
		s.set_deg(degree);
	}

	/*
	 * getMaxDeg2 esegue |V| volte la visita BFS_mod e restitusce il nodo con il degree massimo
	 * ovvero il nodo a partire da quale si possono raggingere più nodi, 
	 * questa versione di getMaxDeg lavora sulla struttura dati passata in input sd2
	 * 
	 */
	private static Nodo getMaxDeg2(ArrayList<Nodo> sd2 ){
		for(int i=0; i<sd2.size();i++){
			BFS_mod(sd2, sd2.get(i));
		}
		int max=0;
		for(int i=0; i<sd2.size(); i++){
				if(max<sd2.get(i).get_deg()){
					max=sd2.get(i).get_deg();
			}
		}
		Nodo MaxNodo=null;
		for(int i=0; i<sd2.size(); i++){
			if(sd2.get(i).get_deg()==max){
				MaxNodo=sd2.get(i);
				break;
			}
		}
		return MaxNodo;
	}
	
	/*
	 * getMaxDeg esegue |V| volte la visita BFS e restituisce il nodo con il degree massimo
	 * il nodo restituito sarà poi la radice dell'albero dei cammini minimi
	 * --lavora sulla struttura dati principale sd
	 */
	private static Nodo getMaxDeg() {
		for(int i=0; i<sd.size(); i++){
			BFS_mod(sd, sd.get(i));
			
		}
		int max=0;
		for(int i=0; i<sd.size(); i++){
			if(max<sd.get(i).get_deg()){
				max=sd.get(i).get_deg();
			}
		}
		Nodo MaxNodo=null;
		for(int i=0; i<sd.size(); i++){
			if(sd.get(i).get_deg()==max){
				MaxNodo=sd.get(i);
				break;
			}
		}
		return MaxNodo;
	}
	
	/*
	 * esisteBianco scandisce tutti i nodi della struttura dati sd 
	 * e controlla se ci sono nodi non esplorati 
	 * @param halt: restituisce vero se c'e' almeno un nodo bianco, altrimenti falso 
	 */
	public static boolean esisteBianco(){
		boolean halt=false; 
		for(int i=0; i<sd.size();i++){
			if(sd.get(i).get_colore().equals("Bianco")){
				halt = true;
				break;
			}
		}
		return halt;
	}
	
	/*
         * solve risolve il problema principale del progetto
         * 
         */ 
	public static void solve(){
	        max = getMaxDeg();
		DFS(sd, max);
		while(esisteBianco()){
			ArrayList<Nodo> sd2 = new ArrayList<Nodo>();
			for(int k=0; k<sd.size();k++){
				if(sd.get(k).get_colore().equals("Bianco")){
					sd2.add(sd.get(k));     
				}
			}
			Nodo max2 = getMaxDeg2(sd2);   
			Nuovi.add(max2);
			max.aggiungi_nodo(max2);
			DFS(sd,max);
			BFS(sd,max);			
		}
	}

	/* 
	 * Metodo che stampa il grafo in formato .dot 
	 * scrive sullo standard output
         */
	private static void stampa() {
		System.out.println("digraph out_"+nome);
		for (int i=0; i<sd.size(); i++) {	
			if(sd.get(i).getNome().equals(max.getNome())){
				System.out.println(max.getNome()+" [label=\" root = "+max.getNome()+" ; |E\'|-|E| = "+Nuovi.size()+"\"];"  );
			} else {
				System.out.println(sd.get(i).getNome()+" [label=\"d("+max.getNome()+","+sd.get(i).getNome()+")="+sd.get(i).get_distanza()+"\"];");
			}
			for (int j=0; j<sd.get(i).get_adj().size(); j++) {	
				if(sd.get(i).getNome().equals(sd.get(i).get_adj().get(j).get_predecessore())){
					if((Nuovi.contains(sd.get(i).get_adj().get(j)))){
						System.out.println(max.getNome()+" -> "+sd.get(i).get_adj().get(j).getNome()+" [color=red; style=dashed];");
					} else {
						System.out.println(sd.get(i).getNome()+" -> "+sd.get(i).get_adj().get(j).getNome()+ " [style=dashed];");
					}
				} else {
					System.out.println(sd.get(i).getNome()+" -> "+sd.get(i).get_adj().get(j).getNome()+";");
				}
			}
		}	
		System.out.println("}");
	}		
	

}
