/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.utils;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Admin
 */
public class XMLUtils {

    public static XMLEventReader createEventReader(StreamSource inputStream) throws XMLStreamException {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        xif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES, false);
        xif.setProperty(XMLInputFactory.IS_VALIDATING, false);

        return xif.createXMLEventReader(inputStream);
    }
}
