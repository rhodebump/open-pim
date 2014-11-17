package forgebiz

class Constraint {

	Query query
	
	def String toString() {
		StringBuffer sb = new StringBuffer();
		if (attribute != null) {
			sb.append("${attribute.name} ");
		}
		if (attributeValue != null) {
			sb.append("${attributeValue} ");
		}
		if (percentage != null) {
			sb.append("${percentage} ");
		}
		
		return sb.toString();
	}


	Attribute attribute
	
	String attributeValue
	
	Double percentage 
	
	static mapping = {
		table 'constraints'
	}
	
	
    static constraints = {
		attribute nullable: false
		attributeValue blank: false, nullable: false
		percentage nullable:true
		
		
    }
}
