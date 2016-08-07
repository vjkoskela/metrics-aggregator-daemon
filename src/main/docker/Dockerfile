# Copyright 2016 Inscope Metrics, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

FROM java:8u92-jre-alpine

MAINTAINER arpnetworking

EXPOSE 7090
WORKDIR /opt/mad

# Logging configuration
ENV LOGBACK_FILE="/opt/mad/config/logback.xml"
ENV LOGBACK_CONFIG="-Dlogback.configurationFile=${LOGBACK_FILE}"

# JVM configuration
ENV ADDITIONAL_JAVA_OPTS=""
ENV JAVA_OPTS="${LOGBACK_CONFIG} ${ADDITIONAL_JAVA_OPTS}"

# MAD configuration
ENV APP_CONFIG_FILE="/opt/mad/config/config.json"
ENV ADDITIONAL_APP_PARAMS=""
ENV APP_PARAMS="${APP_CONFIG_FILE} ${ADDITIONAL_APP_PARAMS}"

# Build
RUN mkdir -p /opt/mad/logs
RUN mkdir -p /opt/mad/config/pipelines
ADD deps/* /opt/mad/lib/
ADD bin/* /opt/mad/bin/
ADD config/* /opt/mad/config/
ADD lib/* /opt/mad/lib/

# Entry point
CMD /opt/mad/bin/mad ${APP_PARAMS}