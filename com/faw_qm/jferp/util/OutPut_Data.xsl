<?xml version="1.0" encoding="gb2312" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<head>
				<title>
					<xsl:value-of select="bom/description/filenumber" />
				</title>
				<meta http-equiv="Content-Type"
					content="text/html; charset=gb2312" />
			</head>
			<body bgColor="#ebebeb" leftMargin="20" topMargin="20">
				<p align="center">发布数据明细表</p>
				<div align="left">
					<table style="BORDER-COLLAPSE: collapse"
						borderColor="#111111" cellSpacing="0" cellPadding="3" width="94%"
						border="0">
						<tr>
							<td width="100%">
								<p>
									<b>说明：</b>
								</p>
							</td>
						</tr>
					</table>
				</div>
				<div align="left">
					<table style="BORDER-COLLAPSE: collapse"
						borderColor="#808080" cellSpacing="0" cellPadding="3" width="94%"
						bgColor="#ffffff" border="1">
						<tr bgColor="#ffffcc">
							<td bgcolor="#e1e1e1">编号</td>
							<td bgcolor="#ffffff">
								<xsl:value-of
									select="bom/description/filenumber" />
							</td>
							<td bgcolor="#e1e1e1">类型</td>
							<td bgcolor="#ffffff">
								<xsl:value-of
									select="bom/description/type" />
							</td>
						</tr>
						<tr bgColor="#ffffcc">
							<td bgcolor="#e1e1e1">日期</td>
							<td bgcolor="#ffffff">
								<xsl:value-of
									select="bom/description/date" />
							</td>
							<td bgcolor="#e1e1e1">来源</td>
							<td bgcolor="#ffffff">
								<xsl:value-of
									select="bom/description/sourcetag" />
							</td>
						</tr>
						<tr bgColor="#ffffcc">
							<td bgcolor="#e1e1e1">注释</td>
							<td colspan="3" bgcolor="#ffffff">
								<xsl:value-of
									select="bom/description/notes" />
							</td>
						</tr>
					</table>
				</div>
				<br/>
				<xsl:for-each select="bom/table">
					<div align="left">
						<table style="BORDER-COLLAPSE: collapse"
							borderColor="#111111" cellSpacing="0" cellPadding="3" width="94%"
							border="0">
							<tr>
								<td width="100%">
									<p>
										<b>
											表名：
											<xsl:value-of
												select="@name" />
										</b>
									</p>
								</td>
							</tr>
						</table>
					</div>
					<div align="left">
						<table style="BORDER-COLLAPSE: collapse"
							borderColor="#808080" cellSpacing="0" cellPadding="3" width="94%"
							bgColor="#ffffff" border="1">
							<tr bgColor="#ffffcc">
								<xsl:apply-templates
									select="col_header" />
							</tr>
							<xsl:for-each select="././record">
								<tr bgColor="#ffffcc">
									<xsl:apply-templates select="col" />
								</tr>
							</xsl:for-each>
						</table>
					</div>
					<br/>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="col_header">
		<td bgcolor="#e1e1e1">
			<xsl:value-of select="." />
		</td>
	</xsl:template>
	<xsl:template match="col">
		<td bgcolor="#ffffff">
			<xsl:value-of select="." />
		</td>
	</xsl:template>
</xsl:stylesheet>
