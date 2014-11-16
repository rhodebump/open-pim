package forgebiz
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.common.SolrDocumentList
//controller that you can use to make cmm orders

import org.apache.solr.common.SolrDocument;


import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class SearchController {

	def index(){
		List<SolrDocument> results = new ArrayList<SolrDocument>();
		render(view: "/search/index", model: [results: results])
	}

	
	///list?offset=10&max=10
	
	
	def search() {
		String keywords = params.keywords;
		SolrQuery query = new SolrQuery();
		query.setQuery(keywords);
		SearchParameters searchParameters = new SearchParameters();
		QueryResponse response = SearchHelper.search(searchParameters,query);
		SolrDocumentList listresults = response.getResults();
		List<ProductQuantity> productQuantities = convert(listresults);

		Long resultCount =listresults.getNumFound();
		render(view: "/search/results", model: [productQuantities: productQuantities,resultCount:resultCount])
	}

	private List<ProductQuantity> convert(SolrDocumentList listresults) {
		List<SolrDocument> results = new ArrayList<SolrDocument>();
		println "there are " + listresults.size() + " results";
		for (int i = 0; i < listresults.size(); ++i) {
			ProductQuantity productQuantity = new ProductQuantity();
			productQuantity.productDocument = listresults.get(i);
			results.add(productQuantity);
		}
		return results;

	}
}
