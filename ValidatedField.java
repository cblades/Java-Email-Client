package edu.wcu.cs.cs363.team3.project02;

import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

/**
 * A JTextField that validates it's content with the given FieldVerifier
 * object.
 *
 * @author Chris Blades
 * @author Benny Reese
 * @author Don Coffin
 *
 * @version 7/4/2010
 */
public class ValidatedField extends JTextField {

    /**
     * Verifier to use when validating this field
     */
    private FieldVerifier verifier;
    
    /**
     * Wether or not this field currently contains valid text
     */
    private boolean valid;

    /**
     * Creates a new ValidatedField that validates it's content against the
     * given FieldVerifier.
     *
     * @param verifier used to validate contents of this field
     */
    public ValidatedField(FieldVerifier verifier) {
        super();
        this.verifier = verifier; 
        setState();
        getDocument().addDocumentListener(new DocumentListener() {
            
            /**
             * Called when text is inserted to this field
             *
             * @param e the event that triggered this call
             */
            public void insertUpdate(DocumentEvent e) {
                setState();
            }

            /**
             * Called when text is removed from this field
             *
             * @param e the event that triggered this call
             */
            public void removeUpdate(DocumentEvent e) {
                setState();
            }

            /**
             * This method is part of the <code>DocumentListener</code> 
             * interface.  It is not used in this class.
             */
            public void changedUpdate(DocumentEvent e) {
                /* Ingore this one */
            }
            
        });
    }

    /**
     * Returns the valid state of this field
     *
     * @return true if the contents of this field validate against the 
     * contained FieldVerifier object
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Sets the validation state of this object.
     */
    private void setState() {
        valid = AbstractTextValidator.get().validateText(this, verifier);
    }

}
