/*
 * Distributed as part of c3p0 v.0.9.5-pre4
 *
 * Copyright (C) 2013 Machinery For Change, Inc.
 *
 * Author: Steve Waldman <swaldman@mchange.com>
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of EITHER:
 *
 *     1) The GNU Lesser General Public License (LGPL), version 2.1, as 
 *        published by the Free Software Foundation
 *
 * OR
 *
 *     2) The Eclipse Public License (EPL), version 1.0
 *
 * You may choose which license to accept if you wish to redistribute
 * or modify this work. You may offer derivatives of this work
 * under the license you have chosen, or you may provide the same
 * choice of license which you have been offered here.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received copies of both LGPL v2.1 and EPL v1.0
 * along with this software; see the files LICENSE-EPL and LICENSE-LGPL.
 * If not, the text of these licenses are currently available at
 *
 * LGPL v2.1: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *  EPL v1.0: http://www.eclipse.org/org/documents/epl-v10.php 
 * 
 */

package com.mchange.v2.c3p0;

import java.sql.Connection;

/**
 *  <p>Having expanded the once-simple ConnectionTester interface to support both
 *  user-specified queries and return of root cause Exceptions (via an out-param),
 *  this interface has grown unnecessarily complex.</p>
 *  
 *  <p>If you wish to implement a custom Connection tester, here is the simple
 *  way to do it</p>
 *  
 *  <ol>
 *    <li>Extend {@link com.mchange.v2.c3p0.AbstractConnectionTester}</li>
 *    <li>Override only the two abstract methods</li>
 *    <ul>
 *       <li><tt>public int activeCheckConnection(Connection c, String preferredTestQuery, Throwable[] rootCauseOutParamHolder)</tt></li>
 *       <li><tt>public int statusOnException(Connection c, Throwable t, String preferredTestQuery, Throwable[] rootCauseOutParamHolder)</tt></li>
 *    </ul>
 *    <li>Take care to ensure that your methods are defined to allow <tt>preferredTestQuery</tt> and 
 *    <tt>rootCauseOutParamHolder</tt> to be <tt>null</tt>.</li>
 *  </ol>
 *  
 *  <p>Parameter <tt>rootCauseOutParamHolder</tt> is an optional parameter, which if supplied, will be a Throwable array whose size
 *  it at least one. If a Connection test fails because of some Exception, the Connection tester may set this Exception as the
 *  zero-th element of the array to provide information about why and how the test failed.</p> 
 */
public abstract class AbstractConnectionTester implements UnifiedConnectionTester
{
    /**
     *  Override, but remember that <tt>preferredTestQuery</tt> and <tt>rootCauseOutParamHolder</tt>
     *  can be null.
     */
    public abstract int activeCheckConnection(Connection c, String preferredTestQuery, Throwable[] rootCauseOutParamHolder);

    /**
     *  Override, but remember that <tt>preferredTestQuery</tt> and <tt>rootCauseOutParamHolder</tt>
     *  can be null.
     */
    public abstract int statusOnException(Connection c, Throwable t, String preferredTestQuery, Throwable[] rootCauseOutParamHolder);

    //usually just leave the rest of these as-is
    public int activeCheckConnection(Connection c)
    { return activeCheckConnection( c, null, null); }

    public int activeCheckConnection(Connection c, Throwable[] rootCauseOutParamHolder)
    { return activeCheckConnection( c, null, rootCauseOutParamHolder); }

    public int activeCheckConnection(Connection c, String preferredTestQuery)
    { return activeCheckConnection( c, preferredTestQuery, null); }

    public int statusOnException(Connection c, Throwable t)
    { return statusOnException( c, t, null, null); }

    public int statusOnException(Connection c, Throwable t, Throwable[] rootCauseOutParamHolder)
    { return statusOnException( c, t, null, rootCauseOutParamHolder); }

    public int statusOnException(Connection c, Throwable t, String preferredTestQuery)
    { return statusOnException( c, t, preferredTestQuery, null); }
}
