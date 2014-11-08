package forgebiz
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.common.SolrDocumentList
//controller that you can use to make cmm orders

import org.apache.solr.common.SolrDocument;


import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class ProductPickerController {

	
	public static ProductAttributeValue getAttributeValue(Product product, Attribute attribute) {
		for(ProductAttributeValue attributeValue:product.productAttributeValues) {
			if (attributeValue.attribute.id.equals(attribute.id)){
				return attributeValue;
			}
		}

		return null;
	}

	public static ProductAttributeValue getAttributeValue(Product product, String attributeName) {
		Attribute attribute = Attribute.findByName(attributeName);
		return getAttributeValue(product,attribute);
	}
	

	def addtocart() {
		Product product = Product.findById(params.productId);



		ProductAttributeValue av = getAttributeValue(product,"vendor");
		String vendorv = av.stringValue;

		if (vendorv.equals("Gare")) {
			//need to get the sku, strip the G
			ProductAttributeValue gare_id_av = getAttributeValue(product,"gare_id");
			String ProductID = gare_id_av.stringValue;
			String ProductQTY = params.quantity;
			render(view: "/productpicker/gare", model: [ProductQTY: ProductQTY,ProductID:ProductID ])


		}else if (vendorv.equals("Chesapeake")) {
			//need to get productid
			//https://www.chesapeakeceramics.com/searchadv.aspx?ShowPics=1&SearchDescriptions=1&SearchTerm=HKX002&=Submit+Query

		}

	}
	def addpq() {
		//construct a pq from the post, add to this form session
		ProductQuantity pq = new ProductQuantity();
		Product product = Product.findById(params.productId);
		pq.product = product;

		//let's init the product so we do not get a rg.hibernate.LazyInitializationException
		pq.product.attributeValues.each { //nothing
			it.attribute }
		pq.quantity = params.quantity.toInteger();
		List<ProductQuantity> pqs = session.pqs;
		if (pqs == null) {
			pqs = new ArrayList<ProductQuantity>();
			session.pqs = pqs;
		}
		pqs.add(pq);


		redirect(action: "index")

	}
	def runcalcspend() {

		session.totalProductSpend = Integer.parseInt(params.totalProductSpend);
		session.totalProductCount = null;
		redirect(action: "calc")

	}

	def runcalctotal() {
		session.totalProductCount =  Integer.parseInt(params.totalProductCount);
		session.totalProductSpend = null;
		redirect(action: "calc")


	}



	private Constraint getDriverConstraint(List<Constraint> allConstraints) {
		for(Constraint constraint:allConstraints) {
			if (constraint.percentage != null) {

				return constraint;
			}
		}

		throw new RuntimeException("Need constraint set with percentage");
	}
	def index(){
		List<SolrDocument> results = new ArrayList<SolrDocument>();
		render(view: "/productpicker/index", model: [results: results])
	}
	def calc(){

		int totalProductCount = 0;
		if (session.totalProductCount) {
			totalProductCount = session.totalProductCount;
		}
		int totalProductSpend = 0;
		if (session.totalProductSpend) {
			totalProductSpend = session.totalProductSpend;
		}
		System.out.println("totalProductCount " + totalProductCount);
		System.out.println("totalProductSpend " + totalProductSpend);


		if ((totalProductCount == 0) && (totalProductSpend == 0)) {
			flash.error = "You need to specify total product count or spend"

			redirect(action: "index")
			return;
		}


		List<Attribute> allAttributes = Attribute.findAll();

		List<Sort> sorts = Sort.findAll();

		List<Constraint> allConstraints = Constraint.findAll();
		Constraint driverConstraint = getDriverConstraint(allConstraints);

		Attribute driverAttr = driverConstraint.attribute;
		println "allAttributes " + allAttributes.size();

		allAttributes.remove(driverAttr);

		Map<Attribute,List<Constraint>>  attributeConstraintsMap = getAttributeConstraintsMap(allConstraints);

		Map<Constraint,List<SolrDocument>> constraintDocs = new HashMap<Constraint,List<SolrDocument>>();

		List<Constraint> constraints = attributeConstraintsMap.get(driverAttr);
		List<ProductQuantity> results = new ArrayList<ProductQuantity>();

		Map<Constraint,Double> constraintCostSum = new HashMap<Constraint,Double>();
		//this has a product count by constraint
		Map<Constraint,Integer> constraintCount = new HashMap<Constraint,Integer>();


		for(Constraint constraint:constraints) {
			SearchParameters searchParameters = new SearchParameters();
			SolrQuery query = new SolrQuery();
			query.setQuery("*:*");
			List<Constraint> constraint1list = new ArrayList<Constraint>();
			constraint1list.add(constraint);
			addConstraint( query, constraint1list,constraint.attribute);
			//all the other attattrs?
			for(Attribute attribute: allAttributes) {
				List<Constraint> otherConstraints = attributeConstraintsMap.get(attribute);
				addConstraint( query,otherConstraints,attribute);
			}
			for(Sort sort:sorts) {
				SolrQuery.ORDER order = null;
				if (sort.orderby == 'desc') {
					order = ORDER.desc;
				} else {
					order = ORDER.asc;
				}
				query.addSort(sort.attribute.getIndexName(), order)
			}
			QueryResponse response = SearchHelper.search(searchParameters,query);
			SolrDocumentList listresults = response.getResults();

			List<ProductQuantity> results2 = filter(listresults,totalProductCount,totalProductSpend, constraintCostSum,constraintCount,constraint);
			results.addAll(results2);
			//constraintDocs.put(constraint,results);
		}


		render(view: "/productpicker/index", model: [productQuantities: results])

	}

	private List<ProductQuantity> filter(SolrDocumentList listresults,int totalProductCount,int totalProductSpend, Map<Constraint,Double> constraintCostSum,Map<Constraint,Integer> constraintCount,Constraint constraint) {



		List<SolrDocument> results = new ArrayList<SolrDocument>();
		println "there are " + listresults.size() + " results";
		for (int i = 0; i < listresults.size(); ++i) {
			ProductQuantity productQuantity = new ProductQuantity();
			productQuantity.productDocument = listresults.get(i);
			boolean vps = isValid( productQuantity,totalProductCount,  totalProductSpend, constraintCostSum,constraintCount,constraint );
			println("vps=" + vps);

			if (vps) {
				results.add(productQuantity);

			} else {
				return results;
			}



		}
		return results;

	}
	private void addConstraint(SolrQuery query,List<Constraint> constraints,Attribute attr) {
		if (constraints == null) {
			return;
		}
		if (constraints.isEmpty()) {
			return;
		}
		StringBuffer sb = new StringBuffer();

		for(Constraint constraint:constraints) {
			if (sb.length() > 0) {
				sb.append(" OR ");
			}

			sb.append(constraint.attributeValue);

		}

		//fq=cat:(computers OR phones)
		query.addFilterQuery(attr.getIndexName() + ":(" +  sb.toString() + ")");

	}










	private boolean isCountGoalMet(Constraint constraint, int totalProductCount, Map<Constraint,Integer> constraintCount) {
		double percentage  = 1.0D;
		if (constraint.percentage != null) {
			percentage = constraint.percentage /100.0;
		}

		double tpc = (double) totalProductCount;
		double totalToKeep = tpc  * percentage;
		int ttk = Math.round(totalToKeep);
		Integer currentCount = constraintCount.get(constraint);
		if (currentCount == null) {
			currentCount = 0;
			constraintCount.put(constraint,currentCount);
		}
		if (currentCount < ttk) {
			println constraint.toString() + " did not meet goal yet currentCount=" + currentCount + ",ttk=" + ttk ;
			return false;
		}else {
			return true;
		}

	}

	private boolean isSpendGoalMet(Constraint constraint, int totalProductSpend, Map<Constraint,Double> constraintCostSum) {
		double percentage  = 1.0D;
		if (constraint.percentage != null) {
			percentage = constraint.percentage /100.0;
		}
		//following is cost
		double totalToSpend = ((double)totalProductSpend) * percentage;
		Double currentSpend = constraintCostSum.get(constraint);
		if (currentSpend == null) {
			currentSpend = 0.0D;
			constraintCostSum.put(constraint,currentSpend);
		}
		if (currentSpend < totalToSpend) {
			return false;
		}else {
			return true;
		}

	}



	private boolean isValid(ProductQuantity productQuantity,
			int totalProductCount, int totalProductSpend,Map<Constraint,Double> constraintCostSum ,Map<Constraint,Integer> constraintCount, Constraint constraint) {

		if (totalProductCount > 0) {
			if (! isCountGoalMet( constraint,  totalProductCount, constraintCount)){
				Integer currentCount = constraintCount.get(constraint);
				currentCount = currentCount + 1;
				constraintCount.put(constraint, currentCount);
				return true;
			}
		}else if (totalProductSpend > 0) {
			if (! isSpendGoalMet( constraint,  totalProductSpend, constraintCostSum)) {
				Double currentSpend = constraintCostSum.get(constraint);
				currentSpend = currentSpend + productQuantity.getExtendedCost();
				constraintCostSum.put(constraint, currentSpend);
				return true;
			}
		}

		return false;

	}


	private Map<Attribute,List<Constraint>>  getAttributeConstraintsMap(List<Constraint> allConstraints) {
		Map<Attribute,List<Constraint>> attributeConstraintsMap = new HashMap<Attribute,List<Constraint>>();
		Set<Attribute> attributes = new HashSet<Attribute>();
		for (Constraint constraint:allConstraints) {
			attributes.add(constraint.attribute);
		}
		System.out.println("There are " + attributes.size() + " attributes in constraints");

		for(Attribute attribute:attributes) {
			println "checking attr " + attribute.toString();
			List<Constraint> constraints = new ArrayList<Constraint>();
			for (Constraint constraint:allConstraints) {
				if (attribute.id.equals(constraint.attribute.id)){
					constraints.add(constraint);
				}
			}
			attributeConstraintsMap.put(attribute, constraints);
		}
		return attributeConstraintsMap;

	}






}
