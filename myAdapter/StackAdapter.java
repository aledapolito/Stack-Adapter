package myAdapter;

import java.util.NoSuchElementException;
import java.lang.NullPointerException;

/**
 * La classe Adapter che implementa le interfacce List e Collection di Java2 Collections Framework versione 1.4.2.
 *	Questa classe è la rappresentazione di uno stack con un indice alla fine e uno all'inizio.
 */

public class StackAdapter implements HList, HCollection {
	/**
	 * Vector che emula lo stack.
	 */
	private Vector stack;

	/**
	 * Indice dell'elemento successivo l'ultimo elemento della Collection.
	 * Si incrementa di 1 ogni volta che un elemento viene aggiunto.
	 * Nel caso di rimozione di un elemento, viene decrementato di uno.
	 */

	private int end;

	/**
	 * Indice al primo elemento dello stack.
	 * Esso è uguale a zero. Non è così solo nel caso di invocazione di una sublist.
	 */
	private int start;

	/**
	 * Puntatore alla collezione, esso è uguale a null, tranne nel caso in cui lo stack sia generato da un altro stack.
	 */
	private StackAdapter root;


	/**
	 * Costruttore: crea uno StackAdapter vuoto.
	 */
	public StackAdapter() {
		stack = new Vector();
		end = 0;
		start = 0;
		root = null;
	}

	/**
	 * Genera uno StackAdapter partendo da una Collection specifica.
	 * @param coll collezione specifica, i cui elementi verranno inseriti nella Collection.
	 * @throws NullPointerException se l'argomento della funzione non è valido, altrimenti creo una istanza che
	 *                              contiene tutti gli elementi della collection specifica.
	 */

	public StackAdapter(HCollection coll) {
		if (coll == null) throw new NullPointerException();
		stack = new Vector();
		Object[] arr = ((StackAdapter) coll).toArray();
		int i;
		for (i = 0; i < arr.length; i++) {
			stack.addElement(arr[i]);
		}
		end = arr.length;
		start =0;
		root = null;
	}

	// Query Operations

	/**
	 * Verifica se lo StackAdapter è vuoto.
	 *
	 * @return true se è vuoto, false altrimenti.
	 */
	public boolean isEmpty() {
		return (start == end);
	}

	/**
	 * Il metodo ritorna la dimensione dello StackAdapter.
	 *
	 * @return il numero di elementi nello stack.
	 */
	public int size() {
		return (end-start);
	}

	/**
	 * Ricerca all'interno dello stack l'oggetto obj.
	 *
	 * @param obj elemento che viene ricercato all'interno della collection.
	 * @return true se esso è nello stack, altrimenti ritorna false.
	 */
	public boolean contains(Object obj) {
		if (this.isEmpty()) return false;
		else {
			for (int i = start; i < size(); i++) {
				if (stack.elementAt(i)==null){
					if (obj==null) return true;
					else return false;
				}
				if ((stack.elementAt(i)).equals(obj))
					return true;
				
			}return false;
		}
	}

	/**
	 * Crea un iteratore, che punta al primo elemento dello StackAdapter.
	 *
	 * @return iteratore.
	 */
	public HIterator iterator() {
		return new StackIteratorAdapter();
	}

	/**
	 * Crea un array di dimensione sufficiente, tale da contenere gli elementi dello StackAdapter.
	 *
	 * @return array di tipo Object riempito con gli elementi dello StackAdapter.
	 */
	public Object[] toArray() {
		Object[] array = new Object[size()];
		for (int index = start; index < end; index++) {
			array[index-start] = stack.elementAt(index);
		}
		return array;
	}

	/**
	 * Il metodo copia gli elementi dello StackAdapter all'interno di un array, fornito come parametro.
	 * Se esso non è sufficientemente grande, viene generato un nuovo array di dimensione tale da poter contenere
	 * tutti gli elementi. Nel caso in cui dovesse essere più grande, le celle rimanenti verranno riempite con elementi di
	 * valore null.
	 * @param arrayTarget array in cui verranno copiati gli elementi della collection.
	 * @throws NullPointerException se l'argomento inserito non è valido.
	 * @return arrayTarget
	*/
	 public Object[] toArray(Object arrayTarget[]) {
		if (arrayTarget == null) throw new NullPointerException();
		if (arrayTarget.length >= end) {
			int i;
			for (i = start; i < end; i++) {
				arrayTarget[i-start] = stack.elementAt(i);
			}
			while (i < arrayTarget.length) {
				arrayTarget[i] = null;
				i++;
			}
		} else {
			Object[] newarray = this.toArray();
			arrayTarget = newarray;
		}
		return arrayTarget;
	}

