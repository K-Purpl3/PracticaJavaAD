package TestExamen;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {
    private Integer pedidoId;
    private Integer productoId;
    private Integer cantidad;
    private Double precioUnitario;
}