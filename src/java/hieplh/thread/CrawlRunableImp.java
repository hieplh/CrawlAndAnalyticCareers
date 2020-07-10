/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.thread;

import hieplh.crawl.Crawler;
import hieplh.crawl.CrawlerHelper;
import hieplh.crawl.CrawlerTVN;
import hieplh.crawl.dao.CareersJobDetailsDAO;
import hieplh.crawl.dao.JobDetailDAO;
import hieplh.crawl.dao.ProvincesJobDetailsDAO;
import hieplh.jaxb.jobdetail.Jobs;
import hieplh.schema.SchemaValidator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.ParseException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Admin
 */
public class CrawlRunableImp implements Runnable {

    private String jobName;
    private String linkPage;
    private String xmlPath;
    private String xslPath;
    private String schemaPath;

    public CrawlRunableImp(String jobName, String linkPage, String xmlPath, String xslPath, String schemaPath) {
        this.jobName = jobName;
        this.linkPage = linkPage;
        this.xmlPath = xmlPath;
        this.xslPath = xslPath;
        this.schemaPath = schemaPath;
    }

    @Override
    public void run() {
        try {
            CrawlerTVN crawlerTVN = new CrawlerTVN();
            int[] items = crawlerTVN.getCount(CrawlerHelper.openConnect(linkPage));
            int countItem = items[0];
            int countTotalItem = items[1];

            int countTotalPage = 1;
            if (countTotalItem % countItem == 0) {
                countTotalPage = countTotalItem / countItem;
            } else {
                countTotalPage = (countTotalItem / countItem) + 1;
            }

            JAXBContext context;
            Unmarshaller unmarshaller;
            JobDetailDAO dao = new JobDetailDAO();
            int index = 0;
            for (int i = 0; i < countTotalPage; i++) {
                try {
                    String linkPageDetail = linkPage
                            + "&page=" + (i + 1);
                    InputStream is = rewriteResourceFile(linkPageDetail);

                    DOMResult result = Crawler.crawlByDOM(is, xslPath);
                    context = JAXBContext.newInstance(Jobs.class);
                    unmarshaller = context.createUnmarshaller();

                    Node node = result.getNode();
                    StringWriter writer = new StringWriter();
                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    transformer.transform(new DOMSource(node), new StreamResult(writer));
                    SchemaValidator.validateXMLBySchema(new ByteArrayInputStream(writer.toString().getBytes()), schemaPath);

                    Jobs jobs = (Jobs) unmarshaller.unmarshal(result.getNode());
                    CareersJobDetailsDAO careersJobDetailsDAO = new CareersJobDetailsDAO();
                    ProvincesJobDetailsDAO provincesJobDetailsDAO = new ProvincesJobDetailsDAO();
                    try {
                        String[] arrId = dao.insert(jobName, jobs.getJob(), index);
                        index = index + arrId.length;

                        for (int j = 0; j < arrId.length; j++) {
                            careersJobDetailsDAO.insert(arrId[j], jobs.getJob().get(j).getDetails().getCareers().getCareer());
                            provincesJobDetailsDAO.insert(arrId[j], jobs.getJob().get(j).getDetails().getProvinces().getProvince());
                        }

                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }

                } catch (IOException | TransformerException | JAXBException | ParseException
                        | TransformerFactoryConfigurationError | XMLStreamException
                        | SAXException e) {
                    System.out.println("");
                    System.out.println("LINK_PAGE: " + linkPage);
                    System.out.println(e.getMessage());
                    System.out.println("");
                }
            }

            System.out.println("JOB_NAME: " + jobName + " - DONE");
        } catch (IOException | TransformerException
                | TransformerFactoryConfigurationError | XMLStreamException e) {
            System.out.println("");
            System.out.println("CrawlRunable");
            System.out.println(e.getMessage());
            System.out.println("");
        }
    }

    public InputStream rewriteResourceFile(String value) throws XMLStreamException {
        StringBuilder sb = new StringBuilder();
        value = value.replace("&", "&amp;");
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("\n");
        sb.append("<career xmlns=\"http://abc.com/career\"");
        sb.append("\n");
        sb.append("host=\"https://www.timviecnhanh.com/\"");
        sb.append("\n");
        sb.append("link=\"" + value + "\">");
        sb.append("\n");
        sb.append("</career>");

        return new ByteArrayInputStream(sb.toString().getBytes());
    }
}
