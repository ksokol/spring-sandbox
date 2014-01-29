<?xml version='1.0' encoding='UTF-8'?>
<xsl:stylesheet version='2.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>
    <xsl:output media-type="text/html; charset=UTF-8" encoding="UTF-8" indent="yes" omit-xml-declaration="yes" />
    <xsl:template match='/'>
        <html>
            <head>
                <title>Spring Sandbox</title>
                <link rel="stylesheet" type="text/css" href="/sandbox/static/css/bootstrap.min.css" />
                <link rel="stylesheet" type="text/css" href="/sandbox/static/css/style.css" />
            </head>
            <body>
                <div class="container">
                    <table class="table table-bordered table-striped tablesorter">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>Created</th>
                                <th>Updated</th>
                                <th>Created by</th>
                            </tr>
                        </thead>
                        <tbody>
                            <xsl:for-each select="response/result/doc">
                                <tr>
                                    <td><xsl:value-of select="str[@name='id']"/></td>
                                    <td><xsl:value-of select="arr[@name='name']/str/text()"/></td>
                                    <td><xsl:value-of select="arr[@name='createdAt']/str/text()"/></td>
                                    <td><xsl:value-of select="arr[@name='updatedAt']/str/text()"/></td>
                                    <td><xsl:value-of select="str[@name='createdBy']"/></td>
                                </tr>
                            </xsl:for-each>
                        </tbody>
                    </table>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>