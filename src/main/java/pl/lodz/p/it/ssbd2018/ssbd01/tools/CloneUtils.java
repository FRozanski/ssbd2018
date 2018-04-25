package pl.lodz.p.it.ssbd2018.ssbd01.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author piotrek
 */
public class CloneUtils {
    
    public static Object deepCloneThroughSerialization(Serializable source) {
        ObjectOutputStream out = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bos);
            out.writeObject(source);
            //De-serialization of object
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            return in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new IllegalArgumentException("Unrecoverable error of object entity serialization", ex);
        } finally {
            try {
                out.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(CloneUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
