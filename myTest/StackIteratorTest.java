package myTest;

import myAdapter.HIterator;
import myAdapter.HList;
import myAdapter.HListIterator;
import myAdapter.StackAdapter;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Summary: questa classe testa tutti i metodi della classe StackIteratorAdapter
 * <br><br>
 * Design test: In questa classe ogni test punta a verificare il corretto funzionamento per ogni singolo metodo.
 * <br><br>
 * Description: la classe StackIteratorAdapter implementa HIterator and HListIterator.
 * Per semplicità, i valori inseriti sono tutti interi appartenenti alla classe
 * Integer (che è comunque parte della classe Object, pertanto rispetta il tipo dei parametri da inserire),
 * perchè è più facile verificare se i metodi assumono valori anomali nei test.
 * <br><br>
 * Preconditions:
 * <br>Un nuovo oggetto vuoto di tipo StackIteratorAdapter deve sempre essere creato prima di ogni test.
 * <br> Le variabili vengono sempre inizializzate, a meno che non voglia verificare per il caso in cui siano uguali a null.
 * <br>Tutti gli elementi contenuti nello stack sono conosciuti a priori, così da poter verificare il corretto funzionamento dei metodi.
 * <br><br>
 * Postconditions: i metodi implementati modificano lo stack nel modo atteso: gli elementi contenuti sono esattamente quelli previsti.
 * <br><br>
 * Execution record: ogni metodo testato è corretto se tutti i test che verificano il corretto funzionamento hanno un risultato positivo.
 * La corretta esecuzione dell'intero test può essere considerato come record di esecuzione.
 * <br><br>
 * Execution variables:
 * <br>HList stack - stack vuoto utilizzato per i metodi comuni sia a HListIterator che HIterator
 * <br>HList listWithData - stack non vuoto con cui vengono testati i metodi forniti dall'interfaccia HListIterator
 * <br>HIterator it - iteratore con cui vengono testati i metodi forniti dall'interfaccia HIterator
 * <br>HListIterator lit - listIterator con cui vengono testati i metodi forniti dall'interfaccia HListIterator
 * <br><br>
 *
 *
 *  @see myAdapter.HIterator
 *  @see myAdapter.HListIterator
 */


public class StackIteratorTest {

    HIterator it;
    HListIterator lit;
    private HList stack, stackWithData;

    /**
     * Summary: metodo per inizializzare le variabili prima dei test
     * <br><br>
     * Description: Viene creata una nuova lista vuota e una non vuota prima di ogni metodo di test,
     * in questo modo la list su cui vengono invocati i vari metodi testati ha sempre uno stato valido
     */
    @Before
    public void setup() {
        stack = new StackAdapter();
        stackWithData = new StackAdapter();

        for (int i = 0; i < 5; i++)
            stackWithData.add(i + 1);
    }

    /**
     * <p>
     * <br><br>Summary:test del costruttore senza parametri.
     * <br><br>Design test: tentativo di creazione di un list iterator da lista vuota e da lista con elementi.
     * <br><br>Description: crea iteratore da lista vuota e da lista piena e ne controlla la posizione iniziale.
     * <br><br>Preconditions: funzione non ha valore null. Il metodo nextIndex() deve funzionare correttamente.
     * <br><br>Postconditions: l'iteratore ha posizione 0 per lista vuota, 1 per lista piena.
     * <br><br>Expected results: l'indice dell'iteratore uguale a 0.
     */
    @Test
    public void testConstructor() {
        lit = stack.listIterator();
        assertEquals(0,lit.nextIndex());

        lit = stackWithData.listIterator();
        assertEquals(1,lit.nextIndex());

    }

