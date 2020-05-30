package utils;

import models.Subscription;

import java.util.HashMap;
import java.util.HashSet;

public class RoutingTable {

    private HashMap<String, HashSet<Subscription>> subscriptionMap;

    public RoutingTable() {
        this.subscriptionMap = new HashMap<String, HashSet<Subscription>>();
    }

    public boolean subscriptionExists(String subscriberId, Subscription subscription) {
        HashSet<Subscription> subscriptions = subscriptionMap.get(subscriberId);
        if (subscriptions == null) {
            return false;
        }
        return subscriptions.contains(subscription);
    }

    public void addSubscription(String subscriberId, Subscription subscription) {
        HashSet<Subscription> subscriptions = subscriptionMap.get(subscriberId);
        if (subscriptions == null) {
            subscriptions = new HashSet<Subscription>();
            subscriptionMap.put(subscriberId, subscriptions);
        }
        subscriptions.add(subscription);
    }
}
