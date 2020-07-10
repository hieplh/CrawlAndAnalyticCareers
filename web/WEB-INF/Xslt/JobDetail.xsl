<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : Job.xsl
    Created on : June 12, 2020, 2:01 AM
    Author     : Admin
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xh="http://www.w3.org/1999/job"
                xmlns:j="http://www.w3.org/1999/jobdetail"
                xmlns:t="http://abc.com/career"
                version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes" encoding="UTF-8" />
    
    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    
    <xsl:template match="t:career">
        <xsl:variable name="listDoc" select="document(@link)"/>
        <xsl:element name="jobs">
            <xsl:for-each select="$listDoc//xh:tr">
                <xsl:variable name="header" select="xh:td[@class='block-item w55']"/>
                <xsl:if test="$header/xh:a[1]/@title">
                    <xsl:element name="job">
                        <xsl:attribute name="submission">
                            <xsl:value-of select="xh:td[@class='text-center w15'][3]/text()"/>
                        </xsl:attribute>
                        <xsl:element name="jobtitle">
                            <xsl:value-of select="$header/xh:a[1]/@title"/>
                        </xsl:element>
                        <xsl:element name="company">
                            <xsl:value-of select="$header/xh:a[2]/@title"/>
                        </xsl:element>
                        
                        <xsl:variable name="link" select="$header/xh:a[1]/@href"/>
                        <xsl:apply-templates select="document($link)"/>
                    </xsl:element>
                </xsl:if>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>

    <xsl:template match="j:root">
        <xsl:element name="details">
            <xsl:variable name="leftDetail" select=".//j:div[@class='col-xs-4 offset20 push-right-20']"/>

            <xsl:element name="address">
                <xsl:value-of select=".//j:div[@class='col-xs-6 p-r-10 offset10']//j:span/text()"/>
            </xsl:element>
            <xsl:element name="salary">
                <xsl:copy-of select="$leftDetail//j:li[1]/text()"/>
            </xsl:element>
            <xsl:element name="experience">
                <xsl:copy-of select="$leftDetail//j:li[2]/text()"/>
            </xsl:element>
            <xsl:element name="level">
                <xsl:copy-of select="$leftDetail//j:li[3]/text()"/>
            </xsl:element>
            <xsl:element name="provinces">
                <xsl:for-each select="$leftDetail//j:li[4]//j:a">
                    <xsl:element name="province">
                        <xsl:value-of select="text()"/>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
            <xsl:element name="careers">
                <xsl:for-each select="$leftDetail//j:li[5]//j:a">
                    <xsl:element name="career">
                        <xsl:value-of select="text()"/>
                    </xsl:element>
                </xsl:for-each>
            </xsl:element>
            
            <xsl:variable name="rightDetail" select=".//j:div[@class='col-xs-4 offset20']"/>
            <xsl:element name="recruitment">
                <xsl:copy-of select="$rightDetail//j:li[1]/text()"/>
            </xsl:element>
            <xsl:element name="gender">
                <xsl:copy-of select="$rightDetail//j:li[2]/text()"/>
            </xsl:element>
            <xsl:element name="natureOfWork">
                <xsl:copy-of select="$rightDetail//j:li[3]/text()"/>
            </xsl:element>
            <xsl:element name="formOfWork">
                <xsl:copy-of select="$rightDetail//j:li[4]/text()"/>
            </xsl:element>
            <xsl:element name="try">
                <xsl:copy-of select="$rightDetail//j:li[5]/text()"/>
            </xsl:element>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>