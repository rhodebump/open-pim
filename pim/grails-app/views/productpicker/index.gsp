<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />

</head>
<body>


	<g:form>


		<label>Product Spend: $4000 spend </label>
		<g:textField name="totalProductSpend" value="4000" />
		<br />


		<g:actionSubmit action="runcalcspend" value="Run Calculations" />
	</g:form>

	<g:form>

		<label>Product Count: 400 sku codes recommended</label>
		<g:textField name="totalProductCount" value="40" />
		<br />


		<g:actionSubmit action="runcalctotal" value="Run Calculations" />
	</g:form>

	<table>
	<tr><th>Name</th><td>Quantity</td></tr>
		<g:each in="${productQuantities}" status="i" var="pq">
		
				<g:formatProduct productQuantity="${pq}" i="${i}" />
				


		</g:each>

	</table>
</body>
</html>