    /**
     * <p>
     * <br><br>Summary:test del costruttore con parametri
     * <br><br>Design test: tentativo di creazione di un listIterator da lista vuota e indice non valido, e da lista con elementi e indice valido.
     * <br><br>Description: crea l'iteratore da lista vuota e da lista piena e ne controlla la posizione iniziale.
     * <br><br>Preconditions: funzione non ha valore null. Il metodo nextIndex() deve funzionare correttamente.
     * <br><br>Postconditions: l'iteratore ha la posizione passata come parametro.
     * <br><br>Expected results: l'indice dell'iteratore uguale alla posizione specificata, se valida. Altrimenti viene lanciata l'eccezione.
     */
    @Test
    public void testConstructorWithParameters() {

        try{
            lit = stack.listIterator(1);
            throw new Exception();
        }catch (Exception e){
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }
        lit = stackWithData.listIterator(2);
        assertEquals(3,lit.nextIndex());

    }

    /**
     * <p>
     * <br><br>Summary:verifica che il metodo indichi correttamente se l'iteratore è alla fine della lista o meno.
     * <br><br>Design test: Il metodo viene invocato su un iteratore di lista vuota e di una lista non vuota. Viene poi invocato su un listIterator di una lista piena e di una lista vuota. Esso viene poi invocato su listIterator che punta a una locazione centrale della lista e su uno che punta alla fine della lista.
     * <br><br>Description:In generale, il metodo viene invocato su una lista e viene verificato il valore ritornato dall'invocazione sull'iteratore del metodo hasNext(). Ciò viene svolto nel caso di un iteratore di una lista vuota e di lista non vuota. Analogamente con un listIterator. Viene infine verificato il caso in cui l'iteratore non punti all'inizio della lista, ma alla fine e il caso in cui punti a una locazione interna alla lista.
     * <br><br>Preconditions:la lista può essere vuota.
     * <br><br>Postconditions:Nel caso in cui la lista sia vuota o l'iteratore sia alla fine della lista hasNext() restituisce false, altrimenti true.
     * <br><br>Expected results:true nel caso in cui vi siano elementi successivi all'iteratore, altrimenti false.
     */
    @Test
    public void testHasNext() {

       it = stack.iterator();
       assertFalse(it.hasNext());

       it = stackWithData.iterator();
       assertTrue(it.hasNext());

       it = stack.listIterator();
        assertFalse(it.hasNext());

        it = stackWithData.listIterator();
        assertTrue(it.hasNext());

        it = stackWithData.listIterator(2);
        assertTrue(it.hasNext());

        it = stackWithData.listIterator(5);
        assertFalse(it.hasNext());

    }

    /**
     * <p>
     * <br><br>Summary:verifica che il metodo restituisca l'elemento che succede l'iteratore.
     * <br><br>Design test: il metodo viene invocato su un iteratore di una lista vuota e di una lista piena. Viene poi invocato su un listIterator di una lista vuota e di una lista piena.
     * <br><br>Description: Il metodo viene invocato su un iteratore di una lista vuota e viene verificato il corretto lancio dell'eccezione. Il metodo viene poi invocato su un iteratore di una lista non vuota e viene verificato che il valore restituito dal metodo sia corretto. Analogamente viene svolto con un listIterator.
     * <br><br>Preconditions: la lista può anche essere vuota.
     * <br><br>Postconditions: Il metodo restituisce l'elemento che succede quello puntato dall'iteratore. Nel caso in cui la lista è vuota, lancia correttamente l'eccezione.
     * <br><br>Expected results: il lancio dell'eccezione nel caso di lista vuota. Nel caso di lista piena, invece, l'elemento della lista che succede l'iteratore.
     */
    @Test
    public void testNext() {
        it = stack.iterator();
        try{
            it.next();
            throw new Exception();
        }catch (Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
        }

        it = stackWithData.iterator();
        assertEquals(1, it.next());

        lit = stack.listIterator();
        try{
            lit.next();
            throw new Exception();
        }catch (Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
        }

        lit = stackWithData.listIterator();
        assertEquals(1, lit.next());
        assertEquals(2, lit.next());

        lit = stackWithData.listIterator(5);
        try{
            lit.next();
            throw new Exception();
        }catch (Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
        }
    }

