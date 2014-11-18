package forgebiz

class ProductTagLib {
	static namespace = "openpim"
   // static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
	
	def formatProductQuantity = { attrs, body ->
		out << render(template: "/includes/productQuantityTemplate",
				model: [productQuantity: attrs.productQuantity,i:attrs.i])
	}
	
	
	def showFacets= { attrs, body ->
		out << render(template: "/includes/facets",
			model: [queryResponse: attrs.queryResponse])
	}
	
	def showFacets2= { attrs, body ->
		out << render(template: "/includes/facets2",
			model: [queryResponse: attrs.queryResponse])
	}
	
	
	def queryDropDown= { attrs, body ->
		out << render(template: "/includes/querySelect",
			model: [])
	}
	//https://github.com/mbrevoort/grails-solr-demo/blob/master/grails-app/views/song/index.gsp
	
	
	def addForm = {
		
	}
	
	//from https://github.com/mbrevoort/grails-solr-plugin/blob/master/grails-app/taglib/org/grails/solr/SolrTagLib.groovy
	def facet = { attrs, body ->
		
			  def field = attrs['field']
			  def queryResponse = attrs['queryResponse']
			  def fq = attrs['fq']
			  def q = attrs['q']
			  def action = attrs['action']
			  def cssClass = (attrs['class']) ? attrs['class'] : "solr-facet"
			  def min = (attrs['min']) ? attrs['min'] as int : 1
		
			  def facetValues = queryResponse.getFacetField(field).values
			  def currentFacetSelection = null
		
			  fq.each { item ->
				if(item.contains(field)) {
				  // TODO: replace hardcoded style
				  currentFacetSelection = link(action:action, params:[q:q, fq: (fq - [item] )]) { "<span style='color:red; font-size:14px'>X</span>" }
				  currentFacetSelection += " " + item.split(":")[1].replace("\\", "")  + " <br/>"
				}
			  }
		
			  if((facetValues && facetValues.size() >= min) || currentFacetSelection){
		
				out << "<div class=\"${cssClass}\">"
				out << body()
				out << currentFacetSelection
		
				queryResponse.getFacetField(field).values.each { item ->
		
				  def linkParams = [:]
				  if(action)
					linkParams.action = action
				  linkParams.params = [:]
				  linkParams.params.q = q
				  linkParams.params.fq = fq.size() ? ([item.asFilterQuery] + fq) : [item.asFilterQuery]
		
				  out << "<ul>"
				  if(!fq.contains(item.asFilterQuery) && item.count > 0) {
					out << "<li>"
					out << link(linkParams) { item }
					out << "</li>"
				  }
				  out << "</ul>"
				}
				out << "</div>"
			  }
			}
	
	
	
}
