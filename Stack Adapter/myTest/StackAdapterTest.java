package myTest;

import myAdapter.HCollection;
import myAdapter.HList;
import myAdapter.HListIterator;
import myAdapter.HIterator;
import myAdapter.StackAdapter;
import org.junit.Before;
import org.junit.Test;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;


/**
 * Summary: questa classe testa tutti i metodi della classe StackAdapter, tranne quelli di StackIteratorAdapter.
 * <br><br>
 * Design test: In questa classe ogni test punta a verificare il corretto funzionamento per ogni singolo metodo.
 * <br><br>
 * Description: la classe StackAdapter implementa HList and HCollection, soprattutto i metodi HCollection sono testati usando un HCollection come object
 * Per semplicità, i valori inseriti sono tutti interi appartenenti alla classe Integer (che è comunque parte della classe Object, pertanto rispetta il tipo dei parametri da inserire),
 * perchè è più facile verificare se i metodi assumono valori anomali nei test.
 * <br><br>
 * Preconditions:
 * <br>Un nuovo oggetto vuoto di tipo StackAdapter deve sempre essere creato prima di ogni test.
 * <br> Le variabili vengono sempre inizializzate, a meno che non voglia verificare il caso in cui siano uguali a null.
 * <br>Metodi che prendono come parametri classi che implementano HCollection sono considerati idonei per questa interfaccia,
 * soprattutto non lanciano l'eccezione ClassCastException.
 * <br>Tutti gli elementi contenuti nello stack sono conosciuti a priori, così da poter verificare il corretto funzionamento dei metodi.
 * <br><br>
 * Postconditions: i metodi implementati modificano lo stack nel modo atteso: gli elementi contenuti sono esattamente quelli previsti.
 * <br><br>
 * Execution record: ogni metodo testato è corretto se tutti i test che verificano il corretto funzionamento hanno un risultato positivo.
 * La corretta esecuzione dell'intero test può essere considerato come record di esecuzione.
 * <br><br>
 * Execution variables:
 * <br>HList stack - stack vuoto per tutti i metodi comuni alle interfacce HCollection e HList.
 * <br>HList listWithData - stack non vuoto con cui vengono testati i metodi forniti dall'interfaccia HListIterator.
 * <br>HCollection coll - oggetto usato per testare i metodi forniti dalla interfaccia HCollection
 * <br><br>
 *
 *
 * @see myAdapter.HList
 * @see myAdapter.HCollection
 */

public class StackAdapterTest {
    HCollection coll;
    private StackAdapter stack, stackWithData;

    /**
     * Summary:metodo per inizializzare le variabili prima dei test.
     * <br><br>
     * Description:Viene creata una nuova Collezione vuota prima di ogni metodo di test,
     * in questo modo la collezione su cui vengono invocati i vari metodi testati ha sempre uno stato valido.
     * Viene anche creato uno stack vuoto e uno riempito.
     */
    @Before
    public void setup() {
        coll = new StackAdapter();
        stack = new StackAdapter();
        stackWithData = new StackAdapter();

        for (int i = 0; i < 5; i++)
            stackWithData.add(i + 1);
    }

    /**
     * Test per il costruttore con parametri.
     * <br><br>Summary: test che verifica la corretta creazione di una istanza di tipo
     *      StackAdapter che contiene un valore della collection, passato come parametro.
     * <br><br>Design test: si aggiungono elementi alla collezione, si invoca il costruttore, passando come parametro
     *      la collezione contenente gli elementi e confronto i rispettivi array ottenuti tramite la chiamata a toArray().
     * <br><br>Preconditions: La collezione non deve essere null e il metodo toArray() deve funzionare correttamente.
     * <br><br>Postconditions: La nuova collezione deve essere identica a quella passata come parametro.
     * <br><br>Expected Results: Le due collezioni devono essere identiche.
     */
    @Test
    public void testConstructorWithParameter() {

        HCollection notValid;
        try{
            notValid = new StackAdapter(null);
        }catch (Exception e){
            assertEquals(NullPointerException.class, e.getClass());
        }

        HCollection newCollection1 = new StackAdapter(coll);
        assertArrayEquals(coll.toArray(), newCollection1.toArray());

        coll.add(1);
        coll.add(2);
        coll.add(3);
        coll.add(4);

        HCollection newCollection2 = new StackAdapter(coll);
        assertArrayEquals(coll.toArray(), newCollection2.toArray());

        assertNotEquals(newCollection1,newCollection2);
    }

    /**
     * Test of {@link myAdapter.StackAdapter#isEmpty()}
     * <p>
     * <br><br>Summary: Metodo che verifica che uno stack è vuoto o meno.
     * <br><br>Design test:viene aggiunto un elemento nella collezione vuota e viene testato, l'elemento viene rimosso è viene nuovamente invocato il metodo.
     * <br><br>Description: dopo aver aggiunto un elemento viene verificato che il contenitore non risulti vuoto, poi esso viene rimosso e viene verificato che esso risulti vuoto.
     * <br><br>Preconditions: Il metodo add() deve funzionare correttamente.
     * <br><br>Postconditions: il metodo deve ritornare true se il contenitore non contiene elementi.
     * <br><br>Expected results: false dopo aver inserito un elemento, true dopo averlo rimosso.
     */
    @Test
    public void testIsEmpty() {
        coll.add(1);
        assertFalse(" la collezione non è vuota  ", coll.isEmpty());
        coll.remove(1);
        assertTrue("la collezione è vuota ", coll.isEmpty());

    }


    /**
     * Test of {@link myAdapter.StackAdapter#size()}
     * <p>
     * <br><br>Summary: metodo che ritorna la corretta numero di elementi all'interno del contenitore.
     * <br><br>Design test: test della corretta dimensione della collezione, testando anche il suo aumento quando vengono aggiunti elementi e il suo decremento dopo la rimozione di uno di essi.
     * <br><br>Description: il metodo size() viene invocato prima e dopo aver aggiunto o rimosso elementi.
     * <br><br>Preconditions: I metodi remove() e add() devono funzionare correttamente.
     * <br><br>Postconditions: Il valore di ritorno deve corrispondere al numero di elementi presenti all'interno della collezione.
     * <br><br>Expected results: 0 se la collezione è vuota, altrimenti il numero di elementi presenti nella collezione.
     * La dimensione della collezione deve variare man mano che inserisco / rimuovo elementi
     */
    @Test
    public void testSize() {
        assertEquals(0, coll.size());
        coll.add(1);
        assertEquals(1,coll.size());
        coll.add(3);
        coll.add(5);
        assertEquals(3,coll.size());
        coll.remove(1);
        assertEquals(2, coll.size());
    }

