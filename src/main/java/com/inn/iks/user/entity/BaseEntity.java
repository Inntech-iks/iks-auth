package com.inn.iks.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
@MappedSuperclass
public abstract class BaseEntity {
	
	@Column(name = "created_date")
	@JsonProperty("created_date")
	Date createdDate;
	
	@Column(name = "created_by")
	@JsonProperty("created_by")
	String createdBy;
	
	@Column(name = "modified_date")
	@JsonProperty("modified_date")
	Date modifiedDate;
	
	@Column(name = "modified_by")
	@JsonProperty("modified_by")
	String modifiedBy;

}
