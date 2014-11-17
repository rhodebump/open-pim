<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />

</head>
<body>


		<g:form controller="search">


		<label>Search</label>
		<g:textField name="keywords" value="" />



		<g:actionSubmit action="search" value="Search" />
	</g:form>

	<openpim:showFacets queryResponse="${queryResponse}" />

	<table>
		<g:each in="${productQuantities}" status="i" var="pq">
		<openpim:formatProductQuantity productQuantity="${pq}" i="${i}" />

		</g:each>

	</table>
	<div class="pagination">
	<g:paginate controller="search" action="search" total="${resultCount}" params="[keywords:params.keywords]" />
	
	</div>
</body>
</html>
