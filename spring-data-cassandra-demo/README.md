
# Spring Data Cassandra

## Dependencies needed

**For Spring Boot**

        <dependency>
        	<groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-cassandra</artifactId>
        </dependency>

**For Non-Spring Boot**

        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-cassandra</artifactId>
            <version>1.5.3.RELEASE</version>
        </dependency>

## Configuration for Cassandra

* BasicCassandraMappingContext – with a default implementation. 
  This is required to map the persistent entities between their 
  object and their persistent formats.

* The default implementation is capable enough, we can use it directly

		@Configuration
		public class CassandraConfig extends AbstractCassandraConfiguration {

			@Value("${cassandra.contactpoints}")
			private String contactPoints;
			@Value("${cassandra.port}")
			private int port;
			@Value("${cassandra.keyspace}")
			private String keySpace;

			@Override
			protected String getKeyspaceName() {
				return keySpace;
			}

			@Bean
			public CassandraClusterFactoryBean cluster() {
				CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
				cluster.setContactPoints(contactPoints);
				cluster.setPort(port);
				return cluster;
			}

			@Bean
			public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
				return new BasicCassandraMappingContext();
			}
		}

## The Cassandra table

*  The only partition column is first_name and the clustering columns are date_of_birth and person_id

		CREATE TABLE people_by_first_name(
		  first_name TEXT,
		  date_of_birth TEXT,
		  person_id TEXT,
		  last_name TEXT,
		  salary DOUBLE,
		  PRIMARY KEY ((first_name), date_of_birth, person_id)
		) WITH CLUSTERING ORDER BY (date_of_birth ASC, person_id DESC);

## Data Mapping

* Using @Table annotation, the bean is directly mapped to a Cassandra data table.
* Also each property is defined as a type of primary key or a simple column ( @Column )

		@Table("people_by_first_name")
		public class Person {

			@PrimaryKey
			private PersonKey key;

			@Column("last_name")
			private String lastName;

			@Column
			private double salary;
		...
		}

* For a composite primary key ( with Clustering columns ), you can isolate the 
  key definition into a separate class. So in the example above, the key 
  definition has been extracted to the PersonKey class, as shown below :

		  @PrimaryKeyClass
		  public class PersonKey implements Serializable {

			private static final long serialVersionUID = 1L;

			@PrimaryKeyColumn(name = "first_name", type = PrimaryKeyType.PARTITIONED)
			private String firstName;

			@PrimaryKeyColumn(name = "date_of_birth", ordinal = 0, type = PrimaryKeyType.CLUSTERED,ordering = Ordering.ASCENDING)
			private String dateOfBirth;

			@PrimaryKeyColumn(name = "person_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
			private String id;

			...
		}

## Using CassandraRepository

* First create a CassandraRepository for your type class

	@Repository
	public interface PersonRepository extends CassandraRepository<Person> {

		List<Person> findByKeyFirstName(final String firstName);

		@Query("select * from people_by_first_name where first_name=?0 and date_of_birth=?1 ")
		List<Person> findByNameAndDOB(final String firstName, final String dob);
	}

* You can use @Query to explicitly declare the query to be run.  
  The ?0 and ?1 refers to the position of arguments in the method 
  signature with which to run the query.

* The CassandraRepository works like any other repository implementation
  in Spring Data having methods as save, findAll, delete etc.

## Use this application

* Run the application and go to : http://localhost:8080/swagger-ui.html

* Post data as : 

	{
	  "key": {
	    "dateOfBirth": "1989-01-30",
	    "firstName": "Malay",
	    "id": "122"
	  },
	  "lastName": "De",
	  "salary": 70000
	}

* Log in to CQLSH and see data : select * from people_by_first_name;

References
==========
https://dzone.com/articles/getting-started-with-spring-data-cassandra

https://www.baeldung.com/spring-data-cassandra-tutorial



