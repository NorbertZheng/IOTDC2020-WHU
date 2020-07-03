# local dep
import connect_server

ANALYSIS_KEYS = {
    "用药规则": connect_server.get_medicalplan,
    "紧急"    : connect_server.get_emergency
}

def analysis(text):
    for key in ANALYSIS_KEYS:
        if key in text:
            return ANALYSIS_KEYS[key]()
    return text
