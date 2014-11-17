package forgebiz

class Manufacturer {
	String name
	
    static constraints = {
    }
	
	def String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("${name} ");
		
		return sb.toString();
	}

	
	
}
