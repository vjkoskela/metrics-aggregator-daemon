/**
 * Copyright 2014 Groupon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arpnetworking.metrics.proxy.models.protocol.v1;

import com.arpnetworking.commons.jackson.databind.ObjectMapperFactory;
import com.arpnetworking.metrics.Metrics;
import com.arpnetworking.metrics.proxy.actors.Connection;
import com.arpnetworking.metrics.proxy.models.messages.Command;
import com.arpnetworking.metrics.proxy.models.protocol.MessagesProcessor;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Processes heartbeat messages.
 *
 * @author Brandon Arp (brandonarp at gmail dot com)
 */
public class HeartbeatMessagesProcessor implements MessagesProcessor {
    /**
     * Public constructor.
     *
     * @param connection ConnectionContext where processing takes place
     */
    public HeartbeatMessagesProcessor(final Connection connection) {
        _connection = connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean handleMessage(final Object message) {
        if (message instanceof Command) {
            _metrics.incrementCounter(HEARTBEAT_COUNTER);
            //TODO(barp): Map with a POJO mapper [MAI-184]
            final Command command = (Command) message;
            final ObjectNode commandNode = (ObjectNode) command.getCommand();
            final String commandString = commandNode.get("command").asText();
            if (COMMAND_HEARTBEAT.equals(commandString)) {
                _connection.send(OK_RESPONSE);
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeMetrics(final Metrics metrics) {
        _metrics = metrics;
        metrics.resetCounter(HEARTBEAT_COUNTER);
    }

    private Metrics _metrics;
    private final Connection _connection;

    private static final String COMMAND_HEARTBEAT = "heartbeat";
    private static final ObjectNode OK_RESPONSE =
            ObjectMapperFactory.getInstance().getNodeFactory().objectNode().put("response", "ok");

    private static final String HEARTBEAT_COUNTER = "actors/connection/command/heartbeat";
}
