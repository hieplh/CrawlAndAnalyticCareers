/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieplh.crawl;

import hieplh.utils.XMLUtils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Admin
 */
public class CrawlerTVN extends CrawlerHelper {

    private static final String PAGINATION = "PAGINATION";

    public StreamSource processHeader(InputStream inputStream) throws UnsupportedEncodingException, IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader br = new BufferedReader(isr);

        String line;
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("\n");
        sb.append("<header xmlns=\"http://www.w3.org/1999/xhtml\" "
                + "class=\"header-site\" id=\"header\">");
        sb.append("\n");

        boolean isBody = false;
        boolean isHeader = false;
        boolean isScript = false;
        boolean isNoScript = false;
        boolean isStyle = false;
        boolean flagUl = false;
        boolean isComment = false;
        boolean isLiTagMissingCloseDivTag = false;
        boolean isMeetErrorDivTag = false;
        boolean isMeetErrorUlTag = false;

        while ((line = br.readLine()) != null) {
            if (checkStartElement(line, "body")) {
                isBody = true;
            } else if (checkStartElement(line, "/body")) {
                isBody = false;
            } else {
                if (isBody) {
                    if (line.contains("<header class=\"header-site\" id=\"header\">")) {
                        isHeader = true;
                    } else if (isHeader) {
                        if (checkStartElement(line.trim(), "script")) {
                            isScript = true;
                        } else if (checkStartElement(line.trim(), "noscript")) {
                            isNoScript = true;
                        } else if (checkStartElement(line.trim(), "style")) {
                            isStyle = true;
                        }

                        if (!isScript && !isNoScript && !isStyle) {
                            line = line.trim();

                            line = line.replace("&", "&amp;");
                            line = line.replace("<br>", "<br/>");
                            line = handleWrongSign(line);

                            if (line.contains("<div id=\"field-hot-content\">")) {
                                isMeetErrorDivTag = true;
                            }
                            if (isMeetErrorDivTag) {
                                if (line.contains("<ul class=\"list col-xs-4 offset20 push-left-20\">")) {
                                    isMeetErrorUlTag = true;
                                }

                                if (isMeetErrorUlTag) {
                                    if (line.contains("<b")) {
                                        line = line.substring(0, line.indexOf("<!--")).trim();
                                    }

                                    if (line.contains("</ul>")) {
                                        isMeetErrorUlTag = false;
                                    }
                                }

                                if (line.contains("</div>")) {
                                    isMeetErrorDivTag = false;
                                }
                            }

                            line = line.replace("&reg;", "&#174;")
                                    .replace("&hellip;", "")
                                    .replace("&nbsp;", "");

                            if (line.contains("<ul class=\"list-post-has-thumbnail no-style home-job-width-li\">")) {
                                flagUl = true;
                            } else {
                                if (line.contains("col-xs-5 offset20 mt_5") || line.contains("col-xs-6 offset20 mt_5")) {
                                    isLiTagMissingCloseDivTag = true;
                                } else if (isLiTagMissingCloseDivTag && line.contains("</li>")) {
                                    line = checkMissingCloseTagDivOfLiTag(line);
                                    isLiTagMissingCloseDivTag = false;
                                }
                                if (flagUl) {
                                    if (line.contains("</ul>")) {
                                        flagUl = false;
                                    }
                                }
                            }

                            if (line.length() != 0) {
                                if (line.contains("<img") || line.contains("<input")) {
                                    line = checkEndElement(line);
                                }
                                if (line.contains("<!--")) {
                                    isComment = true;
                                }
                                if (isComment) {
                                    if (line.contains("-->")) {
                                        isComment = false;
                                    }
                                } else {
                                    if (line.contains("<a") && !line.contains("<abbr")) {
                                        line = checkDuplicateAttribute(line, "<a");
                                    } else if (line.contains("<input")) {
                                        line = checkDuplicateAttribute(line, "<input");
                                    }
                                    sb.append(line.trim()).append("\n");
                                }
                            }
                        }

                        if (checkEndElement(line.trim(), isScript, "script")) {
                            isScript = false;
                        } else if (checkEndElement(line.trim(), isNoScript, "noscript")) {
                            isNoScript = false;
                        } else if (checkEndElement(line.trim(), isStyle, "style")) {
                            isStyle = false;
                        }
                    }
                    if (line.contains("</header>")) {
                        isHeader = false;
                    }
                }
            }
        }

