package za.co.bvr.forth.exceptions;

/**
 *
 * @author nickm
 */
public class VerbNotInDictionaryException extends Exception {

    public VerbNotInDictionaryException() {
    }

    public VerbNotInDictionaryException(String verbName) {
        super(verbName);
    }
    
}
