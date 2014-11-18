package forgebiz
import org.apache.commons.lang.SerializationUtils
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
class SearchHelper {


	public static HttpSolrServer getServer() {
		String url = "http://localhost:8983/solr";
		HttpSolrServer server = new HttpSolrServer( url );
		return server;
	}
	private static void setStartAndRows(SolrQuery solrQuery, SearchParameters searchParameters) {
		try {
			if (searchParameters.getOffset() > 0) {
				solrQuery.setStart searchParameters.getOffset();
			}
		} catch (NumberFormatException e) {
			//not a number
		}

		try {
			if (searchParameters.getMax() > 0) {
				solrQuery.setRows searchParameters.getMax();
			}
		} catch (NumberFormatException e) {
			//not a number
		}
	}

	
	private static QueryResponse dosearch(SolrQuery query) {
		HttpSolrServer server = getServer();
		QueryResponse rsp = server.query(query);

		return rsp
	
		
	}
	
	public static QueryResponse search(SearchParameters searchParameters,SolrQuery query) {
		//SolrQuery query = new SolrQuery();
		//setStartAndRows(query, searchParameters);





		query.setFacet(true);
		//query.addFacetField("cat");
		//query.addFacetField("city_s");
		//query.addFacetField("state_s");
		//query.addFacetField("zip_s");
		query.addFacetField("vendor_s");
		query.addFacetField("size_s");
		
		query.setFacetMinCount(1);
		
		println query.toString();
		
		return dosearch(query);
		
	}

}
