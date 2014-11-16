<tr>
<td colspan="50%">

				<g:form>
					<g:textField name="quantity" value="${pq.quantity}" />

					<g:hiddenField name="productId" value="${pq.productId}" />


					<g:actionSubmit action="addpq" value="Make stick" />
				</g:form>
</td>
<td colspan="50%">

				<g:form target="${pq.vendor }">
					<g:textField name="quantity" value="${pq.quantity}" />

					<g:hiddenField name="productId" value="${pq.productId}" />


					<g:actionSubmit action="addtocart" value="Add To Cart" />
				</g:form>
</td>				
</tr>