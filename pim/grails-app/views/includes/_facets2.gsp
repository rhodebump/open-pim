	<g:if test="${queryResponse != null}">
	
			<g:each in="${queryResponse?.facetFields}" status="i" var="facetField">
 <openpim:facet action="search" field="${facetField.name}" queryResponse="${queryResponse}" fq="${fq}" q="${q}" min="2">
                        <h3>Filter by Genre</h3>
                    </openpim:facet >
                  	</g:each>  
                  	
                  	</g:if>