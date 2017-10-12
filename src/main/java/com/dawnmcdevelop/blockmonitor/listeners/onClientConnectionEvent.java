package com.dawnmcdevelop.blockmonitor.listeners;

import com.dawnmcdevelop.blockmonitor.BlockMonitor;
import com.dawnmcdevelop.blockmonitor.api.Record;
import com.dawnmcdevelop.blockmonitor.api.RecordBuilder;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by johnfg10 on 04/06/2017.
 */
public class onClientConnectionEvent {
    @Listener(order = Order.LAST)
    public void clientConnectionEvent(ClientConnectionEvent event){
        RecordBuilder recordBuilder = new RecordBuilder(event);
        BlockMonitor.executor.execute(recordBuilder);
    }
}