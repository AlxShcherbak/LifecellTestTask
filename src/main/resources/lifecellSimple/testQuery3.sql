/*название склада, адрес склада, телефон склада, количество товара которое может быть размещено на складе,
количество товара которое размещено на складе, количество товара которое можно еще разместить*/
SELECT Warehouse.title, Warehouse.address,Warehouse.phone, Warehouse.stuffLimit,
  SUM(Records.counter) AS incounter, (Warehouse.stuffLimit - SUM(Records.counter)) AS available
FROM Warehouse
  LEFT JOIN Records ON
                      Records.Warehouse_title = Warehouse.title
GROUP BY Records.Warehouse_title;