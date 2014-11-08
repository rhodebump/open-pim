import forgebiz.*

class BootStrap {

	def init = { servletContext ->

		User user = User.findByUsername('admin');
		if (user == null) {
			def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
			def userRole = new Role(authority: 'ROLE_USER').save(flush: true)
			def testUser = new User(username: 'admin', password: 'forgebiz')
			testUser.save(flush: true)
			UserRole.create testUser, adminRole, true
		} else{
			//no need to do data imports
			return;
		}
		createAttributes();

		createConstraints();
		createSorts();
	}
	def destroy = {
	}


	private void createAttributes() {

		createAttribute("vendor","String");
		createAttribute("number_sold","Integer");
		//createAttribute("sum");
		createAttribute("cost","Double");
		//createAttribute("rank");
		createAttribute("size","String");
		createAttribute("sku","String");
		//createAttribute("profitablity");
	}



	private void createAttribute(String name,String type) {
		Attribute attribute = new Attribute();
		attribute.name = name;
		attribute.type = type;
		attribute.save();
	}
	private void createConstraints() {

		createConstraint("size","Small", 10.0);
		createConstraint("size","Medium", 65.0);
		createConstraint("size","Large", 15.0);
		createConstraint("size","Extra Large", 10.0);
	}

	private void createConstraint(String attrname,String attrvalue,double percent){
		Constraint constraint = new Constraint();
		Attribute attr = Attribute.findByName(attrname);
		constraint.attribute = attr;
		constraint.attributeValue = attrvalue;
		constraint.percentage = percent;
		constraint.save();
	}

	private void createSorts() {

		createSort("rank","Ascending");
	}

	private void createSort(String attributeName,String orderby) {
		Sort sortx = new Sort();
		Attribute attr = Attribute.findByName("number_sold");
		sortx.attribute = attr;
		sortx.orderby = orderby;
		sortx.save();
	}
}
