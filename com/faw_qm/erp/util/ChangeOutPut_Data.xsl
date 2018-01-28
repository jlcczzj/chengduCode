<?xml version="1.0" encoding="gb2312" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<head>
				<title>变更通知单</title>
				<meta http-equiv="Content-Type"
					content="text/html; charset=gb2312" />
			</head>
			<body bgColor="#ebebeb" leftMargin="20" topMargin="20">
				<p align="center">变更通知单</p>
				<div align="center">
					<table style="BORDER-COLLAPSE: collapse"
						borderColor="#111111" cellSpacing="0" cellPadding="3" width="94%"
						border="0">
						<tbody>
							<tr>
								<td align="left">
									编号：
									<xsl:value-of
										select="bom/description/filenumber" />
								</td>
								<td align="right">
									创建日期：
									<xsl:value-of
										select="bom/description/date" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div align="center">
					<xsl:for-each select="bom/table">
						<table style="BORDER-COLLAPSE: collapse"
							borderColor="#808080" cellSpacing="0" cellPadding="3" width="94%"
							bgColor="#ffffff" border="1">
							<tbody>
								<tr bgColor="#ffffcc">
									<xsl:apply-templates
										select="col_header" />
								</tr>
								<xsl:for-each select="././record">
									<tr bgColor="#ffffcc">
										<xsl:apply-templates
											select="col" />
									</tr>
								</xsl:for-each>
							</tbody>
						</table>
					</xsl:for-each>
				</div>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="col_header">
		<td bgColor="#e1e1e1">
			<xsl:value-of select="." />
		</td>
	</xsl:template>
	<xsl:template match="col">
		<td bgcolor="#ffffff">
			<xsl:value-of select="." />
		</td>
	</xsl:template>
</xsl:stylesheet>
