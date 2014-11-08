package forgebiz;

class Setting {

	String name
	String description
	String stringValue
	String type
	Date dateValue

	static constraints = {
		name blank: false, nullable: false, unique:true
		description nullable:true, blank:true
		stringValue nullable:true,blank:true
		dateValue nullable:true,blank:true
		type(inList: [
			"String",
			"Integer",
			"Boolean",
			"Date"
		])
	}
}
