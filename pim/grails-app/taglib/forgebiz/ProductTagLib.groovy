package forgebiz

class ProductTagLib {
    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
	
	def formatProductQuantity = { attrs, body ->
		out << render(template: "/includes/productQuantityTemplate",
				model: [productQuantity: attrs.productQuantity,i:attrs.i])
	}
	
	
	def addForm = {
		
	}
	
}
