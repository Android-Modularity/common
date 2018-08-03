package com.march.common.extensions;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * CreateAt : 2018/8/1
 * Describe :
 *
 * @author chendong
 */
public class MsgBus {

    private static MsgBus sInst;

    public static MsgBus getInst() {
        if (sInst == null) {
            synchronized (MsgBus.class) {
                if (sInst == null) {
                    sInst = new MsgBus();
                }
            }
        }
        return sInst;
    }


    private MsgBus() {
        subscribers = new HashSet<>();
    }

    public interface SubscriberInvoker<D> {
        void invoke(D data);
    }

    public static class Subscriber {

        private String            key;
        private SubscriberInvoker invoker;

        public Subscriber(String key, SubscriberInvoker invoker) {
            this.key = key;
            this.invoker = invoker;
        }
    }

    private Set<Subscriber> subscribers;


    public Subscriber register(Subscriber subscriber) {
        subscribers.add(subscriber);
        return subscriber;
    }

    public Subscriber register(String key, SubscriberInvoker invoker) {
        Subscriber subscriber = new Subscriber(key, invoker);
        subscribers.add(subscriber);
        return subscriber;
    }

    public void unRegister(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void unRegister(String key) {
        synchronized (MsgBus.class) {
            Iterator<Subscriber> iterator = subscribers.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().key.equals(key)) {
                    iterator.remove();
                }
            }
        }
    }

    public void post(String key, Object data) {
        for (Subscriber subscriber : subscribers) {
            if (key.equals(subscriber.key)) {
                subscriber.invoker.invoke(data);
            }
        }
    }
}
