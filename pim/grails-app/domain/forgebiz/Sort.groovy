package forgebiz

class Sort {

    static constraints = {
		orderby(inList:["Ascending","Descending"])
		
    }
	
	Attribute attribute
	
	String orderby
	
	static mapping = {
		table 'sorts'
	}

}
