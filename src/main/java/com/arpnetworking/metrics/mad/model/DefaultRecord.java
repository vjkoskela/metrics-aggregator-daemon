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
package com.arpnetworking.metrics.mad.model;

import com.arpnetworking.commons.builder.OvalBuilder;
import com.arpnetworking.logback.annotations.Loggable;
import com.arpnetworking.tsdcore.model.Key;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.ValidateWithMethod;
import org.joda.time.DateTime;

/**
 * Default implementation of the <code>Record</code> interface.
 *
 * @author Brandon Arp (brandonarp at gmail dot com)
 * @author Ville Koskela (ville dot koskela at inscopemetrics dot com)
 */
@Loggable
public final class DefaultRecord implements Record {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return _id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DateTime getTime() {
        return _time;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImmutableMap<String, ? extends Metric> getMetrics() {
        return _metrics;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImmutableMap<String, String> getAnnotations() {
        return _annotations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Record)) {
            return false;
        }

        final Record otherRecord = (Record) other;
        return Objects.equal(getId(), otherRecord.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", Integer.toHexString(System.identityHashCode(this)))
                .add("Metrics", _metrics)
                .add("Id", _id)
                .add("Time", _time)
                .add("Annotations", _annotations)
                .toString();
    }

    // NOTE: Invoked through reflection by OvalBuilder
    @SuppressWarnings("unused")
    private DefaultRecord(final Builder builder) {
        _metrics = builder._metrics;
        _id = builder._id;
        _time = builder._time;
        _annotations = builder._annotations;
    }

    private final ImmutableMap<String, ? extends Metric> _metrics;
    private final String _id;
    private final DateTime _time;
    private final ImmutableMap<String, String> _annotations;

    /**
     * Implementation of builder pattern for <code>DefaultRecord</code>.
     *
     * @author Ville Koskela (ville dot koskela at inscopemetrics dot com)
     */
    public static final class Builder extends OvalBuilder<Record> {

        /**
         * Public constructor.
         */
        public Builder() {
            super(DefaultRecord::new);
        }

        /**
         * The named metrics <code>ImmutableMap</code>. Cannot be null.
         *
         * @param value The named metrics <code>ImmutableMap</code>.
         * @return This instance of <code>Builder</code>.
         */
        public Builder setMetrics(final ImmutableMap<String, ? extends Metric> value) {
            _metrics = value;
            return this;
        }

        /**
         * The identifier of the record. Cannot be null or empty.
         *
         * @param value The identifier.
         * @return This instance of <code>Builder</code>.
         */
        public Builder setId(final String value) {
            _id = value;
            return this;
        }

        /**
         * The timestamp of the record. Cannot be null.
         *
         * @param value The timestamp.
         * @return This instance of <code>Builder</code>.
         */
        public Builder setTime(final DateTime value) {
            _time = value;
            return this;
        }

        /**
         * The annotations <code>ImmutableMap</code>. Optional. Default is an empty
         * <code>ImmutableMap</code>. Cannot be null.
         *
         * @param value The annotations <code>ImmutableMap</code>.
         * @return This instance of <code>Builder</code>.
         */
        public Builder setAnnotations(final ImmutableMap<String, String> value) {
            _annotations = value;
            return this;
        }

        // Called by OVal reflectively
        @SuppressFBWarnings("UPM_UNCALLED_PRIVATE_METHOD")
        private boolean validateAnnotations(final ImmutableMap<String, String> annotations) {
            if (Strings.isNullOrEmpty(annotations.get(Key.HOST_DIMENSION_KEY))) {
                return false;
            }
            if (Strings.isNullOrEmpty(annotations.get(Key.SERVICE_DIMENSION_KEY))) {
                return false;
            }
            if (Strings.isNullOrEmpty(annotations.get(Key.CLUSTER_DIMENSION_KEY))) {
                return false;
            }
            return true;
        }

        @NotNull
        private ImmutableMap<String, ? extends Metric> _metrics;
        @NotNull
        @NotEmpty
        private String _id;
        @NotNull
        private DateTime _time;
        @NotNull
        @ValidateWithMethod(methodName = "validateAnnotations", parameterType = ImmutableMap.class)
        private ImmutableMap<String, String> _annotations = ImmutableMap.of();
    }
}
