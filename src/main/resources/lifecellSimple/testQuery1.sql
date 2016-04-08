/*название склада, Тип оборудования, Модель оборудования, Характеристики, Количество,
Название Фирма владельца оборудования, номер договора, контактное лицо*/
SELECT Warehouse.title, Stuff.`type`, Stuff.model, Stuff.specification, Records.counter,
  Customer.title, Customer.license, Customer.contact
FROM Stuff, Warehouse, Records, Customer
WHERE Records.counter > 0 AND Records.Customer_title = Customer.title
      AND Records.Warehouse_title = Warehouse.title AND Records.Stuff_model = Stuff.model;