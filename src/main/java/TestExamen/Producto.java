package TestExamen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private Integer id;
    private String nombre;
    private Double precio;
    private Integer stock;
    private String categoria;
}