package edu.wcu.cs.cs363.team3.project02;

import javax.swing.text.JTextComponent;
import java.awt.Color;
import javax.swing.border.LineBorder;

/**
 * Encapsulates logic that can be used to validate text against a 
 * FieldVerifier.
 *
 * @author Chris Blades
 * @author Beeny Reese
 * @author Don Coffin
 *
 * @version 7/4/2010
 */
public class AbstractTextValidator {

   /**
    * Instance of this class
    */
   private static final AbstractTextValidator instance = 
        new AbstractTextValidator();

    /**
     * Enforces singleton pattern
     */
    private AbstractTextValidator() {
        // do nothing.
    }

    /**
     * Returns an instance of AbstractTextValidator.
     */
    public static AbstractTextValidator get() {
        return instance;
    }

    /**
     * Returns wether or not a JTextComponent validates against
     * a FieldVerifier.
     *
     * @param textComp the text component to validate
     * @param verifier FieldVerifier used to validate textcomp
     *
     * @return true if textComp validates with verifier
     */
    public boolean validateText(JTextComponent textComp, 
                                FieldVerifier verifier) {
        boolean valid = verifier.verify(textComp.getText());

        if (valid) {
            textComp.setBorder(new LineBorder(Color.BLACK, 1, true));
            textComp.setToolTipText(null);

        } else {
            textComp.setBorder(new LineBorder(Color.RED, 1, true));
            textComp.setToolTipText("Input should look similar to: " + 
                                    verifier.example());

        }
        return valid;
    }

}
