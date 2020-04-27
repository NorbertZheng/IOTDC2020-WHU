#include "web_req.h"

bool connect_WIFI(char *ssid, char *password) {
    Serial.printf("Connecting to %s ", ssid);
    WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED) {
        delay(1000);
        Serial.print(".");
    }
    Serial.println(" connected");
    return true;
}

void print_GET(char *host, int port) {
    WiFiClient client;

    Serial.printf("\n[Connecting to %s ... ", host);
    if (client.connect(host, port)) {
        Serial.println("connected]");

        Serial.println("[Sending a request]");
        client.print(
            String("GET /") + " HTTP/1.1\r\n" +
            "Host: " + host + "\r\n" +
            "Connection: close\r\n" +
            "\r\n"
        );

        Serial.println("[Response:]");
        while (client.connected()) {
            if (client.available()) {
                String line = client.readStringUntil('\n');
                Serial.println(line);
            }
        }
        client.stop();
        Serial.println("\n[Disconnected]");
    } else {
        Serial.println("connection failed!]");
        client.stop();
    }
}

void print_POST(char *host, int port, String data) {
    WiFiClient client;

    if (!client.connect(host, port)) {
        Serial.println("connection failed");
        return;
    }

    // prepare to send post_data
    if (data.length()) {
        int length = data.length();
      
        String postRequest =(String)("POST ") + "/ HTTP/1.1\r\n" +
            "Content-Type: application/json;charset=utf-8\r\n" +
            "Host: " + host + ":" + port + "\r\n" +          
            "Content-Length: " + length + "\r\n" +
            "Connection: Keep Alive\r\n\r\n" +
            data+"\r\n";
        Serial.println(postRequest);
        client.print(postRequest);
    }

    // display response
    Serial.println("[Response:]");
    while (client.connected()) {
        if (client.available()) {
            String line = client.readStringUntil('\n');
            Serial.println(line);
        }
    }
    client.stop();
}

