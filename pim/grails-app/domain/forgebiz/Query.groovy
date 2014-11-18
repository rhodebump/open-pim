package forgebiz

class Query {

	def String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Query:  ${name} ");
		
		return sb.toString();
	}
	
	
	String name
	
	static hasMany = [qconstraints: Constraint, qsorts:Sort]
	
	
    static constraints = {
    }
}
