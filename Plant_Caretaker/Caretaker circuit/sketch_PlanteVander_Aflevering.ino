// Automated plant overseer.
// This Version uses a photoresistor to determine if the plant has been in the dark for too long. If that is the case it lights a bulb to indicate that the plant should be exposed to sunlight.
// Secondly it relies on a moisture resistor to determine the level of water in the dirt. If the water levels dip below a certain threshhold the board actiavtes a pump that will administer water to the plant.

const int earthSensor = A1;
const int lightSensor = A0;
const int pumpe1 = 8;
const int pumpe2 = 9;
const int RED = 12;

const int darkTimerDuration = 5000;
const int waterTimerDuration = 3000;

unsigned long darkTimer = 0;
unsigned long waterTimer = 0;

int earthVoltage;
int earthPercent;
int lightVoltage;
int lightPercent;

boolean watering;
boolean lightOn;

void setup() {

  Serial.begin(9600);

  pinMode(RED, OUTPUT);
  pinMode(pumpe1, OUTPUT);
  pinMode(pumpe2, OUTPUT);


  digitalWrite(RED, LOW);
  // This will determine which way the pump spins.
  digitalWrite(pumpe1, LOW);
  digitalWrite(pumpe2, HIGH);

  //begin serial monitoring
}

void loop() {
  // Reading from the pins to update the values
  earthVoltage = analogRead(earthSensor); // Read moisture sensor value
  earthPercent = map(earthVoltage, 0, 1023, 0, 100); //changing to percent

  lightVoltage = analogRead(lightSensor); // Read the light sensor value
  lightPercent = map(lightVoltage, 0, 1023, 0, 100);

  // Watering Controls
  if (earthPercent <=  20) {
    watering = true;
    digitalWrite(pumpe1, LOW);
    digitalWrite(pumpe2, HIGH);

  } else if (earthVoltage > 20) {
    digitalWrite(pumpe1, LOW);
    digitalWrite(pumpe2, LOW);
  }

  // Light Controls
  if (lightPercent < 15) {
    if (darkTimer == 0 && !lightOn) {
      darkTimer = darkTimerDuration + millis();
    }
    if (darkTimer < millis()) {
      lightOn = true;
      if (lightOn) {
        digitalWrite(RED, HIGH);
        darkTimer = 0;
      }
    }
  } else {
    lightOn = false;
  }

  Serial.println("Earth Percent:");
  Serial.println(earthPercent);//show serial monitor values
  Serial.println("Light Percent:");
  Serial.println(lightPercent);
  /*
    Serial.println("TIME");
    Serial.println(millis());

    Serial.println("DarkTimer");
    Serial.println(darkTimer);
  */
  Serial.println("lightOn");
  Serial.println(lightOn);
  delay(1000);
}

/*
  void setTimer(char timer, int duration) {

  if (timer == 'W') {
    waterTimer = millis() + duration;
  }
  if (timer == 'D') {
    lightTimer = millis() + duration;
  }
  }
*/
/*
  bool timerOn(int time_limit) {
  if (millis() > time_limit) {
    return false;
  }
  else {
    return true;
  }
  }
*/
