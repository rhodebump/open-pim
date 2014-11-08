package forgebiz

class ProductAttributeSet {

	String name
	static hasMany = [attributes: Attribute]
	
    static constraints = {
    }
}
