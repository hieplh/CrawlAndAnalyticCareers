/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.resolver;

import hieplh.crawl.CrawlerTVN;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Admin
 */
public class UrlResolver implements URIResolver {

    @Override
    public Source resolve(String href, String base) throws TransformerException {
        URL url = null;
        InputStream inputStream = null;
        CrawlerTVN crawlerTVN = new CrawlerTVN();
        StreamSource streamSource = null;

        try {
            if (href != null && href.equals("https://www.timviecnhanh.com/")) {
                url = new URL(href);
                inputStream = url.openStream();

                streamSource = crawlerTVN.processHeader(inputStream);
            } else {
                boolean param = false;
                if (href.contains("vieclam/timkiem")) {
                    param = true;
                }
                url = new URL(href);
                inputStream = url.openStream();

                if (param) {
                    streamSource = crawlerTVN.processTableContent(inputStream, null);
                } else {
                    streamSource = crawlerTVN.processContentDetail(inputStream);
                }
            }
        } catch (IOException e) {
            System.out.println("");
            System.out.println("Url Resolver: " + e.getMessage());
            System.out.println("Href: " + href);
            System.out.println("");
        }
        return streamSource;
    }
}
