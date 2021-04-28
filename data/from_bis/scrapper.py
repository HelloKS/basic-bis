#!/usr/local/bin/python3
#-*- coding: utf-8 -*-

from bs4 import BeautifulSoup
import requests
import json
import time

route_data = []
route_detail_data = []

def scrape_bus_route():

    params = {'mapType': 'ROUTE', 'searchType': 0, 'route_id': '', 'searchKeyword': ''}
    req = requests.post("http://bis.gumi.go.kr/map/SearchBusRoute.do", data=params)

    req.raise_for_status()

    soup = BeautifulSoup(req.text, 'html.parser')
    # dl routeList
    routes_dl = soup.find_all(class_='routeList')

    for dl in routes_dl:
        spans = dl.dt.find_all('span')

        route = dict()
        route['uid'] = dl['id']
        if route['uid'] == '':
            continue
        route['id'] = " ".join(spans[0].text.split()).strip()
        route['name'] =  " ".join(spans[1].text.split()).strip().replace('( ', '').replace(' )', '')
        route['timetable'] = scrape_bus_timetable(dl['id'])

        print("Saving UID " + route['id'] + "(" + route['uid'] + ")")
        route_data.append(route)

        #time.sleep(0.1)

    with open('routes.json', 'w', encoding='UTF-8-sig') as f:
        json.dump(route_data, f, ensure_ascii=False)

def scrape_bus_timetable(uid):
    #print("Processing UID: " + str(uid))
    url = "http://bis.gumi.go.kr/city_bus/time_table.do?route_id=" + str(uid)
    req = requests.get(url)

    req.raise_for_status()

    soup = BeautifulSoup(req.text, "html.parser")
    timetable = soup.find_all(class_='common_tb')

    route_timetable = {}
    for idx_timetable, table in enumerate(timetable):
        times = table.find_all(class_='cntr')
        timetable_of_type = []
        for idx_times, td in enumerate(times):
            if idx_times % 2 != 0:
                timetable_of_type.append(td.text.strip())
        if idx_timetable == 0:
            route_timetable['weekday'] = timetable_of_type
        elif idx_timetable == 1:
            route_timetable['holiday'] = timetable_of_type
    return route_timetable

def scrape_bus_stop():
    params = {'mapType': 'STOP', 'searchType': 'S', 'searchKeyword': r'%%'}
    req = requests.post("http://bis.gumi.go.kr/map/AjaxStopList.do", data=params)

    req.raise_for_status()

    with open('stops.json', 'w', encoding='UTF-8-sig') as f:
        f.write(req.text)

def scrape_bus_route_detail():
    url = "http://bis.gumi.go.kr/map/SearchBusRouteResult.do"

    for route in route_data:
        params = {'mapType': 'ROUTE', 'searchType': 1, 'route_id': route['uid'], 'searchKeyword': ''}
        req = requests.post(url, data=params)

        print("Saving UID ", route['uid'])

        req.raise_for_status()
        soup = BeautifulSoup(req.text, 'html.parser')
        detaildiv = soup.find_all("div", class_="slist")
        detail = detaildiv[0].find_all('li')
        
        for idx, bus_stop in enumerate(detail):
            spans = bus_stop.find_all('span')
            bs = dict()
            bs['uid'] = route['uid']
            bs['index'] = idx
            bs['bs_uid'] = bus_stop['id']
            print(bs)
            route_detail_data.append(bs)
            #time.sleep(0.1)
    
    with open('routes_detail.json', 'w', encoding='UTF-8-sig') as f:
        json.dump(route_detail_data, f, ensure_ascii=False)

def main():
    print("[Gumi BIS Scrapper v2]")
    print("Dumping routes")
    scrape_bus_route()
    print("Dumping route detail")
    scrape_bus_route_detail()
    print("Dumping stops")
    scrape_bus_stop()
    
if __name__ == "__main__":
    main()