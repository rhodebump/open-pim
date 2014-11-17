package forgebiz

import grails.transaction.Transactional

/*
 *	Creating and Updating products call the same function, so if you are trying to update a product that does not exist, it will be created
 */
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


	//create or update really, by sku
	def create( Map content, Map params ) {
		log.error "ProductService.create invoked"
		def result
		Product.withTransaction {
			//is it an update or create??
			String sku = content['sku'];
			def instance = Product.findBySku(sku);
			if (instance == null) {
				instance = new Product()
			}
			instance.properties['name', 'sku', 'metaDescription'] = content
			def manufacturer = new Manufacturer();
			def manuMap = content.manufacturer;
			if (manuMap.id) {
				manufacturer = Manufacturer.findById(attrMap.id);
			}else if(manuMap.name)  {
				manufacturer = Manufacturer.findByName(manuMap.name);
				if (manufacturer == null) {
					manufacturer = new Manufacturer();
					manufacturer.properties = manuMap;
					manufacturer.save(failOnError:true)
				}
			}else {
				throw new Exception("need manufacturer name or id");
			}
			instance.manufacturer = manufacturer;
			if (content['productAttributeValues']) {
				content['productAttributeValues']?.each { productAttributeValueMap ->
					
					//look for attr
					def attrMap = productAttributeValueMap.attribute;
					Attribute attr =null;
					if (attrMap.id) {
						attr = Attribute.findById(attrMap.id);

					}else if(attrMap.name)  {
						attr = Attribute.findByName(attrMap.name);
						if (attr == null) {
							attr = new Attribute();
							attr.properties = attrMap;
							attr.save(failOnError:true)
						}
					}else {
						throw new Exception("need to product an attribute name or id");
					}
					//need to find existing product attribute if it exists
					//there is only one pav per attr, so we need to update the pav if it already exists, or create a new one
					def productAttributeValue = getProductAttributeValue(instance,attr);
					productAttributeValue.properties = productAttributeValueMap;
					instance.addToProductAttributeValues( productAttributeValue )
				}
			}
			instance.indexed = false;
			instance.save(failOnError:true)
			result = instance
			if (content['productAttributeValues']) result.productAttributeValues //force lazy loading
			else result.productAttributeValues = [] as Set
		}
		log.error "ProductService.create returning $result"
		result
	}

	
	ProductAttributeValue getProductAttributeValue(Product product,Attribute attribute) {
		for(ProductAttributeValue pav:product.productAttributeValues) {
			if (pav.attribute.id == attribute.id){
				return pav;
			} 
		}
		//none found, let's create a new one
		ProductAttributeValue pav= new ProductAttributeValue();
		pav.attribute= attribute;
		return pav;
		
	}

	def list( Map params ) {
		log.error "ProductService.list invoked with params $params"
		params.max = Math.min( params.max ? params.max.toInteger() : 100, 100 )
		params.offset = params.offset ?: 0
		params.sort = params.sort ?: 'code'
		//def result = Product.findAll();
		def result = Product.executeQuery( query.statement, query.parameters, params )
		log.trace "ProductService.list is returning a ${result.getClass().simpleName} containing ${result.size()} things"
		result

	}
	def update( Map content, Map params ) {
		create(content,params);
	}
	void delete( Map content, Map params ) {
		Product.withTransaction {
			def product = Product.get(params.id)
			product.delete(failOnError:true)
		}
	}
}
