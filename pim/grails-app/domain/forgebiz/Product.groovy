package forgebiz

class Product {
	static hasMany = [productAttributeValues: ProductAttributeValue]

	//one can create set of attributes that will apply to this product
	//ProductAttributeSet productAttributeSet
	Manufacturer manufacturer
	
	String name
	String sku
	String metaTitle
	String metaDescription
	Date dateCreated
	Date lastUpdate
	
	Boolean indexed = false;
	
	

	static constraints = {

		name blank: false, nullable: false
		sku blank: false, nullable: false, unique: true
		metaTitle nullable:true, blank:true
		metaDescription nullable:true, blank:true
		
		dateCreated nullable:true, display:true
		lastUpdate nullable:true, display:true
		
		
	}
}
