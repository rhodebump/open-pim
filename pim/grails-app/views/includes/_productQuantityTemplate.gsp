<tr class="${(i % 2) == 0 ? 'result-item odd' : 'result-item even'}">
	<td><g:link controller="product" action="show"
			id="${productQuantity.productId}">
			<img class="product" src="${productQuantity.imageUrl}" />
		</g:link></td>

	<td><a target="_blank" href="${productQuantity.detailUrl}">
			${productQuantity.name}
		</a></td>

	<td>
		${productQuantity.description}

	</td>

	<td>
		${productQuantity.cost2}
	</td>
	<td>
		${productQuantity.size}
	</td>
	<td>
		Manufacturer ${productQuantity.manufacturer}<br>
		SKU:  ${productQuantity.sku }<br>
		Number In Case:  ${productQuantity.numberInCase}<br>
		Number Sold:  ${productQuantity.numberSold}<br>
	</td>

	<td>
		${productQuantity.extendedCost2}
	</td>
	<td>
		
	</td>




</tr>