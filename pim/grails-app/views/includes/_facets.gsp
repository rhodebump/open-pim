
<g:if test="${queryResponse != null}">


                    
                    
	<g:if test="${! queryResponse?.facetFields?.isEmpty()}">
		<div id="facets">



			<g:if test="${queryResponse.getResults().getNumFound() > 0}">
				<ul style="padding-bottom: 10px">
					<li>Filter your results by clicking on the following:</li>
				</ul>

			</g:if>
			<g:else>
				<ul style="padding-bottom: 10px">
					<li>Sorry, but we didn't find any results.</li>
				</ul>
			</g:else>


			<g:each in="${queryResponse?.facetFields}" status="i"
				var="facetField">
				<g:if test="${facetField.values}">
					<ul style="padding-bottom: 10px">
						<g:each in="${facetField.values}" status="i2"
							var="facetFieldCount">
							<g:set var="facetFieldName" value="${facetField.getName()}" />
							<li><g:link title="filter by ${facetFieldCount.getName()}"
									action="search"
									params="[(facetField.name):facetFieldCount.getName(),q:params.q]">
									${facetFieldCount.getName()}
								</g:link> (${facetFieldCount.getCount()})</li>
						</g:each>

					</ul>
				</g:if>


			</g:each>
		</div>
	</g:if>
</g:if>