/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.thread;

import hieplh.crawl.Crawler;
import java.io.IOException;
import java.util.concurrent.Callable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;

/**
 *
 * @author Admin
 */
public class CallableImp<T> implements Callable<T> {

    private final String xmlPath;
    private final String xslDirPath;
    private final String xslPath;
    private T classify;

    public CallableImp(String xmlPath, String xslDirPath, String xslPath, T classify) {
        this.xmlPath = xmlPath;
        this.xslDirPath = xslDirPath;
        this.xslPath = xslPath;
        this.classify = classify;
    }

    @Override
    public T call() throws Exception {
        try {
            DOMResult result = Crawler.crawlByDOM(xmlPath, xslDirPath + xslPath);

            JAXBContext jaxb = JAXBContext.newInstance(classify.getClass());
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();

            Object obj = unmarshaller.unmarshal(result.getNode());

            return (T) obj;
        } catch (IOException | JAXBException | TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
