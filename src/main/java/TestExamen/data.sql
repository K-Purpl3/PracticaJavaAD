INSERT INTO productos (nombre, precio, stock, categoria) VALUES
                                                             ('Laptop Gamer', 1200.00, 10, 'Electrónica'),
                                                             ('Mouse Inalámbrico', 25.50, 50, 'Electrónica'),
                                                             ('Teclado Mecánico', 89.99, 30, 'Electrónica'),
                                                             ('Monitor 24"', 199.99, 15, 'Electrónica'),
                                                             ('Cable HDMI', 15.00, 100, 'Accesorios');

INSERT INTO clientes (nombre, email, telefono) VALUES
                                                   ('Juan Pérez', 'juan.perez@email.com', '123456789'),
                                                   ('María García', 'maria.garcia@email.com', '987654321'),
                                                   ('Carlos López', 'carlos.lopez@email.com', '456123789');

INSERT INTO pedidos (cliente_id, fecha_pedido, total) VALUES
                                                          (1, '2024-01-15', 1225.50),
                                                          (2, '2024-01-16', 89.99),
                                                          (3, '2024-01-17', 214.99);

INSERT INTO detalles_pedido (pedido_id, producto_id, cantidad, precio_unitario) VALUES
                                                                                    (1, 1, 1, 1200.00),
                                                                                    (1, 2, 1, 25.50),
                                                                                    (2, 3, 1, 89.99),
                                                                                    (3, 4, 1, 199.99),
                                                                                    (3, 5, 1, 15.00);