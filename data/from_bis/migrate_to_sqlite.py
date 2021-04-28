import mariadb
import json
import sys

route_list = []
timetable_list = []
stop_list = []
link_list = []

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

with open('routes.json', 'r', encoding='UTF-8-sig') as f:
    routes = json.load(f)

with open('stops.json', 'r', encoding='UTF-8-sig') as f:
    stops = json.load(f)

with open('routes_detail.json', 'r', encoding='UTF-8-sig') as f:
    link = json.load(f)

c.execute('''CREATE TABLE bus_route(rt_uid varchar(8), rt_id varchar(30), rt_name varchar(30))''')
c.execute('''CREATE TABLE timetable(tt_routeid varchar(8), tt_starttime time, isholiday boolean)''')
c.execute('''CREATE TABLE bus_stop(st_uid int, st_svcid int, st_name text, st_mapx double, st_mapy double)''')
c.execute('''CREATE TABLE route_link(rt_uid varchar(8), rl_num int, st_uid int)''')

for route_data in routes:
    route_list.append([route_data['uid'], route_data['id'], route_data['name']])
    if 'weekday' in route_data['timetable']:
        for weekday in route_data['timetable']['weekday']:
          timetable_list.append([route_data['uid'], weekday, 0])
    if 'holiday' in route_data['timetable']:
        for holiday in route_data['timetable']['holiday']:
            timetable_list.append([route_data['uid'], holiday, 1])

for stop_data in stops:
    stop_list.append([stop_data['STOP_ID'],stop_data['STOP_SERVICEID'],stop_data['STOP_KNAME'],stop_data['LOCAL_X'],stop_data['LOCAL_Y']])

for link_data in link:
    link_list.append([link_data['uid'], link_data['index'], link_data['bs_uid']])

c.executemany('INSERT INTO bus_route VALUES (?,?,?)', route_list)
c.executemany('INSERT INTO timetable VALUES (?,?,?)', timetable_list)
c.executemany('INSERT INTO bus_stop VALUES (?,?,?,?,?)', stop_list)
c.executemany('INSERT INTO route_link VALUES (?,?,?)', link_list)

conn.commit()