package forgebiz

import java.util.Map;

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ProductService)
@Mock([Product,Manufacturer,Attribute,ProductAttributeValue])
class ProductServiceSpec extends Specification {

	def getProductMap() {
		Map content = new HashMap();
		content.sku = "SKU123";
		content.name = "Product Service Test";
		content.metaDescription = "Description";
		content.manufacturer = new HashMap();
		content.manufacturer.name = "Manufacturer Test";
		
		content.productAttributeValues = new ArrayList();
		
		//let's create a couple of attrs
		Map productAttributeValue = new HashMap();
		productAttributeValue.attribute = new HashMap();
		productAttributeValue.attribute.name = "test attribute";
		productAttributeValue.attribute.type = "String";
		productAttributeValue.stringValue = "Test Value";
		
		
		content.productAttributeValues.add(productAttributeValue);
		
		return content;
		
	}
	void "test product created"() {
		given: "List of Users"
			//List<User> users = createUsers()

			def content = getProductMap();	
		
		when: "service is called"
			//"service" represents the grails service you are testing for
		
			//create( Map content, Map params ) 
			def result = service.create(content,null)
  
		then: "Expect something to happen"
			//Assertion goes here
			result.name ==  "Product Service Test";
			result.sku == "SKU123";
			
	}
	
	

	
	
	
	

}
