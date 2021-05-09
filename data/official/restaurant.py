import mariadb
import csv
import sys

restaurant_list = []

try:
    conn = mariadb.connect(
        user="root",
        password="helloks",
        host="192.168.1.132",
        port=3306,
        database="gumibis"
    )
except mariadb.Error as e:
    print(f"Error connecting to MariaDB Platform: {e}")
    sys.exit(1)
    
c = conn.cursor()

with open('res.csv', 'r', encoding='UTF-8-sig') as f:
    restaurant_reader = csv.reader(f)
    next(restaurant_reader, None) #헤더 스킵
    for row in restaurant_reader:
        restaurant_list.append([row[1], row[2], row[4]])


#c.execute('''CREATE TABLE bus_route(rt_uid varchar(8), rt_id varchar(30), rt_name varchar(30))''')

c.executemany('INSERT INTO restaurant(res_name,res_address,res_phone) VALUES (?,?,?)', restaurant_list)

conn.commit()