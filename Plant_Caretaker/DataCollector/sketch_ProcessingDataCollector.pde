import processing.serial.Serial;
import java.util.Scanner;

Serial myPort;
PrintWriter myfile;

void setup() {

  printArray(Serial.list());
  myfile = createWriter("plantData.txt");
  String portName = Serial.list()[0];
  myPort = new Serial(this, portName, 9600);
} // setup end

void draw() {
  
 // myfile.println(arduinoInt);

  // serialEvent(myPort); 
  if (myPort.available() > 0 ) {
    String s = myPort.readStringUntil('\n');

    if (s != null) {
      println(s);
      myfile.println(s);
     
    }
  }
} // draw end

void serialEvent (Serial myPort) {
} // serialEvent end

void keyPressed() {
  myfile.flush(); // writes remaining data
  myfile.close();
}
