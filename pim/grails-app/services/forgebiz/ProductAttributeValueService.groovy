package forgebiz

import grails.transaction.Transactional

@Transactional
class ProductAttributeValueService {

	def serviceMethod() {
	}

	def grailsApplication

	def count(Map params) {
		log.error "ProductAttributeValueService.count invoked"
		
		def result = ProductAttributeValueService.findAll();
		return result.size();
	}
	def show(Map params) {
		
	}
	
	def create( Map content, Map params ) {

		log.trace "ProductAttributeValueService.create invoked"

	//	if (WebUtils.retrieveGrailsWebRequest().getParameterMap().forceGenericError == 'y') {
	//		throw new Exception( "generic failure" )
	//	}

		
		
		
		def result

		Product.withTransaction {
			def productMap = content['product'];
			
			Product product = null;
			if (productMap.id)  {
				product = Product.findById(productMap.id);
				
			} else if (productMap.sku) {
				product = Product.findBySku(productMap.sku);
			}
			
			if (product == null) {
				
				throw new Exception("Product not found " + productMap.toString());
			}
			
			

			
			
			def attrMap = content['attribute'];
			Attribute attr = null;
			
			if (attrMap.id)  {
				attr = Attribute.findById(attrMap.id);
				
			} else if (attrMap.name) {
				attr = Attribute.findByName(attrMap.name);
				if (attr == null) {
					attr = new Attribute();
					attr.properties = attrMap;
					attr.save(failOnError:true)
				}
			}
			
			
			//we need to remove existing attribute value if there is one
			def existing = getExistingProductAttribute(product, attr);
			if (existing) {
				println "existing";
				product.removeFromProductAttributeValues(existing);
			}
			
			def instance = new ProductAttributeValue();
			instance.properties = content
			instance.attribute = attr;
			
			
			
			product.addToProductAttributeValues(instance);
			
			product.indexed = false;
			
			product.save(failOnError:true)
			result = instance
		}
		log.trace "ProductAttributeValueService.create returning $result"
		result
	}
	
	def getExistingProductAttribute(Product product,Attribute attr) {
		
		for(ProductAttributeValue pav :product.productAttributeValues) {
			
			if (pav.attribute.id == attr.id ){
				return pav;	
			}
		}
		return null;
		
	}
	def list( Map params ) {
		log.error "ProductService.list invoked with params $params"

		params.max = Math.min( params.max ? params.max.toInteger() : 100, 100 )
		params.offset = params.offset ?: 0
		params.sort = params.sort ?: 'code'


		//def query = HQLBuilder.createHQL( grailsApplication, params )
		def result = Product.findAll();
		//def result = Product.executeQuery( query.statement, query.parameters, params )
		result.each {
		//supplementThing( it )
		}
		log.trace "ProductService.list is returning a ${result.getClass().simpleName} containing ${result.size()} things"
		result
		
	}
	def update( Map content, Map params ) {
	}
	void delete( Map content, Map params ) {

		Product.withTransaction {
			def product = Product.get(params.id)
			product.delete(failOnError:true)
		}
	}
}
