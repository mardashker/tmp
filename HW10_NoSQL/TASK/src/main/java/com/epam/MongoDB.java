package com.epam;

import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Arrays;

public class MongoDB {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoDB(String connectionString) {
        mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase("sample_restaurants");
        collection = database.getCollection("restaurants");
    }

    public FindIterable<Document> getAll() {
        return collection.find();
    }

    public FindIterable<Document> getByBorough(String borough) {
        return collection.find(Filters.eq("borough", borough));
    }

    public FindIterable<Document> getByBoroughAndCuisine(String borough, String cuisine) {
        return collection.find(
                Filters.and(
                        Filters.eq("borough", borough),
                        Filters.eq("cuisine", cuisine)
                )
        );
    }

    public AggregateIterable<Document> aggregateTask() {
        return collection.aggregate(Arrays.asList(
                new Document("$group",
                        new Document("_id",
                                new Document("borough", "$borough")
                                        .append("cuisine", "$cuisine")
                        ).append("count", new Document("$sum", 1))
                ),
                new Document("$sort", new Document("count", -1))
        ));
    }

    public static void printMongoIterable(MongoIterable<Document> iterable) {
        try (MongoCursor<Document> cursor = iterable.iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }
    }
}
