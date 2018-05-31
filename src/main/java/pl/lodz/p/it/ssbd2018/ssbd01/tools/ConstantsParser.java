/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2018.ssbd01.tools;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 *
 * @author Filip
 */
public class ConstantsParser {
    private static final String resourcesLocation = "src\\main\\resources\\constants\\";
    private static final XStream xStream = new XStream();

    public static void serialize(Object o, String className) {
        String filePath = getFilePath(className);
        try {
            xStream.toXML(o, new FileWriter(new File(filePath)));
        } catch (IOException ex) {
            //TODO
        }
    }

    public static Object deserialize(String className) {
        String filePath = getFilePath(className);
        File xml = new File(filePath);
        Object result = null;
        try {
            result = xStream.fromXML(new FileInputStream(xml));
        } catch (FileNotFoundException ex) {
            //TODO
        }
        return result;
    }
        
    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private static String getFilePath(String className) {
        return resourcesLocation + className + ".xml";
    }
}
