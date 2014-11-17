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
	
	def queryDropDown= { attrs, body ->
		out << render(template: "/includes/querySelect",
			model: [])
	}
	
	
	
	def addForm = {
		
	}
	
}