    /**
     * <p>
     * <br><br>Summary:verifica che il metodo indichi correttamente se ci sono elementi che precedono l'iteratore o meno.
     * <br><br>Design test: il metodo viene invocato sull'iteratore di una lista vuota e una non vuota.
     * <br><br>Description: il metodo viene invocato sull'iteratore di una lista vuota e di una lista non vuota. Se l'iteratore punta all'inizio della lista, allora il metodo restituirà faLse. Se, invece, punta alla fine o a una posizione interna della lista, ritornerà true.
     * <br><br>Preconditions:la lista puà anche essere vuota.
     * <br><br>Postconditions: il metodo restituisce true se l'iteratore ha elementi che lo precedono, altrimenti false.
     * <br><br>Expected results: false nel caso di lista vuota o nel caso in cui l'iteratore è all'inizio della lista, altrimenti true.
     */
    @Test
    public void testHasPrevious() {
        lit= stack.listIterator();
        assertFalse(lit.hasPrevious());

        lit= stackWithData.listIterator();
        assertFalse(lit.hasPrevious());

        lit= stackWithData.listIterator(5);
        assertTrue(lit.hasPrevious());

        lit= stackWithData.listIterator(3);
        assertTrue(lit.hasPrevious());
    }

    /**
     * <p>
     * <br><br>Summary: il metodo verifica il corretto funzionamento di previous().
     * <br><br>Design test: invoca il metodo su listIterator di una lista vuota e verifica che venga lanciata correttamente l'eccezione. Successivamente viene verificato il suo funzionamento nel caso di lista non vuota (l'iteratore deve essere decrementato di uno a ogni chiamata al metodo).
     * <br><br>Description: il metodo viene invocato sul listIterator di una lista vuota e viene verificato il lancio dell'eccezione. Successivamente viene invocato sul listIterator di una lista non vuota. Poiché esso non è all'inizio della lista, ritorna i valori che precedono il listIterator, il quale viene decrementato di uno a ogni chiamata.
     * Infine viene verificato il corretto lancio dell'eccezione, nel caso in cui l'iteratore punti all'inizio di una lista non vuota.
     * <br><br>Preconditions: la lista può anche essere vuota
     * <br><br>Postconditions: il metodo ritorna l'elemento che precede l'iteratore (che viene decrementato). Se non ve ne sono, lancia l'eccezione.
     * <br><br>Expected results: il lancio dell'eccezione se la lista è vuota o l'iteratore è all'inizio della lista, altrimenti l'elemento che precede l'iteratore.
     */
    @Test
    public void testPrevious() {
        lit= stack.listIterator();
        try{
            lit.previous();
            throw new Exception();
        }catch(Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
        }

        lit = stackWithData.listIterator(5);
        assertEquals(5,lit.previous());
        assertEquals(4,lit.previous());

        lit = stackWithData.listIterator();
        try{
            lit.previous();
            throw new Exception();
        }catch (Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
        }
    }

    /**
     * <p>
     * <br><br>Summary: Viene verificato che il metodo restituisca la posizione attuale incrementata di uno.
     * <br><br>Design test: Il metodo viene invocato sul listIterator di una lista vuota e di una non vuota.
     * <br><br>Description: il metodo viene chiamato sul listIterator di una lista vuota e si confronta il valore restituito con quello che ci si aspetta. Successivamente esso viene invocato sul listIterator posto alla fine di una lista non vuota. Viene verificato che il valore ritornato sia uguale alla dimensione della lista.
     * <br><br>Preconditions: la lista può anche essere vuota. Il metodo size() deve funzionare correttamente.
     * <br><br>Postconditions: iteratore immutato e il valore restituito è  (posizione corrente+1).
     * <br><br>Expected results: 0 se la lista è vuota, la dimensione della lista se l'iteratore è nell'ultima posizione della lista oppure posizione corrente incrementata di uno, se la posizione dell'iteratore è compresa tra zero e size().
     */
    @Test
    public void testNextIndex() {
        lit = stack.listIterator();
        assertEquals(0,lit.nextIndex());

        lit = stackWithData.listIterator(5);
        assertEquals(stackWithData.size(),lit.nextIndex());

        lit = stackWithData.listIterator(4);
        assertEquals(5,lit.nextIndex());

    }

