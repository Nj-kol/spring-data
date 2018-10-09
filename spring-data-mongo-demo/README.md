
# Spring Data MongoDB


## Dependencies neeeded

**For Spring Boot**
	
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-mongodb</artifactId>
	</dependency>
	
**For Non-Spring Boot**
	
	<dependency>
		<groupId>org.springframework.data</groupId>
		<artifactId>spring-data-mongodb</artifactId>
		<version>1.10.4.RELEASE</version>
	</dependency>

## MongoTemplate and MongoRepository

* The MongoTemplate follows the standard template pattern in Spring 
  and provides a ready to go, basic API to the underlying persistence engine
  
* The repository follows the Spring Data-centric approach and comes with more
  flexible and complex API operations, based on the well-known access patterns
  in all Spring Data projects
  
## MongoTemplate vs MongoRepository

* MongoTemplate provides a lot more control when it comes to querying data
  and what data to pull from database
* Spring Data repositories provide us a convenient outlook on how to fetch data.
* MongoTemplate is database dependent. What this means is, with Spring Data repositories,
  you can easily switch to a different database altogether by simply using a different 
  Spring Data repositories for MySQL or Neo4J or anything else.
  This is not possible with MongoTemplate.
  
## Configuration for MongoTemplate

	@Configuration
	public class MongoDBConfig {

		@Value("${mongouri}")
		private String mongoServerURI;
		@Value("${dbName}")
		private String dbName;
		
		@Bean
		public Mongo mongo() throws Exception {
			Builder options = MongoClientOptions.builder().writeConcern(WriteConcern.ACKNOWLEDGED);
			MongoClientURI uri = new MongoClientURI(mongoServerURI, options);
			return new MongoClient(uri);
		}

		@Bean
		public MongoTemplate mongoTemplate() throws Exception {
			return new MongoTemplate(mongo(), dbName);
		}
	}
	
## Using MongoTemplate

* Insert :

	User user = new User();
	user.setName("Jon");
	mongoTemplate.insert(user);

* Save – Insert :

  - The save operation has save-or-update semantics:
    if an id is present, it performs an update, if not 
	it does an insert.

	User user = new User();
	user.setName("Albert"); 
	mongoTemplate.save(user, "user");

* Save – Update

  - In this particular example, save uses the semantics of update,
    because we use an object with given _id.
  
	User user = mongoTemplate.findOne(Query.query(Criteria.where("name").is("Jack")), User.class);
	user.setName("Jim");
	mongoTemplate.save(user, "user");

* UpdateFirst

  - updateFirst updates the very first document that matches the query
  
	Query query = new Query();
	query.addCriteria(Criteria.where("name").is("Alex"));
	Update update = new Update();
	update.set("name", "James");
	mongoTemplate.updateFirst(query, update, User.class);

* UpdateMulti

  - UpdateMulti updates all document that matches the given query
	  
	Query query = new Query();
	query.addCriteria(Criteria.where("name").is("Eugen"));
	Update update = new Update();
	update.set("name", "Victor");
	mongoTemplate.updateMulti(query, update, User.class)
	 
* FindAndModify

  - This operation works like updateMulti, but it returns the object before it was modified
  
	Query query = new Query();
	query.addCriteria(Criteria.where("name").is("Markus"));
	Update update = new Update();
	update.set("name", "Nick");
	User user = mongoTemplate.findAndModify(query, update, User.class);

	The returned user object has the same values as the initial state in the database.
	However, the new state exists in the database.
	
* Upsert

  - The upsert operate on the find and modify else create semantics:
    if the document is matched, update it, else create a new document
	by combining the query and update object

	Query query = new Query();
	query.addCriteria(Criteria.where("name").is("Markus"));
	Update update = new Update();
	update.set("name", "Nick");
	mongoTemplate.upsert(query, update, User.class);

* Remove

  mongoTemplate.remove(user, "user");

## Using MongoRepository

* Insert :

	User user = new User();
	user.setName("Jon");
	userRepository.insert(user);

* Save – Insert

	User user = new User();
	user.setName("Aaron");
	userRepository.save(user);

* Save – Update

	user = mongoTemplate.findOne(
	  Query.query(Criteria.where("name").is("Jack")), User.class);
	user.setName("Jim");
	userRepository.save(user);

* Delete :

  userRepository.delete(user);

* FindOne :

  userRepository.findOne(user.getId())
  
* Exists :

  boolean isExists = userRepository.exists(user.getId());
  
* FindAll with Sort :

  List<User> users = userRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
  
  The result will be sorted by name in ascending order:
  
* FindAll with Pageable

	Pageable pageableRequest = new PageRequest(0, 1);
	Page<User> page = userRepository.findAll(pageableRequest);
	List<User> users = pages.getContent();

## Annotations

	@Id
	private String id;
	
	The field level @Id annotation can decorate any type, including long and string.

    If the value of the @Id field is not null, it’s stored in the database as-is; 
	otherwise, the converter will assume you want to store an ObjectId in the database 
	(either ObjectId, String or BigInteger work)
	
	@Document
	public class User {
		//
	}
	
	This annotation simply marks a class as being a domain object that needs 
	to be persisted to the database, along with allowing us to choose
	the name of the collection to be used

References
==========
https://www.baeldung.com/spring-data-mongodb-tutorial

https://www.journaldev.com/18156/spring-boot-mongodb

https://github.com/eugenp/tutorials/tree/master/spring-data-mongodb

https://www.baeldung.com/spring-data-mongodb-index-annotations-converter