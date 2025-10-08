package core.lab_1;

public class CarStrategy implements MovementStrategy {
    public void move(String from, String to) {
        System.out.println("Еду на машине из " + from + " в " + to);
    }

    public String getDescription() {
        return "На машине";
    }
}
