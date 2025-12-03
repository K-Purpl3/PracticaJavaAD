package TestExamen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private Integer id;
    private Integer clienteId;
    private LocalDate fechaPedido;
    private Double total;
}