package com.pixelTrice.elastic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.DoubleTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.HistogramBucket;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.TermsAggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.PutScriptResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.SearchTemplateResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.util.ObjectBuilder;

@Repository
public class ElasticSearchQuery {

	@Autowired
	private ElasticsearchClient elasticsearchClient;
	@Autowired
	private RestClient restclient;

	private final String indexName = "book";

	public String createOrUpdateDocument(Product product) throws IOException {

		IndexResponse response = elasticsearchClient
				.index(i -> i.index(indexName).id(product.getId()).document(product));
		if (response.result().name().equals("Created")) {
			return new StringBuilder("Document has been successfully created.").toString();
		} else if (response.result().name().equals("Updated")) {
			return new StringBuilder("Document has been successfully updated.").toString();
		}
		return new StringBuilder("Error while performing the operation.").toString();
	}

	public Product getDocumentById(String productId) throws IOException {
		Product product = null;
		GetResponse<Product> response = elasticsearchClient.get(g -> g.index(indexName).id(productId), Product.class);

		if (response.found()) {
			product = response.source();
			System.out.println("Product name " + product.getName());
		} else {
			System.out.println("Product not found");
		}

		return product;
	}

	public String deleteDocumentById(String productId) throws IOException {

		DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(productId));

		DeleteResponse deleteResponse = elasticsearchClient.delete(request);
		if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
			return new StringBuilder("Product with id " + deleteResponse.id() + " has been deleted.").toString();
		}
		System.out.println("Product not found");
		return new StringBuilder("Product with id " + deleteResponse.id() + " does not exist.").toString();

	}

	public ResortResponse searchAllDocuments(String destination) throws IOException {
		ResortResponse resortResponse = new ResortResponse();
		List<Resort> products = new ArrayList<>();
		PutScriptResponse response = elasticsearchClient.putScript(r -> r.id("query-script")
				.script(s -> s.lang("mustache").source("{\n"
						+ " \"_source\":[\"resortcode\",\"resortname\",\"numberofmemberratings\",\"membr_rvw\",\"address1\",\"destination\",\"regionLevel1\"],\n"
						+ "  \"query\": {\n" + "  \n" + "    \"bool\" : {\n" + "      \"should\" : [{\n"
						+ "        \"match\" : { \"destination\" : \""+destination+"\" }}\n" + "      ,\n" + "    \n" + "	{\n"
						+ "        \"match\" : { \"regionLevel1\" : \""+destination+"\" }}\n" + "      \n" + "	  ],\n"
						+ "      \"minimum_should_match\" : 1\n" + "    }\n" + "  }\n" + "}")));
		SearchTemplateResponse<Resort> response1 = elasticsearchClient
				.searchTemplate(r -> r.index("partner-inv-index-01").id("query-script"), Resort.class);
		List<Hit<Resort>> hits = response1.hits().hits();

		for (Hit<Resort> hit : hits) {
			Resort product = hit.source();
			products.add(product);
			// System.out.println(product.getResortcode()+":"+product.getResortname()+":"+product.getRegionLevel1());
		}

		Query bydestination = MatchQuery.of(m -> m // <1>
				.field("destination").query(destination))._toQuery();
		Query byregionlevel = MatchQuery.of(m -> m // <1>
				.field("regionLevel1").query(destination))._toQuery();
		Query query = new Query.Builder()
				.bool(b -> b.should(bydestination)
						.should(byregionlevel)
				.minimumShouldMatch("1")).build();
		Query byname = MatchQuery.of(m -> m // <1>
				.field("name").query("ramSearchBook"))._toQuery();
		/*
		 * QueryBuilder qb = QueryBuilders .boolQuery()
		 * .should(QueryBuilders.matchQuery("destination", "USA"))
		 * .should(QueryBuilders.matchQuery("regionLevel1", "USA"))
		 * .minimumShouldMatch(1)._toQuery();
		 */
		Map<String, Aggregation> map = new HashMap<>();
		Aggregation aggregation = new Aggregation.Builder()
				.terms(new TermsAggregation.Builder().field("bedroomcode").build()).build();
		Aggregation aggregation1 = new Aggregation.Builder()
				.terms(new TermsAggregation.Builder().field("privateoccupancy").build()).build();
		Aggregation aggregation2 = new Aggregation.Builder().terms(new TermsAggregation.Builder().field("vep").build())
				.build();
		map.put("agg_bedroom", aggregation);
		map.put("agg_occupancy", aggregation1);
		map.put("agg_vep", aggregation2);
		SearchResponse<Void> response5 = elasticsearchClient
				.search(b -> b.index("partner-inv-index-01").size(0).query(query).aggregations(map), Void.class);

		List<StringTermsBucket> buckets = response5.aggregations().get("agg_bedroom").sterms().buckets().array();
		List<StringBucket> bedroombuckets = new ArrayList<>();
		buckets.stream().forEach(l -> {
			StringBucket bucket = new StringBucket();
			bucket.setKey(l.key());
			bucket.setDocCount(l.docCount());
			bedroombuckets.add(bucket);
		});

		List<StringTermsBucket> bucketso = response5.aggregations().get("agg_occupancy").sterms().buckets().array();

		List<StringBucket> occupancybuckets = new ArrayList<>();
		bucketso.stream().forEach(l -> {
			StringBucket bucket = new StringBucket();
			bucket.setKey(l.key());
			bucket.setDocCount(l.docCount());
			occupancybuckets.add(bucket);
		});
		List<LongTermsBucket> bucketsvep = response5.aggregations().get("agg_vep").lterms().buckets().array();

		List<StringBucket> vepbuckets = new ArrayList<>();
		bucketsvep.stream().forEach(l -> {
			StringBucket bucket = new StringBucket();
			bucket.setKey(l.key());
			bucket.setDocCount(l.docCount());
			vepbuckets.add(bucket);
		});
		resortResponse.setResort(products);
		resortResponse.setAggBedroom(bedroombuckets);
		resortResponse.setAggPrivateocuup(occupancybuckets);
		resortResponse.setAggvep(vepbuckets);
		return resortResponse;
	}

	public ResortResponse searchByFilter(FilterRequest filterRequest) throws ElasticsearchException, IOException {
		System.out.println(filterRequest.getBedroomcode()+":"+filterRequest.getOccupancy());
		String destination="USA";
		Query bydestination = MatchQuery.of(m -> m // <1>
				.field("destination").query(destination))._toQuery();
		Query byregionlevel = MatchQuery.of(m -> m // <1>
				.field("regionLevel1").query(destination))._toQuery();
		Query bybedroomcode=TermQuery.of(t->t.field("bedroomcode").value(filterRequest.getBedroomcode()))._toQuery();
		Query query = new Query.Builder()
				.bool(b -> b.should(bydestination)
						.should(byregionlevel)
				.minimumShouldMatch("1").filter(bybedroomcode)).build();
		Map<String, Aggregation> map = new HashMap<>();
		Aggregation aggregation = new Aggregation.Builder()
				.terms(new TermsAggregation.Builder().field("bedroomcode").build()).build();
		Aggregation aggregation1 = new Aggregation.Builder()
				.terms(new TermsAggregation.Builder().field("privateoccupancy").build()).build();
		Aggregation aggregation2 = new Aggregation.Builder().terms(new TermsAggregation.Builder().field("vep").build())
				.build();
		map.put("agg_bedroom", aggregation);
		map.put("agg_occupancy", aggregation1);
		map.put("agg_vep", aggregation2);
		List<Query> termquery=prepareQueryList(filterRequest);
		System.out.println("termquery.size():"+termquery.size());
		for (Query query2 : termquery) {
			System.out.println(query2);
		}
		SearchResponse<Void> response5 = elasticsearchClient
				.search(b -> b.index("partner-inv-index-01").size(0).query(q->q.bool(c -> c.should(bydestination)
						.should(byregionlevel)
				.minimumShouldMatch("1").filter(termquery))).aggregations(map), Void.class);

		List<StringTermsBucket> buckets = response5.aggregations().get("agg_bedroom").sterms().buckets().array();
		for (StringTermsBucket stringTermsBucket : buckets) {
			System.out.println("key:"+stringTermsBucket.key()+":"+stringTermsBucket.docCount());
		}
		return null;
	}
	private List<Query> prepareQueryList(FilterRequest filterRequest) {
	      Map<String, String> conditionMap = new HashMap<>();
	      conditionMap.put("bedroomcode", filterRequest.getBedroomcode());
	      conditionMap.put("privateoccupancy", filterRequest.getOccupancy());
	     
	      return conditionMap.entrySet()
	                          .stream()
	                          .filter(entry->isEmpty(entry.getValue()))
	                          .map(entry->termQuery(entry.getKey(), entry.getValue()))
	                          .collect(Collectors.toList());
	     }
	private boolean isEmpty(String value) {
	if(value!=null & !value.isEmpty()) {
		return true;
	}
		return false;
	}

	public static Query termQuery(String field, String value) {
        QueryVariant queryVariant = new TermQuery.Builder()
                .caseInsensitive(true)
                .field(field).value(value).build();
        return new Query(queryVariant);
    }
}
