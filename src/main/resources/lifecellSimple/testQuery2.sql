/*Запрос, который возвращает данные о там какие фирмы разместили оборудования на наших сладах
и в каком количестве (название фирмы, количество оборудования)*/
SELECT Customer.title, SUM(Records.counter)
FROM Records
  RIGHT JOIN Customer ON
                        Customer.title = Records.Customer_title
GROUP BY Records.Customer_title;