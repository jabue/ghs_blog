package com.kiiik.minafilter;

import java.io.IOException;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;


/**
 * MINA filter that will close sessions that are failing to read outgoing traffic
 * and whose outgoing queue is around 5MB. Use the system property <tt>session.stalled.cap</tt>
 * to set the max number of bytes allowed in the outgoing queue of a session before considering
 * it stalled.
 *
 * @author Gaston Dombiak
 */
public class StalledSessionsFilter extends IoFilterAdapter {

    private static final int bytesCap = 5242880;

    @Override
	public void filterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest)
            throws Exception {
        // Get number of pending requests
        long pendingBytes = session.getScheduledWriteBytes();
        if (pendingBytes > bytesCap) {
            // Get last time we were able to send something to the connected client
            long writeTime = session.getLastWriteTime();
            int pendingRequests = session.getScheduledWriteMessages();
            // Close the session and throw an exception
            session.close();
            throw new IOException("Closing session that seems to be stalled. Preventing OOM");
        }
        // Call next filter (everything is fine)
        super.filterWrite(nextFilter, session, writeRequest);
    }
}