	// Modification Operations

	/**
	 * Il metodo aggiunge un elemento, passato come parametro, alla fine dello StackAdapter.
	 * @param obj elemento che voglio inserire in coda alla collection.
	 * @return  true ad azione compiuta.
	*/
	 public boolean add(Object obj) {
		stack.insertElementAt(obj,end);
		end++;
		return true;
	}

	/**
	 * Il metodo rimuove la prima occorrenza dell'oggetto obj. Se esso non è presente, lo StackAdapter rimane invariato.
	 *
	 * @param obj elemento che viene cercato all'interno dello StackAdampter ed, eventualmente, eliminato.
	 * @return  true se l'elemento era presente, altrimenti false.
	*/
	 public boolean remove(Object obj) {
		 if(stack.removeElement(obj)){
			 end--;
			 return true;
		 } else
			return false;
	}

	// Bulk Modification Operations

	/**
	 * 	Il metodo verifica che tutti gli elementi presenti in questa specifica collezione siano presenti nello StackAdapter.
	 *  @param coll collezione di elementi che vengono cercati all'interno dello StackAdapter.
	 *  @throws NullPointerException se l'argomento della funzione non è valido.
	 * 	@return true se ciò viene verificato, altrimenti ritorna false.
	*/
	public boolean containsAll(HCollection coll) {

		if (coll == null) throw new NullPointerException();

		StackAdapter collstack = (StackAdapter) coll;
		Object[] array = collstack.toArray();
		int i = 0;
		boolean found = true;
		while (i < size() && found ) {
			if (contains(array[i])) {
				i++;
			}else {
				found = false;

			}
		}
		return found;
	}

	/**
	 *  Aggiunge in coda tutti gli elementi della Collection specificata nell'argomento del metodo.
	 *  @param coll collezione specifica i cui elementi verranno inseriti nello StackAdapter.
	 *  @throws NullPointerException se l'argomento della funzione non è valido.
	 *  @return true se ciò avviene con successo, altrimenti false.
	 *
	*/
	public boolean addAll(HCollection coll) {
		if (coll == null) throw new NullPointerException();

		StackAdapter collstack = (StackAdapter) coll;
		Object[] array = collstack.toArray();
		if (array.length== 0) return false;
		for (int i = 0; i < array.length; i++) {
			add(array[i]);
		}
		return true;

	}

	/**
	 * Il metodo inserisce gli elementi della Collection specificata a partire dall'indice index.
	 * Se vi sono già degli elementi, essi vengono shiftati a dx.
	 * @param index indice di partenza.
	 * @param coll  collezione contenente gli elementi che verranno inseriti.
	 * @throws NullPointerException se la collezione specifica non è valida.
	 * @throws IndexOutOfBoundsException se l'indice non è valido.
	 * @return true se l'inserimento avviene con successo, altrimenti false.
	*/
	public boolean addAll(int index, HCollection coll) {
		if (coll == null) throw new NullPointerException();
		if (index > end || index < 0) throw new IndexOutOfBoundsException();

		StackAdapter collstack = (StackAdapter) coll;
		Object[] array = collstack.toArray();
		if (array.length == 0) return false;
		else {
			int n = array.length;
			for (int i = 0; i < n; i++) {
				add(index, array[i]);
				index++;
			}
			return true;
		}
	}

	/**
	 * Elimina dallo StackAdapter tutti gli elementi contenuti nella Collection specificata.
	 * @param coll collezione i cui elementi verranno rimossi dallo StackAdapter.
	 * @throws NullPointerException se l'argomento della funzione non è valido.
	 * @return true se l'operazione avviene con successo, altrimenti restituisce false.
	 */
	public boolean removeAll(HCollection coll) {
		if (coll==null) throw new NullPointerException();
		boolean changed = false;
		if (coll.size()==0) return false;
		else{
			StackAdapter collAd = (StackAdapter) coll;
			Object[] array = coll.toArray();
			for(int i=0; i < collAd.size() ;i++){
				if (contains(array[i])) {
					remove(array[i]);
					changed=true;
				}
			}
		}return changed;
	}

