<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>Welcome to Open PIM</title>
<style type="text/css" media="screen">
#status {
	background-color: #eee;
	border: .2em solid #fff;
	margin: 2em 2em 1em;
	padding: 1em;
	width: 12em;
	float: left;
	-moz-box-shadow: 0px 0px 1.25em #ccc;
	-webkit-box-shadow: 0px 0px 1.25em #ccc;
	box-shadow: 0px 0px 1.25em #ccc;
	-moz-border-radius: 0.6em;
	-webkit-border-radius: 0.6em;
	border-radius: 0.6em;
}

.ie6 #status {
	display: inline;
	/* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
}

#status ul {
	font-size: 0.9em;
	list-style-type: none;
	margin-bottom: 0.6em;
	padding: 0;
}

#status li {
	line-height: 1.3;
}

#status h1 {
	text-transform: uppercase;
	font-size: 1.1em;
	margin: 0 0 0.3em;
}

#page-body {
	margin: 2em 1em 1.25em 18em;
}

h2 {
	margin-top: 1em;
	margin-bottom: 0.3em;
	font-size: 1em;
}

p {
	line-height: 1.5;
	margin: 0.25em 0;
}

#controller-list ul {
	list-style-position: inside;
}

#controller-list li {
	line-height: 1.3;
	list-style-position: inside;
	margin: 0.25em 0;
}

@media screen and (max-width: 480px) {
	#status {
		display: none;
	}
	#page-body {
		margin: 0 1em 1em;
	}
	#page-body h1 {
		margin-top: 0;
	}
}
</style>
</head>
<body>




	<a href="#page-body" class="skip"><g:message
			code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div id="status" role="complementary" style="display: none">
		>
		<h1>Application Status</h1>
		<ul>
			<li>App version: <g:meta name="app.version" /></li>
			<li>Grails version: <g:meta name="app.grails.version" /></li>
			<li>Groovy version: ${GroovySystem.getVersion()}</li>
			<li>JVM version: ${System.getProperty('java.version')}</li>
			<li>Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>
			<li>Controllers: ${grailsApplication.controllerClasses.size()}</li>
			<li>Domains: ${grailsApplication.domainClasses.size()}</li>
			<li>Services: ${grailsApplication.serviceClasses.size()}</li>
			<li>Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
		</ul>
		<h1>Installed Plugins</h1>
		<ul>
			<g:each var="plugin"
				in="${applicationContext.getBean('pluginManager').allPlugins}">
				<li>
					${plugin.name} - ${plugin.version}
				</li>
			</g:each>
		</ul>
	</div>
	<div id="page-body" role="main">
		<h1>Welcome to Open PIM</h1>
		<div id="controller-list" role="navigation">
		<h2>Tools</h2>
		
			<g:form controller="search">
		<g:textField name="q" value="" />
		<g:actionSubmit action="search" value="Search" />
	</g:form>
	
	
			<ul>
				
				<li class="controller"><g:link controller="product">Products</g:link></li>
				<li class="controller"><g:link controller="productPicker">Product Picker</g:link></li>

			</ul>
		<h2>Administration</h2>	
			<ul>
							<li class="controller"><g:link controller="attribute">Attributes</g:link></li>
				<li class="controller"><g:link controller="productAttributeValue">Product Attribute Values</g:link></li>
				<li class="controller"><g:link controller="query">Query</g:link></li>
				<li class="controller"><g:link controller="sort">Sort</g:link></li>
				<li class="controller"><g:link controller="constraint">Constraint</g:link></li>
				<li class="controller"><g:link controller="setting">Settings</g:link></li>
					<li class="controller"><a href="http://localhost:8983/solr/update?stream.body=%3Cdelete%3E%3Cquery%3E*:*%3C/query%3E%3C/delete%3E">Clear Index</a></li>
					<li class="controller"><a href="http://localhost:8983/solr/update?stream.body=%3Ccommit/%3E">Commit Index</a></li>
				
				</ul>

		</div>

		<div id="controller-list" role="navigation" style="display: none">
			<h2>Available Controllers:</h2>
			<ul>
				<g:each var="c"
					in="${grailsApplication.controllerClasses.sort { it.fullName } }">
					<li class="controller"><g:link
							controller="${c.logicalPropertyName}">
							${c.fullName}
						</g:link></li>
				</g:each>
			</ul>
		</div>
	</div>
</body>
</html>
