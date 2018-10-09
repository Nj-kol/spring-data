package com.njkol.cassandra.model;

import java.io.Serializable;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import io.swagger.annotations.ApiModelProperty;

/**
 * The only partition column is first_name and the clustering columns are
 * date_of_birth and person_id
 * 
 * @author Nilanjan Sarkar
 *
 */
@PrimaryKeyClass
public class PersonKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "First Name")
	@PrimaryKeyColumn(name = "first_name", type = PrimaryKeyType.PARTITIONED)
	private String firstName;

	@ApiModelProperty(notes = "Date Of Birth in YYYY-MM-DD")
	@PrimaryKeyColumn(name = "date_of_birth", ordinal = 0, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
	private String dateOfBirth;

	@ApiModelProperty(notes = "Unique ID of the person")
	@PrimaryKeyColumn(name = "person_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
	private String id;

	public PersonKey() {

	}

	public PersonKey(final String firstName, final String dateOfBirth, final String id) {
		this.firstName = firstName;
		this.id = id;
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonKey other = (PersonKey) obj;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}