    /**
     * <p>
     * <br><br>Summary: il metodo verifica che venga restituita la posizione corrente decrementata di uno.
     * <br><br>Design test: il metodo viene invocato su un listIterator di una lista vuota e di una lista piena. In quest'ultima situazione viene considerato il caso in cui il listIterator sia all'inizio della lista o meno.
     * <br><br>Description: Il metodo viene invocato in pià casi. Viene considerato il caso in cui il listIterator punta a una lista vuota, all'inizio di una lista non vuota e, infine, in una posizione compresa tra zero e size(). Per ogni caso viene svolto un confronto tra valore atteso e quello effettivamente restituito dalla funzione.
     * <br><br>Preconditions: la lista può anche essere vuota.
     * <br><br>Postconditions: iteratore immutato e valore restituito uguale alla posizione corrente-1
     * <br><br>Expected results: Se il listIterator punta a una lista vuota o al primo elemento della lista, ritorna -1; altrimenti (posizione corrente-1).
     */
    @Test
    public void testPreviousIndex() {
        lit = stackWithData.listIterator(0);
        assertEquals(-1,lit.previousIndex());

        lit = stackWithData.listIterator(3);
        assertEquals(2,lit.previousIndex());

        lit= stack.listIterator();
        assertEquals(-1, lit.previousIndex());

    }

    /**
     * <p>
     * <br><br>Summary:verifica che il metodo rimuova l'elemento puntato dall'iteratore
     * <br><br>Design test: il metodo viene invocato sull'iteratore di una lista piena e successivamente sul listIterator di una lista piena.
     * <br><br>Description:Il metodo viene invocato sull'iteratore di una lista piena, senza chiamare prima il metodo next() o previous() e viene verificato il corretto lancio dell'eccezione.
     * Successivamente viene invocato next() e poi il metodo da testare. Si verifica che la dimensione sia diminuita e che sia stato rimosso l'elemento giusto (tramite il confronto dell'array restituito da toArray() con uno creato ad hoc).
     * Dopo il listIterator viene fatto puntare all'elemento di indice 2, viene chiamata il metodo previous() e poi il metodo da testare e si verifica, allo stesso modo del caso precedente, la corretta rimozione dell'elemento.
     * Infine Viene verificato che venga lanciata l'eccezione se, dopo la chiamata a next() e previous(), viene chiamato il metodo add(Object).
     * <br><br>Preconditions:i metodi next(), previous() e add(Object) funzionano correttamente.
     * <br><br>Postconditions: la rimozione dell'elemento e il decremento della dimensione massima.
     * <br><br>Expected results: l'elemento ritornato da next() o da previous() viene eliminato; altrimenti viene lanciata un'eccezione.
     */
    @Test
    public void Remove() {

        it = stackWithData.iterator();
        try{
            it.remove();
            throw new Exception();
        }catch(Exception e){
            assertEquals(IllegalStateException.class, e.getClass());
        }

        it.next();
        it.remove();
        assertEquals(4,stackWithData.size());
        assertArrayEquals(new Object[]{1,3,4,5}, stackWithData.toArray());

        lit = stackWithData.listIterator(2);
        lit.previous();
        lit.remove();
        assertEquals(3, stackWithData.size());
        assertArrayEquals(new Object[]{1,4,5}, stackWithData.toArray());

        System.out.println();
        lit.add(1);
        try{
            lit.remove();
            throw new Exception();
        }catch(Exception e){
            assertEquals(IllegalStateException.class, e.getClass());
        }


    }

