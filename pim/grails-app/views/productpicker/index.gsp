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
			<tr class="${(i % 2) == 0 ? 'result-item odd' : 'result-item even'}">

				<td>
						<g:link controller="product" action="show" id="${pq.productId}">${pq.name}</g:link>	
				</td>
				<td>
				${pq.cost2}
				</td>
			<td>
				${pq.size}
				</td>
						<td>
				${pq.numberInCase}
				</td>
				
				<td>
				${pq.extendedCost2}
				</td>
				<td>
				${pq.vendor}
					</td>
				</td>
				<td>
				<g:form>
					<g:textField name="quantity" value="${pq.quantity}" />

					<g:hiddenField name="productId" value="${pq.productId}" />


					<g:actionSubmit action="addpq" value="Make stick" />
				</g:form>
				</td>
				<td>
				<g:form target="${pq.vendor }">
					<g:textField name="quantity" value="${pq.quantity}" />

					<g:hiddenField name="productId" value="${pq.productId}" />


					<g:actionSubmit action="addtocart" value="Add To Cart" />
				</g:form>
				


			</td>



			</tr>

		</g:each>

	</table>
</body>
</html>