    /**
     * Test of {@link myAdapter.StackAdapter#contains(Object)}
     * <p>
     * <br><br>Summary: Il test verifica che il metodo ritorna true se il contenitore contiene l'elemento specificatp.
     * <br><br>Design test: dopo aver aggiunto due elementi, verifico la presenza di alcuni elementi e l'assenza di altri. 
     * <br><br>Description: dopo aver aggiunto due elementi, si verifica che il metodo ritorna true se uno dei due viene passato come parametro, altrimenti false.
	 * <br><br>Preconditions: il metodo add(Object) funziona correttamente.
     * <br><br>Postconditions: il metodo ritorna true se l'elemento passato come argomento è effettivamente contenuto, false altrimenti.
     * <br><br>Expected results: false se l'elemento non è presente nella collezione, true altrimenti.
     */
    @Test
    public void testContains() {
        coll.add(1);
        coll.add(3);
        coll.add(4.5);
        coll.add(null);
        coll.add("pippo");
        assertTrue("la collezione contiene 1",coll.contains(1));
        assertTrue("la coll contiene 4.5 ", coll.contains(4.50));
        assertTrue("nullpointerexception", coll.contains(null));
        assertFalse("coll non contiene 2", coll.contains(2));
        assertFalse("coll contiene pippo", coll.contains("pippo"));
    }



    /**
     * Test of {@link myAdapter.StackAdapter#toArray()}
     * <p>
     * <br><br>Summary: Il test verifica che il metodo toArray() ritorni un array contenente tutti gli oggetti presenti nella collezione, nell'ordine in cui sono inseriti.
     * <br><br>Design test: dopo che alcuni dati vengono inseriti, un array creato manualmente viene comparato con quello ritornato dal metodo
     * <br><br>Description: dopo aver inserito o rimosso alcuni dati nella collezione, viene verificato che l'oggetto ritornato da toArray() corrisponda all'array di Object
     * contenente gli elementi nello stesso ordine in cui sono stati inseriti nella collezione.
     * <br><br>Preconditions: il metodo add(Object) e remove() funzionano correttamente.
     * <br><br>Postconditions: il metodo restituisce un array di Objects contenente tutti gli elementi contenuti nella collection, nell'ordine in cui essi stono stati originariamente inseriti.
     * <br><br>Expected Results: l'array ottenuto dal metodo e quello creato manualmente devono combaciare.
     */
    @Test
    public void testToArray() {
        coll.add(1);
        coll.add(2);
        coll.add(3);
        coll.add(4);

        assertArrayEquals(new Object[]{1, 2, 3, 4}, coll.toArray());

        coll.remove(3);
        assertArrayEquals(new Object[]{1, 2, 4}, coll.toArray());

        coll = new StackAdapter();
        assertArrayEquals(new Object[0], coll.toArray());
    }

