package com.pixelTrice.elastic;

import java.util.List;

public class FilterUnit {
private String doc_count_error_upper_bound;
private String sum_other_doc_count;
private List<Bucket> buckets;
public String getDoc_count_error_upper_bound() {
	return doc_count_error_upper_bound;
}
public void setDoc_count_error_upper_bound(String doc_count_error_upper_bound) {
	this.doc_count_error_upper_bound = doc_count_error_upper_bound;
}
public String getSum_other_doc_count() {
	return sum_other_doc_count;
}
public void setSum_other_doc_count(String sum_other_doc_count) {
	this.sum_other_doc_count = sum_other_doc_count;
}
public List<Bucket> getBuckets() {
	return buckets;
}
public void setBuckets(List<Bucket> buckets) {
	this.buckets = buckets;
}
}
