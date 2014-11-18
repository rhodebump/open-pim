package forgebiz

class Sort {

	
	Query query
	
    static constraints = {
		orderby(inList:["Ascending","Descending"])
		
    }
	
	Attribute attribute
	
	String orderby
	
	static mapping = {
		table 'sorts'
	}
	
	
	def String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Sort:  ${attribute.toString()} ${orderby}");
		
		return sb.toString();
	}
	

}
