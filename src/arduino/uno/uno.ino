#include "uno.h"

// WIFI param
char *ssid     = "Tenda_69F8D8";
char *password = "262621123";
// host param
char *host  = "192.168.0.105";
int port    = 3000;
String post_data = "post_data=123";

void setup() {
    Serial.begin(115200);
    Serial.println();

    // connect to WIFI
    connect_WIFI(ssid, password);
}


void loop() {
    print_POST(host, port, post_data);
    // print_GET(host, port);
    delay(5000);
}
