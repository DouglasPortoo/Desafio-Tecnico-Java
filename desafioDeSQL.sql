--Quantos clientes temos na base?

SELECT COUNT(*) AS total_clientes
FROM customers;

--Quantos quartos temos cadastrados?

SELECT COUNT(*) AS total_quartos
FROM rooms;

--Quantas reservas em aberto o hotel possui no momento?

SELECT COUNT(*) AS total_reservas_abertas
FROM reservations
WHERE status IN ('SCHEDULED', 'IN_USE');

--Quantos quartos temos vagos no momento?

SELECT COUNT(*) AS total_quartos_vagos
FROM rooms
WHERE id NOT IN (
    SELECT room_id
    FROM reservations
    WHERE status IN ('SCHEDULED', 'IN_USE')
);


--Quantos quartos temos ocupados no momento?

SELECT COUNT(*) AS total_quartos_ocupados
FROM rooms
WHERE id IN (
    SELECT room_id
    FROM reservations
    WHERE status IN ('IN_USE')
);


--Quantas reservas futuras o hotel possui?

SELECT COUNT(*) AS total_reservas_futuras
FROM reservations
WHERE status = 'SCHEDULED';

--Qual o quarto mais caro do hotel?

SELECT room_number, price
FROM rooms
ORDER BY price DESC
LIMIT 1;


--Qual o quarto com maior histórico de cancelamentos?

SELECT room_id, COUNT(*) AS total_cancelamentos
FROM reservations
WHERE status = 'CANCELED'
GROUP BY room_id
ORDER BY total_cancelamentos DESC
LIMIT 1;

-- Liste todos os quartos e a quantidade de clientes que já ocuparam cada um.

SELECT room_number, COUNT(DISTINCT customer_id) AS total_clientes
FROM reservations
GROUP BY room_number;


--Quais são os 3 quartos que possuem um histórico maior de ocupações?

SELECT room_number, COUNT(DISTINCT customer_id) AS total_clientes
FROM reservations
GROUP BY room_number
ORDER BY total_clientes DESC
LIMIT 3;

--No próximo mês, o hotel fará uma promoção para os seus 10 clientes que possuírem maior histórico de reservas e você foi acionado pelo seu time para extrair esta informação do banco de dados. Quem são os 10 clientes?

WITH reservas_por_cliente AS (
    SELECT customer_id, COUNT(*) AS total_reservas
    FROM reservations
    GROUP BY customer_id
)

SELECT customers.name, total_reservas
FROM customers
JOIN reservas_por_cliente ON customers.id = reservas_por_cliente.customer_id
ORDER BY total_reservas DESC
LIMIT 10;




