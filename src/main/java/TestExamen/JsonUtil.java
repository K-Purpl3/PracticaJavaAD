package TestExamen;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        // Para manejar LocalDate
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT);
    }

    public static String toJson(Object objeto) throws JsonProcessingException {
        return mapper.writeValueAsString(objeto);
    }

    public static <T> T fromJson(String json, Class<T> clase) throws JsonProcessingException {
        return mapper.readValue(json, clase);
    }

    public static void guardarEnArchivo(Object objeto, String rutaArchivo) throws IOException {
        mapper.writeValue(new File(rutaArchivo), objeto);
    }

    public static <T> T leerDesdeArchivo(String rutaArchivo, Class<T> clase) throws IOException {
        return mapper.readValue(new File(rutaArchivo), clase);
    }
}