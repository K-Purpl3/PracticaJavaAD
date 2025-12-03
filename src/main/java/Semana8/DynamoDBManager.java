/*package utils;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DynamoDBManager {
    private static DynamoDbClient dynamoDbClient;
    private static DynamoDbEnhancedClient dynamoDbEnhancedClient;

    public static void initializeClients() {
        Properties properties = new Properties();
        try (InputStream input = DynamoDBManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(input);

            String accessKeyId = properties.getProperty("aws_access_key_id");
            String secretAccessKey = properties.getProperty("aws_secret_access_key");
            String sessionToken = properties.getProperty("aws_session_token");

            AwsCredentials awsCredentials = AwsSessionCredentials.create(accessKeyId, secretAccessKey, sessionToken);

            dynamoDbClient = DynamoDbClient.builder()
                    .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                    .region(Region.US_EAST_1)
                    .build();

            dynamoDbEnhancedClient = DynamoDbEnhancedClient.builder()
                    .dynamoDbClient(dynamoDbClient)
                    .build();

        } catch (IOException ex) {
            System.err.println("Error al cargar el archivo de propiedades: " + ex.getMessage());
        }
    }

    // Métodos para obtener los clientes, inicializándolos si es necesario
    public static DynamoDbClient getDynamoDbClient() {
        if (dynamoDbClient == null) {
            initializeClients();
        }
        return dynamoDbClient;
    }

    public static DynamoDbEnhancedClient getEnhancedClient() {
        if (dynamoDbEnhancedClient == null) {
            initializeClients();
        }
        return dynamoDbEnhancedClient;
    }
}
*/

package Semana8;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDBManager {
    private static DynamoDbClient dynamoDbClient;
    private static DynamoDbEnhancedClient dynamoDbEnhancedClient;

    public static void initializeClients() {

        usarAWSDynamoDBReal();

    }



    private static void usarAWSDynamoDBReal() {
        System.out.println("Conectando a AWS DynamoDB real");

        try {
            java.util.Properties properties = new java.util.Properties();
            try (java.io.InputStream input = DynamoDBManager.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    throw new RuntimeException("No se pudo encontrar db.properties en el classpath");
                }
                properties.load(input);
            }

            String accessKeyId = properties.getProperty("aws_access_key_id");
            String secretAccessKey = properties.getProperty("aws_secret_access_key");
            String sessionToken = properties.getProperty("aws_session_token");

            software.amazon.awssdk.auth.credentials.AwsCredentials awsCredentials;
            if (sessionToken != null && !sessionToken.trim().isEmpty()) {
                awsCredentials = software.amazon.awssdk.auth.credentials.AwsSessionCredentials.create(accessKeyId, secretAccessKey, sessionToken);
            } else {
                awsCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
            }

            dynamoDbClient = DynamoDbClient.builder()
                    .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                    .region(Region.US_EAST_1)
                    .build();

            dynamoDbEnhancedClient = DynamoDbEnhancedClient.builder()
                    .dynamoDbClient(dynamoDbClient)
                    .build();

            System.out.println("Conectado a AWS DynamoDB real");

        } catch (Exception ex) {
            System.err.println("Error al conectar con AWS DynamoDB: " + ex.getMessage());
            //usarDynamoDBLocal();
        }
    }

    public static DynamoDbClient getDynamoDbClient() {
        if (dynamoDbClient == null) {
            initializeClients();
        }
        return dynamoDbClient;
    }

    public static DynamoDbEnhancedClient getEnhancedClient() {
        if (dynamoDbEnhancedClient == null) {
            initializeClients();
        }
        return dynamoDbEnhancedClient;
    }
}