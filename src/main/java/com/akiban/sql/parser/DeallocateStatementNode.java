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

import com.akiban.sql.StandardException;

/**
 * DEALLOCATE a prepared statement.
 */

public class DeallocateStatementNode extends StatementNode
{
    private String name;

    /**
     * Initializer for an DeallocateStatementNode
     *
     * @param name The name of the cursor
     */

    public void init(Object name) {
        this.name = (String)name;
    }
    
    public String getName() {
        return name;
    }

    /**
     * Fill this node with a deep copy of the given node.
     */
    public void copyFrom(QueryTreeNode node) throws StandardException {
        super.copyFrom(node);
        
        DeallocateStatementNode other = (DeallocateStatementNode)node;
        this.name = other.name;
    }

    /**
     * Convert this object to a String.  See comments in QueryTreeNode.java
     * for how this should be done for tree printing.
     *
     * @return This object as a String
     */

    public String toString() {
        return "name: " + name + "\n" +
            super.toString();
    }

    public String statementToString() {
        return "DEALLOCATE";
    }

}
