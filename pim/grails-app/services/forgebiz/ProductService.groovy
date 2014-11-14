package forgebiz

import grails.transaction.Transactional

@Transactional
class ProductService {

	def serviceMethod() {
	}

	def grailsApplication

	def count(Map params) {
		log.error "ProductService.count invoked"
		
		def result = Product.findAll();
		return result.size();
	}
	def show(Map params) {
		
	}
	
	def create( Map content, Map params ) {

		log.error "ProductService.create invoked"

	//	if (WebUtils.retrieveGrailsWebRequest().getParameterMap().forceGenericError == 'y') {
	//		throw new Exception( "generic failure" )
	//	}

		def result

		Product.withTransaction {
			def instance = new Product()
			instance.properties['name','sku'] = content
			if (content['productAttributeValues']) {
				content['productAttributeValues']?.each { productAttributeValueMap ->
					def productAttributeValue = new ProductAttributeValue()
					//look for attr
					def attrMap = productAttributeValueMap.attribute;
					Attribute attr =null;
					if (attrMap.id) {
						 attr = Attribute.findById(attrMap.id);

					}else if(attrMap.name)  {
						println "looking for attrname " + attrMap.name;
				
						attr = Attribute.findByName(attrMap.name);
						if (attr == null) {
							attr = new Attribute();
							attr.properties = attrMap;
							attr.save(failOnError:true)
						}

						
					}else {
						throw new Exception("need to product an attribute name or id");
					}
					
		
					productAttributeValue.attribute= attr;
					productAttributeValue.properties = productAttributeValueMap;
					
					println productAttributeValue.toString();
					
					//part.thing = instance
					instance.addToProductAttributeValues( productAttributeValue )
				}
			}
			instance.indexed = false;
			instance.save(failOnError:true)
			result = instance
			if (content['productAttributeValues']) result.productAttributeValues //force lazy loading
			else result.productAttributeValues = [] as Set
		}
	//	supplementThing( result )
		log.error "ProductService.create returning $result"
		result
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
