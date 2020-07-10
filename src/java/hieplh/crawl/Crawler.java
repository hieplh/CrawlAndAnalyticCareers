/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.crawl;

import hieplh.resolver.UrlResolver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Admin
 */
public class Crawler {

    public static DOMResult crawlByDOM(String configPath, String xslPath)
            throws FileNotFoundException, TransformerConfigurationException,
            TransformerException, IOException {

        InputStream is = new FileInputStream(configPath);

        StreamSource xslCreate = new StreamSource(xslPath);

        TransformerFactory factory = TransformerFactory.newInstance();
        DOMResult dom = new DOMResult();
        UrlResolver resolver = new UrlResolver();

        factory.setURIResolver(resolver);
        Transformer transformer = factory.newTransformer(xslCreate);

        transformer.transform(new StreamSource(is), dom);

        return dom;
    }

    public static DOMResult crawlByDOM(InputStream inputStream, String xslPath)
            throws FileNotFoundException, TransformerConfigurationException,
            TransformerException, IOException {
        TransformerFactory factory = TransformerFactory.newInstance();
        DOMResult dom = new DOMResult();
        UrlResolver resolver = new UrlResolver();

        factory.setURIResolver(resolver);
        Transformer transformer = factory.newTransformer(new StreamSource(xslPath));

        transformer.transform(new StreamSource(inputStream), dom);

        return dom;
    }

    public static DOMResult crawlByDOM(InputStream inputStream, Templates template)
            throws FileNotFoundException, TransformerConfigurationException,
            TransformerException, IOException {
        TransformerFactory factory = TransformerFactory.newInstance();
        DOMResult dom = new DOMResult();
        UrlResolver resolver = new UrlResolver();

        factory.setURIResolver(resolver);
        Transformer transformer = template.newTransformer();

        transformer.transform(new StreamSource(inputStream), dom);
        return dom;
    }
}
