package com.pixelTrice.elastic;

import java.util.List;

public class ResortResponse {
private List<Resort> resort;
private List<StringBucket> aggBedroom;
private List<StringBucket> aggPrivateocuup;
private List<StringBucket> aggvep;
public List<Resort> getResort() {
	return resort;
}
public void setResort(List<Resort> resort) {
	this.resort = resort;
}
public List<StringBucket> getAggBedroom() {
	return aggBedroom;
}
public void setAggBedroom(List<StringBucket> aggBedroom) {
	this.aggBedroom = aggBedroom;
}
public List<StringBucket> getAggPrivateocuup() {
	return aggPrivateocuup;
}
public void setAggPrivateocuup(List<StringBucket> aggPrivateocuup) {
	this.aggPrivateocuup = aggPrivateocuup;
}
public List<StringBucket> getAggvep() {
	return aggvep;
}
public void setAggvep(List<StringBucket> aggvep) {
	this.aggvep = aggvep;
}



}
