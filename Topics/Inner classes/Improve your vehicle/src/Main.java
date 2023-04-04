
class Vehicle {

    private String name;

    public Vehicle(String name) {
        this.name = name;
    }

    // create constructor

    class Engine {
        int horsePower;
        // create constructor

        void start() {
            System.out.println("RRRrrrrrrr....");
        }

        // create method printHorsePower()
        void printHorsePower() {
            System.out.println("Vehicle " + name + " has "
                    + horsePower + " horsepower.");
        }

        public Engine(int horsePower) {
            this.horsePower = horsePower;
        }

    }
}

// this code should work
class EnjoyVehicle {

    public static void main(String[] args) {

        Vehicle vehicle = new Vehicle("Dixi");
        Vehicle.Engine engine = vehicle.new Engine(20);
        engine.printHorsePower();
    }
}