	/**
	 * Rimuove dallo StackAdapter tutti gli elementi che non sono presenti nella Collection specifica.
	 * @param coll collezione di elementi che, se prenti nello StackAdapter, non verranno rimossi.
	 * @throws NullPointerException se l'argomento della funzione non è valido.
	 * @return true se l'operazione avviene con successo, altrimenti false.
	 */
	public boolean retainAll(HCollection coll) {
		boolean changed = false;

		if (coll == null) throw new NullPointerException();
		if (isEmpty()) return false;
		else{
			StackAdapter collad = (StackAdapter) coll;
			Object[] arr = toArray();
			for(int i =0; i< arr.length; i++){
				if (!collad.contains(arr[i])){
					remove(arr[i]);
					changed=true;
				}
			}
		}
		return changed;
	}

	/**
	 * Elimina tutti gli elementi dello StackAdapter.
	 */
	public void clear() {
		end =0;
		start=0;
		stack.removeAllElements();
	}

	// Comparison and hashing

	/**
	 * Confronta tue elementi della stessa classe per verificare che sono uguali.
	 * @param obj oggetto che viene comparato.
	 * @return true se e solo se i due oggetti confrontati sono equivalenti, altrimenti false.
	 */
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass()!=getClass()) return false;

		if (((StackAdapter) obj).size() != size()) return false;

		if (((StackAdapter) obj).size() == size() && size()==0) return true;

		Object[] arr = ((StackAdapter) obj).toArray();
		boolean valido = true;
		int i = 0;
		while (valido && i<size()){
			if (arr[i].equals(stack.elementAt(i))){
				i++;
			}else{
				valido =false;
			}
		}
		return valido;
	}

	/**
	 * Ritorna l'hashcode valido per questa lista
 	 * @return l'hashcode corrispondente allo stack
	 */
	public int hashCode() {
		return stack.hashCode();
	}

	// Positional Access Operations

	/**
	 * Ritorna l'elemento corrispondente allo specifico index.
	 * @param index indice dell'elemento da restituire.
	 * @throws IndexOutOfBoundsException se l'indice non è valido.
	 * @return l'elemento posizionato nell'indice passato come parametro.
	 */
	public Object get(int index) {
		if (index<start || index>=size()) throw new IndexOutOfBoundsException();
		return stack.elementAt(index);
	}

	/**
	 * Sostituisce l'elemento già presente nell'indice specificato con quello passato come argomento.
	 * @param index   indice dell'elemento da sostituire.
	 * @param element elemento da inserire nella posizione specificata.
	 * @throws IndexOutOfBoundsException se l'indice non è valido.
	 * @return l'elemento che si trovava precedentemente nella posizione index.
	 */
	public Object set(int index, Object element) {
		if (index <start || index >=size()) throw new IndexOutOfBoundsException();
		Object o = stack.elementAt(index);
		stack.setElementAt(element, index);
		return o;
	}

	/**
	 * Inserisce l'elemento nell'index specificato:
	 * 1-verifica che l'index sia valido;
	 * 2-se è valido inserisce l'elemento shiftando a dx gli elementi già presenti nello stack.
	 * @param index indice di partenza.
	 * @param element oggetto che deve essere inserito.
	 */
	public void add(int index, Object element) {
		if (index > size() || index<0) throw new IndexOutOfBoundsException();

		stack.insertElementAt(element, index);
		end++;
	}

	/**
	 * Rimuove l'elemento nella posizione specificata in questo elenco. Sposta tutti gli elementi successivi a sinistra (decrementa di uno i loro indici). Restituisce l'elemento che è stato rimosso dall'elenco.
	 * @param index the index of the element to removed.
	 * @throws IndexOutOfBoundsException se l'indice non è valido.
	 * @return l'elemento rimosso.
	 */
	public Object remove(int index) {
		if ( index < 0 || index >=size()) throw new IndexOutOfBoundsException();
		Object obj = stack.elementAt(index+start);
		stack.removeElementAt(index+start);
		end--;
		return obj;
	}

	// Search Operations

	/**
	 * Il metodo restituisce l'indice della prima occorrenza dell' elemento specificato.
	 * @param obj elemento che viene cercato.
	 * @return l'indice della prima occorrenza, se non presente restituisce -1.
	 */
	public int indexOf(Object obj) {
		return stack.indexOf(obj);
	}

	/**
	 * Il metodo ritorna l'indice dell'ultima occorrenza dell'elemento specificato.
	 * @param obj elemento che viene cercato.
	 * @return l'indice dell'ultima occorrenza, se non presente restituisce -1.
	 */
	public int lastIndexOf(Object obj) {
		return stack.lastIndexOf(obj);
	}

	// List Iterators

	/**
	 * Il metodo genera un list iterator degli elementi dello StackAdapter.
	 * @return l'iteratore agli elementi dello stack.
	 */
	public HListIterator listIterator() {
		return new StackIteratorAdapter();
	}

	/**
	 * ritorna un list iterator degli elementi dello StackAdapter, partendo da uno specifico index
	 * @param index indice del primo elemento restituito da una chiamata di next().
	 * @throws IndexOutOfBoundsException se l'indice non è valido.
	 * @return l'iteratore posizionato alla posizione specificata.
	 */
	public HListIterator listIterator(int index) {
		if (index <start || index>size() ) throw new IndexOutOfBoundsException();
		return new StackIteratorAdapter(index);
	}

	// View

	/**
	 * Restituisce una sottolista degli elementi compresi tra gli indici indicato, toIndex escluso.
	 * Ogni modifica non strutturale effettuata alla sottolista, si ripercuote sulla lista da cui è stata generata e viceversa.
	 *
	 * @param fromIndex indice di partenza della sublist.
	 * @param toIndex   indice finale, non compreso, della sublist.
	 * @throws IndexOutOfBoundsException se gli indici passati come parametri non sono validi.
	 * @return una sottolista della lista originale contenente i valori compresi tra fromIndex e toIndex, escluso.
	 *
	 */
	public HList subList(int fromIndex, int toIndex) {
		if (fromIndex<start||toIndex>size()||fromIndex>toIndex) throw new IndexOutOfBoundsException();
		else {
				if (fromIndex==toIndex) return new StackAdapter();
				else{
					StackAdapter list = new StackAdapter();
					list.stack = this.stack;
					list.root = this;
					list.end = toIndex;
					list.start = fromIndex;
					return list;
				}
			}
		}


	//stack methods 

	/**
	 * Verifica che lo stack sia vuoto.
	 * @return true se esso non contiene elementi, altrimenti ritorna false.
	 */
	public boolean empty(){
		return isEmpty();
	}

	/**
	 * Legge l'oggetto sulla cima dello stack, senza rimuoverlo.
	 * @throws java.util.EmptyStackException se lo stack è vuoto.
	 * @return l'oggetto in cima allo stack.
	 */

	public Object peek() {
		return stack.elementAt(end-1);
	}

	/**
	 * Rimuove l'oggetto in cima allo stack e lo restituisce.
	 * @throws java.util.EmptyStackException se lo stack è vuoto.
	 * @return l'oggetto in cima allo stack che è stato rimosso.
 	 */
	public Object pop() {
		end--;
		return stack.elementAt(end);
	}

	/**
	 * Inserisce un elemento in cima allo stack.
	 * @param item oggetto da inserire.
	 * @return l'argomento della funzione, ovvero l'oggetto aggiunto in cima allo stack.
	 */
	public Object push(Object item) {
		stack.insertElementAt(item, end);
		end++;
		return item;
	}

	/**
	 * Il metodo cerca all'interno dello stack l'oggetto passato come argomento. Se esso è presente all'interno dello stack, il metodo restituisce
	 * la distanza di questo oggetto dalla cima dello stack. Se ve ne sono più di uno, allora si considera quello più vicino alla cima dello stack e viene restituita la sua
	 * distanza dalla cima. Se l'oggetto si trova in cima alla lista, la distanza risulta uno.
	 * @param o l'oggetto desiderato.
	 * @return la posizione (1-based) dalla cima dello stack dove l'oggetto si trova, se l'oggetto non viene trovato ritorna -1.
	 */
	public int search(Object o) {
		int distance = 1;
		boolean found = false;
		int i = end-1;
		while (!found && i>=start ){
			if (o.equals(stack.elementAt(i))){
				found=true;
			}else{
				distance++;
			}
			i--;
		}
		if (!found) return -1;
		else return distance;
	}


	/**
	 * Private class dell'iteratore, che implementa le interfacce HListIterator e HIterator
	 */
	private class StackIteratorAdapter implements HIterator, HListIterator {

		/**
		 * Iteratore
		 */
		private int point;

		/**
		 * Flag per il controllo validità di set e remove.
		 * Nel costruttore lo inizializzo a false, ma esso viene impostato a true dai metodi previous() e next().
		 */
		boolean modified = false;

		/**
		 * Flag per decidere quale elemento rimuovere nel metodo remove:
		 * true: per rimuovere l'elemento successivo,
		 * false: per rimuovere l'elemento precedente.
		 */
		boolean foward = false;

		/**
		 * Costruttore
		 */

		public StackIteratorAdapter() {
			point = 0;
		}

		/**
		 * Costruttore con parametri.
		 *
		 * @param i indice ove punta l'iteratore.
		 * @throws IndexOutOfBoundsException se il parametro non è valido.
		 */
		public StackIteratorAdapter(int i) {
			if (i > size() || i < start) throw new IndexOutOfBoundsException();
			point = i;
		}

		/**
		 * Metodo che verifica se l'iteratore ha elementi a cui puntare se si muove in avanti lungo la lista.
		 *
		 * @return true se ci sono elementi proseguendo in avanti lungo la lista, false altrimenti.
		 */
		public boolean hasNext() {
			if (point == size()) return false;
			return true;
		}

		/**
		 * Questo metodo ritorna l'elemento successivo all'iteratore e poi incrementato di uno l'iteratore.
		 *
		 * @return l'oggetto successivo a quello puntato dall'iteratore.
		 * @throws NoSuchElementException se l'iteratore è alla fine della lista.
		 */
		public Object next() {
			if (!hasNext()) throw new NoSuchElementException();
			else {
				point++;
				modified = true;
				foward = true;
				return	stack.elementAt(point-1);
			}
		}

		/**
		 * Questo metodo verifica che vi siano elementi che precedono l'iteratore.
		 *
		 * @return true se esiste un elemento nella posizione precedente, false altrimenti.
		 */
		public boolean hasPrevious() {
			if (point == 0) return false;
			return true;
		}

		/**
		 * Il metodo restituisce l'elemento che precede l'iteratore e decrementa di uno l'iteratore.
		 *
		 * @return l'oggetto che precede quello puntato dall'iteratore.
		 * @throws NoSuchElementException se non ve ne sono.
		 */
		public Object previous() {
			if (!hasPrevious()) throw new NoSuchElementException();
			else {
				point--;
				modified = true;
				foward = false;
				return get(point);
			}
		}

		/**
		 * Il metodo ritorna l'indice successivo a quello in cui è posizionato l'iteratore.
		 *
		 * @return intero che indica la posizione successiva a quella dell'iteratore, size() se l'iteratore è nell'ultima posizione valida.
		 */
		public int nextIndex() {
			if (point==size()) return size();
			return point+1;
		}

		/**
		 * Il metodo restituisce l'indice precedente all'iteratore.
		 *
		 * @return intero che indica la posizione precedente a quella dell'iteratore, -1 se l'iteratore si trova all'inizio della lista.
		 */
		public int previousIndex() {
			if (point==0) return -1;
			return point-1;
		}

		/**
		 * Il metodo rimuove l'ultimo elemento che è stato ritornato da next() o da previous().
		 *
		 * @throws IllegalStateException se, dopo la chiamata a previous() o a next(), è stata chiamata la funzione StackIterator.add()
		 *                               oppure se nè previous() o next() sono stati chiamati precedentemente.
		 */
		public void remove() {
			if (!modified) throw new IllegalStateException();
			if (!foward) {
				StackAdapter.this.remove(point);
			} else {
				StackAdapter.this.remove(point--);
				point--;
			}
			modified = false;
		}

		/**
		 * Sostituisce l'ultimo elemento ritornato da next() o da previous() con l'elemento che viene passato per argomento.
		 *
		 * @param obj l'elemento con cui sostituire l'ultimo elemento restituito da next() o previous().
		 * @throws IllegalStateException se nè next o previous sono stati chiamati precedentemente oppure se, dopo la loro chiamata,
		 *                               sono state invocate le funzioni Stackiterator.add() o StackIterator.remove()
		 */
		public void set(Object obj) {
			if (!modified) throw new IllegalStateException();
			if (foward) {
				StackAdapter.this.set(point-1, obj);
			}else{
				StackAdapter.this.set(point, obj);
			}
			modified=true;
		}

		/**
		 * Il metodo inserisce l'elemento che viene passato come argomento esattamente dove si trova l'iteratore.
		 * Se la lista non contiene nessun elemento, allora questo elemento sarà l'unico della lista.
		 *
		 * @param obj elemento da inserire nella posizione corrente dell'iteratore.
		 */
		public void add(Object obj) {
			StackAdapter.this.add(point, obj);
			point++;
			modified = false;
		}
	}

}

