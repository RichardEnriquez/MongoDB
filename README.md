#  MongoDBDAM
## Instalacion

![Logo mongoDB](https://upload.wikimedia.org/wikipedia/commons/9/93/MongoDB_Logo.svg)
<font size = 4 > Para poder utilizar Mongodb tendremos que descargar e instalarlo.
para descargar pulsar  __[aqui](https://www.mongodb.com/download-center/community)__ </font>

## Librerias

Necesitamos estos librerias para trabajar con mongodb

1. [bson](https://mvnrepository.com/artifact/org.mongodb/bson)
2. [mongodb-driver-core](https://mvnrepository.com/artifact/org.mongodb/mongodb-driver-core)
3. [mongodb-driver](https://mvnrepository.com/artifact/org.mongodb/mongodb-driver)

## Operador

mongodb usa unos operador para hacer consulta o filtrar

https://api.mongodb.com/java/3.0/?com/mongodb/client/model/Filters.html

## Conectar

![image-20200227191113013](https://github.com/RichardEnriquez/MongoDB/blob/master/Image/11.png)

~~~java
// asigna instancia de mongodb
import com.mongodb.MongoClient;
// asigna instancia de bases de datos
import com.mongodb.client.MongoDatabase;
~~~

~~~java
MongoClient client = new MongoClient();
MongoDatabase database = client.getDatabase("default-db");
~~~
~~~java
MongoClientURI uri = new MongoClientURI("mongodb://user1:pwd1@host1/?authSource=db1");
MongoClient mongoClient = new MongoClient(uri);
~~~
~~~java
String user; 
String database; 
char[] password; 
// ...
MongoCredential credential = MongoCredential.createCredential(user, database, password);
MongoClient mongoClient = new MongoClient(new ServerAddress("host1", 27017),
                                         Arrays.asList(credential));
~~~					 
## Insertar

![2](https://github.com/RichardEnriquez/MongoDB/blob/master/Image/2.png)

~~~java
class People
{
    String  name;
    Integer edad;
}
~~~

~~~java
//para converte java a json o json a java
import org.bson.Document;
// asignar Coleccion
import com.mongodb.client.MongoCollection;
~~~

~~~java	
								  //key   value         key    value
collection.insertOne(new Document("name","jose").append("edad",20));
~~~
![3](https://github.com/RichardEnriquez/MongoDB/blob/master/Image/3.png)

## Eliminar

![4](https://github.com/RichardEnriquez/MongoDB/blob/master/Image/4.png)

~~~java
// operator sirve para filtrar
import static com.mongodb.client.model.Filters.*;
~~~
~~~java
// eliminar uno fila que tiene name igual a jose
collection.deleteOne(eq("name","jose"));
// eliminar todo los filas que tiene name igual a jose
collection.deleteMany(eq("name","jose"));
~~~

![5](https://github.com/RichardEnriquez/MongoDB/blob/master/Image/5.png)

## Actualizar

![6](https://github.com/RichardEnriquez/MongoDB/blob/master/Image/6.png)

~~~java
// actualizar elemento que tiene campo nombre igual jose si edad a 21
collection.updateOne(eq("name","jose"),new Document("$set",newDocument("edad","21")));
// actualizar todos elementos que tiene campo nombre igual jose si edad a 21 
collection.updateMany(eq("name","jose"),new Document("$set",newDocument("edad","21")));
    
~~~
![7](https://github.com/RichardEnriquez/MongoDB/blob/master/Image/7.png)
![8](https://github.com/RichardEnriquez/MongoDB/blob/master/Image/8.png)

## Consulta
![9](https://github.com/RichardEnriquez/MongoDB/blob/master/Image/9.png)
![10](https://github.com/RichardEnriquez/MongoDB/blob/master/Image/10.png)

### Cierrar Session
~~~java
MongoClient.close()
~~~
