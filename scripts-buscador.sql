create database searchservice

use searchservice

-- creacion de tablas

-- Tabla de categorías
CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE
);

-- Tabla de productos
CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL
);

-- insercion de datos primero en categoria por q la tabla productos tiene una llave foranea q depende de la tabla categoria
INSERT INTO categorias (nombre) VALUES 
('Electronics'),
('Tools'),
('Furniture'),
('Appliances');

-- despues insercion de productos en la tabla productos con los id de la tabla categorias
INSERT INTO productos (nombre, categoria_id, precio, stock) VALUES 
('Laptop', 1, 1000, 50),
('Screwdriver', 2, 25, 50),
('Table', 3, 150, 50),
('Mouse', 1, 25, 50),
('Smartphone', 1, 800, 50),
('Headphones', 1, 200, 50),
('Coffee Maker', 4, 60, 50),
('Office Chair', 3, 120, 50),
('Camera', 1, 500, 50),
('Lamp', 3, 45, 50),
('Power Bank', 1, 35, 50),
('Blender', 4, 80, 50),
('Wrench Set', 2, 55, 50),
('Electric Drill', 2, 150, 50),
('Keyboard', 1, 50, 50),
('Bookshelf', 3, 200, 50);


-- procediemiento almacenados

-- procedimiento almacenado para obtener el stock de un producto
DELIMITER $$

CREATE PROCEDURE obtener_stock(IN producto_id INT)
BEGIN
    SELECT stock 
    FROM productos 
    WHERE id = producto_id;
END $$

DELIMITER ;

-- procedimiento almacenado para hacer una venta lo cual baja el stock 

DELIMITER $$

CREATE PROCEDURE realizar_venta_simple(
    IN producto_id INT, 
    IN cantidad_vender INT,
    OUT resultado INT
)
BEGIN
    -- Realizar la venta y actualizar el stock
    UPDATE productos
    SET stock = stock - cantidad_vender
    WHERE id = producto_id;

    -- Verificar si la actualización afectó alguna fila
    IF ROW_COUNT() > 0 THEN
        -- Retornar 1 si la venta fue exitosa
        SET resultado = 1;
    ELSE
        -- Si no se actualizó el producto, retornar 0
        SET resultado = 0;
    END IF;
END $$

DELIMITER ;

-- procedimiento almacenado para compra lo cual sube el stock 

DELIMITER $$

CREATE PROCEDURE realizar_compra_simple(
    IN producto_id INT, 
    IN cantidad_comprada INT,
    OUT resultado INT
)
BEGIN
    -- Realizar la compra y actualizar el stock
    UPDATE productos
    SET stock = stock + cantidad_comprada
    WHERE id = producto_id;

    -- Verificar si la actualización afectó alguna fila
    IF ROW_COUNT() > 0 THEN
        -- Retornar 1 si la compra fue exitosa
        SET resultado = 1;
    ELSE
        -- Si no se actualizó el producto, retornar 0
        SET resultado = 0;
    END IF;
END $$

DELIMITER ;

-- procedimiento almacenado para agregar un producto al catalogo

DELIMITER $$

CREATE PROCEDURE agregar_producto(
    IN nombre_producto VARCHAR(255),
    IN id_categoria INT,
    IN precio DECIMAL(10, 2),
    IN stock INT,
    OUT resultado INT
)
BEGIN
    -- Insertar el nuevo producto en la tabla producto
    INSERT INTO productos (nombre, categoria_id, precio, stock)
    VALUES (nombre_producto, id_categoria, precio, stock);

    -- Verificar si se insertó el producto correctamente
    IF LAST_INSERT_ID() > 0 THEN
        -- Si el producto se insertó correctamente, retornar 1
        SET resultado = 1;
    ELSE
        -- Si no se insertó, retornar 0
        SET resultado = 0;
    END IF;
END $$

DELIMITER ;

-- procedimiento para ver los productos

DELIMITER $$

CREATE PROCEDURE VerProductos()
BEGIN
    SELECT id, nombre, precio, stock
    FROM productos;
END $$

DELIMITER ;


-- prueba de los procedimientos almacenado

CALL obtener_stock(1);-- obtener stock 

CALL realizar_venta_simple(1, 10, @resultado); -- venta realizada
SELECT @resultado;

CALL realizar_compra_simple(1, 10, @resultado); -- compra realizada
SELECT @resultado;

CALL agregar_producto('HDMI', 2, 150.00, 30, @resultado); -- agregar un nuevo producto al stock
SELECT @resultado;

CALL VerProductos; -- ver los prodcuctos 



-- select from de las dos tablas
SELECT *FROM CATEGORIAS
SELECT *FROM PRODUCTOS
use searchservice
-- 





