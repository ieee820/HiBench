/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intel.hibench.streambench.storm.trident;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.*;
import backtype.storm.topology.base.BaseRichSpout;

import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.FilterNull;
import storm.trident.operation.builtin.MapGet;
import storm.trident.operation.builtin.Sum;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.testing.MemoryMapState;
import storm.trident.tuple.TridentTuple;
import storm.kafka.trident.*;


import com.intel.hibench.streambench.storm.util.*;
import com.intel.hibench.streambench.storm.spout.*;
import com.intel.hibench.streambench.storm.topologies.*;

public class Sketch extends BaseFunction {
  private int fieldIndex;
  private String separator;

  public Sketch(int fieldIndex, String separator) {
    this.fieldIndex = fieldIndex;
    this.separator = separator;
  }

  @Override
  public void execute(TridentTuple tuple, TridentCollector collector){
    String record = tuple.getString(0);
    String[] fields = record.split(separator);
    if (fields.length > fieldIndex) 
      collector.emit(new Values(fields[fieldIndex]));
  }
}
