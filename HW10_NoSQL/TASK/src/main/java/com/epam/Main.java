package com.epam;

public class Main {
    public static void main(String[] args) {
        String password = System.getenv("mongoDBpass");
        String dbname = "sample_restaurants";

        String connectionString = "mongodb+srv://jowyn:" + password + "@hw10.vdjej.gcp.mongodb.net/" + dbname + "?retryWrites=true&w=majority";
        MongoDB mongoDB = new MongoDB(connectionString);

        var restaurantsInNewYork = mongoDB.getAll();
        MongoDB.printMongoIterable(restaurantsInNewYork);

        var restaurantsInManhattan = mongoDB.getByBorough("Manhattan");
        MongoDB.printMongoIterable(restaurantsInManhattan);

        var restaurantsInManhattanWithPizzas = mongoDB.getByBoroughAndCuisine("Manhattan", "Pizza");
        MongoDB.printMongoIterable(restaurantsInManhattanWithPizzas);

        var aggregateResult = mongoDB.aggregateTask();
        MongoDB.printMongoIterable(aggregateResult);
    }
}