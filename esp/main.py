import ntptime                               
ntptime.settime() 
import machine
import urequests
import json
import utime
import uasyncio
from machine import Pin
from time import sleep
import time
p0 = Pin(3, Pin.OUT) 
p1 = Pin(5, Pin.OUT) 


async def blink(led,buzzer):
    while True:
        buzzer.on()
        led.on()
        await uasyncio.sleep_ms(50)
        led.off()
        await uasyncio.sleep_ms(50)

async def main_blink(led,buzzer):
    req = function()
	if (req.text() >"True"):
        uasyncio.create_task(blink(led,buzzer))
    await uasyncio.sleep_ms(10_000)
    if (led.value() == 1):
	led.off() 
	
	
def function():
	tiempo=fecha()
	id_maquina="ESP-34562"
	info_cliente="JuanRamos-311516622"
	info = json.dumps({"id": id_maquina,"address": info_cliente,"date":tiempo })
	res = urequests.post('http://ec2-100-26-46-25.compute-1.amazonaws.com:5000/input', headers = {'content-type': 'application/json'}, data = info)
	return res
	

def fecha():
    tiempo=utime.localtime()
    fecha=""     
    for i in range(6):
        if i < 3:
            if i<2:
                fecha+=str(tiempo[i])+"-"
            else:
                fecha+=str(tiempo[i])+" "                              
        else:       
            if tiempo[i]<=9:
                   val="0"+str(tiempo[i])
            else:
                   val=str(tiempo[i])
            fecha+=val
            if i < 5:
                fecha+=":"           
            
    return fecha