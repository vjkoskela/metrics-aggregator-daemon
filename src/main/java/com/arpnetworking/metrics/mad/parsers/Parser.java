/**
 * Copyright 2014 Brandon Arp
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
package com.arpnetworking.metrics.mad.parsers;

import com.arpnetworking.metrics.mad.parsers.exceptions.ParsingException;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Interface for classes which create instances of <code>T</code>.
 *
 * @param <T> The data type to parse.
 *
 * @author Brandon Arp (barp at groupon dot com)
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public interface Parser<T> {

    /**
     * Create a <code>Record</code> from a serialized representation.
     *
     * @param data Some serialized representation of a <code>Record</code>.
     * @return Instance of <code>Record</code> from the data.
     * @throws ParsingException If parsing of the data fails for any reason.
     */
    T parse(byte[] data) throws ParsingException;
}
