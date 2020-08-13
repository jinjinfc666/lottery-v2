package com.jll.game.mesqueue.kafka;

import org.springframework.kafka.listener.MessageListener;

public interface KafkaConsumer extends MessageListener<Integer, String>{

}
