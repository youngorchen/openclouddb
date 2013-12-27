/*
 * Copyright 2012-2015 org.opencloudb.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opencloudb.server.response;

import java.nio.ByteBuffer;

import org.opencloudb.mysql.PreparedStatement;
import org.opencloudb.net.FrontendConnection;
import org.opencloudb.net.mysql.EOFPacket;
import org.opencloudb.net.mysql.FieldPacket;
import org.opencloudb.net.mysql.PreparedOkPacket;

/**
 * @author mycat
 */
public class PreparedStmtResponse {

    public static void response(PreparedStatement pstmt, FrontendConnection c) {
        byte packetId = 0;

        // write preparedOk packet
        PreparedOkPacket preparedOk = new PreparedOkPacket();
        preparedOk.packetId = ++packetId;
        preparedOk.statementId = pstmt.getId();
        preparedOk.columnsNumber = pstmt.getColumnsNumber();
        preparedOk.parametersNumber = pstmt.getParametersNumber();
        ByteBuffer buffer = preparedOk.write(c.allocate(), c);

        // write parameter field packet
        int parametersNumber = preparedOk.parametersNumber;
        if (parametersNumber > 0) {
            for (int i = 0; i < parametersNumber; i++) {
                FieldPacket field = new FieldPacket();
                field.packetId = ++packetId;
                buffer = field.write(buffer, c);
            }
            EOFPacket eof = new EOFPacket();
            eof.packetId = ++packetId;
            buffer = eof.write(buffer, c);
        }

        // write column field packet
        int columnsNumber = preparedOk.columnsNumber;
        if (columnsNumber > 0) {
            for (int i = 0; i < columnsNumber; i++) {
                FieldPacket field = new FieldPacket();
                field.packetId = ++packetId;
                buffer = field.write(buffer, c);
            }
            EOFPacket eof = new EOFPacket();
            eof.packetId = ++packetId;
            buffer = eof.write(buffer, c);
        }

        // send buffer
        c.write(buffer);
    }

}