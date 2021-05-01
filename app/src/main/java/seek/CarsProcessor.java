package seek;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CarsProcessor implements Processor {

    private Map<String, Integer> input;

    public CarsProcessor() {

    }

    public CarsProcessor(Map<String, Integer> input) {
        this.input = input;
    }

    @Override
    public void execute() {
        System.out.println("===== Total cars: ===== \n" + getTotalCars(input));

        System.out.println("===== Total cars by date: =====");
        for (Map.Entry<String, Integer> entry : getTotalCarsByDate(input).entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("===== Least number of cars: =====");
        for (Map.Entry<String, Integer> entry : threeHalfHourLeastCars(input).entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("===== Least number of cars per 1.5 hours: =====");
        for (Map.Entry<String, Integer> entry : bestOneandHalfHourCars(input).entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public Map<String, Integer> getTotalCarsByDate(Map<String, Integer> halfHourCarRecord) {
        Map<String, Integer> totalCarsByDateMap = new HashMap<>();
        halfHourCarRecord.forEach((key, value) -> {
            var date = LocalDateTime.parse(key).toLocalDate().toString();
            var numberOfCars = value;
            if (totalCarsByDateMap.containsKey(date)) {
                numberOfCars += totalCarsByDateMap.get(date);
            }
            totalCarsByDateMap.put(date, numberOfCars);
        });

        return totalCarsByDateMap;
    }

    public Integer getTotalCars(Map<String, Integer> halfHourCarRecord) {
        return halfHourCarRecord.values().stream().reduce(0, (carTotals, numberOfCars) -> carTotals + numberOfCars);
    }

    public Map<String, Integer> threeHalfHourLeastCars(Map<String, Integer> halfHourCarRecord) {
        return halfHourCarRecord.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(3)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    public Map<String, Integer> bestOneandHalfHourCars(Map<String, Integer> halfHourCarRecord) {
        var keys = halfHourCarRecord.keySet().toArray();
        var values = halfHourCarRecord.values().toArray();

        int lowestcars = 0;
        int index = 0;
        for (var i = 0; i < values.length - 2; i++) {
            int cars = (int) values[i] + (int) values[i + 1] + (int) values[i + 2];
            if (i == 0 || cars < lowestcars) {
                lowestcars = cars;
                index = i;
            }
        }
        Map<String, Integer> result = new LinkedHashMap<>();
        result.put(String.valueOf(keys[index]), halfHourCarRecord.get(keys[index]));
        result.put(String.valueOf(keys[index + 1]), halfHourCarRecord.get(keys[index + 1]));
        result.put(String.valueOf(keys[index + 2]), halfHourCarRecord.get(keys[index + 2]));

        return result;
    }
}
