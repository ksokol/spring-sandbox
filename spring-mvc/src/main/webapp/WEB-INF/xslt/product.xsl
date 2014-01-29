<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" omit-xml-declaration="yes"/>

    <xsl:template match="/root">
    
        <html>
            <head><title>XSLT PRODUCT</title></head>
            <body>            	
                <h1>Product <xsl:value-of select="product/@id"/></h1>

    			<ul>
    				<xsl:for-each select="product">    
    				    <li><xsl:value-of select="name"/></li>
         				<li><xsl:value-of select="description"/></li>        
      				</xsl:for-each>
				</ul>				
            </body>
        </html>	
	
    </xsl:template>

</xsl:stylesheet>