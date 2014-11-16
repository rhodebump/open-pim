<tr class="${(i % 2) == 0 ? 'result-item odd' : 'result-item even'}">
	<td><g:link controller="product" action="show"
			id="${productQuantity.productId}">
			<img src="${productQuantity.imageUrl}" />
		</g:link></td>

	<td><g:link controller="product" action="show"
			id="${productQuantity.productId}">
			${productQuantity.name}
		</g:link></td>

	<td>
		${productQuantity.product.description}
	</td>

	<td>
		${productQuantity.cost2}
	</td>
	<td>
		${productQuantity.size}
	</td>
	<td>
		${productQuantity.numberInCase}
	</td>

	<td>
		${productQuantity.extendedCost2}
	</td>
	<td>
		${productQuantity.vendor}
	</td>




</tr>