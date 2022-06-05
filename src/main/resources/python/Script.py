from Adafruit_BMP085 import BMP085
import paho.mqtt.client as mqtt
import time

# ===========================================================================
# Example Code
# ===========================================================================

# Initialise the BMP085 and use STANDARD mode (default value)
# bmp = BMP085(0x77, debug=True)
bmp = BMP085(0x77)

# To specify a different operating mode, uncomment one of the following:
# bmp = BMP085(0x77, 0)  # ULTRALOWPOWER Mode
# bmp = BMP085(0x77, 1)  # STANDARD Mode
# bmp = BMP085(0x77, 2)  # HIRES Mode
# bmp = BMP085(0x77, 3)  # ULTRAHIRES Mode

temp = bmp.readTemperature()

# Read the current barometric pressure level
pressure = bmp.readPressure()

# To calculate altitude based on an estimated mean sea level pressure
# (1013.25 hPa) call the function as follows, but this won't be very accurate
altitude = bmp.readAltitude()

# To specify a more accurate altitude, enter the correct mean sea level
# pressure level.  For example, if the current pressure level is 1023.50 hPa
# enter 102350 since we include two decimal places in the integer value
# altitude = bmp.readAltitude(102350)

def on_connect(client, userdata, flags, rc):
	if rc == 0:
		print ("connected")
	else:
		print("failed")

client = mqtt.Client("control1")
client.username_pw_set("pi", "pi")
client.on_connect = on_connect
client.connect("test.mosquitto.org", port=1883)
client.loop_start()
while True:
	temp = bmp.readTemperature()
	pressure = bmp.readPressure()
	altitude = bmp.readAltitude()
	client.publish("helfer","T" + str(temp) + "/P" + str(pressure) + "/A" + str(altitude), 0)
	time.sleep(5)