    /**
     * <p>
     * <br><br>Summary:verifica che l'elemento restituito dalla chiamata al metodo next() o al metodo previous() venga sostituito dall'oggetto passato come parametro del metodo.
     * <br><br>Design test: viene invocato il metodo senza prima chiamare next() o previous().
     * Successivamente viene invocato il metodo next(), per due volte, e il metodo remove() e il metodo da testare.
     * Successivamente viene invocato add() e poi il metodo set(Object).
     * Infine viene considerato il caso valido: viene invocato next() e poi il metodo da testare.
     * <br><br>Description:viene prima verificato che se chiamo il metodo senza prima chiamare next() o previous(), viene lanciata l'eccezione.
     * Successivamente viene invocato il metodo next(), per due volte, e il metodo remove(). Quindi viene verificato il corretto lancio dell'eccezione.
     * Successivamente viene invocato add(), quindi viene verificato nuovamente il lancio dell'eccezione.
     * Infine viene considerato il caso valido. Quindi si verifica che le modifche precedenti siano avvenute correttamente e che anche quelle successive avvengano correttamente.
     * <br><br>Preconditions: i metodi next(), remove() e add(Object) devono funzionare correttamente.
     * <br><br>Postconditions: l'iteratore rimane invariato, ma l'elemento restituito da next() o da previous() viene sostituito da quello passato come parametro.
     * <br><br>Expected results: valore specificato rimpiazza l'elemento restituito da next() o da previous(). Se tali metodi non sono stati invocati oppure sono stati invocati; ma, prima di invocare set(Object), viene invocato add(Object) o remove(), allora viene lanciata l'eccezione.
      */
    @Test
    public void testSet() {
        lit = stackWithData.listIterator();
        try{
            lit.set("Avengers");
            throw new Exception();
        }catch(Exception e){
            assertEquals(IllegalStateException.class, e.getClass());
        }

        lit.next();
        lit.next();
        lit.remove();
        try{
           lit.set("Assemble");
           throw new Exception();
        }catch(Exception e){
            assertEquals(IllegalStateException.class, e.getClass());
        }

        lit.add(5);
        try{
            lit.set("!!!");
            throw new Exception();
        }catch(Exception e){
            assertEquals(IllegalStateException.class, e.getClass());
        }

        assertArrayEquals(new Object[] {5,1,2,4,5},stackWithData.toArray());
        lit = stackWithData.listIterator(3);
        lit.next();
        lit.set(7);
        lit.set(9);
        assertEquals(9,lit.previous());
        assertArrayEquals(new Object[] {5,1,2,9,5},stackWithData.toArray());


    }

    /**
     * <p>
     * <br><br>Summary:verifica che il metodo inserisca l'elemento passato come parametro esattamente dove si trova l'iteratore.
     * <br><br>Design test:il metodo viene invocato su un listIterator di una lista vuota e, successivamente, di una lista non vuota.
     * <br><br>Description:il metodo viene invocato sul listIterator di una lista vuota e verifico che l'elemento inserito è l'unico elemento presente. Successivamente il metodo viene invocato sull'iteratore di una lista non vuota più volte e vengono svolti alcuni inserimenti. Infine viene verificato (tramite un confronto tra l'array restituito da toArray() e uno creato ad hoc) che gli inserimenti siano avvenuti correttamente.
     * <br><br>Preconditions: la lista può anche essere vuota. I metodi size() e previous() devono funzionare correttamente.
     * <br><br>Postconditions: l'elemento specificato viene aggiunto nella posizione in cui si trova l'iteratore
     * <br><br>Expected results:l'elemento specifivato viene aggiunto nella posizione corrente dell'iteratore.
     */
    @Test
    public void testAdd() {

        lit = stack.listIterator();
        lit.add(1);
        try{
            lit.next();
            throw new Exception();
        }catch(Exception e){
            assertEquals(NoSuchElementException.class, e.getClass());
        }
        assertEquals(1,lit.previous());
        assertEquals(1,stack.size());

        lit = stackWithData.listIterator(5);
        lit.add(6);
        assertEquals(6,lit.previous());

        lit = stackWithData.listIterator(2);
        lit.add(7);

        lit = stackWithData.listIterator();
        lit.add(0);
        assertArrayEquals(new Object[] {0,1,2,7,3,4,5,6}, stackWithData.toArray());


    }


}