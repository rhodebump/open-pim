<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />

</head>
<body>


	<g:form>
		<g:hiddenField name="totalProductSpend" value="4000" />
		
			<openpim:queryDropDown/>
					
		<g:actionSubmit action="calc" value="Run Analysis" />
	</g:form>

<br/>


	<table>
		<g:each in="${productQuantities}" status="i" var="pq">
		
					<openpim:formatProductQuantity productQuantity="${pq}" i="${i}" />
				


		</g:each>

	</table>
</body>
</html>
