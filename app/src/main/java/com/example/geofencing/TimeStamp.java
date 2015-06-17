package com.example.geofencing;

public class TimeStamp {
	  private long id;
	  private String timestamp;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getComment() {
	    return timestamp;
	  }

	  public void setComment(String comment) {
	    this.timestamp = comment;
	  }

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return timestamp;
	  }
	} 
