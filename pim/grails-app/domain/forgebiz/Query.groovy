package forgebiz

class Query {

	String name
	
	static hasMany = [qconstraints: Constraint, qsorts:Sort]
	
	
    static constraints = {
    }
}
