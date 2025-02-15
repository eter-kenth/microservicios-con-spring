create database operadorservice

use operadorservice

-- tablas de la base de datos operadorservice

CREATE TABLE Estados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado VARCHAR(50) NOT NULL,
    estado INT(1) NOT NULL
);

-- registro de la tabla estado (carga inicial) 

INSERT INTO Estados (nombre_estado, estado)
VALUES 
('Activo', 1), 
('Inactivo', 0);

-- Tabla de Ventas
CREATE TABLE Ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT,
    cantidad INT,
    estado_id INT,
    fecha TIMESTAMP,
    FOREIGN KEY (estado_id) REFERENCES Estados(id)
);

-- Tabla de Compras
CREATE TABLE Compras (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto_id INT,
    cantidad INT,
    estado_id INT,
    fecha TIMESTAMP,
    FOREIGN KEY (estado_id) REFERENCES Estados(id)
);

-- Tabla de Ingresos de Productos

CREATE TABLE Ingresos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(255),
    cantidad INT,
    estado_id INT,
    fecha TIMESTAMP,
    categoria_id INT,  -- Nuevo campo para almacenar la categoría del producto
    FOREIGN KEY (estado_id) REFERENCES Estados(id)
);

-- procedimientos almacenados 

-- procedimiento para ingresar la venta

DELIMITER $$

CREATE PROCEDURE IngresarVenta(
    IN p_producto_id INT,
    IN p_cantidad INT,
    IN p_fecha DATETIME
)
BEGIN
    DECLARE estado_activo INT;
    
    -- Obtener el ID del estado activo
    SET estado_activo = 1;  -- Asumimos que 1 es el estado "activo"
    
    -- Insertar la venta en la tabla de ventas
    INSERT INTO Ventas (producto_id, cantidad, estado_id, fecha)
    VALUES (p_producto_id, p_cantidad, estado_activo, p_fecha);
    
END$$

DELIMITER ;

-- procedimiento para ingresar compra

DELIMITER $$

CREATE PROCEDURE IngresarCompra(
    IN p_producto_id INT,
    IN p_cantidad INT,
    IN p_fecha DATETIME
)
BEGIN
    DECLARE estado_activo INT;
    
    -- Obtener el ID del estado activo
    SET estado_activo = 1;  -- Asumimos que 1 es el estado "activo"
    
    -- Insertar la compra en la tabla de compras
    INSERT INTO Compras (producto_id, cantidad, estado_id, fecha)
    VALUES (p_producto_id, p_cantidad, estado_activo, p_fecha);
    
END$$

DELIMITER ;

-- procedimiento almanacenada para registrar el ingreso de un nuevo producto al stock

DELIMITER $$

CREATE PROCEDURE IngresarIngreso(
    IN p_nombre_producto VARCHAR(255),
    IN p_cantidad INT,
    IN p_fecha DATETIME,
    IN p_categoria_id INT
)
BEGIN
    DECLARE estado_activo INT;

    -- Obtener el ID del estado activo
    SET estado_activo = 1;  -- Asumimos que 1 es el estado "activo"
    
    -- Insertar el ingreso en la tabla de ingresos
    INSERT INTO Ingresos (nombre_producto, cantidad, estado_id, fecha, categoria_id)
    VALUES (p_nombre_producto, p_cantidad, estado_activo, p_fecha, p_categoria_id);
    
END$$

DELIMITER ;

-- prueba de procedimientos

CALL IngresarVenta(1, 5, '2025-02-14 10:30:00'); -- registro de ingreso de venta

CALL IngresarCompra(2, 10, '2025-02-14 10:30:00'); -- registro de ingreso de compra

CALL IngresarIngreso(3, 15, '2025-02-14 10:30:00'); -- registro de ingreso de nuevo producto al stock

-- select from de las tablas

	
SELECT *FROM Compras
SELECT *FROM Ventas
SELECT *FROM ingresos 

DELETE FROM ventas WHERE id = 1;
ALTER TABLE ventas AUTO_INCREMENT = 1;

use operadorservice




