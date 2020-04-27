#ifndef WEB_REQ_H
#define WEB_REQ_H

/*
    This is the header of web_req module
*/

// dep header
#include <ESP8266WiFi.h>

// funcs
extern bool connect_WIFI(char *ssid, char *passward);
extern void print_GET(char *host, int port);
extern void print_POST(char *host, int port, String data);

#endif

