# APIGateway

Build all project by executing a maven clean install command from build folder. This will build all services, service registry and api gateway

Commands to execute the jars

Product Service
java -jar productInformation-0.0.1-SNAPSHOT.jar /share/config/productService/ProductInformation.properties /share/config/productService/log4j2.properties

Recommendation Service
java -jar recommendationService-1.0.jar /share/config/recommendationService/recommendations.txt /share/config/recommendationService/log4j2.properties

Inventory Service
java -jar InventoryDetails-1.0.jar /share/config/inventoryService/InventoryService.properties /share/config/inventoryService/log4j2.properties

Shipping Service 
java -jar ShippingProduct-1.0.jar /share/config/shippingService/log4j2.properties


Customer Service

java -jar CustomerService-1.0.jar /share/config/customerService/CustomerDetails.properties /share/config/customerService/log4j2.properties

API Gateway 

java -jar zuul.jar /share/config/zuul/log4j2.properties

Eureka 

java  -jar EurekaServer-1.0.jar

