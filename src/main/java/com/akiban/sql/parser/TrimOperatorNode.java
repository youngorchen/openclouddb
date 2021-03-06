/**
 * Copyright 2011-2013 Akiban Technologies, Inc.
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

package com.akiban.sql.parser;

import com.akiban.sql.types.ValueClassName;

public class TrimOperatorNode extends BinaryOperatorNode
{
    @Override
    public void init(Object trimSource, Object trimChar, Object operatorType)
    {   
        BinaryOperatorNode.OperatorType optype = (BinaryOperatorNode.OperatorType)operatorType;
        switch(optype)
        {
            default:     assert false : "TrimOperatorNode.init(trimSource, trimChar, operatorType) called with wrong OperatoryType: " + operatorType;
            case LTRIM:
            case TRIM:
            case RTRIM:  super.init(trimSource,
                                    trimChar,
                                    optype.name(),
                                    optype.name().toLowerCase(),
                                    ValueClassName.StringDataValue,
                                    ValueClassName.StringDataValue);
        }
    }
}