        return new StreamSource(new ByteArrayInputStream(sb.toString().getBytes()));
    }

    public StreamSource processTableContent(InputStream inputStream, String type) throws UnsupportedEncodingException, IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader br = new BufferedReader(isr);

        String line;
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("\n");
        if (type != null) {
            sb.append("<table class=\"table-content\">");
        } else {
            sb.append("<table xmlns=\"http://www.w3.org/1999/job\" "
                    + "class=\"table-content\">");
        }

        sb.append("\n");

        boolean isBody = false;
        boolean isTable = false;
        boolean isScript = false;
        boolean isNoScript = false;
        boolean isStyle = false;
        boolean isComment = false;
        boolean flag = false;
        boolean isIgnoreATag = false;
        boolean isTagDivContent = false;

        while ((line = br.readLine()) != null) {
            if (checkStartElement(line, "body")) {
                isBody = true;
            } else if (checkStartElement(line, "/body")) {
                isBody = false;
            } else {
                if (isBody) {
                    if (type == null) {
                        if (line.contains("<table class=\"table-content\">")) {
                            isTable = true;
                        } else if (isTable) {
                            if (checkStartElement(line.trim(), "script")) {
                                isScript = true;
                            } else if (checkStartElement(line.trim(), "noscript")) {
                                isNoScript = true;
                            } else if (checkStartElement(line.trim(), "style")) {
                                isStyle = true;
                            }

                            if (!isScript && !isNoScript && !isStyle) {
                                line = line.trim();

                                line = line.replace("&", "&amp;");
                                line = line.replace("<br>", "<br/>");

                                line = line.replace("&reg;", "&#174;")
                                        .replace("&hellip;", "")
                                        .replace("&nbsp;", "");

                                line = handleWrongSign(line);

                                if (line.contains("<img") || line.contains("<input")) {
                                    line = checkEndElement(line);
                                }
                                if (line.contains("<a") && !line.contains("<abbr")) {
                                    line = checkDuplicateAttribute(line, "<a");
                                } else if (line.contains("<input")) {
                                    line = checkDuplicateAttribute(line, "<input");
                                }

                                if (line.length() != 0) {
                                    if (line.contains("<tbody>")) {
                                        flag = true;
                                    }
                                    if (flag) {
                                        if (line.contains("<!--")) {
                                            isComment = true;
                                        }
                                        if (isComment) {
                                            if (line.contains("-->")) {
                                                isComment = false;
                                            }
                                        } else {
                                            sb.append(line.trim()).append("\n");
                                        }
                                    }
                                    if (line.contains("</tbody>")) {
                                        flag = false;
                                    }
                                }

                                if (checkEndElement(line.trim(), isScript, "script")) {
                                    isScript = false;
                                } else if (checkEndElement(line.trim(), isNoScript, "noscript")) {
                                    isNoScript = false;
                                } else if (checkEndElement(line.trim(), isStyle, "style")) {
                                    isStyle = false;
                                }
                            }
                            if (line.contains("</table>")) {
                                isTable = false;
                            }
                        }
                    } else {
                        if (line.contains("<table class=\"table-content\">")) {
                            isTable = true;
                        } else if (line.contains("<div class=\"box-content\">")) {
                            isTagDivContent = true;
                        } else if (line.contains("</table>")) {
                            isTable = false;
                        } else if (isTable || isTagDivContent) {
                            if (checkStartElement(line.trim(), "script")) {
                                isScript = true;
                            } else if (checkStartElement(line.trim(), "noscript")) {
                                isNoScript = true;
                            } else if (checkStartElement(line.trim(), "style")) {
                                isStyle = true;
                            }

                            if (!isScript && !isNoScript && !isStyle) {
                                line = line.trim();

                                line = line.replace("&", "&amp;");
                                line = line.replace("<br>", "<br/>");

                                line = line.replace("&reg;", "&#174;")
                                        .replace("&hellip;", "")
                                        .replace("&nbsp;", "");

                                line = handleWrongSign(line);
                                
                                if (line.contains("<img") || line.contains("<input")) {
                                    line = checkEndElement(line);
                                }
                                if (line.contains("<a") && !line.contains("<abbr")) {
                                    line = checkDuplicateAttribute(line, "<a");
                                } else if (line.contains("<input")) {
                                    line = checkDuplicateAttribute(line, "<input");
                                }

                                if (line.length() != 0) {

                                    if (line.contains("<tbody>")) {
                                        flag = true;
                                    }

                                    if (type != null) {
                                        if (line.contains("<tfoot>")) {
                                            flag = true;
                                        } else if (line.contains("<a class=\"prev item active\">")) {
                                            flag = false;
                                            isIgnoreATag = true;
                                        } else if (isTagDivContent && line.contains("<header class=\"block-title\">")) {
                                            flag = true;
                                        }
                                    }

                                    if (flag) {
                                        if (line.contains("<!--")) {
                                            isComment = true;
                                        }
                                        if (isComment) {
                                            if (line.contains("-->")) {
                                                isComment = false;
                                            }
                                        } else {
                                            sb.append(line.trim()).append("\n");
                                        }
                                    }

                                    if (line.contains("</tbody>")) {
                                        flag = false;
                                    } else if (line.contains("</tfoot>")) {
                                        flag = true;
                                    } else if (line.contains("</a>") && isIgnoreATag) {
                                        flag = true;
                                        isIgnoreATag = false;
                                    } else if (isTagDivContent && line.contains("</header>")) {
                                        flag = false;
                                        isTagDivContent = false;
                                    }
                                }
                            }

                            if (checkEndElement(line.trim(), isScript, "script")) {
                                isScript = false;
                            } else if (checkEndElement(line.trim(), isNoScript, "noscript")) {
                                isNoScript = false;
                            } else if (checkEndElement(line.trim(), isStyle, "style")) {
                                isStyle = false;
                            }
                        }
                    }
                }
            }
        }
        sb.append("</table>");
        return new StreamSource(new ByteArrayInputStream(sb.toString().getBytes()));
    }

    public int[] getCount(InputStream inputStream) throws IOException, XMLStreamException {
        XMLEventReader reader = XMLUtils.createEventReader(processTableContent(inputStream, PAGINATION));
        int[] arr = new int[2];

        boolean isH3 = false;
        boolean isSpan = false;
        boolean isTBody = false;

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (event.isStartElement()) {
                StartElement element = (StartElement) event;

                if (element.getName().toString().equals("h3")) {
                    Attribute attr = element.getAttributeByName(new QName("class"));
                    if (attr.getValue().equals("title font-roboto text-primary")) {
                        isH3 = true;
                    }
                } else if (element.getName().toString().equals("tbody")) {
                    isTBody = true;
                }

                if (isH3) {
                    if (element.getName().toString().equals("span")) {
                        Attribute attr = element.getAttributeByName(new QName("class"));
                        if (attr.getValue().equals("count")) {
                            isSpan = true;
                        }
                    }
                } else if (isTBody) {
                    if (element.getName().toString().equals("tr")) {
                        arr[0] = arr[0] + 1;
                    }
                }

            }

            if (event.isCharacters()) {
                Characters element = (Characters) event;
                String tmp = element.getData().trim();
                if (isSpan && !tmp.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < tmp.length(); i++) {
                        if (Character.toString(tmp.charAt(i)).matches("\\d")) {
                            sb.append(tmp.charAt(i));
                        }
                    }
                    arr[1] = Integer.parseInt(sb.toString());
                }
            }

            if (event.isEndElement()) {
                EndElement element = (EndElement) event;
                if (isSpan && element.getName().toString().equals("span")) {
                    isSpan = false;
                    isH3 = false;
                } else if (isTBody && element.getName().toString().equals("tbody")) {
                    isTBody = false;
                }
            }
        }

        return arr;
    }

    public StreamSource processContentDetail(InputStream inputStream) throws UnsupportedEncodingException, IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader br = new BufferedReader(isr);

        String line;
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("\n");
        sb.append("<root xmlns=\"http://www.w3.org/1999/jobdetail\">");
        sb.append("\n");

        boolean isBody = false;
        boolean isStartFlag = false;
        boolean isArticle = false;
        boolean isScript = false;
        boolean isNoScript = false;
        boolean isStyle = false;
        boolean isWrongPrefixDiv = false;

        while ((line = br.readLine()) != null) {
            if (checkStartElement(line, "body")) {
                isBody = true;
            } else if (checkStartElement(line, "/body")) {
                isBody = false;
            } else {
                if (isBody) {
                    if (line.contains("<div class=\"detail-content box-content\" id=\"left-content\">")) {
                        isStartFlag = true;
                    } else if (isStartFlag) {
                        if (line.contains("<article class=\"block-content\">")) {
                            isArticle = true;
                        } else if (isArticle) {
                            if (checkStartElement(line.trim(), "script")) {
                                isScript = true;
                            } else if (checkStartElement(line.trim(), "noscript")) {
                                isNoScript = true;
                            } else if (checkStartElement(line.trim(), "style")) {
                                isStyle = true;
                            } else if (line.contains("<div class=\"addthis_toolbox addthis_default_style\"")) {
                                isWrongPrefixDiv = true;
                            }

                            if (!isScript && !isNoScript && !isStyle && !isWrongPrefixDiv) {
                                line = line.trim();

                                line = line.replace("&", "&amp;");
                                line = line.replace("<br>", "<br/>");

                                line = line.replace("&reg;", "&#174;")
                                        .replace("&hellip;", "")
                                        .replace("&nbsp;", "");

                                line = handleWrongSign(line);

                                if (line.contains("<table>")) {
                                    break;
                                }

                                if (line.length() != 0) {
                                    if (line.contains("<img") || line.contains("<input")) {
                                        line = checkEndElement(line);
                                    }
                                    if (line.contains("<!--")) {
                                        int startComment = line.indexOf("<!--");
                                        int endComment = line.indexOf("-->");
                                        line = line.replace(line.substring(startComment, endComment + 3), "");
                                    }

                                    if (line.contains("<a") && !line.contains("<abbr")) {
                                        line = checkDuplicateAttribute(line, "<a");
                                    } else if (line.contains("<input")) {
                                        line = checkDuplicateAttribute(line, "<input");
                                    }
                                    sb.append(line.trim()).append("\n");
                                }
                            }

                            if (checkEndElement(line.trim(), isScript, "script")) {
                                isScript = false;
                            } else if (checkEndElement(line.trim(), isNoScript, "noscript")) {
                                isNoScript = false;
                            } else if (checkEndElement(line.trim(), isStyle, "style")) {
                                isStyle = false;
                            } else if (isWrongPrefixDiv && line.contains("</div>")) {
                                isWrongPrefixDiv = false;
                            }
                        }

                        if (line.contains("</article>")) {
                            isArticle = false;
                            isStartFlag = false;
                        }
                    }
                }
            }
        }
        sb.append("</root>");

        return new StreamSource(new ByteArrayInputStream(sb.toString().getBytes()));
    }
}
