# **Traffic Management System**

The Traffic Management System is a Java-based console application that simulates and manages traffic lights at a road junction. Users can dynamically add and remove roads, configure intervals for traffic light transitions, and monitor the system in real-time.

---

## **Features**
- **Dynamic Road Management**:
    - Add roads with user-defined names.
    - Delete roads by name.
- **Traffic Light Simulation**:
    - Simulates traffic light transitions using a circular queue.
    - Displays road states (`open`/`closed`) with countdown timers.
- **Real-Time Updates**:
    - Updates road states dynamically each second.
    - Uses ANSI color codes to indicate road statuses:
        - **Green**: Open roads.
        - **Red**: Closed roads.
- **User-Friendly Menu**:
    - Intuitive menu for navigation and interaction.

---

## **Getting Started**

### **Prerequisites**
- **Java**: Ensure Java 17 or higher is installed.
- **Maven**: Install Maven for dependency management and building the project.

### **Clone the Repository**
```bash
git clone <repository-url>
cd traffic-management-system
```

---

## **Building and Running**

### **Build the Project**
Use Maven to clean and build the project:
```bash
mvn clean install
```

### **Run the Application**

#### **Using Maven**
Run the project using Maven's `exec:java` goal:
```bash
mvn exec:java -Dexec.mainClass="org.example.Main"
```

#### **Using the Command Line**
1. **Compile the Project**:
   Compile the Java files into the `target` directory:
   ```bash
   javac -d target/classes src/main/java/org/example/*.java src/main/java/org/example/controller/*.java src/main/java/org/example/io/*.java src/main/java/org/example/model/*.java src/main/java/org/example/timer/*.java
   ```

2. **Run the Project**:
   Execute the `Main` class:
   ```bash
   java -cp target/classes org.example.Main
   ```

---

## **User Guide**

### **Menu Options**
Once the application starts, you'll be greeted with the menu:
```plaintext
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
```

- **1. Add Road**:
    - Input a road name to add it to the system.
- **2. Delete Road**:
    - Input the name of a road to remove it from the system.
- **3. Open System**:
    - View the real-time state of all roads, including their open/closed status and timers.
- **0. Quit**:
    - Exit the application.

---

## **Testing the Application**

Run tests using Maven:
```bash
mvn test
```

Tests include:
- **Unit Tests**: Validate individual components like `TrafficLights` and `SystemTimer`.
- **Acceptance Tests**: Simulate user interactions and verify the system’s end-to-end functionality.

---

## **Project Structure**
```plaintext
src/
├── main/
│   ├── java/
│   │   ├── org/example/
│   │   │   ├── Main.java                        # Entry point of the application
│   │   │   ├── controller/
│   │   │   │   ├── Choice.java                  # Enum for menu options
│   │   │   │   ├── MainMenuController.java      # Handles menu interactions
│   │   │   │   └── DisplayTask.java             # Timer task for real-time updates
│   │   │   ├── io/
│   │   │   │   └── ConsolePrinter.java          # Manages console output
│   │   │   ├── model/
│   │   │   │   ├── TrafficLights.java           # Traffic light management
│   │   │   │   ├── TrafficLightsInterface.java  # Interface for traffic light logic
│   │   │   ├── timer/
│   │   │   │   ├── SystemTimer.java             # Timer for real-time updates
│   │   │   │   └── SystemTimerInterface.java    # Interface for the timer
├── test/
│   ├── java/
│   │   ├── org/example/
│   │   │   ├── TrafficManagementAcceptanceTest.java # Acceptance tests
│   │   │   └── ...                                # Unit tests
```

---

## **How It Works**

### **Road Management**
1. **Adding Roads**:
    - Add a road by providing its name.
    - If the system has reached its road capacity, it will display an error message.
2. **Deleting Roads**:
    - Remove a road by its name.
    - If the road does not exist, the system will display an error message.

### **Traffic Light Simulation**
- Roads transition states (`open`/`closed`) based on a circular queue.
- Real-time updates are displayed every second, showing road states and remaining times.

### **Enhanced Visualization**
- Roads that are **open** are displayed in **green**.
- Roads that are **closed** are displayed in **red**.

---

## **Example Usage**

### **Sample Session** 🚦
```plaintext
Welcome to the traffic management system!
Input the number of roads:
> 3
Input the interval:
> 8
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 1
Input road name:
> First
First added
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 1
Input road name:
> Second
Second added
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 3
Road "First" is open for 8s.
Road "Second" is closed for 8s.
! Press "Enter" to open menu !
```

--- 