    /**
     * Test of {@link myAdapter.StackAdapter#toArray(Object[])}
     * <p>
     * <br><br>Summary: simile al test del metodo toArray () con la differenza che, se è abbastanza grande, l'array passato come parametro è quello che deve essere restituito.
     * <br><br>Design Test:niente viene inserito dentro la collezione e un array parzialmente occupato viene passato come parametro.
     * <br>Alcuni elementi sono inseriti nella collezione e un array riempito solo in parte di dimensione maggiore è passato come parametro.
     * <br>Alcuni elementi sono inseriti nella collezione e un array riempito solo in parte di dimensione minore è passato come parametro. Viene anche testato il corretto lancio dell'eccezione nel caso in cui il parametro sia null.
     * <br><br>Description: per tutte e tre le situazioni elencate nel Design, viene verificato usando un array creato manualmente.
     * <br><br>Preconditions: Il metodo add(Object) deve funzionare correttamente.
     * <br><br>Postconditions: l'array che viene restituito deve contenere tutti gli elementi presenti nella collezione sulla quale viene invocato il metodo
     * e nell'ordine in cui compaiono nella collezione.
     * <br><br>Expected results: il metodo ritorna l'array passato come parametro riempito con gli elementi della collezione, se grande abbastanza; altrimenti ne ritorna uno sufficientemente grande. Se la collezione è vuota, l'array fornito come parametro non viene modificato.
     */
    @Test
    public void testToArrayWithParameter() {
        Object[] array1 = null;

        try {
            coll.toArray(array1);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        array1 = new Object[]{1, 2, 3};
        assertArrayEquals(new Object[]{null, null, null}, coll.toArray(array1));

        coll.add(5);
        coll.add(6);
        assertArrayEquals(new Object[]{5, 6, null}, coll.toArray(array1));

        coll.add(7);
        coll.add(8);
        assertArrayEquals(new Object[]{5, 6, 7, 8}, coll.toArray(array1));
    }

    /**
     * Test of {@link myAdapter.StackAdapter#add(Object)}
     * <p>
     * <br><br>Summary: Il metodo testa il corretto funzionamento del metodo add(Object), che aggiunge l' elemento passato come parametro alla fine dello StackAdapter.
     * <br><br>Design test: gli elementi vengono aggiunti alla collezione. Poi l'array, restituito dalla chiamata a toArray(), viene poi confrontato con uno creato manualmente (contiene gli elementi che sono stati inseriti nella collezione, nell'ordine in cui è avvenuto l'inserimento).
     * <br><br>Description: Dopo aver aggiunto dei valori, viene verificato - confrontando l'array ritornato da toArray() con uno creato a mano - che gli elementi sono stati inseriti correttamente.
     * <br><br>Preconditions:il metodo toArray () deve funzionare correttamente.
     * <br><br>Postconditions: La collezione deve contenere gli oggetti passati come parametri, nell'ordine in cui sono inseriti.
     * <br><br>Expected results: gli elementi dell'array devono essere nella collezione nello stesso ordine in cui sono inseriti.
     */
    @Test
    public void testAdd() {
        coll.add(3);
        coll.add(null);
        coll.add(6);
        coll.add("last");

        assertArrayEquals(new Object[]{3,null,6,"last"}, coll.toArray());
    }

    /**
     * Test of {@link myAdapter.StackAdapter#remove(Object)}
     * <p>
     * <br><br>Summary: Verifica che il metodo testato rimuova l'elemento passato come parametro. Se questo non è presente nella collezione, restituisce false.
     * <br><br>Design test:
     * <br>Il metodo viene chiamato sulla collezione vuota.
     * <br>Il metodo viene invocato su una collezione non vuota; ma passando come argomento un elemento non contenuto nella collezione.
     * <br>Il metodo viene invocato su una collezione non vuota, passando come argomento un elemento contenuto nella collezione.
     * <br><br>Description: Il metodo viene invocao su una collezione vuota, successivamente vengono aggiunti elementi alla collezione, anche ripetuti, e il metodo viene invocato, due volte con elementi presenti nel contenitore e una volta con un elemento non presente. Infine viene verificato che la collezione abbia il numero di elementi atteso e nell'ordine corretto (Così da verificare che sia stata rimossa la prima occorrenza dell'elemento specifico). 
     * <br><br>Preconditions: i metodi add (Object ) e toArray () devono funzionare correttamente.
     * <br><br>Postconditions: dopo aver invocato il metodo, la collezione non deve contenere l'elemento passato come parametro, restituendo true. Se la collezione non viene modficata, restituisce false.
     * <br><br>Expected results: dopo aver rimosso lo specifico elemento, la dimensione della collezione deve essere decrementata di uno, la prima occorrenza dell'elemento passato come parametro deve essere stata rimossa e deve restituire true. Se l'elemento non è presente ritorna false.
     */
    @Test
    public void testRemove() {
        assertFalse(coll.remove(new Object()));

        coll.add(2);
        coll.add(3);
        coll.add(2);
        coll.add(null);
        assertFalse(coll.remove(1));
        assertTrue(coll.remove(2));
        assertTrue(coll.remove(null));
        assertEquals(2, coll.size());
        assertArrayEquals(new Object[]{3, 2}, coll.toArray());

    }

    /**
     * Test of
     * {@link myAdapter.StackAdapter#containsAll(HCollection)}
     * <p>
     * <br><br>Summary: Il test verifica che la collezione contiene tutti gli elementi della collezione passata come parametro.
     * <br><br>Design test: gli elementi sono aggiunti alla collezione sulla quale il metodo verrà invocato.
     * Successivamente verrà creata una collezione contenente tutti gli elementi della collezione principale e un'altra nella quale vengono inseriti sia elementi presenti che assenti nella collezione principale.
     * Viene poi invocato il metodo e si verifica che la collezione principale contenga tutti gli elementi della prima collezione creata manualmente e non tutti quelli della seconda.
     * Viene anche testato il lancio di una eccezione nel caso la collezione passata come parametro sia non valida (=null).
     * <br><br>Description: Dopo aver testato il lancio dell'eccezione, sono stati aggiunti degli elementi alla collezione principale e sono state create due collezioni, una passando come parametro al costruttore la collezione principale, l'altra aggiungendo manualmente degli elementi
     * sia contenuti che non nella collezione principale. Poi verifica che con la prima collezione il risultato sia true e nel secondo sia false.
     * <br><br>Preconditions: Il metodo add(Object obj) deve funzionare correttamente.
     * <br><br>Postconditions: Il metodo deve restituire true se la collezione contiene tutti gli elementi della collezione passata come parametro al metodo.
     * <br><br>Expected results: Il risultato risulta true se e solo se la collezione passata come parametro contiene un sottoinsieme degli elementi della collezione principale.
     */
    @Test
    public void testContainsAll() {
        try {
            stack.containsAll(null);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        coll.add(1);
        coll.add(2);
        coll.add(3);
        coll.add(4);
        coll.add(null);

        HCollection testColl1 = new StackAdapter(coll);

        assertTrue(coll.containsAll(testColl1));

        HCollection testColl2 = new StackAdapter();
        testColl2.add(1);
        testColl2.add(8);
        testColl2.add(3);
        testColl2.add(5);
        testColl2.add(null);
        assertFalse(coll.containsAll(testColl2));

    }

    /**
     * Test of {@link myAdapter.StackAdapter#addAll(HCollection)}
     * <p>
     * <br><br>Summary: Il test verifica che il metodo AddAll (HCollection) inserisca tutti gli elementi contenuti nella collezione passata come parametro nella collezione su cui il metodo è invocato.
     * <br><br>Design test: inizialmente viene verificato che il metodo lanci l'eccezione se la collezione ha valore null.
     * <br>Poi viene verificato che essa ritorni false nel caso in cui la collezione passata come parametro sia vuota.
     * <br>Poi viene creata una collezione in cui vengono successivamente inseriti degli elementi.
     * <br>Questa collezione viene usata come parametro del metodo da testare, invocato sulla collezione principale.
     * <br>Successivamente si verifica che effettivamente tutti gli elementi siano presenti nella collezione principale.
     * <br><br>Description:
     * <br>Il metodo viene invocato sulla collezione con valore null per verificare il lancio dell'eccezione.
     * <br>Il metodo ha come parametro una collezione vuota, pertanto ritorna false.
     * <br>Il metodo viene invocato su una collezione non vuota.
     * <br><br>Preconditions: i metodi add (Object obj) e containsAll (HCollection coll) devono funzionare correttamente.
     * <br><br>Postconditions: tutti gli elementi della collection devono appartene anche alla collection principale.
     * <br><br>Expected results: la collezione contiene tutti gli elementi della collezione passata come parametro.
     */
    @Test
    public void testAddAll() {
        HCollection testColl = null;

        try {
            stack.addAll(0,testColl);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        HCollection testColl1 = new StackAdapter();
        assertFalse(coll.addAll(testColl1));

        HCollection testColl2 = new StackAdapter();
        testColl2.add(1);
        testColl2.add(2);
        testColl2.add(3);

        coll.addAll(testColl2);
        assertTrue(coll.containsAll(testColl2));

    }

    /**
     * Test of {@link myAdapter.StackAdapter#addAll(int, HCollection)}
     * <p>
     * <br><br>Summary: Il test verifica che il metodo AddAll (int, HCollection) inserisca nello StackAdapter tutti gli elementi contenuti nella collezione, passata come parametro, a partire dall'indice specificato.
     * <br><br>Design test: inizialmente viene verificato che il metodo lanci l'eccezione se la collezione ha valore null o se l'indice non è valido.
     * <br>Poi viene verificato che essa ritorni false nel caso in cui la collazione passata come parametro sia vuota.
     * <br>Poi viene creata una collezione in cui vengono successivamente inseriti degli elementi.
     * <br>Questa collezione viene usata come parametro del metodo da testare, insieme all'indice di partenza, invocandolo sullo stack vuoto.
     * <br>Successivamente si verifica che effettivamente tutti gli elementi siano presenti nello stack.
     * <br><br>Description:
     * <br>Il metodo viene invocato sulla collezione con valore null per verificare il lancio dell'eccezione.
     * <br>Il metodo viene invocato sulla collezione con valore dell'indice non valido per verificare il lancio dell'eccezione.
     * <br>Il metodo ha come parametro una collezione vuota, pertanto ritorna false.
     * <br>Il metodo viene invocato passando come parametro una collezione non vuota e un indice valido.
     * <br><br>Preconditions: i metodi add (Object obj) e containsAll (HCollection coll) devono funzionare correttamente
     * <br><br>Postconditions: tutti gli elementi della collection devono appartene anche alla collection principale.
     * <br><br>Expected results: la collezione contiene tutti gli elementi della collezione passata come parametro e, essendo gli elementi inseriti nello stack vuoto, gli array generati chiamando la funzione toArray() su entrambi i contenitori risultano uguali.
     */
    @Test
    public void testAddAllIndex() {
        HCollection testColl = null;

        try {
            stack.addAll(0,testColl);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        try {
            stack.addAll(stack.size(),testColl);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        HCollection testColl1 = new StackAdapter();
        assertFalse(stack.addAll(0,testColl1));
        assertArrayEquals(stack.toArray(), testColl1.toArray());

        HCollection testColl2 = new StackAdapter();
        testColl2.add(1);
        testColl2.add(2);
        testColl2.add(3);

        stack.addAll(0,testColl2);
        assertTrue(stack.containsAll(testColl2));
        assertArrayEquals(stack.toArray(), testColl2.toArray() );
    }



    /**
     * Test of {@link myAdapter.StackAdapter#removeAll(HCollection)}
     * <p>
     * <br><br>Summary: Il test verifica il metodo che rimuove dalla collezione principale tutti gli elementi presenti nella collezione passata come parametro.
     * <br><br>Design test:
     * <br>Si verifica che se viene passata una collezione vuota, la collezione principale non viene modificata.
     * <br>Verifica che se viene passata come parametro una collezione i cui elementi non sono nella collezione principale, quest'ultima non viene modificata.
     * <br>verifica che, se una collezione con elementi in comune viene passata, essi vengono eliminati.
     * <br><br>Description: Viene verificato il corretto lancio dell'eccezione nel caso in cui il parametro è null.
     * Successivamente, dopo la verifica della corretta operazione con collezioni vuote, vengono aggiunti elementi alla collezione principale e un elemento nell'altra collezione, che non appartiene a quella principale.
     * Dopo aver verificato che il valore di ritorno è false, viene aggiunto un altro elemento, presente anche nella collezione principale. Si verifica che l'invocazione al metodo testato ritorni true, e che la collezione principale sia stata modificata correttamente.
     * <br><br>Preconditions:
     * <br>La collezione passata come parametro non deve essere null.
     * <br>I metodi add(Object ) e toArray() devono funzionare correttamente.
     * <br><br>Postconditions: Nella collezione principale non ci devono essere elementi contenuti nella collezione passata come parametro.
     * <br><br>Expected results: false se la collezione passata come parametro è vuota, true se almeno un elemento della collezione principale viene eliminato.
     */
    @Test
    public void testRemoveAll() {
        HCollection testColl = new StackAdapter();

        try {
            coll.removeAll(null);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }

        assertFalse(coll.removeAll(testColl));

        coll.add(1);
        coll.add(4);
        coll.add(3);
        coll.add(2);

        testColl.add(5);
        assertFalse(coll.removeAll(testColl));
        assertArrayEquals(new Object[]{1, 4, 3, 2}, coll.toArray());


        testColl.add(2);

        assertTrue(coll.removeAll(testColl));
        assertArrayEquals(new Object[]{1,4,3}, coll.toArray());

        coll.add(null);
        testColl.add(null);
        coll.removeAll(testColl);
        assertArrayEquals(new Object[]{1,4,3}, coll.toArray());
    }

    /**
     * Test of {@link myAdapter.StackAdapter#retainAll(HCollection)}
     * <p>
     * <br><br>Summary: verifica che il metodo rimuova dalla collezione principale tutti gli elementi non in comune con la collezione passata come parametro.
     * <br><br>Design test:
     * <br>si verifica che il metodo restituisca false, se la collezione principale e quella passata come parametro sono vuote.
     * <br>si verifica che il metodo restituisca true quando la collezione principale è piena e quella passata come parametro vuota. In questo caso viene anche verificato che tutti gli elementi della collezione principale vengono rimossi.
     * <br>Si verifica il caso in cui le collezioni hanno un elemento in comune e il caso in cui non hanno alcun elemento in comune.
     * <br><br>Description: la collezione principale è sempre riempita con gli stessi valori; mentre una seconda collezione, quella passata come parametro, contiene sempre una combinazione diversa di valori. Così è possibile verificare che il metodo lavori correttamente nei diversi casi.
     * <br><br>Preconditions:
     * <br>La collezione passata come parametro non può essere null, ma può essere vuota o con valori ripetuti. I metodi size(), add(Object), toArray() e clear()  devono funzionare correttamente.
     * <br>
     * <br><br>Postconditions: la collezione principale deve contenere solo gli elementi contenuti anche nella collezione passata come parametro e deve tornare true se la collezione principale viene modificata.
     * <br><br>Expected results: la collezione principale deve avere dimensione zero se non ci sono elementi in comune, altrimenti ci devono essere solo gli elementi in comune con l'altra collezione.
     */
    @Test
    public void testRetainAll() {
        HCollection testColl = new StackAdapter();

        assertFalse(coll.retainAll(testColl));


        coll.add(1);
        coll.add(2);
        coll.add(3);
        assertTrue(coll.retainAll(testColl));
        assertEquals(0, coll.size());

        coll.add(1);
        coll.add(2);
        coll.add(3);
        testColl.add(2);
        assertTrue(coll.retainAll(testColl));
        assertArrayEquals(new Object[]{2}, coll.toArray());
        coll.clear();

        coll.add(1);
        coll.add(1);
        coll.add(1);
        assertTrue(coll.retainAll(testColl));
        assertEquals(0, coll.size());
    }

    /**
     * Test of {@link myAdapter.StackAdapter#clear()}
     * <p>
     * <br><br>Summary: il test verifica che il metodo elimini tutti gli elementi nella collezione in cui viene chiamato.
     * <br><br>Design test:
     * <br>Viene verificato che il metodo funzioni anche nel caso in cui la collezione sia vuota.
     * <br>Successivamente viene verificato che il metodo funzioni anche nel caso in cui la collezione non è vuota.
     * <br><br>Description: il metodo viene invocato sulla collezione, inizialmente vuota. Successivamente essa viene riempita e il metodo viene invocato. Per verificare che la collezione sia vuota, si verifica che la sua dimensione sia uguale a zero.
     * <br><br>Preconditions:
     * <br>La collezione può essere sia vuota che riempita. I metodi add(Object) e size() devono funzionare correttamente.
     * <br>
     * <br><br>Postconditions:La collezione non deve contenere nessun elemento.
     * <br><br>Expected results: La dimensione della collezione uguale a zero.
     */
    @Test
    public void testClear() {
       coll.clear();
       assertEquals(0, coll.size());

       coll.add(13);
       coll.add(2);
       coll.add("hulk");
       coll.add("spacca");
       coll.clear();
       assertEquals(0, coll.size());

    }

    /**
     * Test of {@link myAdapter.StackAdapter#equals(Object)}
     * <p>
     * <br><br>Summary: Il metodo deve ritornare true se la dimensione delle due collezioni è uguale e gli stessi elementi sono contenuti nello stesso ordine.
     * <br><br>Design test: Vengono inseriti gli stessi elementi e confrontati sia nella collezione principale che in quella creata. Viene testato anche l'inserimento di tipi incompatibili.
     * <br><br>Description:
     * <br>Dopo la creazione del nuovo stack, gli stessi elementi vengono inseriti in entrambi gli stack nello stesto ordine. Poi i due contenitori vengono confrontati. Poi viene aggiunto un elemento in uno dei due e vengono confrontati nuovamente.
     * <br>Il secondo stack viene poi svuotato e riempito con gli stessi elementi, ma in ordine opposto. I contenitori vengono nuovamente confrontati.
     * <br>Successivamente il secondo stack viene riempito con elementi, sia contenuti che non, nello stack principale e viene svolto un confronto.
     * <br>Poi la collezione principale viene riempita con elementi contenuti nel nuovo stack, ma non nello stesso ordine in cui sono contenuti nello stack e viene svolto un confronto.
     * <br>Infine confronta lo stack con un array di Object.
     * <br><br>Preconditions: i metodi add(Object ) e clear() devono funzionare correttamente
     * <br><br>Postconditions: i metodi devono ritornare true se i due contenitori hanno gli stessi elementi nello stesso ordine e deve ritornare false se i due oggetti sono incompatibili -difatti se gli oggetti non sono dello stesso tipo, non possono essere uguali-.
     * <br><br>Expected results: true solo nel primo caso descritto e nel caso di elementi compatibili, false negli altri casi descritti.
     */
    @Test
    public void testEquals() {
        HList stack2 = new StackAdapter();
        stack.add(1);
        stack.add(2);
        stack.add(3);

        stack2.add(1);
        stack2.add(2);
        stack2.add(3);

        assertTrue(stack.equals(stack2));

        stack2.add(4);
        assertFalse(stack.equals(stack2));

        stack2.clear();
        stack2.add(3);
        stack2.add(2);
        stack2.add(1);
        assertFalse(stack.equals(stack2));

        stack2.clear();
        stack2.add(1);
        stack2.add(4);
        stack2.add(3);
        assertFalse(stack.equals(stack2));


        coll.add(1);
        coll.add(3);
        coll.add(2);
        assertFalse(stack.equals(coll));

        assertFalse(stack.equals(new Object[]{1, 2, 3}));
    }

    /**
     * Test of {@link myAdapter.StackAdapter#hashCode()}
     * <p>
     * <br><br>Summary: verifica del calcolo dell'hash Code
     * <br><br>Design test: vengono inseriti gli stessi dati nello stack principale e in uno appena creato. L'hash Code deve essere uguale. Poi il metodo viene ritestato invertendo l'ordine degli elementi.
     * <br><br>Description: dopo aver inserito gli elementi nello stack principale, ne viene creato un secondo che contiene gli stessi elementi del primo nello stesso ordine. Il risultato deve essere lo stesso. Successivamente viene invertito l'ordine di due elementi e viene ricalcolato l'hash Code. Esso risulta diverso da quello dello stack principale.
     * <br><br>Preconditions: add(Object) deve funzionare correttamente
     * <br><br>Postconditions: solo se gli oggetti confrontati hanno gli stessi elementi disposti nello stesso ordine, l'hash code deve essere identico.
     * <br><br>Expected results: l'hash code di due oggetti coincide quando gli elementi sono uguali e la loro disposizione all'interno dei due oggetti è la stessa.
     */
    @Test
    public void testHashCode() {
        stack.add(1);
        stack.add(2);
        stack.add(3);

        HList list2 = new StackAdapter();
        list2.add(1);
        list2.add(2);
        list2.add(3);

        assertEquals(list2.hashCode(), stack.hashCode());

        list2.clear();
        list2.add(1);
        list2.add(3);
        list2.add(2);
        assertNotEquals(list2.hashCode(),stack.hashCode());

        list2.clear();
        assertNotEquals(list2.hashCode(), stack.hashCode());

        HCollection coll = new StackAdapter();
        coll.add(1);
        coll.add(2);
        coll.add(3);
        assertEquals(coll.hashCode(), stack.hashCode());
    }

    /**
     * Test of {@link myAdapter.StackAdapter#get(int)}
     * <p>
     * <br><br>Summary: test che verifica il corretto funzionamento della funzione get().
     * <br><br>Design test: dopo aver inserito alcuni elementi, si verifica che il metodo restituisca l'elemento nella posizione passata come parametro. Viene anche verificato il corretto lancio di un'eccezione.
     * <br><br>Description: Dopo aver inserito manualmente gli elementi nello stack, il metodo viene invocato passando come parametro la dimensione del contenitore. Poi viene utilizzato un ciclo for, per verificare che il metodo restituisca i valori attesi.
     * <br><br>Preconditions: l'indice inserito non deve essere minore di zero o più grande del numero di elementi inseriti (compreso).
     * <br><br>Postconditions: l'elemento restituito deve essere quello contenuto nella posizione specificata nell'argomento del metodo.
     * <br><br>Expected results: il ciclo for termina correttamente
     */
    @Test
    public void testGet() {
        stack.add(0);
        stack.add(1);
        stack.add(2);
        stack.add(3);
        stack.add(4);
        stack.add(5);

        try {
            stack.get(stack.size());
            throw new NullPointerException();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        for (int i = 0; i < 5; i++)
            assertEquals(i, stack.get(i));
    }

    /**
     * Test of {@link myAdapter.StackAdapter#set(int, Object)}
     * <p>
     * <br><br>Summary: il metodo deve sostituire correttamente l'elemento nella posizione, passata come parametro, con l'oggetto specificato.
     * <br><br>Design test: dopo aver creato un secondo stack, essi vengono riempiti con n valori. Successivamente si verifica che gli elementi vengano sostituiti correttamente.
     * <br><br>Description: Dopo aver riempito entrambi gli stack (quello principale e quello appena creato), il metodo viene invocato passando come parametro un indice non valido, così da poter verificare il corretto lancio dell'eccezione. Successivamente il metodo viene invocato in un ciclo for, così da modificare completamente il contenuto dello stack principale e renderlo uguale al secondo stack.
     * <br><br>Preconditions: i metodi add(Object), size() e equals() devono funzionare correttamente.
     * <br><br>Postconditions: lo stack principale diviene uguale al secondo stack, grazie al corretto funzionamento del metodo nel ciclo for.
     * <br><br>Expected results: l'eccezione, nel caso di indici non validi, deve essere lanciata correttamente e il metodo equals deve ritornare true.
     */
    @Test
    public void testSet() {

        HList list2 = new StackAdapter();
        stack.add(0);
        stack.add(0);
        stack.add(0);
        stack.add(0);
        stack.add(0);

        list2.add(1);
        list2.add(2);
        list2.add(3);
        list2.add(4);
        list2.add(5);

        try {
            stack.set(stack.size(), "error");
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        for (int i = 0; i < stack.size(); i++)
            stack.set(i, i + 1);

        assertEquals(stack, list2);
    }

    /**
     * Test of {@link myAdapter.StackAdapter#add(int, Object)}
     * <p>
     * <br><br>Summary: il metodo deve inserire l'oggetto nella posizione specificata.
     * <br><br>Design test: nello stack non vuoto vengono sostituiti degli elementi e viene verificato che la dimensione si incrementi di uno e che la sostituzione sia avvenuta correttamente.
     * <br><br>Description: il metodo verifica che la chiamata alla funzione add (Integer, Object) inserisca l'elemento speficicato nella posizione specificata. Questo viene verificato ad ogni inserimento, confrontando l'array restituito da toArray() con un array creato sul momento, contenente gli elementi nella posizione attesa. In particolare, viene verificato il corretto funzionamento del metodo nel caso di inserimento all'inizio e alla fine dello stack. Viene anche verificato il corretto lancio dell'eccezione.
     * <br><br>Preconditions:
     * <br>L'indice deve essere positivo e minore o uguale della dimensione dello stack.
     * <br>I metodi size(), toArray() e clear() devono funzionare correttamente.
     * <br><br>Postconditions: Se la posizione è valida, gli elementi devono essere aggiunti in quella specifica locazione.
     * <br><br>Expected results: I due array (quello creato a mano e quello generato da toArray()) devono risultare uguali.
     */
    @Test
    public void testAddIndex() {

        stackWithData.add(0, 0);
        assertEquals(6, stackWithData.size());
        assertArrayEquals(new Object[]{0, 1, 2, 3, 4, 5}, stackWithData.toArray());

        stackWithData.add(3, 2.5);
        assertEquals(7, stackWithData.size());
        assertArrayEquals(new Object[]{0, 1, 2, 2.5, 3, 4, 5}, stackWithData.toArray());

        stackWithData.add(stackWithData.size(), 5.5);
        assertEquals(8, stackWithData.size());
        assertArrayEquals(new Object[]{0, 1, 2, 2.5, 3, 4, 5, 5.5}, stackWithData.toArray());

        try {
            stackWithData.add(-1, "Exception");
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        stackWithData.clear();
        stackWithData.add(0, "wiped");
        assertArrayEquals(new Object[]{"wiped"}, stackWithData.toArray());

    }

    /**
     * Test of {@link myAdapter.StackAdapter#remove(int)}
     * <p>
     * <br><br>Summary: Si verifica che il metodo rimuova l'elemento nella posizione specificata.
     * <br><br>Design test: dopo aver aggiunto valori allo stack, viene creato un nuovo stack e si verifica che essi sono differenti. Successivamente si eliminano alcuni elementi dallo stack principale e il confronto viene nuovamente svolto.
     * <br><br>Description: Dopo aver aggiunto valori allo stack principale e aver creato un array che contiene solo alcuni elementi contenuti in quello principale, si verifica che risulteranno uguali solo nel momento in cui gli elementi non in comune vengono rimossi dallo stack principale. Viene testato anche il lancio dell'eccezione, nel caso in cui lo stack è vuoto e nel caso di indice non valido.
     * <br><br>Preconditions: I metodi add (Object), equals (Object) e size() devono funzionare correttamente.
     * <br><br>Postconditions: vengono rimossi solo gli elementi situati nella posizione passata come parametro.
     * <br><br>Expected results: Gli stack devono risultare uguali.
     */
    @Test
    public void testRemoveIndex() {
        try {
            stack.remove(0);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        stack.add(1);
        stack.add(2);
        stack.add(3);
        stack.add(4);
        stack.add(5);
        stack.add(6);

        try {
            stack.remove(stack.size());
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

        HList stack2 = new StackAdapter();
        stack2.add(1);
        stack2.add(3);
        stack2.add(5);

        assertNotEquals(stack,stack2);

        stack.remove(5);
        stack.remove(3);
        stack.remove(1);

        assertEquals(stack, stack2);

    }

    /**
     * Test of {@link myAdapter.StackAdapter#indexOf(Object)}
     * <p>
     * <br><br>Summary: Il metodo verifica che l'indice restituito dal metodo indexOf(Object) sia corretto (deve corrispondere all'indice della prima occorrenza dell'oggetto obj)
     * <br><br>Design test: il metodo viene chiamato sullo stack vuoto. Dopo vengono inseriti alcuni elementi (non tutti diversi tra di loro) e il metodo viene chiamato, passando come parametro gli elementi duplicati. Viene anche considerato il caso in cui l'elemento non sia presente nello stack.
     * <br><br>Description: inizialmente il metodo viene invocato su uno stack vuoto. Successivamente vengono inseriti dei valori nello stack e il metodo viene invocato più volte. Il metodo viene invocato nei casi in cui l'elemento specificato appartiene alla collezione, ma non è ripetuto; nel caso in cui è ripetuto e nel caso in cui non è presente.
     * <br><br>Preconditions: lo stack può essere vuoto; ma non può essere null.
     * <br><br>Postconditions: viene restituito l'indice della prima occorrenza dell'elemento passato come parametro, altrimenti -1.
     * <br><br>Expected results: -1 per una lista vuota; l'indice della prima occorrenza dell'elemento (0-based e partendo dall'inizio dello stack e non dalla cima).
     */
    @Test
    public void testIndexOf() {
        assertEquals(-1, stack.indexOf(9));

        stack.add(1);
        stack.add(2);
        stack.add(3);
        stack.add(1);
        stack.add(5);

        assertEquals(1, stack.indexOf(2));
        assertEquals(0, stack.indexOf(1));
        assertEquals(-1, stack.indexOf(6));
    }

    /**
     * Test of {@link myAdapter.StackAdapter#lastIndexOf(Object)}
     * <p>
     * <br><br>Summary: viene testato il metodo che restituisce l'indice dell'ultima occorrenza dell'elemento passato come parametro.
     * <br><br>Design test: viene verificato il caso in cui lo stack è vuoto. Dopo vengono inseriti degli elementi nello stack e viene verificato che il valore restituito dal metodo (passando come parametro gli elementi duplicati) sia quello atteso.
     * <br><br>Description: il primo test viene svolto su uno stack vuoto. Le successive invocazioni vengono svolte dopo aver inserito dei valori, alcuni dei quali sono anche ripetuti. Si considerano i casi di elementi ripetuti, non ripetuti e non presenti nello stack.
     * <br><br>Preconditions: il metodo add(Object) deve funzionare correttamente, lo stack può essere vuoto; ma non null.
     * <br><br>Postconditions: -1 se l'elemento non è presente nello stack, altrimenti l'indice dell'ultimo elemento uguale a quello passato come parametro.
     * <br><br>Expected results: -1 per stack vuoto o elemento non trovato; l'indice dell'ultima occorrenza dell'elemento specificato (0-based e partendo dall'inizio dello stack, non dalla cima).
     */
    @Test
    public void testLastIndexOf() {
        assertEquals(-1, stack.lastIndexOf(9));

        stack.add(1);
        stack.add(2);
        stack.add(3);
        stack.add(1);
        stack.add(5);

        assertEquals(1, stack.lastIndexOf(2));
        assertEquals(3, stack.lastIndexOf(1));
        assertEquals(-1, stack.lastIndexOf(6));
    }


    /**Test of {@link myAdapter.StackAdapter#iterator()}
     * <p>
     * <br><br>Summary:viene verificato il corretto funzionamento del metodo iterator().
     * <br><br>Description: viene creato un array della stessa dimensione dello StackAdapter e viene invocato l'iteratore alla collezione. Viene verificato il corretto funzionamento tramite un ciclo while. Successivamente viene svolto un confronto tra l'array, generato da toArray(), e uno creato tramite il ciclo while. Vengono considerati sia il caso in cui l'iteratore punta a uno StackAdapter non vuoto, che quello in cui punta a una collezione vuota. Infine, sempre utilizzando l'iteratore e un ciclo while, l'array viene svuotato e si verifica che la dimensione sia effettivamente uguale a zero.
     * <br><br>Preconditions:i metodi size () e toArray () devono funzionare correttamente.
     * <br><br>Postconditions: non vengono lanciate eccezioni
     * <br><br>Expected results: non vengono lanciate eccezioni
     */
    @Test
    public void testIteratorMethods() {
        Object[] testArray = new Object[stackWithData.size()];
        HIterator stackIterator = stackWithData.iterator();

        int i = 0;
        while (stackIterator.hasNext()) {
            testArray[i] = stackIterator.next();
            i++;
        }
        assertArrayEquals(stackWithData.toArray(), testArray);

        Object[] testArray2 = new Object[coll.size()];
        HIterator collIterator = coll.iterator();

        i = 0;
        while (collIterator.hasNext()) {
            testArray2[i] = collIterator.next();
            i++;
        }
        assertArrayEquals(coll.toArray(), testArray2);

        collIterator = coll.iterator();
        i = 0;
        while (collIterator.hasNext()) {
            testArray[i] = collIterator.next();
            collIterator.remove();
            i++;
        }
        assertEquals(0, coll.size());
    }


    /**
     * Test of {@link myAdapter.StackAdapter#listIterator(int)}
     * <p>
     * <br><br>Summary: verifica che il metodo listIterator(int) restituisca correttamente un iteratore inizializzato alla locazione passata come parametro.
     * <br><br>Design test: l'iteratore viene invocato su una lista non vuota e viene verificato che l'oggetto ritornato dalla prima invocazione di next() corrisponda all'elemento di indice successivo a quello fornito. Viene verificato anche il corretto lancio dell'eccezione, nel caso in cui next() venga invocato alla fine della lista. Viene verificato il corretto lancio dell'eccezione nel caso di indice non valido.
     * <br><br>Description: per verificare che il metodo funzioni correttamente, viene creato un iteratore su una lista non vuota, e il metodo next () viene invocato per verificare che il valore ritornato è quello atteso. Next() viene anche chiamato su un iteratore alla posizione n+1 (n = dimensione della lista).
     * <br>Alla fine viene verificato che il metodo viene eseguito passando un indice non valido (più grande della dimensione della lista), per verificare che venga lanciata l'eccezione.
     * <br><br>Preconditions: i metodi next() dell'iteratore e size () della lista devono funzionare correttamente.
     * <br><br>Postconditions: l'indice deve indicare l'elemento ritornato dall'invocazione di next().
     * <br><br>Expected results: l'elemento ritornato è quello corrispondente a quello restituito da next().
     */
    @Test
    public void testListIteratorIndex() {
        HListIterator listIterator = stackWithData.listIterator(3);
        assertEquals(4, listIterator.next());

        listIterator = stackWithData.listIterator(stackWithData.size());
        try {
            listIterator.next();
            throw new Exception();
        } catch (Exception e) {
            assertEquals(NoSuchElementException.class, e.getClass());
        }

        try {
            listIterator = stackWithData.listIterator(stackWithData.size() + 1);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }
    }

    /**
     * Test of {@link myAdapter.StackAdapter#subList(int, int)}
     * <p>
     * <br><br>Summary: il metodo verifica la corretta creazione di una sottolista della lista di partenza.
     * <br><br>Design test: il metodo viene invocato passando come parametro due indici validi e viene verificato confrontando l'array (restituito da toArray()) con un array contenente i valori che la sottolista dovrebbe contenere. Viene testato anche con una sottolista vuota (se i due indici passati come parametro sono uguali), e il corretto lancio di una eccezione se gli indici non sono validi.
     * <br><br>Description: Le verifiche al metodo vengono svolte sulla dimensione e gli elementi della sottolista.
     * <br>Prima viene verificato che passando come parametri due indici uguali, la sottolista risulti vuota.
     * <br>Poi, presi due indici diversi e validi, si verifica che la sottolista contenga gli elementi compresi tra quegli indici (l'ultimo escluso).
     * <br>Infine viene verificato il corretto lancio di una eccezione nel caso di indici non validi
     * <br><br>Preconditions: i metodi size() e toArray() devono funzionare correttamente.
     * <br><br>Postconditions: La lista principale non viene modificata dopo l'invocazione del metodo. Con gli indici from = 0 e to = size() la sottolista risulta uguale alla lista.
     * <br><br>Expected results: la sottolista contiene tutti gli elementi della lista nell'intervallo [from, to)
     */
    @Test
    public void testSubList() {
        HList subList = stackWithData.subList(2, 2);
        assertEquals(0, subList.size());

        subList = stackWithData.subList(2, 4);
        assertEquals(2, subList.size());
        assertArrayEquals(new Object[]{3, 4}, subList.toArray());

        subList = stackWithData.subList(0, stackWithData.size());
        assertEquals(stackWithData, subList);

        stackWithData.clear();
        subList = stackWithData.subList(0, 0);
        try {
            subList = stackWithData.subList(2, 3);
            throw new Exception();
        } catch (Exception e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
        }

    }

    /**
     *
     * <br><br>Summary: vengono verificati i seguenti metodi:
     * Test of {@link myAdapter.StackAdapter#empty()}
     * Test of {@link myAdapter.StackAdapter#peek()}
     * Test of {@link myAdapter.StackAdapter#pop()}
     * Test of {@link myAdapter.StackAdapter#push(Object)}
     * Test of {@link myAdapter.StackAdapter#search(Object)}
     * <br><br>Design test: viene verificato che il metodo empty() funzioni. Esso viene invocato sia sullo stack vuoto che con quello non vuoto. Poi lo stack vuoto viene riempito e viene verificato su di esso il corretto inserimento degli elementi. Successivamente viene invocato il metodo peek() e si verifica che lo stack non sia stato modificato. Poi viene verificato il funzionamento di search() con tutti gli elementi presenti nello stack e con elementi non presenti.Infine viene verificato che, dopo la chiamata a pop(), lo stack sia stato modificato. Le verifiche alle modifiche dello stack vengono svolte tramite la chiamata a toArray(), confrontando l'array che viene restituito con uno creato a mano, i cui elementi sono quelli attesi.
     * <br><br>Description:
     * <br>Viene invocato il metodo empty su uno stack vuoto e uno non vuoto.
     * <br>Successivamente lo stack vuoto viene riempito e si verifica che non risulti più vuoto e che gli elementi siano stati inseriti nel modo corretto.
     * <br>Poi viene chiamato il metodo peek(), così da verificare che restituisca l'elemento in cima allo stack; ma non lo rimuova.
     * <br>Poi vengono testati i metodi search(Object) e pop(), il primo verificando che ritorni la distanza dalla cima dello stack (1-based) o -1 se l'elemento non è presente nello stack; il secondo confrontando l'array generato da toArray() con quello che ci si aspetta di ottenere.
     * <br><br>Preconditions: lo stack può essere vuoto, ma non null.
     * <br><br>Postconditions: lo stack viene modificata con la chiamata a push(Object) e pop() e rimane invariata nella chiamata degli altri metodi
     * <br><br>Expected results:
     * <br>Il metodo push() inserisce gli elementi dalla cima dello stack.
     * <br>il metodo pop() elimina l'elemento in cima allo stack e lo restituisce.
     * <br> Il metodo search(Object) restituisce la distanza dell'oggetto dalla cime dello stack (1-based), se presente; altrimenti -1.
     * <br>Il metodo peek() restituisce l'elemento in cima allo stack; ma non lo rimuove.
     * <br> Il metodo empty() mi dice se lo stack è vuoto o meno.
     */
    @Test
    public void testStack(){
        assertTrue(stack.empty());
        assertFalse(stackWithData.empty());
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        assertFalse(stack.empty());
        assertArrayEquals(new Object[]{1,2,3,4,5}, stack.toArray());

        assertEquals(5,stack.peek());
        assertArrayEquals(new Object[]{1,2,3,4,5}, stack.toArray());

        assertEquals(1,stack.search(5));
        assertEquals(2,stack.search(4));
        assertEquals(3,stack.search(3));
        assertEquals(4,stack.search(2));
        assertEquals(5,stack.search(1));
        assertEquals(-1,stack.search(10));
        assertEquals(-1,stack.search(9));

        assertEquals(5, stack.pop());
        assertEquals(4, stack.peek());
        assertArrayEquals(new Object[]{1,2,3,4}, stack.toArray());

    }
}
