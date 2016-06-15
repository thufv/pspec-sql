<?xml version='1.0' encoding='UTF-8' ?>
<helpset version="2.0">
	<title>PSpec Editor - Help</title>
	<maps>
		<homeID>top </homeID>
		<mapref location="pspec.jhm" />
	</maps>
	<view mergetype="javax.help.UniteAppendMerge">
		<name>TOC</name>
		<label>Table Of Contents</label>
		<type>javax.help.TOCView</type>
		<data>pspec_toc.xml</data>
	</view>

	<presentation default="true">
		<name>main window</name>
		<size width="800" height="600" />
		<location x="200" y="200" />
		<title>Project X Help</title>
		<toolbar>
			<helpaction>javax.help.BackAction</helpaction>
			<helpaction>javax.help.ForwardAction</helpaction>
			<helpaction image="homeicon">javax.help.HomeAction</helpaction>
		</toolbar>
	</presentation>

	<impl>
		<helpsetregistry helpbrokerclass="javax.help.DefaultHelpBroker" />
		<viewerregistry viewertype="text/html"
			viewerclass="com.sun.java.help.impl.CustomKit" />
		<viewerregistry viewertype="text/xml"
			viewerclass="com.sun.java.help.impl.CustomXMLKit" />
	</impl>
</helpset>