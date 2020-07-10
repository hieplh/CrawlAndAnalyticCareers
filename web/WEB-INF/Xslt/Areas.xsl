<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xh="http://www.w3.org/1999/xhtml"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:t="http://abc.com/career"
                version="1.0">
    <xsl:output method="html" omit-xml-declaration="yes" indent="yes"/>
    
    <xsl:template match="t:career">
        <xsl:variable name="listDoc" select="document(@host)"/>
        <xsl:element name="areas">
            <xsl:for-each select="$listDoc//xh:form[@id='job_advance_search']//xh:select[@id='header_search_province']//xh:option">
                <xsl:if test="@value != 66">
                    <xsl:element name="area">
                        <xsl:attribute name="value">
                            <xsl:value-of select="@value"/>
                        </xsl:attribute>
                        <xsl:element name="name">
                            <xsl:value-of select="text()"/>
                        </xsl:element>
                    </xsl:element>
                </xsl:if>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>