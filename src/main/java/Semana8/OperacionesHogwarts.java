package Semana8;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class OperacionesHogwarts {

    private final DynamoDbClient dynamoDbClient;
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    public OperacionesHogwarts() {
        this.dynamoDbClient = DynamoDBManager.getDynamoDbClient();
        this.dynamoDbEnhancedClient = DynamoDBManager.getEnhancedClient();
    }

    public void crearTabla(String nombreTabla) {
        if (tablaExiste(nombreTabla)) {
            System.out.println("La tabla ya existe: " + nombreTabla);
            return;
        }

        dynamoDbEnhancedClient.table(nombreTabla, TableSchema.fromBean(CasaHogwarts.class)).createTable();
        System.out.println("Creando tabla: " + nombreTabla);

        DynamoDbWaiter waiter = dynamoDbClient.waiter();
        waiter.waitUntilTableExists(DescribeTableRequest.builder().tableName(nombreTabla).build());
        System.out.println("Tabla '" + nombreTabla + "' creada y activa.");
    }

    private boolean tablaExiste(String nombreTabla) {
        try {
            dynamoDbClient.describeTable(r -> r.tableName(nombreTabla));
            return true;
        } catch (ResourceNotFoundException e) {
            return false;
        }
    }

    public void insertarCasa(CasaHogwarts casa) {
        DynamoDbTable<CasaHogwarts> tabla = dynamoDbEnhancedClient.table("CasasHogwarts", TableSchema.fromBean(CasaHogwarts.class));
        tabla.putItem(casa);
        System.out.println("Casa insertada correctamente: " + casa.getNombre());
    }

    public CasaHogwarts obtenerCasa(String nombreCasa) {
        DynamoDbTable<CasaHogwarts> tabla = dynamoDbEnhancedClient.table("CasasHogwarts", TableSchema.fromBean(CasaHogwarts.class));
        Key key = Key.builder().partitionValue(nombreCasa).build();
        CasaHogwarts casa = tabla.getItem(r -> r.key(key));
        if (casa != null) {
            System.out.println("Casa encontrada: " + casa.getNombre());
        } else {
            System.out.println("Casa no encontrada");
        }
        return casa;
    }

    public void actualizarCasa(CasaHogwarts casa) {
        if (obtenerCasa(casa.getNombre()) != null) {
            DynamoDbTable<CasaHogwarts> tabla = dynamoDbEnhancedClient.table("CasasHogwarts", TableSchema.fromBean(CasaHogwarts.class));
            tabla.updateItem(casa);
            System.out.println("Casa actualizada: " + casa.getNombre());
        } else {
            System.out.println("La casa no existe y no se puede actualizar: " + casa.getNombre());
        }
    }

    public void borrarCasa(String nombre) {
        DynamoDbTable<CasaHogwarts> tabla = dynamoDbEnhancedClient.table("CasasHogwarts", TableSchema.fromBean(CasaHogwarts.class));
        Key key = Key.builder().partitionValue(nombre).build();
        tabla.deleteItem(r -> r.key(key));
        System.out.println("Casa borrada: " + nombre);
    }

    public void imprimirTodasLasCasas() {
        DynamoDbTable<CasaHogwarts> tabla = dynamoDbEnhancedClient.table("CasasHogwarts", TableSchema.fromBean(CasaHogwarts.class));
        ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder().build();
        PageIterable<CasaHogwarts> casas = tabla.scan(scanRequest);
        casas.items().forEach(System.out::println);
    }

    public void poblarDatosEjemplo() {
        // Crear la casa Dracorfan con los datos del PDF
        Profesor jefe = new Profesor();
        jefe.setNombre("Aldous Nightshade");
        jefe.setAsignatura("Magia Arcana");

        Estudiante estudiante1 = new Estudiante();
        estudiante1.setNombre("Selena Shade");
        estudiante1.setCurso(4);
        estudiante1.setFechaNacimiento("2007-05-23");
        estudiante1.setMascota("Umbra");

        Estudiante estudiante2 = new Estudiante();
        estudiante2.setNombre("Theo Blackthorn");
        estudiante2.setCurso(3);
        estudiante2.setFechaNacimiento("2008-10-11");

        Estudiante estudiante3 = new Estudiante();
        estudiante3.setNombre("Luna Ashwood");
        estudiante3.setCurso(5);
        estudiante3.setFechaNacimiento("2006-01-17");
        estudiante3.setMascota("Mistral");

        CasaHogwarts dracorfan = new CasaHogwarts();
        dracorfan.setNombre("Dracorfan");
        dracorfan.setFundador("Leonis Dracorfan");
        dracorfan.setFantasma("La Sombra de Ébano");
        dracorfan.setJefe(jefe);
        dracorfan.setEstudiantes(Arrays.asList(estudiante1, estudiante2, estudiante3));

        insertarCasa(dracorfan);
    }

    public void borrarTabla(String nombreTabla) {
        if (!tablaExiste(nombreTabla)) {
            System.out.println("La tabla no existe: " + nombreTabla);
            return;
        }

        dynamoDbClient.deleteTable(DeleteTableRequest.builder().tableName(nombreTabla).build());
        System.out.println("Eliminando tabla: " + nombreTabla);

        DynamoDbWaiter waiter = dynamoDbClient.waiter();
        waiter.waitUntilTableNotExists(DescribeTableRequest.builder().tableName(nombreTabla).build());
        System.out.println("Tabla '" + nombreTabla + "' eliminada.");
    }

    public void poblarCasasDesdeJson(String nombreArchivo) {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(nombreArchivo)) {
            if (inputStream == null) {
                System.err.println("No se pudo encontrar el archivo: " + nombreArchivo);
                return;
            }

            // Leer el array de casas desde el JSON - FORMA CORREGIDA
            List<CasaHogwarts> casas = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<CasaHogwarts>>() {}
            );

            // Insertar cada casa en DynamoDB
            for (CasaHogwarts casa : casas) {
                insertarCasa(casa);
            }

            System.out.println("Datos cargados desde JSON correctamente");

        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}