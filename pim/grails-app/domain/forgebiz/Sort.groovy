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

}
